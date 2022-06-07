package nz.co.afleet.bit603_a3_johnmcpherson.ui;

import android.view.View;

import androidx.test.espresso.ViewInteraction;

import org.hamcrest.Matcher;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class TestUtilities {
    public static void confirmTextViewTextIsCorrect(int viewId, String expectedText) {
        // using View Matcher and ViewInteraction to do the check
        Matcher<View> viewMatcher = withId(viewId);
        ViewInteraction viewInteraction = onView(viewMatcher);
        viewInteraction.check(matches(withText(expectedText)));
    }

    public static void setViewInteractionText(ViewInteraction viewInteraction, String text) {
        viewInteraction.perform(replaceText(text));
    }
}
