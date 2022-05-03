package lk.ac.mrt.cse.dbs.simpleexpensemanager.ui;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public  class DbHelper extends SQLiteOpenHelper {
    public static final String DbName = "ExpenseManger.Db";
    public static final String TableName="Account";
    public static final String Col1="Account_No";
    public static final String Col2="Bank";
    public static final String Col3="Account_Holder";
    public static final String Col4="Init_Balance";







    public DbHelper(Context context){
        super(context,DbName,null,1);
//        SQLiteDatabase db=this.getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table "+ "Account"+" (Account_No Text primary key,Bank Text,Account_Holder Text,Init_Balance Float)");


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
}
