package com.rsydfhmy.mydtsapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.provider.BaseColumns;

public class DBMahasiswa extends SQLiteOpenHelper {

    //InnerClass, untuk mengatur artibut seperti Nama Tabel, nama-nama kolom dan Query
    static abstract class MyColumns implements BaseColumns {
        //Menentukan Nama Table dan Kolom
        static final String NamaTabel = "Mahasiswa";
//        static final String ID = "ID";
        static final String Nama = "Nama_Mahasiswa";
        static final String NoHp = "no_Hp";
        static final String JenisKelamin = "Jenis_Kelamin";
        static final String Lokasi = "Lokasi";
        static final String Alamat = "Alamat";
        static final String Foto = "Foto";
    }

    private static final String NamaDatabse = "mhs.db";
    private static final int VersiDatabase = 14;

    //Query yang digunakan untuk membuat Tabel
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE "+MyColumns.NamaTabel+
            "("+MyColumns.Nama+" TEXT PRIMARY KEY,"+MyColumns.NoHp+
            " TEXT NOT NULL, "+MyColumns.JenisKelamin+" TEXT NOT NULL, "+MyColumns.Lokasi+
            " TEXT NOT NULL, "+MyColumns.Alamat+" TEXT NOT NULL ,"+MyColumns.Foto+" BLOB )";

    //Query yang digunakan untuk mengupgrade Tabel
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "+MyColumns.NamaTabel;

    DBMahasiswa(Context context) {
        super(context, NamaDatabse, null, VersiDatabase);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    // Get data method
    public Cursor getdata(String sql)
    {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);

    }
    // delete data method
    public void deleteData(int id)
    {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "DELETE FROM Mahasiswa WHERE Nama_mahasiswa = ?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();;
        statement.bindDouble(1, (double)id);
        statement.execute();
        database.close();
    }
}
