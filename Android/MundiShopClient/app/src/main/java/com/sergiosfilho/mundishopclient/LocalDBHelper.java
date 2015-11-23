package com.sergiosfilho.mundishopclient;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

import businessentities.Purchase;

public class LocalDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "mundishop.db";
    private static final int DB_VERSION = 1;
    private Context context;

    //Tables and columns names
    private static final String DB_PURCHASE = "purchase";
    private static final String DB_PURCHASE_ID = BaseColumns._ID;
    private static final String DB_PURCHASE_PRODUCT_ID = "productId";
    private static final String DB_PURCHASE_PRODUCT_NAME = "productName";
    private static final String DB_PURCHASE_CUSTOMER_NAME = "customerName";
    private static final String DB_PURCHASE_CREDIT_CARD_FLAG = "ccFlag";
    private static final String DB_PURCHASE_AMOUNT_PAID = "amountPaid";

    public LocalDBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTablePurchase = "create table " + DB_PURCHASE + " (" + DB_PURCHASE_ID + " int primary key, "
            + DB_PURCHASE_PRODUCT_ID + " int, " + DB_PURCHASE_PRODUCT_NAME + " text, " + DB_PURCHASE_CUSTOMER_NAME + " text,"
            + DB_PURCHASE_CREDIT_CARD_FLAG + " int," + DB_PURCHASE_AMOUNT_PAID + " real)";
        db.execSQL(createTablePurchase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + DB_PURCHASE);
        onCreate(db);
    }

    public void insertPurchase(SQLiteDatabase db, Purchase purchase, String productName) {
        String sqlInsert = "insert into " + DB_PURCHASE + "(" + DB_PURCHASE_PRODUCT_ID + ", "
            + DB_PURCHASE_PRODUCT_NAME + ", " + DB_PURCHASE_CUSTOMER_NAME + ", "
            + DB_PURCHASE_CREDIT_CARD_FLAG + ", " + DB_PURCHASE_AMOUNT_PAID + ") values ("
            + purchase.ProductId + ", '" + productName + "', '" + purchase.CustomerName + "', "
            + purchase.CreditCardFlag + ", " + purchase.AmountPaid + ")";
        db.execSQL(sqlInsert);
    }

    public List<PurchaseHistoryItem> retrieveAllPurchases(SQLiteDatabase db){
        ArrayList<PurchaseHistoryItem> retVal = new ArrayList<>();
        String sqlSelect = "select * from " + DB_PURCHASE;
        Cursor resultCursor = db.rawQuery(sqlSelect, null);
        while(resultCursor.moveToNext()){
            PurchaseHistoryItem item = new PurchaseHistoryItem();
            item.Purchase = new Purchase();
            item.Purchase.AmountPaid = resultCursor.getFloat(resultCursor.getColumnIndex(DB_PURCHASE_AMOUNT_PAID));
            item.Purchase.CustomerName = resultCursor.getString(resultCursor.getColumnIndex(DB_PURCHASE_CUSTOMER_NAME));
            item.Purchase.CreditCardFlag = resultCursor.getInt(resultCursor.getColumnIndex(DB_PURCHASE_CREDIT_CARD_FLAG));
            item.ProductName = resultCursor.getString(resultCursor.getColumnIndex(DB_PURCHASE_PRODUCT_NAME));
            retVal.add(item);
        }
        return retVal;
    }

    public class PurchaseHistoryItem {
        public Purchase Purchase;
        public String ProductName;
    }

}
