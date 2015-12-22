package com.example.tipcalculator;

public class DataProvider {
	
    private String seekpercent;
    private String billamount;
    private String tip;
    private String total_bill;
    private String total_split;
    private String created_at;

 
public DataProvider(String seekpercent, String billamount, String tip, String total_bill, String total_split, String created_at)
{
    this.seekpercent =seekpercent;
    this.billamount = billamount;
    this.tip = tip;
    this.total_bill = total_bill;
    this.total_split = total_split;
    this.created_at = created_at;
 
}
 
    public String getSeekPercent() {
        return seekpercent;
    }
 
    public void setSeekPercent(String seekpercent) {
        this.seekpercent = seekpercent;
    }
 
    public String getBillAmount() {
        return billamount;
    }
 
    public void setBillAmount(String billamount) {
        this.billamount = billamount;
    }
 
    public String getTip() {
        return tip;
    }
 
    public void setTip(String tip) {
        this.tip = tip;
    }
    
    
    public String getTotalBill() {
        return total_bill;
    }
 
    public void setTotalBill(String total_bill) {
        this.total_bill = total_bill;
    }
    
    public String getTotalSplit() {
        return total_split;
    }
 
    public void setTotalSplit(String total_split) {
        this.total_split = total_split;
    }
  
    public String getCreatedAt() {
        return created_at;
    }
 
    public void setCreatedAt(String created_at) {
        this.created_at = created_at;
    }
    
}