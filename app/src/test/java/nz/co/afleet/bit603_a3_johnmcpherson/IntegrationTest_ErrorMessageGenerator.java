package nz.co.afleet.bit603_a3_johnmcpherson;

import android.app.Application;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.LinkedHashMap;

import static nz.co.afleet.bit603_a3_johnmcpherson.ErrorMessageGenerator.determineErrorMessage;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class IntegrationTest_ErrorMessageGenerator {
    private Application application;

    @Before
    public void initialiseApplication() {
        application = ApplicationProvider.getApplicationContext();
    }

    @Test
    public void checkMessageForSuccess() {
        LinkedHashMap<Integer, Boolean> fieldsFilledOrNotFilled = new LinkedHashMap<>();
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
        LinkedHashMap<Integer, Boolean> fieldsFilledOrNotFilled = new LinkedHashMap<>();
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
        LinkedHashMap<Integer, Boolean> fieldsFilledOrNotFilled = new LinkedHashMap<>();
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
    public void checkErrorMessageOneFieldOnly() {
        LinkedHashMap<Integer, Boolean> fieldsFilledOrNotFilled = new LinkedHashMap<>();
        fieldsFilledOrNotFilled.put(R.string.delete_user_user_field_name, false);

        checkErrorMessage("Please enter user name",
                R.string.delete__user_error_unable_to_delete,
                R.string.delete_user_details_required_header,
                fieldsFilledOrNotFilled,
                false
        );
    }

    @Test
    public void checkMessageForSecondFieldMissing() {
        LinkedHashMap<Integer, Boolean> fieldsFilledOrNotFilled = new LinkedHashMap<>();
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
    public void checkMessageForSuccessUsingThreeFields() {
        LinkedHashMap<Integer, Boolean> fieldsFilledOrNotFilled = new LinkedHashMap<>();
        fieldsFilledOrNotFilled.put(R.string.add_user_user_field_name, true);
        fieldsFilledOrNotFilled.put(R.string.add_user_password_field_name, true);
        fieldsFilledOrNotFilled.put(R.string.add_user_date_of_birth_field_name, true);

        checkErrorMessage("Sorry. We cannot add that user because the user name is duplicated. Please try again",
                R.string.add_user_error_unable_to_add,
                R.string.add_user_details_required_header,
                fieldsFilledOrNotFilled,
                false
        );
    }

    @Test
    public void checkMessageForSuccessUsingNoFields() { // not an expected scenario. But let's edge test anyway
        LinkedHashMap<Integer, Boolean> fieldsFilledOrNotFilled = new LinkedHashMap<>();

        checkErrorMessage("",
                R.string.add_user_error_unable_to_add,
                R.string.add_user_details_required_header,
                fieldsFilledOrNotFilled,
                true
        );
    }

    @Test
    public void checkMessageForThreeFieldsMissing() {
        LinkedHashMap<Integer, Boolean> fieldsFilledOrNotFilled = new LinkedHashMap<>();
        fieldsFilledOrNotFilled.put(R.string.add_user_user_field_name, false);
        fieldsFilledOrNotFilled.put(R.string.add_user_password_field_name, false);
        fieldsFilledOrNotFilled.put(R.string.add_user_date_of_birth_field_name, false);

        checkErrorMessage("Please enter user name, password and date of birth",
                R.string.add_user_error_unable_to_add,
                R.string.add_user_details_required_header,
                fieldsFilledOrNotFilled,
                false
        );
    }

    @Test
    public void checkMessageForActionFailed() {
        LinkedHashMap<Integer, Boolean> fieldsFilledOrNotFilled = new LinkedHashMap<>();
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
                                   LinkedHashMap<Integer, Boolean> fieldsFilledOrNotFilled,
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
