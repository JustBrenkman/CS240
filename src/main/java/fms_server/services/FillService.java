/*
 * Copyright (c) 2019.
 * @author Ben Brenkman
 * Last Modified 3/7/19 7:42 PM
 */

package fms_server.services;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import fms_server.dao.*;
import fms_server.exceptions.DataBaseException;
import fms_server.logging.Logger;
import fms_server.models.Event;
import fms_server.models.Person;
import fms_server.models.User;
import fms_server.requests.FillRequest;
import fms_server.results.FillResult;

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
            User user = userDAO.getUserByUsername(request.getUserName()); // Get the user

            // Delete existing data about user
            Generator.setUser(user.getUsername());
            personDAO.deleteAll(user.getUsername(), user.getId()); // Deletes all except the user
            eventDAO.deleteAll(user.getUsername());

            // Get person from user and generate additional information
            Person person = personDAO.get(user.getId());
            Person spouse = Generator.generateSpouse(person);
            Logger.info("person: " + person.toString());
            Logger.info("spouse: " + spouse.toString());
            ancestors = Generator.generateGenerations(Arrays.asList(person), request.getGenerations(), events, 2019 - 35);

            personDAO.add(spouse);
            personDAO.addAll(ancestors);
            personDAO.update(person); // Update person information
            eventDAO.addAll(events);
        } catch (ModelNotFoundException e) {
            Logger.error("Cannot find user", e);
            return new FillResult(false, "Unable to generate for user, user may not exist");
        }
        return new FillResult(true, "Successfully added " + ancestors.size() + " persons and " + events.size() + " events to database");
    }

    /**
     * This generates the events and ancestors for a person
     */
    public static class Generator {
        /**
         * List of first male names
         */
        private static String[] firstMaleNames = {"Liam", "Noah", "William", "James", "Logan", "Benjamin", "Mason", "Elijah", "Oliver", "Jacob", "Lucas", "Michael","Alexander","Ethan","Daniel","Matthew","Aiden","Henry","Joseph","Jackson"};
        /**
         * List of first female names
         */
        private static String[] firstFemaleNames = {"Emma","Olivia","Ava","Isabella","Sophia","Mia","Charlotte","Amelia","Evelyn","Abigail","Harper","Emily","Elizabeth","Avery","Sofia","Ella","Madison","Scarlett","Victoria","Aria"};
        /**
         * List of surnames
         */
        private static String[] lastNames = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Miller", "Davis", "Garcia", "Rodriguez", "Wilson"};
        /**
         * List of random locations
         */
        private static List<LinkedTreeMap<String, Object>> locations;
        /**
         * Current user to fill data in
         */
        private static String user;

        /*
          Get a list of random information
         */
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

        /**
         * Creates a new male
         *
         * @return new male person
         */
        static Person generateMalePerson() {
            Random random = new Random();
            String firstName = firstMaleNames[random.nextInt(firstMaleNames.length - 1)];
            return generatePerson(firstName, "m", random);
        }

        /**
         * Create a new female
         * @return female person
         */
        static Person generateFemalePerson() {
            Random random = new Random();
            String firstName = firstFemaleNames[random.nextInt(firstFemaleNames.length - 1)];
            return generatePerson(firstName, "f", random);
        }

        /**
         * Creates a person
         * @param firstname first name
         * @param gender gender
         * @param random random number generator
         * @return a new person
         */
        private static Person generatePerson(String firstname, String gender, Random random) {
            return new Person(
                    UUID.randomUUID().toString(),
                    user,
                    firstname,
                    lastNames[random.nextInt(lastNames.length - 1)],
                    gender,
                    null,
                    null,
                    null
            );
        }

        /**
         * Create a spouse for someone
         * @param person the person who is single
         * @return spouse
         */
        public static Person generateSpouse(Person person) {
            Person spouse = person.getGender().equals("m") ? generateFemalePerson() : generateMalePerson();
            if (spouse != null) {
                person.setSpouseID(spouse.getId());
                spouse.setSpouseID(person.getId());
            }
            return spouse;
        }

        /**
         * Generates mother and father
         * @param person person whom you want to give parents to
         * @return mother and father collection
         */
        private static HashMap<String, Person> generateImmediateGeneration(Person person) {
            if (person == null)
                return new HashMap<>();
            Person mother = generateFemalePerson();
            Person father = generateMalePerson();
            person.setMotherID(mother.getId());
            person.setFatherID(father.getId());
            father.setSpouseID(mother.getId());
            mother.setSpouseID(father.getId());
            HashMap<String, Person> personList = new HashMap<>();
            personList.put("mother", mother);
            personList.put("father", father);
            return personList;
        }

        /**
         * initial call to the recursive function
         * @param people list of people to generate parents for
         * @param depth number of generations to fill
         * @param events list of all events generated
         * @param year year range
         * @return list of persons generated
         */
        public static List<Person> generateGenerations(List<Person> people, int depth, List<Event> events, int year) {
            return gGDive(people, depth, 0, events, year);
        }

        /**
         * Recursive generative function
         * @param people list of people to generate parents for
         * @param depth number of generations to fill
         * @param current current generation
         * @param events list of all events generated
         * @param year year range
         * @return list of persons generated
         */
        private static List<Person> gGDive(Collection<Person> people, int depth, int current, List<Event> events, int year) {
            if (current >= depth)
                return new ArrayList<>();
            current++;
            List<Person> generated = new ArrayList<>();
            for (Person person : people) {
                HashMap<String, Person> peopleToAdd = generateImmediateGeneration(person);
                events.addAll(generateEventsForCouple(peopleToAdd, year));
                generated.addAll(peopleToAdd.values());
                peopleToAdd.remove("spouse");
                generated.addAll(gGDive(peopleToAdd.values(), depth, current, events, year - 50));
            }
            return generated;
        }

        /**
         * Generates the events for a couple
         * @param couples map of couple, mother and father
         * @param year year range
         * @return list of events
         */
        public static List<Event> generateEventsForCouple(HashMap<String, Person> couples, int year) {
            List<Event> events = new ArrayList<>();
            Random random = new Random();
            Map<String, Object> loc;

            loc = locations.get(random.nextInt(locations.size() - 1));
            Event birth = new Event(
                    UUID.randomUUID().toString(),
                    user,
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
                    user,
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
                    user,
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
                    user,
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
                    user,
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
                    user,
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

        public static String getUser() {
            return user;
        }
        public static void setUser(String user) {
            Generator.user = user;
        }
    }

}
