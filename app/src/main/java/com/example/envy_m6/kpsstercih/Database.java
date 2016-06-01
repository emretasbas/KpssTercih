package com.example.envy_m6.kpsstercih;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;


public class Database extends SQLiteOpenHelper {

    private static final int MYDATABASE_VERSION = 1;

    private static final String MYDATABASE_NAME = "mydatabase_name";//database adı

    //database tablo ve sütun tanımlama
    private static final String TABLE_NAME_SEHIR = "table_name_sehir";
    private static String SEHIR_ID = "sehir_id";
    private static String SEHIR_ADI = "sehir_adi";

    private static final String TABLE_NAME_ILCE = "table_name_ilce";
    private static String ILCE_ID = "ilce_id";
    private static String ILCE_SEHIR_ID = "ilce_sehir_id";
    private static String ILCE_ADI = "ilce_adi";

    private static final String TABLE_NAME_OKUL = "table_name_okul";
    private static String OKUL_ID = "okul_id";
    private static String OKUL_ILCE_ID = "okul_ilce_id";
    private static String OKUL_ADI = "okul_adi";
    private static String OKUL_BILGI1 = "okul_bilgi1";
    private static String OKUL_BILGI2 = "okul_bilgi2";
    private static String OKUL_BILGI3 = "okul_bilgi3";

    public Database(Context context) {
        super(context, MYDATABASE_NAME, null, MYDATABASE_VERSION);
    }

    //tabloları oluşturma. 2. ve 3. tablonun sonunda birer tane daha ID var.
    //bunun sebebi, 2. tablo 1.nin, 3. tabloda 2.nin alt tablosudur. Birbirine bağlantıyı sağlar.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_SEHIR = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_SEHIR + "("
                + SEHIR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + SEHIR_ADI + " TEXT" + ")";
        db.execSQL(CREATE_TABLE_SEHIR);

        String CREATE_TABLE_ILCE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_ILCE + "("
                + ILCE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ILCE_ADI + " TEXT,"
                + SEHIR_ADI + " TEXT,"
                + ILCE_SEHIR_ID + " INT" + ")";
        db.execSQL(CREATE_TABLE_ILCE);

        String CREATE_TABLE_OKUL = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_OKUL + "("
                + OKUL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + OKUL_ADI + " TEXT,"
                + SEHIR_ADI + " TEXT,"
                + ILCE_ADI + " TEXT,"
                + OKUL_BILGI1 + " TEXT,"
                + OKUL_BILGI2 + " TEXT,"
                + OKUL_BILGI3 + " TEXT,"
                + OKUL_ILCE_ID + " INT" + ")";
        db.execSQL(CREATE_TABLE_OKUL);
    }

    //veritabanından silmek için gerekli metodlar.
    public void sehirSil(int id){

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_SEHIR, SEHIR_ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }

    public void ilceSil(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_ILCE, ILCE_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    public void okulSil(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_OKUL, OKUL_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    //veritabanına eklemek için gerekli metodlar.
    public void sehirEkle(String sehir_adi) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SEHIR_ADI, sehir_adi);

        db.insert(TABLE_NAME_SEHIR, null, values);
        db.close();
    }

    public void ilceEkle(String ilce_adi, String sehir_adi, int sehirin_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ILCE_ADI, ilce_adi);
        values.put(SEHIR_ADI, sehir_adi);
        values.put(ILCE_SEHIR_ID, sehirin_id);

        db.insert(TABLE_NAME_ILCE, null, values);
        db.close();
    }

    public void okulEkle(String okul_adi, String sehir_adi, String ilce_adi, String bilgi1, String bilgi2, String bilgi3, int ilcenin_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(OKUL_ADI, okul_adi);
        values.put(SEHIR_ADI, sehir_adi);
        values.put(ILCE_ADI, ilce_adi);
        values.put(OKUL_BILGI1, bilgi1);
        values.put(OKUL_BILGI2, bilgi2);
        values.put(OKUL_BILGI3, bilgi3);
        values.put(OKUL_ILCE_ID, ilcenin_id);
        db.insert(TABLE_NAME_OKUL, null, values);
        db.close();
    }

    //Tabloları birer hashmap'e atar ve verileri burada saklar.
    public HashMap<String, String> sehirDetay(int id){

        HashMap<String,String> sehir = new HashMap<String,String>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME_SEHIR+ " WHERE sehir_id="+id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            sehir.put(SEHIR_ADI, cursor.getString(1));
        }
        cursor.close();
        db.close();

        return sehir;
    }

    public HashMap<String, String> ilceDetay(int id){

        HashMap<String,String> ilce = new HashMap<String,String>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME_ILCE + " WHERE ilce_id="+id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            ilce.put(ILCE_ADI, cursor.getString(1));
            ilce.put(SEHIR_ADI, cursor.getString(2));
        }
        cursor.close();
        db.close();

        return ilce;
    }

    public HashMap<String, String> okulDetay(int id){

        HashMap<String,String> okul = new HashMap<String,String>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME_OKUL+ " WHERE okul_id="+id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            okul.put(OKUL_ADI, cursor.getString(1));
            okul.put(SEHIR_ADI, cursor.getString(2));
            okul.put(ILCE_ADI, cursor.getString(3));
            okul.put(OKUL_BILGI1, cursor.getString(4));
            okul.put(OKUL_BILGI2, cursor.getString(5));
            okul.put(OKUL_BILGI3, cursor.getString(6));
        }
        cursor.close();
        db.close();

        return okul;
    }

    //Hashmapteki veriler arraylist ile listelenir.
    public ArrayList<HashMap<String, String>> sehirler(){

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NAME_SEHIR;
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<HashMap<String, String>> sehirlist = new ArrayList<HashMap<String, String>>();

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                for(int i=0; i<cursor.getColumnCount();i++)
                {
                    map.put(cursor.getColumnName(i), cursor.getString(i));
                }

                sehirlist.add(map);
            } while (cursor.moveToNext());
        }
        db.close();

        return sehirlist;
    }

    public  ArrayList<HashMap<String, String>> ilceler(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NAME_ILCE + " WHERE "+ILCE_SEHIR_ID+"=" + id;
        Cursor cursor2 = db.rawQuery(selectQuery, null);
        ArrayList<HashMap<String, String>> ilcelist = new ArrayList<HashMap<String, String>>();

        if (cursor2.moveToFirst()){
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                for (int i=0; i<cursor2.getColumnCount(); i++){
                    map.put(cursor2.getColumnName(i), cursor2.getString(i));
                }
                ilcelist.add(map);
            } while (cursor2.moveToNext());
        }
        db.close();
        return ilcelist;
    }

    public ArrayList<HashMap<String, String>> okullar(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NAME_OKUL+ " WHERE " + OKUL_ILCE_ID + "=" + id;
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<HashMap<String, String>> okullist = new ArrayList<HashMap<String, String>>();

        if (cursor.moveToFirst()){
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                for (int i=0; i<cursor.getColumnCount(); i++){
                    map.put(cursor.getColumnName(i), cursor.getString(i));
                }
                okullist.add(map);
            }while (cursor.moveToNext());
        }
        db.close();
        return okullist;
    }

    //Tabloları güncellemek için.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_SEHIR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_ILCE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_OKUL);
        onCreate(db);

    }

}
