package com.example.scandal;

import android.content.Intent;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class HomeActivityIntentTest {

    @Rule
    public IntentsTestRule<HomeActivity> intentsTestRule = new IntentsTestRule<>(HomeActivity.class);

    @Before
    public void setUp() {
        // Setup tasks before each test, if needed
    }

    @Test
    public void testSettingsActivityIntent() {
        onView(withId(R.id.imageGearOne)).perform(click());
        intended(hasComponent(SettingsAndOrganiserActivity.class.getName()));
    }

    @Test
    public void testBrowseEventActivityIntent() {
        onView(withId(R.id.buttonBrowseEvents)).perform(click());
        intended(hasComponent(BrowseEventActivity.class.getName()));
    }

    @Test
    public void testAttendeeEventActivityIntent() {
        onView(withId(R.id.buttonViewMyAttendeeEvents)).perform(click());
        intended(hasComponent(AttendeeEventActivity.class.getName()));
    }

    @Test
    public void testQRCodeScannerIntent() {
        onView(withId(R.id.buttonScanQRCode)).perform(click());
        intended(hasComponent(QRCodeScanner.class.getName()));
    }

    @Test
    public void testProfileActivityIntent() {
        onView(withId(R.id.profilePicture)).perform(click());
        intended(hasComponent(Profile.class.getName()));
    }

    @Test
    public void testAdminLoginIntent() {
        onView(withId(R.id.buttonAdminLogin)).perform(click());
        intended(hasComponent(AdminActivity.class.getName()));
    }
}
