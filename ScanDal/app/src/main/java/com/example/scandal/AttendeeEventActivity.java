package com.example.scandal;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Activity for displaying events attended by an attendee.
 */
public class AttendeeEventActivity extends AppCompatActivity {
    /**
     * FrameLayout for navigating back to the main page.
     */
    FrameLayout backMain;
    /**
     * ListView for displaying events.
     */
    ListView eventsList;
    /**
     * Firebase Firestore instance for database operations.
     */
    FirebaseFirestore db;
    /**
     * Called when the activity is starting. This is where most initialization should go:
     * calling setContentView(int) to inflate the activity's UI, initializing objects, etc.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     *                           Note: Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_events_page);

        backMain = findViewById(R.id.buttonBack_MyEventsPage);
        eventsList = findViewById(R.id.listView_MyEventsPage);
        db = FirebaseFirestore.getInstance();

        backMain.setOnClickListener(v -> finish());
        eventsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String eventName = (String) parent.getItemAtPosition(position);
                Intent intent = new Intent(AttendeeEventActivity.this, SignedUpEventDetailsActivity.class);
                intent.putExtra("eventName", eventName);
                startActivity(intent);
            }
        });
        loadEvents();
    }
    /**
     * Retrieves and displays event pulled from firebase
     */
    private void loadEvents() {
        List<String> eventNames = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, eventNames);
        eventsList.setAdapter(adapter);

        final String deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        db.collection("profiles")
                .whereEqualTo("deviceId", deviceId)
                .limit(1)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                        Map<String, Object> profileData = documentSnapshot.getData();
                        if (profileData != null && profileData.containsKey("signedUp")) {
                            Map<String, Object> signedUpEvent = (Map<String, Object>) profileData.get("signedUp");
                            // Load every events a user signed up
                            for (Object eventNameObject : signedUpEvent.values()) {
                                String eventName = (String) eventNameObject;
                                if (eventName != null) {
                                    eventNames.add(eventName);
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        }
                    }
                });
    }
}
