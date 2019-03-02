package fms_server.services;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import fms_server.dao.*;
import fms_server.logging.Logger;
import fms_server.models.Event;
import fms_server.models.Person;
import fms_server.models.User;
import fms_server.requests.FillRequest;
import fms_server.results.FillResult;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Fill service class
 */
public class FillService extends Service {
    private EventDAO eventDAO;
    private UserDAO userDAO;
    private PersonDAO personDAO;
    /**
     * Fill service constructor
     * @param dao DataAccessObject for database access
     */
    public FillService(IDatabaseAccessObject dao) {
        super(dao);
    }

    public FillService(EventDAO eventDAO, UserDAO userDAO, PersonDAO personDAO) {
        super(eventDAO);
        this.personDAO = personDAO;
        this.eventDAO = eventDAO;
        this.userDAO = userDAO;
    }

    /**
     * Will generate and fill database with random information. TODO get spouse if already exists
     * @param request FillRequest
     * @return whether fill was successful
     */
    public FillResult fill(FillRequest request) throws DataBaseException {
        List<Person> ancestors;
        List<Event> events = new ArrayList<>();
        try {
            User user = userDAO.getUserByUsername(request.getUserName());
            Person person = personDAO.get(user.getId());
            Person spouse = Generator.generateSpouse(person);
            ancestors = Generator.generateGenerations(Arrays.asList(person, spouse), request.getGenerations(), events, 2019);
            events = Generator.generateEvents(ancestors);
            events.addAll(Generator.generateEvents(person));
            events.addAll(Generator.generateEvents(spouse));
            personDAO.add(spouse);
            personDAO.addAll(ancestors);
            personDAO.update(person);
            eventDAO.addAll(events);
        } catch (ModelNotFoundException e) {
//            e.printStackTrace();
            Logger.error("Cannot find user", e);
            return new FillResult(false, "Unable to generate for user, user may not exist");
        }
        return new FillResult(true, "Successfully added " + ancestors.size() + " persons and " + events.size() + " events to database");
    }

    /**
     * This generates the events and ancestors for a person
     */
    public static class Generator {
        private static String[] firstMaleNames = {"Liam", "Noah", "William", "James", "Logan", "Benjamin", "Mason", "Elijah", "Oliver", "Jacob", "Lucas", "Michael","Alexander","Ethan","Daniel","Matthew","Aiden","Henry","Joseph","Jackson"};
        private static String[] firstFemaleNames = {"Emma","Olivia","Ava","Isabella","Sophia","Mia","Charlotte","Amelia","Evelyn","Abigail","Harper","Emily","Elizabeth","Avery","Sofia","Ella","Madison","Scarlett","Victoria","Aria"};
        private static String[] lastNames = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Miller", "Davis", "Garcia", "Rodriguez", "Wilson"};
        private static String[] eventTypes = {"birth", "travel", "death"};
        private static List<LinkedTreeMap<String, Object>> locations;

        static {
            Type type = new TypeToken<LinkedTreeMap<String, List<LinkedTreeMap<String, Object>>>>(){}.getType();
            try {
                LinkedTreeMap<String, List<LinkedTreeMap<String, Object>>> data = (new Gson()).fromJson(new String(Files.readAllBytes(Paths.get("res/json/locations.json"))), type);
                locations = data.get("data");
                Logger.fine(Arrays.toString(locations.toArray()));
            } catch (Exception e) {
                Logger.error("Unable to get location data", e);
            }
        }

        static Person generateMalePerson() {
            Random random = new Random();
            String firstName = firstMaleNames[random.nextInt(firstMaleNames.length - 1)];
            return generatePerson(firstName, "m", random);
        }

        static Person generateFemalePerson() {
            Random random = new Random();
            String firstName = firstFemaleNames[random.nextInt(firstFemaleNames.length - 1)];
            return generatePerson(firstName, "f", random);
        }

        private static Person generatePerson(String firstname, String gender, Random random) {
            return new Person(
                    UUID.randomUUID().toString(),
                    null,
                    firstname,
                    lastNames[random.nextInt(lastNames.length - 1)],
                    gender,
                    null,
                    null,
                    null
            );
        }

        private static Person getSpouse(Person person, List<Person> persons) {
            if (person.getSpouseID() == null)
                return null;
           Person spouse = null;
           for (Person sp : persons)
               if (sp.getId().equals(person.getSpouseID()))
                   spouse = sp;
           return spouse;
        }

        public static Person generateSpouse(Person person) {
            Person spouse = person.getSpouseID() == null? person.getGender().equals("m") ? generateFemalePerson() : generateMalePerson() : null;
            if (spouse != null) {
                person.setSpouseID(spouse.getId());
                spouse.setSpouseID(person.getId());
            }
            return spouse;
        }

        private static HashMap<String, Person> generateImmediateGeneration(Person person) {
            if (person == null)
                return new HashMap<>();
            Person mother = generateFemalePerson();
            Person father = generateMalePerson();
            Person spouse = generateSpouse(person);
            person.setMotherID(mother.getId());
            person.setFatherID(father.getId());
            father.setSpouseID(mother.getId());
            mother.setSpouseID(father.getId());
            if (spouse != null)
                person.setSpouseID(spouse.getId());
            HashMap<String, Person> personList = new HashMap<>();
            personList.put("mother", mother);
            personList.put("father", father);
            if (spouse != null)
                personList.put("spouse", spouse);
            return personList;
        }

        public static List<Person> generateGenerations(List<Person> people, int depth, List<Event> events, int year) {
            return gGDive(people, depth, 0, events, year);
        }

        private static List<Person> gGDive(List<Person> people, int depth, int current, List<Event> events, int year) {
            current++;
            if (current > depth)
                return new ArrayList<>();
            List<Person> generated = new ArrayList<>();
            for (Person person : people) {
                HashMap<String, Person> peopleToAdd = generateImmediateGeneration(person);
                events.addAll(generateEventsForCouple(peopleToAdd, year));
                generated.addAll(peopleToAdd.values());
            }
            year -= 50;
            generated.addAll(gGDive(generated, depth, current, events, year));
            return generated;
        }

        public static List<Event> generateEventsForCouple(HashMap<String, Person> couples, int year) {
            List<Event> events = new ArrayList<>();
            Random random = new Random();
            Map<String, Object> loc = locations.get(random.nextInt(locations.size() - 1));

            loc = locations.get(random.nextInt(locations.size() - 1));
            Event birth = new Event(
                    UUID.randomUUID().toString(),
                    null,
                    couples.get("mother").getPersonID(),
                    (double) loc.getOrDefault("latitude", -111.6509753),
                    (double) loc.getOrDefault("longitude", 40.245769),
                    String.valueOf(loc.getOrDefault("country", "USA")),
                    String.valueOf(loc.getOrDefault("city", "Provo")),
                    "birth",
                    random.nextInt(5) + year - 30
            );

            loc = locations.get(random.nextInt(locations.size() - 1));
            Event birth1 = new Event(
                    UUID.randomUUID().toString(),
                    null,
                    couples.get("father").getPersonID(),
                    (double) loc.getOrDefault("latitude", -111.6509753),
                    (double) loc.getOrDefault("longitude", 40.245769),
                    String.valueOf(loc.getOrDefault("country", "USA")),
                    String.valueOf(loc.getOrDefault("city", "Provo")),
                    "birth",
                    random.nextInt(5) + year - 30
            );

            loc = locations.get(random.nextInt(locations.size() - 1));
            Event travel1 = new Event(
                    UUID.randomUUID().toString(),
                    null,
                    couples.get("father").getPersonID(),
                    (double) loc.getOrDefault("latitude", -111.6509753),
                    (double) loc.getOrDefault("longitude", 40.245769),
                    String.valueOf(loc.getOrDefault("country", "USA")),
                    String.valueOf(loc.getOrDefault("city", "Provo")),
                    "travel",
                    random.nextInt(5) + year - 20
            );

            loc = locations.get(random.nextInt(locations.size() - 1));
            Event travel2 = new Event(
                    UUID.randomUUID().toString(),
                    null,
                    couples.get("mother").getPersonID(),
                    (double) loc.getOrDefault("latitude", -111.6509753),
                    (double) loc.getOrDefault("longitude", 40.245769),
                    String.valueOf(loc.getOrDefault("country", "USA")),
                    String.valueOf(loc.getOrDefault("city", "Provo")),
                    "travel",
                    random.nextInt(5) + year - 20
            );

            loc = locations.get(random.nextInt(locations.size() - 1));
            int marraigeYear = random.nextInt(2) + year;
            Event marriage1 = new Event(
                    UUID.randomUUID().toString(),
                    null,
                    couples.get("mother").getPersonID(),
                    (double) loc.getOrDefault("latitude", -111.6509753),
                    (double) loc.getOrDefault("longitude", 40.245769),
                    String.valueOf(loc.getOrDefault("country", "USA")),
                    String.valueOf(loc.getOrDefault("city", "Provo")),
                    "marriage",
                    marraigeYear
            );

            Event marriage2 = new Event(
                    UUID.randomUUID().toString(),
                    null,
                    couples.get("father").getPersonID(),
                    (double) loc.getOrDefault("latitude", -111.6509753),
                    (double) loc.getOrDefault("longitude", 40.245769),
                    String.valueOf(loc.getOrDefault("country", "USA")),
                    String.valueOf(loc.getOrDefault("city", "Provo")),
                    "marriage",
                    marraigeYear
            );

            events.add(birth);
            events.add(birth1);
            events.add(marriage1);
            events.add(marriage2);
            events.add(travel1);
            events.add(travel2);
            return events;
        }

        public static List<Event> generateEvents(List<Person> people) {
            List<Event> events = new ArrayList<>();
            for (Person person : people)
                events.addAll(generateEvents(person));
            return events;
        }

        public static List<Event> generateEvents(Person person) {
            List<Event> events = new ArrayList<>();
            Random random = new Random();
            for (String type : eventTypes) {
                Map<String, Object> loc = locations.get(random.nextInt(locations.size() - 1));
                events.add(new Event(
                        UUID.randomUUID().toString(),
                        null,
                        person.getPersonID(),
                        (double) loc.getOrDefault("latitude", -111.6509753),
                        (double) loc.getOrDefault("longitude", 40.245769),
                        String.valueOf(loc.getOrDefault("country", "USA")),
                        String.valueOf(loc.getOrDefault("city", "Provo")),
                        type,
                        random.nextInt(2019)
                ));
            }
            return events;
        }
    }

}
