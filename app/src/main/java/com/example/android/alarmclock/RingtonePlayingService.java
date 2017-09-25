package com.example.android.alarmclock;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by Aluko on 9/11/2017.
 */

public class RingtonePlayingService extends Service {

    MediaPlayer media_song;
    int startId;
    boolean isRunning;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);


        //fetch the extra string from the alarm on / alarm off values
        String state = intent.getExtras().getString("extra");

        // fetch the alarm tone options
        Integer alarm_sound_choice = intent.getExtras().getInt("alarm_choice");
        Log.e("Ringtone extra is ", state);
        Log.e("alarm choice is ", alarm_sound_choice.toString());


        //put the notification here, test it out

        // notification
        // set the notification service
        NotificationManager notify_manager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

        // set up an intent that goes to the mainActivity
        Intent intent_main_activity = new Intent(this.getApplicationContext(),MainActivity.class);

        // set a pending intent
        PendingIntent pending_intent_main_activity = PendingIntent.getActivity(this, 0, intent_main_activity,0);


        // make the notification parameters
        Notification notification_popup = new Notification.Builder(this)
                .setContentTitle("An Alarm has going off")
                //.setSmallIcon(R.mipmap.ic_launcer)
                .setContentText("Click me")
                .setContentIntent(pending_intent_main_activity)
                .setAutoCancel(true)
                .getNotification();



        // This convert the extra strings from the intent to
        // the startId values, 0 and 1

        assert state != null;
        switch (state) {
            case "alarm on":
                startId = 1;
                break;
            case "alarm off":
                startId = 0;
                break;
            default:
                startId = 0;
                break;
        }


        // if else statements

        // if there is no music playing and the user press "alarm on"
        // music should start playing

        if (!this.isRunning && startId == 1){
            Log.e("there is no music, ","and u want start");

            this.isRunning = true;
            this.startId = 0;


            // set notification start command
            notify_manager.notify(0, notification_popup);

            // play the alarm sound depending on the past alarm choice id

            if (alarm_sound_choice == 0) {

                // pick a randomly selected audio file
                int minimum_number = 1;
                int maximum_number = 4;


                Random random_number = new Random();
                int alarm_number = random_number.nextInt(maximum_number + minimum_number );
                Log.e("Random number is ",String.valueOf(alarm_number));



                if (alarm_number == 1) {
                    // Create an instance of the media player
                    media_song = MediaPlayer.create(this, R.raw.car_alarm);
                    // start the ringtone
                    media_song.start();
                } else if (alarm_number == 2) {
                    // Create an instance of the media player
                    media_song = MediaPlayer.create(this, R.raw.dove);
                    // start the ringtone
                    media_song.start();
                } else if (alarm_number == 3) {
                    // Create an instance of the media player
                    media_song = MediaPlayer.create(this, R.raw.light);
                    // start the ringtone
                    media_song.start();
                } else if (alarm_number == 4) {
                    // Create an instance of the media player
                    media_song = MediaPlayer.create(this, R.raw.tropicalal);
                    // start the ringtone
                    media_song.start();
                } else {
                    // Create an instance of the media player
                    media_song = MediaPlayer.create(this, R.raw.dove);
                    // start the ringtone
                    media_song.start();
                }
            }


            else if (alarm_sound_choice == 1){
                // Create an instance of the media player
                media_song = MediaPlayer.create(this, R.raw.dove);
                // start the ringtone
                media_song.start();
            }

            else if (alarm_sound_choice == 2){
                // Create an instance of the media player
                media_song = MediaPlayer.create(this, R.raw.light);
                // start the ringtone
                media_song.start();

            }

            else if (alarm_sound_choice == 3) {
                // Create an instance of the media player
                media_song = MediaPlayer.create(this, R.raw.tropicalal);
                // start the ringtone
                media_song.start();

            }

            else if (alarm_sound_choice == 4){
                // Create an instance of the media player
                media_song = MediaPlayer.create(this, R.raw.car_alarm);
                // start the ringtone
                media_song.start();
            }

            else {
                // Create an instance of the media player
                media_song = MediaPlayer.create(this, R.raw.dove);
                // start the ringtone
                media_song.start();
            }



        }

        // if there is music playing and the user pressed "alarm off"
        // music should stop playing

        else if (this.isRunning && startId ==0){
            Log.e("there is music, ","and u want end");

            // stop the ring tone
            media_song.stop();
            media_song.reset();

            this.isRunning = false;
            this.startId = 0;
        }



        // if the user press random button
        // just to bug proof the app
        // if there is no music playing and the user presses
        // alarm off, do nothing.

       else if (!this.isRunning && startId ==0){
            Log.e("there is no music, ","and u want end");

            this.isRunning = false;
            this.startId =0;
        }


       // if there is music playing and the user presses "alarm on"
        // do nothing
        else if (this.isRunning && startId == 1) {
            Log.e("there is music, ","and u want start");

            this.isRunning = true;
            this.startId = 1;
        }


        // the final statement of if nothing is done.
        // or if none of the above is done
        else {
            Log.e("if there is sound ", " and you want end");
        }


        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        // Tell the user we stopped.
        Log.e("on destroy called", "ohh yah");
        super.onDestroy();
        this.isRunning = false;

        Toast.makeText(this, "On destroy called", Toast.LENGTH_SHORT).show();


    }
}
