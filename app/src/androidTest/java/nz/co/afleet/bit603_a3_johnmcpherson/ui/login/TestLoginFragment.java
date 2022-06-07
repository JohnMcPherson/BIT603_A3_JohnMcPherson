package nz.co.afleet.bit603_a3_johnmcpherson.ui.login;

import android.content.Context;
import android.view.View;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import nz.co.afleet.bit603_a3_johnmcpherson.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * This series of tests is functionally identical to those conducted in Assignment 1
 * The technical difference is that it operates on a Fragment (LoginFragment)
 * Tests have been modelled on Assignment 2, and code converted to work with the fragment
 * Note that we are using AndroidJUnit4 (not Robolectric), so a device (virtual or real) is required to support the tests
 */
@RunWith(AndroidJUnit4.class)
public class TestLoginFragment {
    FragmentScenario<LoginFragment> loginFragmentScenario;
    Context context;
    ViewInteraction viewInteractionUserName;
    ViewInteraction viewInteractionPassword;

    final String admin = "Admin";
    final String ADMIN_PASSWORD = "CookieManagement84";

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
        confirmTextViewTextIsCorrect(R.id.textUserLabel, "User");
        confirmTextViewTextIsCorrect(R.id.textPasswordLabel, "Password");
        confirmTextViewTextIsCorrect(R.id.buttonLogin, "Login");
    }

    public static void confirmTextViewTextIsCorrect(int viewId, String expectedText) {
        // using View Matcher and ViewInteraction to do the check
        Matcher<View> viewMatcher = withId(viewId);
        ViewInteraction viewInteraction = onView(viewMatcher);
        viewInteraction.check(matches(withText(expectedText)));
    }

    @Test
    public void initialText_isCorrect() {
        confirmTextViewTextIsCorrect(R.id.textUserLabel, "User");
        confirmTextViewTextIsCorrect(R.id.textPasswordLabel, "Password");
        confirmTextViewTextIsCorrect(R.id.buttonLogin, "Login");
        confirmErrorMessage("");
    }

    @Test
    public void testMandatoryIndicators() {
        testMandatoryIndicatorIsCorrect(R.id.mandatoryUser);
        testMandatoryIndicatorIsCorrect(R.id.mandatoryPassword);
    }

    private void testMandatoryIndicatorIsCorrect(int indicatorViewId) {
        confirmTextViewTextIsCorrect(indicatorViewId, "*");
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
        setUserName(admin);
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

    @Test
    public void errorMessageCorrectForAdminLogin() {
        // enter the correct user name and password and confirm error message is cleared
        setUserName(admin);
        setPassword(ADMIN_PASSWORD);
        clickLoginButton();
        confirmErrorMessage("");
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
        setViewInteractionText(viewInteractionUserName, userName);
    }

    private void setPassword(String password) {
        setViewInteractionText(viewInteractionPassword, password);
    }

    private static void setViewInteractionText(ViewInteraction viewInteraction, String text) {
        viewInteraction.perform(replaceText(text));
    }

    private void confirmErrorMessage(String requiredText) {
        confirmTextViewTextIsCorrect(R.id.textErrorMessage, requiredText);
    }

}