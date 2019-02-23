package fms_server;

import fms_server.dao.*;
import fms_server.logging.Logger;
import fms_server.models.Person;
import fms_server.models.Person;
import org.junit.jupiter.api.*;

public class PersonDAOTest {
    @BeforeAll
    static void setup() throws DataBaseException {
        Logger.setLogLevel(Logger.LEVEL.INFO);
        Logger.setShouldPrintStackTrace(false);
        Logger.head("Testing personDAO test cases");
        DataBase.createTables();
    }

    /**
     * Clear the tables before each test
     * @throws DataBaseException If something goes wrong
     */
    @BeforeEach
    void clearTables() throws DataBaseException {
        PersonDAO personDAO = new PersonDAO();
        personDAO.clear();
    }

    /**
     * Test adding a bunch of stuff to the table
     */
    @Test
    @DisplayName("ADD pass test case")
    void addPass() {
        PersonDAO personDAO = new PersonDAO();
        try {
            Person personToAdd = new Person("hello", "JustBrenkman", "Ben", "Brenkman", "m", "1", "2", "3");
            Person personToAdd1 = new Person("hello1", "JustBrenkman", "Ben", "Brenkman", "m", "1", "2", "3");
            personDAO.add(personToAdd);
            personDAO.add(personToAdd1);
        } catch (DataBaseException e) {
            e.printStackTrace();
            Logger.fail("Adding to Person table test case");
            Assertions.fail();
        }
        Logger.pass("Passed adding test case");
    }

    /**
     * Tests adding two identical objects
     */
    @Test
    @DisplayName("ADD fail test case")
    void addFail() {
        Assertions.assertThrows(DataBaseException.class, () -> {
            PersonDAO personDAO = new PersonDAO();
            Person personToAdd = new Person("hello", "JustBrenkman", "Ben", "Brenkman", "m", "1", "2", "3");
            personDAO.add(personToAdd);
            personDAO.add(personToAdd); // This should throw an error
        });
        Logger.pass("Passed adding two identical events");
    }

    /**
     * Tests DAO to get several events from database
     */
    @Test
    @DisplayName("GET pass test case")
    void getPass() {
        PersonDAO personDAO = new PersonDAO();
        try {
            Person personToAdd = new Person("hello", "JustBrenkman", "Ben", "Brenkman", "m", "1", "2", "3");
            Person personToAdd1 = new Person("hello1", "JustBrenkman", "Ben", "Brenkman", "m", "1", "2", "3");
            Person personToAdd2 = new Person("hello2", "JustBrenkman", "Ben", "Brenkman", "m", "1", "2", "3");
            personDAO.add(personToAdd);
            personDAO.add(personToAdd1);
            personDAO.add(personToAdd2);
            Person userTest = personDAO.get("hello");
            Assertions.assertEquals(userTest, personToAdd);
            userTest = personDAO.get("hello1");
            Assertions.assertEquals(userTest, personToAdd1);
            userTest = personDAO.get("hello2");
            Assertions.assertEquals(userTest, personToAdd2);
        } catch (DataBaseException | ModelNotFoundException e) {
            Assertions.fail();
        }
        Logger.pass("Passed get event test case");
    }

    /**
     * Gets an id that doesn't exist, must fail
     */
    @Test
    @DisplayName("GET fail test case")
    void getFail() {
        Assertions.assertThrows(ModelNotFoundException.class, () -> {
            PersonDAO personDAO = new PersonDAO();
            personDAO.get("1");
        });
        Logger.pass("Passed get non existent event test case");
    }


    /**
     * Adds an event then deletes it
     */
    @Test
    @DisplayName("DELETE pass test case")
    void deletePass() {
        try {
            PersonDAO personDAO = new PersonDAO();
            Person personToAdd = new Person("hello", "JustBrenkman", "Ben", "Brenkman", "m", "1", "2", "3");
            personDAO.add(personToAdd);
            personDAO.delete("hello");
        } catch (DataBaseException | ModelNotFoundException e) {
            e.printStackTrace();
            Assertions.fail();
        }
        Logger.pass("Passed deletion of event");
    }

    /**
     * Tries to delete an event that does not exist
     */
    @Test
    @DisplayName("DELETE fail test case")
    void deleteFail() {
        Assertions.assertThrows(ModelNotFoundException.class, () -> {
            PersonDAO personDAO = new PersonDAO();
            personDAO.delete("1");
        });
        Logger.pass("Passed deletion of event that does not exist");
    }

    /**
     * Adds a couple events to database, clears then checks to see if they are still in the database
     */
    @Test
    @DisplayName("DELETE all test case")
    void deleteAll() {
        Assertions.assertAll(() -> {
            try {
                PersonDAO personDAO = new PersonDAO();
                Person personToAdd = new Person("hello", "JustBrenkman", "Ben", "Brenkman", "m", "1", "2", "3");
                Person personToAdd1 = new Person("hello1", "JustBrenkman", "Ben", "Brenkman", "m", "1", "2", "3");
                Person personToAdd2 = new Person("hello2", "JustBrenkman", "Ben", "Brenkman", "m", "1", "2", "3");
                personDAO.add(personToAdd);
                personDAO.add(personToAdd1);
                personDAO.add(personToAdd2);
                personDAO.clear();
                Assertions.assertThrows(ModelNotFoundException.class, () -> personDAO.get("1"));
                Assertions.assertThrows(ModelNotFoundException.class, () -> personDAO.get("2"));
                Assertions.assertThrows(ModelNotFoundException.class, () -> personDAO.get("3"));
            } catch (DataBaseException e) {
                Logger.fail("Failed to delete all entries");
                Assertions.fail();
            }
            Logger.pass("Passed deletion of all entries");
        });
    }
}
