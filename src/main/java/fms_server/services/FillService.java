package fms_server.services;

import fms_server.dao.*;
import fms_server.models.Person;
import fms_server.models.User;
import fms_server.requests.FillRequest;
import fms_server.results.FillResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

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
     * Will generate and fill database with random information
     * @param request FillRequest
     * @return whether fill was successful
     */
    public FillResult fill(FillRequest request) throws DataBaseException {
        try {
            User user = userDAO.getUserByUsername(request.getUserName());
            Person person = personDAO.get(user.getId());
            List<Person> personList = Generator.generateImmediateGeneration(person);
            Person spouse = Generator.getSpouse(person, personList);
            spouse.setSpouseID(person.getId());
            List<Person> spouseFamily = Generator.generateImmediateGeneration(spouse);
            for (Person person1 : personList)
                personDAO.add(person1);
            for (Person person1 : spouseFamily)
                personDAO.add(person1);
            personDAO.update(person);
//            Logger.fine("Person to update: " + person.toString());
//            Logger.fine(personDAO.get(person.getId()).toString());
//            Logger.fine(String.valueOf(person));
        } catch (ModelNotFoundException e) {
            e.printStackTrace();
        }
        return new FillResult(true, "Generations are filled");
    }

    public static class Generator {
        private static String[] firstMaleNames = {"Liam", "Noah", "William", "James", "Logan", "Benjamin", "Mason", "Elijah", "Oliver", "Jacob", "Lucas", "Michael","Alexander","Ethan","Daniel","Matthew","Aiden","Henry","Joseph","Jackson"};
        private static String[] firstFemaleNames = {"Emma","Olivia","Ava","Isabella","Sophia","Mia","Charlotte","Amelia","Evelyn","Abigail","Harper","Emily","Elizabeth","Avery","Sofia","Ella","Madison","Scarlett","Victoria","Aria"};
        private static String[] lastNames = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Miller", "Davis", "Garcia", "Rodriguez", "Wilson"};
        private static String[] eventTypes = {"birth", "death", "travel", "marriage"};

        static Person generateMalePerson() {
            Random random = new Random();
            String firstName = firstMaleNames[(firstMaleNames.length - 1) % random.nextInt(30)];
            return generatePerson(firstName, "m", random);
        }

        static Person generateFemalePerson() {
            Random random = new Random();
            String firstName = firstFemaleNames[(firstMaleNames.length - 1) % random.nextInt(30)];
            return generatePerson(firstName, "f", random);
        }

        private static Person generatePerson(String firstname, String gender, Random random) {
            return new Person(
                    UUID.randomUUID().toString(),
                    null,
                    firstname,
                    lastNames[(lastNames.length - 1) % random.nextInt(30)],
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

        private static List<Person> generateImmediateGeneration(Person person) {
            if (person == null)
                return new ArrayList<>();
//            Logger.fine(String.valueOf(person));
            Person mother = generateFemalePerson();
            Person father = generateMalePerson();
            Person spouse = person.getGender().equals("m") ? generateFemalePerson() : generateMalePerson();
            person.setMotherID(mother.getId());
            person.setFatherID(father.getId());
            person.setSpouseID(spouse.getId());
            List<Person> personList = new ArrayList<>();
            personList.add(mother);
            personList.add(father);
            personList.add(spouse);
//            Logger.fine("Updated person: " + person.toString());
            return personList;
        }

//        public static List<Event> generateEvents(Person person) {
//            List<Event> events = new ArrayList<>();
//            for (String type : eventTypes) {
//                events.add(new Event(
//
//                ));
//            }
//        }
    }
}
