package com.example.tipcalculator;

import java.util.List;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

public class AntiTheftService extends Service implements SensorEventListener {
        private final float MAX_CHANGE_X = 0.000000002f;
        private final float MAX_CHANGE_Y = 0.000000002f;
        private final float MAX_CHANGE_Z = 0.000000002f;
        private final float MOTION_LENGTH_ALLOWED = 5f;

        private Notification notification;
        protected static boolean isRunning = false;

        private float startMovementTime;
        private float prevMovementTime;
        private float[] accelSensors = {0,0,0};

        private SensorManager sensorManager;
        private Sensor accelerometer;

        private float mx = 0f;
        private float my = 0f;
        private float mz = 0f;

        protected static boolean alarmSounding = false;
        private Thread enterCode;
        private static Thread alarm;
        private MediaPlayer mp;

        @Override
        public IBinder onBind(Intent arg0) {
                return null;
        }

        public void onCreate()
        {
                notification = new Notification(R.drawable.ic_launcher,"Alarm is Triggered",System.currentTimeMillis());
                notification.flags |= Notification.FLAG_ONGOING_EVENT|Notification.FLAG_NO_CLEAR;
                notification.setLatestEventInfo(getApplicationContext(), "AntiTheft", "Service is Running",
                                PendingIntent.getActivity(this, 0, new Intent(this, AlarmActivity.class), 0));

                sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
                accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }

        public int onStartCommand(Intent intent, int flags, int startId)
        {
                Toast.makeText(this, "Alarm Activated", Toast.LENGTH_SHORT).show();
                startForeground(10, notification);
                startMovementTime = prevMovementTime = System.nanoTime();

                sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);

                return START_STICKY;
        }


        public void onDestroy()
        {
                Toast.makeText(this, "Alarm Deactivated", Toast.LENGTH_SHORT).show();
                isRunning = false;
                try {
                	 alarmSounding = false;
                alarm.stop();
                } catch(Exception e) {
                }
                sensorManager.unregisterListener(this);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

        @Override
        public void onSensorChanged(SensorEvent event) {
                if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER) return;

                if (!isRunning) {accelSensors[0] = event.values[0];accelSensors[1] = event.values[1];accelSensors[2] = event.values[2];isRunning = true;}


                float dx = (event.values[0] - accelSensors[0])/(event.timestamp - prevMovementTime);if (Math.abs(dx) > Math.abs(mx)) mx = dx;
                float dy = (event.values[1] - accelSensors[1])/(event.timestamp - prevMovementTime);if (Math.abs(dy) > Math.abs(my)) my = dy;
                float dz = (event.values[2] - accelSensors[2])/(event.timestamp - prevMovementTime);if (Math.abs(dz) > Math.abs(mz)) mz = dz;


                if (Math.abs(dx) > MAX_CHANGE_X || Math.abs(dy) > MAX_CHANGE_Y || Math.abs(dz) > MAX_CHANGE_Z)
                {
                        prevMovementTime = event.timestamp;
                        if (prevMovementTime - startMovementTime > 100000000f && !alarmSounding)//TODO: replace with 5000000000f
                                soundAlarm(event.timestamp);
                }
                else
                {
                        startMovementTime = prevMovementTime = event.timestamp;
                }

        }

        private void soundAlarm(float timestamp)
        {
                alarmSounding = true;
                try
                {
                        enterCode = new Thread(new Runnable() {

                                @Override
                                public void run() {
                                        // TODO Auto-generated method stub
                                     //   startActivity(new Intent(getApplicationContext(),EnterCode.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

                                }
                        });
                        enterCode.start();
                        
                }
                catch (Exception e)
                {}
                if (alarmSounding)
                {
                        alarm = new Thread(new Runnable() {

                                @Override
                                public void run() {
                                        // TODO Auto-generated method stub
//                                      MediaPlayer mp;
                                        try
                                        {
                                                mp = MediaPlayer.create(getApplicationContext(), R.raw.button123);
                                                try
                                                {
                                                        int delay = AlarmActivity.delay()*1000;
                                                        Thread.sleep(delay);
                                                        AudioManager am = ((AudioManager) getSystemService(Context.AUDIO_SERVICE));
                                                        while (alarmSounding)
                                                        {
                                                                if (!mp.isPlaying())
                                                                        mp.start();
                                                                	mp.setVolume(1.0f, 1.0f);
                                                                am.setStreamVolume(AudioManager.STREAM_MUSIC, am.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
                                                                Thread.sleep(500);
                                                        }
                                                        mp.stop();
                                                }
                                                catch (Exception e)
                                                {int x = 4;}
                                        }
                                        catch (Exception e)
                                        {int x = 4;}
                                }
                        });
                        alarm.start();
                        //while (alarmSounding);
                        //alarm.stop();
                }
        }

        protected static void silence()
        {
                alarmSounding = false;
                //alarm.stop();
        }
}