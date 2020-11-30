package android.example.wallenote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION =1;
    public static String DATABASE_NAME = "wallenote_db";
    private static final String TABLE_TRANSAKSI= "transaksi";
    private static final String KEY_ID_TRANSAKSI = "id_transaksi";
    private static final String KEY_JUDUL = "judul";
    private static final String KEY_TANGGAL ="tanggal";
    private static final String KEY_JUMLAH ="jumlah";
    private static final String KEY_JENIS = "jenis";
    private static final String KEY_KETERANGAN = "keterangan";

    private static final String CREATE_TABLE_TRANSAKSI = "CREATE TABLE " + TABLE_TRANSAKSI + "("
            + KEY_ID_TRANSAKSI + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_JUDUL + " TEXT,"
            + KEY_TANGGAL + " DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,"
            + KEY_JENIS + " TEXT,"
            + KEY_JUMLAH + " INTEGER,"
            + KEY_KETERANGAN + " TEXT) ";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //mengeksekusi perintah create tabel transaksi
        db.execSQL(CREATE_TABLE_TRANSAKSI);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_TRANSAKSI + "'");
        onCreate(db);
    }
    public void addTransaksi(String judul, String jenis,int jumlah, String keterangan) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_JUDUL, judul);
        values.put(KEY_JENIS, jenis);
        values.put(KEY_JUMLAH, jumlah);
        values.put(KEY_KETERANGAN, keterangan);

        db.insert(TABLE_TRANSAKSI, null, values);
    }

    //method untuk update transaksi
    public void updateTransaksi(int id, String judul, int jumlah, String keterangan) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_JUDUL, judul);
        values.put(KEY_JUMLAH,jumlah);
        values.put(KEY_KETERANGAN, keterangan);

        db.update(TABLE_TRANSAKSI, values, KEY_ID_TRANSAKSI + " = ?", new String[]{String.valueOf(id)});
    }

    public void delete(int id){
        SQLiteDatabase db=this.getWritableDatabase();
        String deleteQuery="DELETE FROM "+TABLE_TRANSAKSI+" WHERE "+KEY_ID_TRANSAKSI+" = '"+ id+"'";
        db.execSQL(deleteQuery);
        db.close();
    }

    public ArrayList<Map<String, String>> getTransaksi(){
        ArrayList<Map<String, String>> arrayList=new ArrayList<>();
        int id_transaksi=0;
        String jenis="";
        int jumlah=0;
        String judul="";
        int tanggal=0;

        String selectQuery="SELECT * FROM "+TABLE_TRANSAKSI;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor c=db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do{
                id_transaksi=c.getInt(c.getColumnIndex(KEY_ID_TRANSAKSI));
                jenis=c.getString(c.getColumnIndex(KEY_JENIS));
                jumlah=c.getInt(c.getColumnIndex(KEY_JUMLAH));
                judul=c.getString(c.getColumnIndex(KEY_JUDUL));
                tanggal=c.getInt(c.getColumnIndex(KEY_TANGGAL));
                Map<String, String> itemMap=new HashMap<>();
                itemMap.put(KEY_ID_TRANSAKSI, String.valueOf(id_transaksi));
                itemMap.put(KEY_JENIS, jenis);
                itemMap.put(KEY_JUMLAH, String.valueOf(jumlah));
                itemMap.put(KEY_JUDUL, judul);
                itemMap.put(KEY_TANGGAL, String.valueOf(tanggal));
                arrayList.add(itemMap);
            }while (c.moveToNext());
        }
        return arrayList;
    }

    public ArrayList<Map<String, String>> getPengeluaran(){
        ArrayList<Map<String, String>> arrayList=new ArrayList<>();
        int id_transaksi=0;
        String jenis="";
        int jumlah=0;
        String judul="";
        String tanggal="";

        String selectQuery="SELECT * FROM "+TABLE_TRANSAKSI+" WHERE "+KEY_JENIS+" = 'Pengeluaran'";
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor c=db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do{
                id_transaksi=c.getInt(c.getColumnIndex(KEY_ID_TRANSAKSI));
                jenis=c.getString(c.getColumnIndex(KEY_JENIS));
                jumlah=c.getInt(c.getColumnIndex(KEY_JUMLAH));
                judul=c.getString(c.getColumnIndex(KEY_JUDUL));
                tanggal=c.getString(c.getColumnIndex(KEY_TANGGAL));
                Map<String, String> itemMap=new HashMap<>();
                itemMap.put(KEY_ID_TRANSAKSI, String.valueOf(id_transaksi));
                itemMap.put(KEY_JENIS, jenis);
                itemMap.put(KEY_JUMLAH, String.valueOf(jumlah));
                itemMap.put(KEY_JUDUL, judul);
                itemMap.put(KEY_TANGGAL, tanggal);
                arrayList.add(itemMap);
            }while (c.moveToNext());
        }
        return arrayList;
    }

    public ArrayList<Map<String, String>> getPemasukan(){
        ArrayList<Map<String, String>> arrayList=new ArrayList<>();
        int id_transaksi=0;
        String jenis="";
        int jumlah=0;
        String judul="";
        String tanggal="";

        String selectQuery="SELECT * FROM "+TABLE_TRANSAKSI+" WHERE "+KEY_JENIS+" = 'Pemasukan'";
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor c=db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do{
                id_transaksi=c.getInt(c.getColumnIndex(KEY_ID_TRANSAKSI));
                jenis=c.getString(c.getColumnIndex(KEY_JENIS));
                jumlah=c.getInt(c.getColumnIndex(KEY_JUMLAH));
                judul=c.getString(c.getColumnIndex(KEY_JUDUL));
                tanggal=c.getString(c.getColumnIndex(KEY_TANGGAL));
                Map<String, String> itemMap=new HashMap<>();
                itemMap.put(KEY_ID_TRANSAKSI, String.valueOf(id_transaksi));
                itemMap.put(KEY_JENIS, jenis);
                itemMap.put(KEY_JUMLAH, String.valueOf(jumlah));
                itemMap.put(KEY_JUDUL, judul);
                itemMap.put(KEY_TANGGAL, tanggal);
                arrayList.add(itemMap);
            }while (c.moveToNext());
        }
        return arrayList;
    }

}
