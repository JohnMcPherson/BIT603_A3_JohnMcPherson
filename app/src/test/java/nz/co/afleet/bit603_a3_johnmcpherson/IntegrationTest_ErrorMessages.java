package nz.co.afleet.bit603_a3_johnmcpherson;

import android.app.Application;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.HashMap;

import static nz.co.afleet.bit603_a3_johnmcpherson.FormChecker.determineErrorMessage;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class IntegrationTest_ErrorMessages {
    private Application application;

    @Before
    public void initialiseApplication() {
        application = ApplicationProvider.getApplicationContext();
    }

    @Test
    public void testErrorMessages() {

        checkMessageForSuccess();
        checkMessageForBothFieldsMissing();
        checkMessageForFirstFieldMissing();
        checkMessageForSecondFieldMissing();
        checkMessageForActionFailed();
    }

    @Test
    public void checkMessageForSuccess() {
        HashMap<Integer, Boolean> fieldsFilledOrNotFilled = new HashMap<>();
        fieldsFilledOrNotFilled.put(R.string.login_user_field_name, true);
        fieldsFilledOrNotFilled.put(R.string.login_password_field_name, false);

        checkErrorMessage("",
                R.string.error_incorrect_login,
                R.string.login_details_required_header,
                fieldsFilledOrNotFilled,
                true
        );
    }

    @Test
    public void checkMessageForBothFieldsMissing() {
        HashMap<Integer, Boolean> fieldsFilledOrNotFilled = new HashMap<>();
        fieldsFilledOrNotFilled.put(R.string.login_user_field_name, false);
        fieldsFilledOrNotFilled.put(R.string.login_password_field_name, false);

        checkErrorMessage("Please enter your user name and password",
                R.string.error_incorrect_login,
                R.string.login_details_required_header,
                fieldsFilledOrNotFilled,
                false
        );
    }

    @Test
    public void checkMessageForFirstFieldMissing() {
        HashMap<Integer, Boolean> fieldsFilledOrNotFilled = new HashMap<>();
        fieldsFilledOrNotFilled.put(R.string.login_user_field_name, false);
        fieldsFilledOrNotFilled.put(R.string.login_password_field_name, true);

        checkErrorMessage("Please enter your user name",
                R.string.error_incorrect_login,
                R.string.login_details_required_header,
                fieldsFilledOrNotFilled,
                false
        );
    }

    @Test
    public void checkMessageForSecondFieldMissing() {
        HashMap<Integer, Boolean> fieldsFilledOrNotFilled = new HashMap<>();
        fieldsFilledOrNotFilled.put(R.string.login_user_field_name, true);
        fieldsFilledOrNotFilled.put(R.string.login_password_field_name, false);

        checkErrorMessage("Please enter your password",
                R.string.error_incorrect_login,
                R.string.login_details_required_header,
                fieldsFilledOrNotFilled,
                false
        );
    }

    @Test
    public void checkMessageForActionFailed() {
        HashMap<Integer, Boolean> fieldsFilledOrNotFilled = new HashMap<>();
        fieldsFilledOrNotFilled.put(R.string.login_user_field_name, true);
        fieldsFilledOrNotFilled.put(R.string.login_password_field_name, true);

        checkErrorMessage("Sorry. We did not recognise that user/password combination. Please try again",
                R.string.error_incorrect_login,
                R.string.login_details_required_header,
                fieldsFilledOrNotFilled,
                false
        );
    }

    private void checkErrorMessage(String expectedMessage,
                                   int stringResourceInvalidCombination,
                                   int stringResourceDetailsRequiredHeader,
                                   HashMap<Integer, Boolean> fieldsFilledOrNotFilled,
                                   boolean actionSucceeded) {

        String actualMessage = determineErrorMessage(
                application,
                stringResourceInvalidCombination,
                stringResourceDetailsRequiredHeader,
                fieldsFilledOrNotFilled,
                actionSucceeded
        );

        assertTrue(actualMessage.equals(expectedMessage));
    }
}
