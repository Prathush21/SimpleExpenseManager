package lk.ac.mrt.cse.dbs.simpleexpensemanager.ui;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.nio.DoubleBuffer;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

public  class DbHelper extends SQLiteOpenHelper {
    public static final String DbName = "190471C";
    public static final String TableName="Account";
/*
    public static final String Col1="Account_No";
    public static final String Col2="Bank";
    public static final String Col3="Account_Holder";
    public static final String Col4="Init_Balance";
*/







    public DbHelper(Context context){
        super(context,DbName,null,1);
        SQLiteDatabase db=this.getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table "+ "Account"+" (Account_No Text primary key,Bank Text,Account_Holder Text,Init_Balance Float)");
        db.execSQL("create table "+ "Transactions" + "(Date Date ,Account_No Text,Type Text,Amount Double)");




    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("Drop Table IF Exists "+"Account");



    }

    public boolean insertData(String account_no,String bank, String holder,double init_balance){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Account_No",account_no);
        contentValues.put("Bank",bank);
        contentValues.put("Account_Holder",holder);
        contentValues.put("Init_Balance",init_balance);
        long result = db.insert("Account",null ,contentValues);

        if(result == -1)
            return false;
        else
            return true;


    }

    public boolean insertTransData(String date, String Account_No, String Type, double amount){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Date", date);
        contentValues.put("Account_No",Account_No);
        contentValues.put("Type", Type);
        contentValues.put("Amount",amount);
        long result = db.insert("Transactions",null ,contentValues);


        if(result == -1)
            return false;
        else
            return true;
    }

    public HashMap getData(){
        SQLiteDatabase db = this.getReadableDatabase();
        HashMap<String,Account> accounts = new HashMap<String,Account>();
        String[] projection = {"Account_No","Bank","Account_Holder","Init_Balance"};

        Cursor result;
        result=db.query("Account",projection,null,null,null,null,null);
        while(result.moveToNext()) {
            String acc_no = result.getString(
                    result.getColumnIndexOrThrow("Account_No"));
            String bank = result.getString(
                    result.getColumnIndexOrThrow("Bank"));
            String holder = result.getString(
                    result.getColumnIndexOrThrow("Account_Holder"));
            Double init_balance = result.getDouble(
                    result.getColumnIndexOrThrow("Init_Balance"));

            Account acc = new Account(acc_no,bank,holder,init_balance);
            accounts.put(acc_no,acc);



        }

        return accounts;


    }
    public List<Transaction> getTransData(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Transaction> transactions = new LinkedList<>();
        String[] projection = {"Date","Account_No","Type","Amount"};

        Cursor result;
        result=db.query("Transactions",projection,null,null,null,null,null);
        while(result.moveToNext()) {
            ExpenseType expenseType;
            String date = result.getString(
                    result.getColumnIndexOrThrow("Date"));
            String acc_no = result.getString(
                    result.getColumnIndexOrThrow("Account_No"));
            String type = result.getString(
                    result.getColumnIndexOrThrow("Type"));
            if(type.equalsIgnoreCase("EXPENSE")){
                expenseType= ExpenseType.EXPENSE;
            }else{
                expenseType= ExpenseType.INCOME;
            }
            Double amount = result.getDouble(
                    result.getColumnIndexOrThrow("Amount"));

            Transaction t  = new Transaction (date,acc_no,expenseType,amount);
            transactions.add(t);


        }
        return transactions;


    }
    public Account getAccount(String acc_no){
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {"Account_No","Bank","Account_Holder","Init_Balance"};


        Cursor result;
        //result=db.query("Account",projection,"Account_No" + " = ?", new String[]{acc_no},null,null,null);
        result=db.rawQuery("SELECT * FROM Account WHERE Account_No=?", new String[] {acc_no + ""});
        Account acc=null;
        while(result.moveToNext()) {
            String account_no = result.getString(
                    result.getColumnIndexOrThrow("Account_No"));
            String bank = result.getString(
                    result.getColumnIndexOrThrow("Bank"));
            String holder = result.getString(
                    result.getColumnIndexOrThrow("Account_Holder"));
            Double init_balance = result.getDouble(
                    result.getColumnIndexOrThrow("Init_Balance"));

            acc = new Account(account_no,bank,holder,init_balance);





        }
        return acc;





    }
    public void updateBalance(Double Balance,String acc_no) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE "+"Account"+" SET Init_Balance = "+"'"+Balance+"' "+ "WHERE Account_no = "+"'"+acc_no+"'");

    }



}
