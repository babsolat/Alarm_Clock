package com.example.android.alarmclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    AlarmManager alarm_manager;
    TimePicker alarm_timePicker;
    TextView update_text;
    Context context;
    PendingIntent Pending_Intent;
    int choose_alarm_sound;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = this;


        //initialize our alarm manager
        alarm_manager = (AlarmManager) getSystemService(ALARM_SERVICE);


        // initialize our time picker
        alarm_timePicker = (TimePicker) findViewById(R.id.timePicker);


        // initialize our text update box
        update_text = (TextView) findViewById(R.id.update_text);

        // create an instance of a calendar

        final Calendar calendar = Calendar.getInstance();


        // Create an intent for the Alarm Receiver.
        final Intent my_intent  = new Intent(this.context, Alarm_Receiver.class);


        //initialize the start button
        final Button alarm_on = (Button) findViewById(R.id.alarm_on);



        // create spinner

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.alarm_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        // set an OnClick Listener to the OnItemSelected method
        spinner.setOnItemSelectedListener(this);



        //Create an onClick Listener to start the alarm
        alarm_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //setting calendar instance with the hour and minute that we picked on the time picker.

                calendar.set(Calendar.HOUR_OF_DAY, alarm_timePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE, alarm_timePicker.getCurrentMinute());

                // get the int values of the hour and minute.
                int hour = alarm_timePicker.getCurrentHour();
                int minute = alarm_timePicker.getCurrentMinute();


                //convert the int value to string
                String hour_string = Integer.toString(hour);
                String minute_string = Integer.toString(minute);

                if (hour > 12) {
                    hour_string = String.valueOf(hour - 12);
                }

                if (minute < 10) {
                    // 10:7 --> 10:07

                    minute_string = "0" + String.valueOf(minute);
                }


                // method that changes the update textBox.
                set_alarm_text("Alarm set to: " + hour_string + ":" + minute_string);



                // Create a pending Intent that delays the intent.
                //until the specified calendar time.

                // put in extra string into my_intent
                //tells the clock that you pressed alarm on button
                my_intent.putExtra("extra", "alarm on");

                // putin an extra long value into my_intent
                //Tells the clock that you want a certain value from the drop down value of the clock
                my_intent.putExtra("alarm_array_option", choose_alarm_sound);


                //Create a pending Intent that delays the Intent
                //Until the specified calendar time.
                Pending_Intent = PendingIntent.getBroadcast(MainActivity.this, 0, my_intent, PendingIntent.FLAG_UPDATE_CURRENT);


                // Set the Alarm manager
                alarm_manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), Pending_Intent);

            }

        });




        // initialize the stop button
        Button alarm_off = (Button) findViewById(R.id.alarm_off);


        //Create an onClick Listener to stop the alarm or undo the alert set

        alarm_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // method that changes the update textBox
                set_alarm_text("Alarm_off");


                //  Cancel the alarm
                alarm_manager.cancel(Pending_Intent);


                //put extra string into my intent
                //tell the clock that you pressed the "alarm_off" button
                my_intent.putExtra("extra","alarm off");

                // Also put an extra long into the alarm off section
                // To prevent crashes in a null pointer exception.
                my_intent.putExtra("alarm_array_option",choose_alarm_sound);


                //stop the ringtone
                sendBroadcast(my_intent);
            }
        });


    }

    private void set_alarm_text(String output) {
        update_text.setText(output);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)

        // outputting what ever value the user has selected.
        //display in short period of time
        Toast.makeText(getApplicationContext(), "The alarm tone is :",
                Toast.LENGTH_SHORT).show();
        choose_alarm_sound = (int)id;

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // Another interface callback
    }
}