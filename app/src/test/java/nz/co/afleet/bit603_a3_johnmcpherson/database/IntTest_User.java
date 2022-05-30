package nz.co.afleet.bit603_a3_johnmcpherson.database;

import android.app.Application;

import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class IntTest_User {
    private Application application;
    InventoryDatabase inventoryDatabase;

    private final String JOHN = "John Doe";
    private final String JOHN_PASSWORD = "hisPassowrd";
    private final String JOHN_DATE_OF_BIRTH = "20/12/1996";
    private final String JOHN_EMPLOYEE_NUMBER = "K004";
    private final String JOHN_PHONE_NUMBER = "04 498 2168";
    private final String JOHN_ADDRESS = "14 Avery Street, Lower Hutt, 5012";

    @Before
    public void initialiseApplicationAndDatabase() {
        application = ApplicationProvider.getApplicationContext();
        inventoryDatabase = InventoryDatabase.getInstance(application);
    }

    @Test
    public void testCreateUser() {
        User userJohn = new User(   JOHN,
                                    JOHN_PASSWORD,
                                    JOHN_DATE_OF_BIRTH,
                                    JOHN_EMPLOYEE_NUMBER,
                                    JOHN_PHONE_NUMBER,
                                    JOHN_ADDRESS);
    }

    @Test
    public void testCreateUserAttemptNulls() {
        User userJohn = new User(   JOHN,
                                    null,
                                    JOHN_DATE_OF_BIRTH,
                                    JOHN_EMPLOYEE_NUMBER,
                                    JOHN_PHONE_NUMBER,
                                    JOHN_ADDRESS);
    }

    @After
    public void tearDown() {
        InventoryDatabase.TESTING_tearDown();
    }
}
