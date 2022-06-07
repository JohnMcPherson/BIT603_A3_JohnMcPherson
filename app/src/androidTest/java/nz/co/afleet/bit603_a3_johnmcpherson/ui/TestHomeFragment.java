package nz.co.afleet.bit603_a3_johnmcpherson.ui;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import nz.co.afleet.bit603_a3_johnmcpherson.R;
import nz.co.afleet.bit603_a3_johnmcpherson.database.User;
import nz.co.afleet.bit603_a3_johnmcpherson.ui.home.HomeFragment;

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
public class TestHomeFragment {
    FragmentScenario<HomeFragment> loginFragmentScenario;
    Context context;
    private ViewInteraction welcomeMessage;

    final String ADMIN = "Admin";
    final String ADMIN_PASSWORD = "CookieManagement84";

    @Before
    public void launchFragmentAndLogin() {
        loginFragmentScenario = FragmentScenario.launchInContainer(HomeFragment.class);
        loginFragmentScenario.onFragment(action ->{
            context = action.getContext();
        });
        User.loginUser(context, ADMIN, ADMIN_PASSWORD);
        welcomeMessage = onView(ViewMatchers.withId(R.id.textViewWelcomeMessage));
    }

    @Test
    public void test() {

    }


}