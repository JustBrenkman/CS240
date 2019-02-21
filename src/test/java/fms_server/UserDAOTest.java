package fms_server;

import fms_server.dao.*;
import fms_server.logging.Logger;
import fms_server.models.User;
import org.junit.jupiter.api.*;

public class UserDAOTest {
    @BeforeAll
    static void setup() throws DataBaseException {
        Logger.setLogLevel(Logger.LEVEL.INFO);
        Logger.setShouldPrintStackTrace(false);
        Logger.head("Testing UserDAO test cases");
        DataBase.createTables();
    }

    /**
     * Clear the tables before each test
     * @throws DataBaseException If something goes wrong
     */
    @BeforeEach
    void clearTables() throws DataBaseException {
        UserDAO userDAO = new UserDAO();
        userDAO.clear();
    }

    /**
     * Test adding a bunch of stuff to the table
     */
    @Test
    @DisplayName("ADD pass test case")
    void addPass() {
        UserDAO userDAO = new UserDAO();
        try {
            User userToAdd = new User("JustBrenkman", "oihsafihoiafiaosfoisdhfoisdbvosdv", "JustBrenkman@gmail.com", "Ben", "Brenkman", "m", "1");
            User userToAdd1 = new User("JustBrenkman1", "oihsafihoiafiaosfoisdhfoisdbvosdv", "JustBrenkman1@gmail.com", "Ben", "Brenkman", "m", "2");
            userDAO.add(userToAdd);
            userDAO.add(userToAdd1);
        } catch (DataBaseException e) {
            e.printStackTrace();
            Logger.fail("Adding to User table test case");
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
            UserDAO userDAO = new UserDAO();
            User userToAdd = new User("JustBrenkman", "oihsafihoiafiaosfoisdhfoisdbvosdv", "JustBrenkman@gmail.com", "Ben", "Brenkman", "m", "1");
            userDAO.add(userToAdd);
            userDAO.add(userToAdd); // This should throw an error
        });
        Logger.pass("Passed adding two identical events");
    }

    /**
     * Tests DAO to get several events from database
     */
    @Test
    @DisplayName("GET pass test case")
    void getPass() {
        UserDAO userDAO = new UserDAO();
        try {
            User userToAdd = new User("JustBrenkman", "oihsafihoiafiaosfoisdhfoisdbvosdv", "JustBrenkman@gmail.com", "Ben", "Brenkman", "m", "1");
            User userToAdd1 = new User("JustBrenkman1", "oihsafihoiafiaosfoisdhfoisdbvosdv", "JustBrenkman1@gmail.com", "Ben", "Brenkman", "m", "2");
            User userToAdd2 = new User("JustBrenkman2", "oihsafihoiafiaosfoisdhfoisdbvosdv", "JustBrenkman2@gmail.com", "Ben", "Brenkman", "m", "3");
            userDAO.add(userToAdd);
            userDAO.add(userToAdd1);
            userDAO.add(userToAdd2);
            User userTest = userDAO.get("1");
            Assertions.assertEquals(userTest, userToAdd);
            userTest = userDAO.get("2");
            Assertions.assertEquals(userTest, userToAdd1);
            userTest = userDAO.get("3");
            Assertions.assertEquals(userTest, userToAdd2);
        } catch (DataBaseException | ModelNotFoundException e) {
            Assertions.fail();
        }
        Logger.pass("Passed get event test case");
    }

    /**
     * Tests ti see if we can get user based on email
     */
    @Test
    @DisplayName("GET pass user by email")
    void getUserEmailPass() {
        UserDAO userDAO = new UserDAO();
        User userToCheck = new User("JustBrenkman", "oihsafihoiafiaosfoisdhfoisdbvosdv", "JustBrenkman@gmail.com", "Ben", "Brenkman", "m", "1");
        try {
            userDAO.add(userToCheck);
            Assertions.assertEquals(userToCheck, userDAO.getUserByEmail("JustBrenkman@gmail.com"));
        } catch (DataBaseException | ModelNotFoundException e) {
            Logger.fail("Failed to retrieve user by email");
            Assertions.fail();
        }
        Logger.pass("Passed getting user by email");
    }

    /**
     * Gets an id that doesn't exist, must fail
     */
    @Test
    @DisplayName("GET fail test case")
    void getFail() {
        Assertions.assertThrows(ModelNotFoundException.class, () -> {
            UserDAO userDAO = new UserDAO();
            userDAO.get("1");
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
            UserDAO userDAO = new UserDAO();
            User userToAdd = new User("JustBrenkman", "oihsafihoiafiaosfoisdhfoisdbvosdv", "JustBrenkman@gmail.com", "Ben", "Brenkman", "m", "1");
            userDAO.add(userToAdd);
            userDAO.delete("1");
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
            UserDAO userDAO = new UserDAO();
            userDAO.delete("1");
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
                UserDAO userDAO = new UserDAO();
                User userToAdd = new User("JustBrenkman", "oihsafihoiafiaosfoisdhfoisdbvosdv", "JustBrenkman@gmail.com", "Ben", "Brenkman", "m", "1");
                User userToAdd1 = new User("JustBrenkman1", "oihsafihoiafiaosfoisdhfoisdbvosdv", "JustBrenkman1@gmail.com", "Ben", "Brenkman", "m", "2");
                User userToAdd2 = new User("JustBrenkman2", "oihsafihoiafiaosfoisdhfoisdbvosdv", "JustBrenkman2@gmail.com", "Ben", "Brenkman", "m", "3");
                userDAO.add(userToAdd);
                userDAO.add(userToAdd1);
                userDAO.add(userToAdd2);
                userDAO.clear();
                Assertions.assertThrows(ModelNotFoundException.class, () -> userDAO.get("1"));
                Assertions.assertThrows(ModelNotFoundException.class, () -> userDAO.get("2"));
                Assertions.assertThrows(ModelNotFoundException.class, () -> userDAO.get("3"));
            } catch (DataBaseException e) {
                Logger.fail("Failed to delete all entries");
                Assertions.fail();
            }
            Logger.pass("Passed deletion of all entries");
        });
    }
}
