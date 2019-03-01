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
        try {
            User user = userDAO.getUserByUsername(request.getUserName());
            Person person = personDAO.get(user.getId());
            Person spouse = Generator.generateSpouse(person);
            List<Person> ancestors = Generator.generateGenerations(Arrays.asList(person, spouse), request.getGenerations());
            List<Event> events = Generator.generateEvents(ancestors);
            events.addAll(Generator.generateEvents(person));
            events.addAll(Generator.generateEvents(spouse));
            personDAO.add(spouse);
            personDAO.addAll(ancestors);
            personDAO.update(person);
            eventDAO.addAll(events);
        } catch (ModelNotFoundException e) {
            e.printStackTrace();
            return new FillResult(false, "Unable to generate for user, user may not exist");
        }
        return new FillResult(true, "Generations are filled");
    }

    /**
     * This generates the events and ancestors for a person
     */
    public static class Generator {
        private static String[] firstMaleNames = {"Liam", "Noah", "William", "James", "Logan", "Benjamin", "Mason", "Elijah", "Oliver", "Jacob", "Lucas", "Michael","Alexander","Ethan","Daniel","Matthew","Aiden","Henry","Joseph","Jackson"};
        private static String[] firstFemaleNames = {"Emma","Olivia","Ava","Isabella","Sophia","Mia","Charlotte","Amelia","Evelyn","Abigail","Harper","Emily","Elizabeth","Avery","Sofia","Ella","Madison","Scarlett","Victoria","Aria"};
        private static String[] lastNames = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Miller", "Davis", "Garcia", "Rodriguez", "Wilson"};
        private static String[] eventTypes = {"birth", "death", "travel", "marriage"};
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

        private static List<Person> generateImmediateGeneration(Person person) {
            if (person == null)
                return new ArrayList<>();
            Person mother = generateFemalePerson();
            Person father = generateMalePerson();
            Person spouse = generateSpouse(person);
            person.setMotherID(mother.getId());
            person.setFatherID(father.getId());
            father.setSpouseID(mother.getId());
            mother.setSpouseID(father.getId());
            if (spouse != null)
                person.setSpouseID(spouse.getId());
            List<Person> personList = new ArrayList<>();
            personList.add(mother);
            personList.add(father);
            if (spouse != null)
                personList.add(spouse);
            return personList;
        }

        public static List<Person> generateGenerations(List<Person> people, int depth) {
            return gGDive(people, depth, 0);
        }

        private static List<Person> gGDive(List<Person> people, int depth, int current) {
            current++;
            if (current > depth)
                return new ArrayList<>();
            List<Person> generated = new ArrayList<>();
            for (Person person : people) {
                generated.addAll(generateImmediateGeneration(person));
            }
            generated.addAll(gGDive(generated, depth, current));
            return generated;
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
