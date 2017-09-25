package com.example.android.alarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Aluko on 9/9/2017.
 */

public class Alarm_Receiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e("we are in the receiver.", "Yay!");


        // fetch extra strings from the intent
        // tells the app whether the user pressed the alarm on button or the alarm off button
        String get_your_string = intent.getExtras().getString("extra");

        Log.e("what is the key ?", get_your_string);

        // fetch the extra long from the intent
        // tells the app the  value the user picked from the drop down menu Spinner
        Integer get_your_alarm_choice = intent.getExtras().getInt("alarm_choice");

        Log.e("the alarm choice is ?", get_your_alarm_choice.toString());

        // Create an Intent to the Ringtone Service
        Intent service_intent = new Intent(context, RingtonePlayingService.class);
        //pass the extra string from Receiver to Ringtone Playing Service
        service_intent.putExtra("extra",get_your_string);

        // pass the extra integer from the Receiver to the ringtone playing services
        service_intent.putExtra("alarm_choice",get_your_alarm_choice);

        // start the Ringtone service
        context.startService(service_intent);

    }
}
