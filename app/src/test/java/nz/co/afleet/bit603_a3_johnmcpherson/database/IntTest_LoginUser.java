package nz.co.afleet.bit603_a3_johnmcpherson.database;

import android.app.Application;

import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static nz.co.afleet.bit603_a3_johnmcpherson.database.IntTest_User.JOHN;
import static nz.co.afleet.bit603_a3_johnmcpherson.database.IntTest_User.JOHN_PASSWORD;
import static nz.co.afleet.bit603_a3_johnmcpherson.database.IntTest_User.createStandardUser;
import static nz.co.afleet.bit603_a3_johnmcpherson.database.User.loginUser;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class IntTest_LoginUser {
    private Application application;
    ApplicationDatabase inventoryDatabase;

    @Before
    public void initialiseApplicationAndDatabase() {
        application = ApplicationProvider.getApplicationContext();
        inventoryDatabase = ApplicationDatabase.getInstance(application);
    }

    @Test
    public void testCreateUserAndLogin() {
        boolean loggedIn = loginUser(application, JOHN, JOHN_PASSWORD);
        assertFalse(loggedIn);
        User newUser = createStandardUser(application);
        loggedIn = loginUser(application, JOHN, JOHN_PASSWORD);
        assertTrue(loggedIn);
        User loggedInUser = User.getLoggedInUser();
        assertEquals(newUser, loggedInUser);
    }

    @After
    public void tearDown() {
        ApplicationDatabase.TESTING_tearDown();
    }
}
