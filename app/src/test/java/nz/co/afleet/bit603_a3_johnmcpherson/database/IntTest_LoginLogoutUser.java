package nz.co.afleet.bit603_a3_johnmcpherson.database;

import android.app.Application;

import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import static nz.co.afleet.bit603_a3_johnmcpherson.database.IntTest_User.ADMIN;
import static nz.co.afleet.bit603_a3_johnmcpherson.database.IntTest_User.ADMIN_PASSWORD;
import static nz.co.afleet.bit603_a3_johnmcpherson.database.IntTest_User.JOHN;
import static nz.co.afleet.bit603_a3_johnmcpherson.database.IntTest_User.JOHN_PASSWORD;
import static nz.co.afleet.bit603_a3_johnmcpherson.database.IntTest_User.createStandardUser;
import static nz.co.afleet.bit603_a3_johnmcpherson.database.User.getLoggedInUser;
import static nz.co.afleet.bit603_a3_johnmcpherson.database.User.loginUser;
import static nz.co.afleet.bit603_a3_johnmcpherson.database.User.logout;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class IntTest_LoginLogoutUser {
    private Application application;
    ApplicationDatabase inventoryDatabase;

    @Before
    public void initialiseApplicationAndDatabase() {
        application = ApplicationProvider.getApplicationContext();
        inventoryDatabase = ApplicationDatabase.getInstance(application);
    }

    @Test
    public void testCreateUserLoginAndLogout() {
        boolean loggedIn = loginUser(application, JOHN, JOHN_PASSWORD);
        assertFalse(loggedIn);

        loggedIn = loginUser(application, ADMIN, ADMIN_PASSWORD);
        // check that admin logged in
        assertTrue(loggedIn);
        // check that the logged in user is admin
        User loggedInUser = User.getLoggedInUser();
        assertEquals(loggedInUser.getName(), ADMIN);

        // now login with a new user
        User newUser = createStandardUser(application);
        loggedIn = loginUser(application, JOHN, JOHN_PASSWORD);
        assertTrue(loggedIn);

        // check that the logged in user has changed to new user
        loggedInUser = User.getLoggedInUser();
        assertEquals(newUser, loggedInUser);

        logout();
        assertNull(getLoggedInUser()); // check logged out
    }

    @After
    public void tearDown() {
        ApplicationDatabase.TESTING_tearDown();
    }
}
