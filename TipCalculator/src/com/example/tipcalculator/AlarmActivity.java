package com.example.tipcalculator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

public class AlarmActivity extends Activity {
        private ToggleButton btnEnabled;
        private Button btnSetCode;
        private Button btnSetDelay;
        
        private EditText tbsCode;
        private EditText tbgCode;
        private EditText tbcCode;
        private EditText tbtCode;
        

        private AlertDialog dlgSetCode;
        private AlertDialog dlgGetCode;
        private AlertDialog dlgConfirmCode;
        private AlertDialog dlgTestCode;
        
        private AlertDialog dlgSetDelay;
        private AlertDialog dlgAlert;
        
        Intent ATService;
        private static String code = "123";
        private String temp;
        private boolean tempb;
        
        private final static String SERVICE_ARMED = "AntiTheft is currently armed. Please disarm before changing settings.";
        private final static String CODE_NOT_SET = "Please set a code before arming.";
        private final static String ENTER_CURRENT_CODE = "Enter the current Password";
        private final static String ENTER_NEW_CODE = "Enter the new Password";
        private final static String CONFIRM_NEW_CODE = "Confirm the new Password";
        
        private final static int[] delay = {3,5,10,15,20};
        public static int waitFor = delay[0];
        
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarmactivity);
        
        btnEnabled = (ToggleButton) findViewById(R.id.btnEnabled);
        btnEnabled.setOnClickListener(enabledListener);btnEnabled.setChecked(AntiTheftService.isRunning);
        btnSetCode = (Button) findViewById(R.id.btnSetCode);
        btnSetCode.setOnClickListener(setCodeListener);
        btnSetDelay = (Button) findViewById(R.id.btnSetDelay);
        btnSetDelay.setOnClickListener(setDelayListener);

        tbsCode = new EditText(this);tbsCode.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        tbgCode = new EditText(this);tbgCode.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        tbcCode = new EditText(this);tbcCode.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        tbtCode = new EditText(this);tbtCode.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        
        dlgSetCode = (new AlertDialog.Builder(this)).setTitle("Set Password").setMessage(ENTER_CURRENT_CODE).setView(tbsCode)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        if (code.equals(tbsCode.getText().toString())) getNewCode();
                                        else Toast.makeText(getApplicationContext(), "Incorrect password", Toast.LENGTH_SHORT).show();
                                }})
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                                // Nothing
                                }
                                }).create();
        
        dlgGetCode = (new AlertDialog.Builder(this)).setTitle("Set Password").setMessage(ENTER_NEW_CODE)
                        .setView(tbgCode).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        temp = tbgCode.getText().toString();
                                        confirmNewCode();
                                }})
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                        // Nothing
                                }
                                }).create();
        
        dlgConfirmCode = (new AlertDialog.Builder(this)).setTitle("Set Password").setMessage(CONFIRM_NEW_CODE)
                        .setView(tbcCode).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        if (temp.equals(tbcCode.getText().toString()))
                                        {
                                                code = temp;
                                                Toast.makeText(getApplicationContext(), "Password Changed Successfully", Toast.LENGTH_SHORT).show();
                                        }
                                        else Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                                }})
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                        // Nothing
                                }
                        }).create();
        
        dlgTestCode = (new AlertDialog.Builder(this)).setTitle("Enter Password").setMessage(ENTER_CURRENT_CODE)
                        .setView(tbtCode).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        if (code.equals(tbtCode.getText().toString()))
                                        {
                                                stopService(ATService);
                                                AntiTheftService.alarmSounding = false;
                                                btnEnabled.setChecked(false);
                                        }
                                        else Toast.makeText(getApplicationContext(), "Incorrect Password", Toast.LENGTH_SHORT).show();
                                }})
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                        // Nothing
                                }
                        }).create();

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        final CharSequence[] items = {delay[0]+" seconds", delay[1]+" seconds", delay[2]+" seconds",delay[3]+" seconds",delay[4]+" seconds"};
        builder1.setTitle("Delay before alarm sounds");
        builder1.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                Toast.makeText(getApplicationContext(), "alarm delay set to "+items[item], Toast.LENGTH_SHORT).show();
                waitFor = delay[item];
                dialog.dismiss();
            }
        });
        dlgSetDelay = builder1.create();
        
        AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
        builder2.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                        }
                });
        dlgAlert = builder2.create();
        
//        String ns = Context.NOTIFICATION_SERVICE;
//        NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
//        int icon = R.drawable.ic_launcher;
//        CharSequence tickerText = "AntiTheft Running";
//        long when = System.currentTimeMillis();
//        Notification notification = new Notification(icon,tickerText,when);
//        Context context = getApplicationContext();
//        CharSequence contentTitle = "AntiTheft";
//        CharSequence contentText = "Service is Running";
//        Intent notificationIntent = new Intent(this, AntiTheftMain.class);
//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
//        notification.flags |= Notification.FLAG_ONGOING_EVENT|Notification.FLAG_NO_CLEAR;
//        notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
//        mNotificationManager.notify(1, notification);
        
        ATService = new Intent(this, AntiTheftService.class);
    }
    
    private boolean isArmed() {
                // TODO Auto-generated method stub
                return false;
        }

        private OnClickListener enabledListener = new OnClickListener() {
                
                @Override
                public void onClick(View v) {
                        if (btnEnabled.isChecked())
                                if (code != "") startService(ATService);
                                else
                                {
                                        dlgAlert.setTitle(CODE_NOT_SET);
                                        dlgAlert.show();
                                        btnEnabled.setChecked(false);
                                }
                        else testCode();
                }
        };
        
        private OnClickListener setCodeListener = new OnClickListener() {
                
                @Override
                public void onClick(View v) {
                        if (btnEnabled.isChecked()) {dlgAlert.setTitle(SERVICE_ARMED);dlgAlert.show();}
                        else setCode();
                }
        };
        
        private OnClickListener setDelayListener = new OnClickListener() {
                
                @Override
                public void onClick(View v) {
                        if (btnEnabled.isChecked()) {dlgAlert.setTitle(SERVICE_ARMED);dlgAlert.show();}
                        else dlgSetDelay.show();
                        
                }
        };
        
        private void testCode()
        {
                btnEnabled.setChecked(true);
                tbtCode.setText("");
                dlgTestCode.show();
        }
        
        private void setCode()
        {
                tbsCode.setText("");
                dlgSetCode.show();
        }
        
        private void getNewCode()
        {
                tbgCode.setText("");
                dlgGetCode.show();
        }
        
        private void confirmNewCode()
        {
                tbcCode.setText("");
                dlgConfirmCode.show();
        }
        
        protected static boolean isCode(String test)
        {
                return code.equals(test);
        }
        
        protected static int delay()
        {
                return waitFor;
        }
        
        public void onClickMain2(View view)
        {
     	   MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.buttonclick);
     	   mp.start();
     	   super.onBackPressed();
        } 
}
