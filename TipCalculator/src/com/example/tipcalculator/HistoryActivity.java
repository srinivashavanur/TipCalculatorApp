package com.example.tipcalculator;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.os.Bundle;
public class HistoryActivity extends Activity{
	ListView listView;
	SQLiteDatabase sqLiteDatabase;
	DatabaseHelper db;
	Cursor cursor;
	ListDataAdapter listDataAdapter;
	
@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.historyactivity);
listView = (ListView)findViewById(R.id.list_view);
listDataAdapter = new ListDataAdapter(getApplicationContext(),R.layout.row_layout);
listView.setAdapter(listDataAdapter);
db = new DatabaseHelper(getApplicationContext());
sqLiteDatabase = db.getReadableDatabase();
cursor = db.fetch(sqLiteDatabase);
if(cursor.moveToFirst())
{
    do {
        String seekpercent,billamount,tip,total_bill,total_split,created_at;
        seekpercent= cursor.getString(0) + "%\n" + "Tip";
        billamount= cursor.getString(1) + "\nBill Amt";
        tip="+ 	"+ cursor.getString(2)+"\nTip Amt";
        total_bill="=" + cursor.getString(3)+"\nTotal Bill";
        total_split= "Split on\n" + cursor.getString(4) + "\n" +"People";
        created_at="Spent on\n"+ cursor.getString(5);
        DataProvider dataProvider=new DataProvider(seekpercent,billamount,tip,total_bill,total_split,created_at);
       // DataProvider dataProvider=new DataProvider(seekpercent,billamount,tip,total_bill,total_split);
        listDataAdapter.add(dataProvider);
        
        	
        

    }while (cursor.moveToNext());
}
if(listDataAdapter.getCount() == 0)
Toast.makeText(this, "No Items Available",Toast.LENGTH_LONG).show();
	
 
}

public void onClick_Delete(View view)
{
	MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.buttonclick);
	mp.start();
	db.deleteDB(sqLiteDatabase);
	startActivity(new Intent("com.example.HistoryActivity"));

	
}
public void onClick_MainMenu(View view)
{
	   MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.buttonclick);
	   mp.start();
	   Intent intent = new Intent(HistoryActivity.this, TipMainActivity.class);
	    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);   
	    startActivity(intent);
	   
} 

}
