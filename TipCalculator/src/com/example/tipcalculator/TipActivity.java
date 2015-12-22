
/*
package com.example.tipcalculator;
import android.app.Activity;
import android.os.Bundle;
public class TipActivity extends Activity{
	@Override
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.tipactivity);
	}
}billEditTextWatcher
*/

package com.example.tipcalculator;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

// main Activity class for the TipCalculator
public class TipActivity extends Activity 
{
	
	DatabaseHelper myDb;
   // constants used when saving/restoring state
   private static final String BILL_TOTAL = "BILL_TOTAL";
   private static final String CUSTOM_PERCENT = "CUSTOM_PERCENT";
   private static final String SPLIT_ON = "SPLIT_ON";
   Button btnAddData;
   private double currentBillTotal; 
   private int currentCustomPercent; 
   private EditText tip10EditText; 
   private EditText total10EditText; 
   private EditText tip15EditText; 
   private EditText TipPer1EditText;
   private EditText TipPer2EditText;
   SeekBar customSeekBar;
   private EditText TipPer3EditText;
   private EditText TotalPer1EditText;
   private EditText TotalPer2EditText;
   private EditText TotalPer3EditText;
   private EditText tipperCustomEditText;
   private EditText totalperCustomEditText;
   private EditText total15EditText; 
   private EditText billEditText; 
   private EditText tip20EditText; 
   private EditText total20EditText;  
   private EditText BillPerEdit;
   private TextView customTipTextView; 
   private EditText tipCustomEditText; 
   private EditText totalCustomEditText; 
   private EditText billSplitText;  
   private int currentSplit;

   // Called when the activity is first created.
   @Override
   public void onCreate(Bundle savedInstanceState) 
   {
      super.onCreate(savedInstanceState); // call superclass's version
      setContentView(R.layout.tipactivity); // inflate the GUI
      myDb = new DatabaseHelper(this);

      // check if app just started or is being restored from memory
      if ( savedInstanceState == null ) // the app just started running
      {
         currentBillTotal = 0.0; // initialize the bill amount to zero
         currentCustomPercent = 17; // initialize the custom tip to 18%
        
      } // end if
      else // app is being restored from memory, not executed from scratch
      {
         // initialize the bill amount to saved amount
         currentBillTotal = savedInstanceState.getDouble(BILL_TOTAL); 
         
         // initialize the custom tip to saved tip percent 
         currentCustomPercent = 
            savedInstanceState.getInt(CUSTOM_PERCENT);
         
         currentSplit = savedInstanceState.getInt(SPLIT_ON);
         
         
      } // end else
      
      // get references to the 10%, 15% and 20% tip and total EditTexts
      tip10EditText = (EditText) findViewById(R.id.tip10EditText);
      total10EditText = (EditText) findViewById(R.id.total10EditText);
      tip15EditText = (EditText) findViewById(R.id.tip15EditText);
      total15EditText = (EditText) findViewById(R.id.total15EditText);
      tip20EditText = (EditText) findViewById(R.id.tip20EditText);
      total20EditText = (EditText) findViewById(R.id.total20EditText);
      TipPer1EditText = (EditText) findViewById(R.id.TipPer1EditText);
      TipPer2EditText = (EditText) findViewById(R.id.TipPer2EditText);
      TipPer3EditText = (EditText) findViewById(R.id.TipPer3EditText);
      
      TotalPer1EditText = (EditText) findViewById(R.id.TotalPer1EditText);
      TotalPer2EditText = (EditText) findViewById(R.id.TotalPer2EditText);
      TotalPer3EditText = (EditText) findViewById(R.id.TotalPer3EditText);
      TipPer2EditText = (EditText) findViewById(R.id.TipPer2EditText);
      TipPer3EditText = (EditText) findViewById(R.id.TipPer3EditText);
      
      // get the TextView displaying the custom tip percentage
      customTipTextView = (TextView) findViewById(R.id.customTipTextView);
      
      // get the custom tip and total EditTexts 
      tipCustomEditText = (EditText) findViewById(R.id.tipCustomEditText);
      totalCustomEditText = 
         (EditText) findViewById(R.id.totalCustomEditText);

      // get the billEditText 
      billEditText = (EditText) findViewById(R.id.billEditText);

      // billEditTextWatcher handles billEditText's onTextChanged event
      billEditText.addTextChangedListener(billEditTextWatcher);
      
      
      billSplitText = (EditText) findViewById(R.id.billSplitText);
      billSplitText.addTextChangedListener(billSplitTextWatcher);
      
      BillPerEdit = (EditText) findViewById(R.id.BillPerEdit);
      
      tipperCustomEditText = (EditText) findViewById(R.id.tipperCustomEditText);
      totalperCustomEditText = (EditText) findViewById(R.id.totalperCustomEditText);

      
      // get the SeekBar used to set the custom tip amount
      customSeekBar = (SeekBar) findViewById(R.id.customSeekBar);
      customSeekBar.setOnSeekBarChangeListener(customSeekBarListener);
      btnAddData = (Button)findViewById(R.id.button6);
      AddData();
   } // end method onCreate

   
   
   
   //Added this to get the values from text to insert data into database.
   public  void AddData() {
       btnAddData.setOnClickListener(
               new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                	   MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.buttonclick);
               		mp.start();
                      boolean isInserted = myDb.insertData(customSeekBar.getProgress(),
                    		  billEditText.getText().toString(),
                               tipCustomEditText.getText().toString(),
                               totalCustomEditText.getText().toString(),
                               billSplitText.getText().toString()
                               );
                       if(isInserted =true)
                           Toast.makeText(TipActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
                       else
                           Toast.makeText(TipActivity.this,"Data not Inserted",Toast.LENGTH_LONG).show();
                   }
               }
       );
   }
   

   
   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
       // Handle action bar item clicks here. The action bar will
       // automatically handle clicks on the Home/Up button, so long
       // as you specify a parent activity in AndroidManifest.xml.
       int id = item.getItemId();

       //noinspection SimplifiableIfStatement
       if (id == R.id.action_settings) {
           return true;
       }

       return super.onOptionsItemSelected(item);
   }
   
   
   
   
   
   
   
   
   
   
   
   
   // updates 10, 15 and 20 percent tip EditTexts
   private void updateStandard() 
   {
      // calculate bill total with a ten percent tip
      double tenPercentTip = currentBillTotal * .1;
      double tenPercentTotal = currentBillTotal + tenPercentTip;
      
      double BillPerPersonvalue;
      
      
       BillPerPersonvalue = currentBillTotal / currentSplit;
     
       if(currentSplit == 0)
       {
    	   BillPerPersonvalue=0.0;
       }
       
       double TipPer1Total = tenPercentTip / currentSplit;
       
       if(currentSplit == 0)
       {
    	   TipPer1Total = 0.0;
       }
       
       double TotalPer1Total = tenPercentTotal / currentSplit;
       
       if(currentSplit == 0)
       {
    	   TotalPer1Total = 0.0;
       }
      
      BillPerEdit.setText(String.format("%.02f",BillPerPersonvalue));
      
      // set tipTenEditText's text to tenPercentTip
      tip10EditText.setText(String.format("%.02f", tenPercentTip));

      // set totalTenEditText's text to tenPercentTotal
      total10EditText.setText(String.format("%.02f", tenPercentTotal));
      TipPer1EditText.setText(String.format("%.02f", TipPer1Total));
      TotalPer1EditText.setText(String.format("%.02f", TotalPer1Total));
      

      // calculate bill total with a fifteen percent tip
      double fifteenPercentTip = currentBillTotal * .15;
      double fifteenPercentTotal = currentBillTotal + fifteenPercentTip;
      
      double TipPer2Total = fifteenPercentTip / currentSplit;
      
      
      
      if(currentSplit == 0)
      {
   	   TipPer2Total = 0.0;
      }
      
      double TotalPer2Total = fifteenPercentTotal /currentSplit;
      
      if(currentSplit == 0)
      {
   	   TotalPer2Total = 0.0;
      }

      // set tipFifteenEditText's text to fifteenPercentTip
      tip15EditText.setText(String.format("%.02f", fifteenPercentTip));

      // set totalFifteenEditText's text to fifteenPercentTotal
      total15EditText.setText(
         String.format("%.02f", fifteenPercentTotal));
      TipPer2EditText.setText(String.format("%.02f", TipPer2Total));
      TotalPer2EditText.setText(String.format("%.02f", TotalPer2Total));

      // calculate bill total with a twenty percent tip
      double twentyPercentTip = currentBillTotal * .20;
      double twentyPercentTotal = currentBillTotal + twentyPercentTip;
      double TipPer3Total = twentyPercentTip / currentSplit;
      
      if(currentSplit == 0)
      {
   	   TipPer3Total = 0.0;
      }
      double TotalPer3Total = twentyPercentTotal /currentSplit;
      
      if(currentSplit == 0)
      {
   	   TotalPer3Total = 0.0;
      }

      // set tipTwentyEditText's text to twentyPercentTip
      tip20EditText.setText(String.format("%.02f", twentyPercentTip));

      // set totalTwentyEditText's text to twentyPercentTotal
      total20EditText.setText(String.format("%.02f", twentyPercentTotal));
      TipPer3EditText.setText(String.format("%.02f", TipPer3Total));
      TotalPer3EditText.setText(String.format("%.02f", TotalPer3Total));
   } // end method updateStandard

   // updates the custom tip and total EditTexts
   private void updateCustom() 
   {
      // set customTipTextView's text to match the position of the SeekBar
      customTipTextView.setText(currentCustomPercent + "%");

      // calculate the custom tip amount
      double customTipAmount = 
         currentBillTotal * currentCustomPercent * .01;

      // calculate the total bill, including the custom tip
      double customTotalAmount = currentBillTotal + customTipAmount;
      
      double TipPerPerson = customTipAmount / currentSplit;
      if(currentSplit == 0)
      {
    	  TipPerPerson = 0.0;
      }
      
      double TotalPerPerson = customTotalAmount / currentSplit;
      
      if(currentSplit == 0)
      {
    	  TotalPerPerson = 0.0;
      }
      
      tipperCustomEditText.setText(String.format("%.02f", TipPerPerson));
      totalperCustomEditText.setText(String.format("%.02f", TotalPerPerson));
      

      // display the tip and total bill amounts
      tipCustomEditText.setText(String.format("%.02f", customTipAmount));
      totalCustomEditText.setText(
         String.format("%.02f", customTotalAmount));
      
   } // end method updateCustom

   // save values of billEditText and customSeekBar
   @Override
   protected void onSaveInstanceState(Bundle outState)
   {
      super.onSaveInstanceState(outState);
      
      outState.putDouble(BILL_TOTAL, currentBillTotal);
      outState.putInt(CUSTOM_PERCENT, currentCustomPercent);
      outState.putInt(SPLIT_ON,currentSplit);
   } // end method onSaveInstanceState
   
   // called when the user changes the position of SeekBar
   private OnSeekBarChangeListener customSeekBarListener = 
      new OnSeekBarChangeListener() 
   {
      // update currentCustomPercent, then call updateCustom
      @Override
      public void onProgressChanged(SeekBar seekBar, int progress,
         boolean fromUser) 
      {
         // sets currentCustomPercent to position of the SeekBar's thumb
         currentCustomPercent = seekBar.getProgress();
         updateCustom(); // update EditTexts for custom tip and total
      } // end method onProgressChanged

      @Override
      public void onStartTrackingTouch(SeekBar seekBar) 
      {
      } // end method onStartTrackingTouch

      @Override
      public void onStopTrackingTouch(SeekBar seekBar) 
      {
      } // end method onStopTrackingTouch
   }; // end OnSeekBarChangeListener

   // event-handling object that responds to billEditText's events
   private TextWatcher billEditTextWatcher = new TextWatcher() 
   {
      // called when the user enters a number
      @Override
      public void onTextChanged(CharSequence s, int start, 
         int before, int count) 
      {         
         // convert billEditText's text to a double
         try
         {
        	 
            currentBillTotal = Double.parseDouble(s.toString());
         } // end try
         catch (NumberFormatException e)
         {
            currentBillTotal = 0.0; // default if an exception occurs
         } // end catch 
         
         
         try
         {
        	 currentSplit=Integer.parseInt(billSplitText.getText().toString());
            
         } // end try
         catch (NumberFormatException e)
         {
        	 currentSplit = 0; // default if an exception occurs
         }

         // update the standard and custom tip EditTexts
         updateStandard(); // update the 10, 15 and 20% EditTexts
         updateCustom(); // update the custom tip EditTexts
      } // end method onTextChanged
      
      @Override
      public void afterTextChanged(Editable s) 
      {
      } // end method afterTextChanged

      @Override
      public void beforeTextChanged(CharSequence s, int start, int count,
         int after) 
      {
      } // end method beforeTextChanged
   }; // end billEditTextWatcher
   
   private TextWatcher billSplitTextWatcher = new TextWatcher() 
   {
      // called when the user enters a number
      @Override
      public void onTextChanged(CharSequence s, int start, 
         int before, int count) 
      {         
         // convert billEditText's text to a double
         
         
         try
         {
        	 currentSplit=Integer.parseInt(billSplitText.getText().toString());
            
         } // end try
         catch (NumberFormatException e)
         {
        	 currentSplit = 0; // default if an exception occurs
         }

         // update the standard and custom tip EditTexts
         updateStandard(); // update the 10, 15 and 20% EditTexts
         updateCustom(); // update the custom tip EditTexts
      }
      @Override
      public void afterTextChanged(Editable s) 
      {
      } // end method afterTextChanged

      @Override
      public void beforeTextChanged(CharSequence s, int start, int count,
         int after) 
      {
      } // end method beforeTextChanged
   }; // end billSplitTextWatcher
   
   
   public void onClickClear(View view) {
	   MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.buttonclick);
	   mp.start();
	   billEditText.setText("");
	   billSplitText.setText("");
		}
   public void onClickMain(View view)
   {
	   MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.buttonclick);
	   mp.start();
	   super.onBackPressed();
   } 
   
   public void onClickSave(View view)
   {
	   MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.buttonclick);
	   mp.start();
	   
   }
} // end class TipCalculator

