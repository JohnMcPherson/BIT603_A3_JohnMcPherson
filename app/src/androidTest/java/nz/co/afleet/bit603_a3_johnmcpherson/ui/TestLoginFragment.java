package nz.co.afleet.bit603_a3_johnmcpherson.ui;

import android.content.Context;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import nz.co.afleet.bit603_a3_johnmcpherson.R;
import nz.co.afleet.bit603_a3_johnmcpherson.ui.TestUtilities;
import nz.co.afleet.bit603_a3_johnmcpherson.ui.login.LoginFragment;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

/**
 * This series of tests is functionally identical to those conducted in Assignment 1
 * The technical difference is that it operates on a Fragment (LoginFragment)
 * Tests have been modelled on Assignment 2, and code converted to work with the fragment
 * Note that we are using AndroidJUnit4 (not Robolectric), so a device (virtual or real) is required to support the tests
 */
@RunWith(AndroidJUnit4.class)
public class TestLoginFragment {
    private FragmentScenario<LoginFragment> loginFragmentScenario;
    private Context context;
    private ViewInteraction viewInteractionUserName;
    private ViewInteraction viewInteractionPassword;

    @Before
    public void launchFragment() {
        loginFragmentScenario = androidx.fragment.app.testing.FragmentScenario.launchInContainer(LoginFragment.class);
        loginFragmentScenario.onFragment(action ->{
            context = action.getContext();
        });
        viewInteractionUserName = onView(ViewMatchers.withId(R.id.editTextUserName));
        viewInteractionPassword = onView(withId(R.id.editTextPassword));
    }

    @Test
    public void testLoginFragment() {
        androidx.fragment.app.testing.FragmentScenario.launchInContainer(LoginFragment.class);
        TestUtilities.confirmTextViewTextIsCorrect(R.id.textUserLabel, "User");
        TestUtilities.confirmTextViewTextIsCorrect(R.id.textPasswordLabel, "Password");
        TestUtilities.confirmTextViewTextIsCorrect(R.id.buttonLogin, "Login");
    }

    @Test
    public void initialText_isCorrect() {
        TestUtilities.confirmTextViewTextIsCorrect(R.id.textUserLabel, "User");
        TestUtilities.confirmTextViewTextIsCorrect(R.id.textPasswordLabel, "Password");
        TestUtilities.confirmTextViewTextIsCorrect(R.id.buttonLogin, "Login");
        confirmErrorMessage("");
    }

    @Test
    public void testMandatoryIndicators() {
        testMandatoryIndicatorIsCorrect(R.id.mandatoryUser);
        testMandatoryIndicatorIsCorrect(R.id.mandatoryPassword);
    }

    private void testMandatoryIndicatorIsCorrect(int indicatorViewId) {
        TestUtilities.confirmTextViewTextIsCorrect(indicatorViewId, "*");
        // colour not tested
    }

    @Test
    public void errorMessagesAreCorrect_NotLoggedIn() {
        // check our starting point
        confirmErrorMessage("");

        //click the login button and check initial error message
        clickLoginButton();
        confirmErrorMessage("Please enter your user name and password");

        //enter a user name and check missing password message
        String ADMIN = "Admin";
        setUserName(ADMIN);
        clickLoginButton();
        confirmErrorMessage("Please enter your password");

        //enter an incorrect password and confirm error message
        setPassword("junkpassword");
        clickLoginButton();
        confirmErrorMessage("Sorry. We did not recognise that user/password combination. Please try again");

        // clear the user name and check missing user name message
        setUserName("");
        clickLoginButton();
        confirmErrorMessage("Please enter your user name"); // Current Problem
    }

    private void clickLoginButton() {
        // get the ViewInteraction
        ViewInteraction loginButtonViewInteraction = getViewInteraction(R.id.buttonLogin) ;
        // click
        loginButtonViewInteraction.perform(click());
    }


    /**
     *
     * @param viewId
     * @return the ViewInteraction matching the viewId
     */
    private ViewInteraction getViewInteraction(int viewId) {
        return onView(withId(viewId));
    }

    private void setUserName(String userName) {
        TestUtilities.setViewInteractionText(viewInteractionUserName, userName);
    }

    private void setPassword(String password) {
        TestUtilities.setViewInteractionText(viewInteractionPassword, password);
    }

    private void confirmErrorMessage(String requiredText) {
        TestUtilities.confirmTextViewTextIsCorrect(R.id.textErrorMessage, requiredText);
    }

}