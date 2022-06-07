package nz.co.afleet.bit603_a3_johnmcpherson;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import nz.co.afleet.bit603_a3_johnmcpherson.ui.login.LoginFragment;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class TestLoginFragment {
    @Before
    public void launchFragment() {
        androidx.fragment.app.testing.FragmentScenario.launchInContainer(LoginFragment.class);
    }

    @Test
    public void testLoginFragment() {
//        FragmentScenario fragmentScenario = androidx.fragment.app.testing.FragmentScenario.launchInContainer(LoginFragment.class);
        confirmTextViewTextIsCorrect(R.id.textUserLabel, "User");
        confirmTextViewTextIsCorrect(R.id.textPasswordLabel, "Password");
        confirmTextViewTextIsCorrect(R.id.buttonLogin, "Login");
    }

    public static void confirmTextViewTextIsCorrect(int viewId, String expectedText) {
        onView(withId(viewId)).check(matches(withText(expectedText)));
    }
}