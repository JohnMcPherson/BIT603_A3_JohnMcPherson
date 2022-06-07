package nz.co.afleet.bit603_a3_johnmcpherson.database;

import android.app.Application;
import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class IntTest_User {
    private Application application;
    ApplicationDatabase inventoryDatabase;

    // field visibility is default, to allow reuse from other test files
    static final String ADMIN = "Admin";
    static final String ADMIN_PASSWORD = "CookieManagement84";
    static final String JOHN = "John Doe";
    static final String JOHN_PASSWORD = "hisPassword";

    private static final String JOHN_DATE_OF_BIRTH = "20/12/1996";
    final static String JOHN_EMPLOYEE_NUMBER = "K004";
    private static final String JOHN_PHONE_NUMBER = "04 498 2168";
    private static final String JOHN_ADDRESS = "14 Avery Street, Lower Hutt, 5012";
    private static final  boolean JOHN_IS_ADMIN = false;

    @Before
    public void initialiseApplicationAndDatabase() {
        application = ApplicationProvider.getApplicationContext();
        inventoryDatabase = ApplicationDatabase.getInstance(application);
    }

    @Test
    public void testCreateUser() {
        User userJohn = createStandardUserJohn();
        assertEquals(userJohn.getName(), JOHN);
        assertEquals(userJohn.getPassword(), JOHN_PASSWORD);
        assertEquals(userJohn.getDateOfBirth(), JOHN_DATE_OF_BIRTH);
        assertEquals(userJohn.getEmployeeNumber(), JOHN_EMPLOYEE_NUMBER);
        assertEquals(userJohn.getPhoneNumber(), JOHN_PHONE_NUMBER);
        assertEquals(userJohn.getAddress(), JOHN_ADDRESS);
        assertEquals(userJohn.isAdmin(), JOHN_IS_ADMIN);
    }

   private User createStandardUserJohn() {
        return createStandardUser(application);
   }

    // default visibility so we can reuse for testing login
    static User createStandardUser(Context context) {
        return User.createUser( context,
                                JOHN,
                                JOHN_PASSWORD,
                                JOHN_DATE_OF_BIRTH,
                                JOHN_EMPLOYEE_NUMBER,
                                JOHN_PHONE_NUMBER,
                                JOHN_ADDRESS,
                                JOHN_IS_ADMIN);
    }

    @Test
    public void testCreateUserBlankName() {
        User userNoName = User.createUser(application,
                                    "", // ensure that "" is caught
                                        JOHN_PASSWORD,
                                        JOHN_DATE_OF_BIRTH,
                                        JOHN_EMPLOYEE_NUMBER,
                                        JOHN_PHONE_NUMBER,
                                        JOHN_ADDRESS,
                                        JOHN_IS_ADMIN);
        assertNull(userNoName);
    }

    @Test
    public void testCreateUserBlankPhoneNumber() {
        User noPhoneNumber = User.createUser(application,
                                            JOHN,
                                            JOHN_PASSWORD,
                                            JOHN_DATE_OF_BIRTH,
                                            JOHN_EMPLOYEE_NUMBER,
                                            null, // ensure that null is caught
                                            JOHN_ADDRESS,
                                            JOHN_IS_ADMIN);
        assertNull(noPhoneNumber);
    }

    @Test
    public void testIsDuplicateOfUser() {
        createStandardUserJohn();
        assertTrue(User.isDuplicateOfUserName(application, JOHN));
    }

    @Test
    public void testDuplicateNotAdded() {
        User newUser = createStandardUserJohn();
        assertNotNull(newUser);
        User failedUserAddition = createStandardUserJohn(); // duplicate name, so should not be added
        assertNull(failedUserAddition);
    }

    @Test
    public void testRemoveUser() {
        User newUser = createStandardUserJohn();
        assertNotNull(newUser);
        boolean removed = User.removeUser(application, JOHN);
        assertTrue(removed); // confirm that removeUser() believes it removed the user
        assertFalse(User.isDuplicateOfUserName(application, JOHN)); // confirm that JOHN really is gone
    }

    @After
    public void tearDown() {
        ApplicationDatabase.TESTING_tearDown();
    }
}
