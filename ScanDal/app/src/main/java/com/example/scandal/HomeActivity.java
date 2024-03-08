package com.example.scandal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Placeholder;

/**
 * Activity for homepage. Displays homepage to the User and provides functionality.
 */
public class HomeActivity extends AppCompatActivity {
    /**
     * Profile image
     */
    private ImageView profile;
    /**
     * Layout for QRCode scanner
     */
    private LinearLayout scan;
    /**
     * Clickable text for viewing attendee events
     */
    private TextView attendeeEvents;
    /**
     * Clickable text for browsing events
     */
    private TextView eventBrowser;

    /**
     * Makes class attributes clickable
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr_scanning_page);
        profile = findViewById(R.id.profilePicture);
        scan = findViewById(R.id.buttonScanQRCode);
        attendeeEvents = findViewById(R.id.buttonViewMyAttendeeEvents);
        eventBrowser = findViewById(R.id.buttonBrowseEvents);
        ImageView settings = findViewById(R.id.imageGearOne);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(HomeActivity.this, SettingsAndOrganiserActivity.class);
                startActivity(myintent);


            }
        });
//        eventBrowser.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent myintent = new Intent(HomeActivity.this, PlaceHolder.class);
//                startActivity(myintent);
//
//
//            }
//        });
//        attendeeEvents.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent myintent = new Intent(HomeActivity.this, PlaceHolder.class); // replace placeholder class with the activity for attendee
//                startActivity(myintent);
//            }
//        });
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myintent = new Intent(HomeActivity.this, QRCodeScanner.class);
                myintent.putExtra("Activity", 1);
                startActivity(myintent);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myintent = new Intent(HomeActivity.this, Profile.class);
                startActivity(myintent);
            }
        });

    }
}
