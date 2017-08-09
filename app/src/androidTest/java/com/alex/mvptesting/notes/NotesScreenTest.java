package com.alex.mvptesting.notes;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.alex.mvptesting.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class NotesScreenTest {

    @Rule
    public ActivityTestRule<NotesActivity> notesActivityActivityTestRule =
            new ActivityTestRule<NotesActivity>(NotesActivity.class);

    @Test
    public void clickFabButton_opensAddNoteScreen() {
        onView(withId(R.id.fab_add_note)).perform(click());

        onView(withId(R.id.toolbar_add_note)).check(matches(isDisplayed()));
    }

    @Test
    public void clickListItem_opensNoteDetailsScreen() {

    }
}