package nz.co.afleet.bit603_a3_johnmcpherson.database;

import android.app.Application;

import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class IntTest_User {
    private Application application;
    ApplicationDatabase inventoryDatabase;

    private final String JOHN = "John Doe";
    private final String JOHN_PASSWORD = "hisPassowrd";
    private final String JOHN_DATE_OF_BIRTH = "20/12/1996";
    private final String JOHN_EMPLOYEE_NUMBER = "K004";
    private final String JOHN_PHONE_NUMBER = "04 498 2168";
    private final String JOHN_ADDRESS = "14 Avery Street, Lower Hutt, 5012";
    private final  boolean JOHN_IS_ADMIN = false;

    @Before
    public void initialiseApplicationAndDatabase() {
        application = ApplicationProvider.getApplicationContext();
        inventoryDatabase = ApplicationDatabase.getInstance(application);
    }

    @Test
    public void testCreateUser() {
        User userJohn = new User(   JOHN,
                                    JOHN_PASSWORD,
                                    JOHN_DATE_OF_BIRTH,
                                    JOHN_EMPLOYEE_NUMBER,
                                    JOHN_PHONE_NUMBER,
                                    JOHN_ADDRESS,
                                    JOHN_IS_ADMIN);
        assertEquals(userJohn.getName(), JOHN);
        assertEquals(userJohn.getPassword(), JOHN_PASSWORD);
        assertEquals(userJohn.getDateOfBirth(), JOHN_DATE_OF_BIRTH);
        assertEquals(userJohn.getEmployeeNumber(), JOHN_EMPLOYEE_NUMBER);
        assertEquals(userJohn.getPhoneNumber(), JOHN_PHONE_NUMBER);
        assertEquals(userJohn.getAddress(), JOHN_ADDRESS);
        assertEquals(userJohn.isAdmin(), JOHN_IS_ADMIN);
    }

    @After
    public void tearDown() {
        ApplicationDatabase.TESTING_tearDown();
    }
}
