package nz.co.afleet.bit603_a3_johnmcpherson;

import android.app.Application;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static nz.co.afleet.bit603_a3_johnmcpherson.Utilities.determineErrorMessage;
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

    private void checkMessageForSuccess() {
        checkErrorMessage("",
                true,
                true,
                R.string.error_incorrect_login,
                R.string.login_details_required_header,
                R.string.login_user_field_name,
                R.string.login_password_field_name,
                true
        );

    }

    private void checkMessageForBothFieldsMissing() {
        checkErrorMessage("Please enter your user name and password",
                false,
                false,
                R.string.error_incorrect_login,
                R.string.login_details_required_header,
                R.string.login_user_field_name,
                R.string.login_password_field_name,
                false
        );

    }

    private void checkMessageForFirstFieldMissing() {
        checkErrorMessage("Please enter your user name",
                false,
                true,
                R.string.error_incorrect_login,
                R.string.login_details_required_header,
                R.string.login_user_field_name,
                R.string.login_password_field_name,
                false
        );

    }

    private void checkMessageForSecondFieldMissing() {
        checkErrorMessage("Please enter your password",
                true,
                false,
                R.string.error_incorrect_login,
                R.string.login_details_required_header,
                R.string.login_user_field_name,
                R.string.login_password_field_name,
                false
        );
    }

    private void checkMessageForActionFailed() {

        checkErrorMessage("Sorry. We did not recognise that user/password combination. Please try again",
                true,
                true,
                R.string.error_incorrect_login,
                R.string.login_details_required_header,
                R.string.login_user_field_name,
                R.string.login_password_field_name,
                false
        );
    }

    private void checkErrorMessage(String expectedMessage,
                                   boolean isFilledFirstField,
                                   boolean isFilledSecondField,
                                   int stringResourceInvalidCombination,
                                   int stringResourceDetailsRequiredHeader,
                                   int stringResourceNameOfFirstField,
                                   int stringResourceNameOfSecondField,
                                   boolean actionSucceeded) {

        String actualMessage = determineErrorMessage(
                application,
                isFilledFirstField,
                isFilledSecondField,
                stringResourceInvalidCombination,
                stringResourceDetailsRequiredHeader,
                stringResourceNameOfFirstField,
                stringResourceNameOfSecondField,
                actionSucceeded
        );

        assertTrue(actualMessage.equals(expectedMessage));
    }
}
