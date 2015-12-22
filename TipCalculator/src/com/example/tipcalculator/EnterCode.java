package com.example.tipcalculator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EnterCode extends Activity {
        Button btnOK;
        Button btnCancel;
        EditText tbCode;

        /** Called when the activity is first created. */
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_code);
        
        btnOK = (Button) findViewById(R.id.btnOk);
        tbCode = (EditText) findViewById(R.id.tbCode);
        
        btnOK.setOnClickListener(new View.OnClickListener() {
                
                @Override
                public void onClick(View v) {
                        if (AlarmActivity.isCode(tbCode.getText().toString())) AntiTheftService.silence();
                        else Toast.makeText(getApplicationContext(), "incorrect password", Toast.LENGTH_LONG).show();
                        finish();
                }
        });
        
        }

}