package com.sliit.madlab11;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import android.os.Build;
import android.os.Bundle;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import android.content.Intent;
import android.app.PendingIntent;
import android.widget.TextView;

import com.sliit.madlab11.database.DBHandler;


public class Student extends AppCompatActivity {

    private static final String CHANNEL_ID = "0001";
    TextView txt_studentName;
    String studentName;
    RecyclerView recyclerView;
    ArrayList<String> users, subjects, messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        txt_studentName = findViewById(R.id.txt_student);
        recyclerView = findViewById(R.id.recyclerView);

        users = new ArrayList<>();
        subjects = new ArrayList<>();
        messages = new ArrayList<>();

        saveMessageData();

        CustomAdapter customAdapter = new CustomAdapter(users,subjects,messages,Student.this);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Student.this));

        createNotificationChannel();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null){
            studentName = (String) bundle.get("username");
            txt_studentName.setText("Welcome " + studentName);
        }
    }

    void saveMessageData() {
        DBHandler dbHandler = new DBHandler(getApplicationContext());

        users = (ArrayList<String>) dbHandler.readAllMessage("User");
        subjects = (ArrayList<String>) dbHandler.readAllMessage("Subject");
        messages = (ArrayList<String>) dbHandler.readAllMessage("Message");
    }

    private void createNotificationChannel() {
        String un, msg, sub;

        un = users.get(0);
        sub = subjects.get(0);
        msg = messages.get(0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(this, Message.class);

        intent.putExtra("user",un);
        intent.putExtra("subject",sub);
        intent.putExtra("message",msg);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("You have a new Message")
                .setContentText("Click here to view latest message!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(0, builder.build());
    }
}