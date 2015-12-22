package com.example.tipcalculator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
 
import java.util.ArrayList;
import java.util.List;
 
public class ListDataAdapter extends ArrayAdapter {
    List list= new ArrayList();
    public ListDataAdapter(Context context,int resource){
        super(context,resource);
    }
     static class LayoutHandler{
         TextView SEEKPERCENT,BILLAMOUNT,TIP,TOTAL_BILL,TOTAL_SPLIT,CREATED_AT;
         
     }
    public void add(Object object){
        super.add(object);
        list.add(object);
 
    }
    public int getCount(){
        return list.size();
    }
    public Object getTtem(int position){
        return list.get(position);
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LayoutHandler layoutHandler;
        if(row == null)
        {
            LayoutInflater layoutInflater =(LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=layoutInflater.inflate(R.layout.row_layout,parent,false);
            layoutHandler=new LayoutHandler();
            layoutHandler.SEEKPERCENT=(TextView)row.findViewById(R.id.seek_percent_id);
            layoutHandler.BILLAMOUNT=(TextView)row.findViewById(R.id.custom_bill_amt_id);
            layoutHandler.TIP=(TextView)row.findViewById(R.id.custom_tip_id);
            layoutHandler.TOTAL_BILL=(TextView)row.findViewById(R.id.custom_total_bill_id);
            layoutHandler.TOTAL_SPLIT=(TextView)row.findViewById(R.id.custom_split_id);
            layoutHandler.CREATED_AT=(TextView)row.findViewById(R.id.creation_id);
            row.setTag(layoutHandler);
        }
 
        else{
            layoutHandler=(LayoutHandler)row.getTag();
        }
         DataProvider dataProvider=(DataProvider)this.getTtem(position);
        layoutHandler.SEEKPERCENT.setText(dataProvider.getSeekPercent());
        layoutHandler.BILLAMOUNT.setText(dataProvider.getBillAmount());
        layoutHandler.TIP.setText(dataProvider.getTip());
        layoutHandler.TOTAL_BILL.setText(dataProvider.getTotalBill());
        layoutHandler.TOTAL_SPLIT.setText(dataProvider.getTotalSplit());
        layoutHandler.CREATED_AT.setText(dataProvider.getCreatedAt());
        
        return row;
    }
}