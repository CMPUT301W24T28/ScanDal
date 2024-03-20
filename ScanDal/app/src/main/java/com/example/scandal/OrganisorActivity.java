package com.example.scandal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
/**
 * Activity to the organizer's homepage
 */
public class OrganisorActivity extends AppCompatActivity {
    /**
     * A button to initiate the creation of an event
     */
    LinearLayout buttonCreateNewEvents;
    /**
     * A button to display an organizer's events
     */
    Button buttonSendNotifications;
    LinearLayout buttonViewMyEvents;
    /**
     * Button leading back to homepage
     */
    FrameLayout buttonBackToHomepage;
    /**
     * Provides functionality for buttons in the organizer homepage
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organisor_homepage);
        buttonSendNotifications = findViewById(R.id.sendnotifs);
        buttonCreateNewEvents = findViewById(R.id.buttonCreateNewEvents);
        buttonBackToHomepage = findViewById(R.id.buttonBackToHomepage);

        FrameLayout backToOrganiser = findViewById(R.id.buttonBack_OrganisorHomepage);


        // Navigate back to OrganisorActivity
        backToOrganiser.setOnClickListener(v -> finish());

        buttonCreateNewEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_create = new Intent(OrganisorActivity.this, EventActivity.class);
                startActivity(intent_create);
            }
        });

        // Navigate back to home page
        buttonBackToHomepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_home = new Intent(OrganisorActivity.this, HomeActivity.class);
                startActivity(intent_home);
            }
        });

        buttonSendNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = "Event Notification"; // This should be retrieved from a user input or your data model
                String message = "You have an upcoming event!"; // This should also be retrieved as above

                FcmNotificationsSender notificationsSender = new FcmNotificationsSender(
                        "/topics/all",
                        title,
                        message,
                        getApplicationContext(),
                        OrganisorActivity.this
                );

                notificationsSender.SendNotifications(); // Make sure you're calling the correct method (case-sensitive)
            }
        });
    }
}
