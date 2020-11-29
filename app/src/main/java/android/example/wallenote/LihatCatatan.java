package android.example.wallenote;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LihatCatatan extends AppCompatActivity {
    String id;
    TextView txjudul,txjenis,txjumlah,txtanggal,txketerangan;
    String idtran ="", judul="",jenis="", jumlah= "", tanggal="", keterangan="";
    Button del,updt;
    DBHelper dbcenter;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_catatan);
        dbcenter = new DBHelper(this);
        txjudul=findViewById(R.id.lht_judul);
        txjenis=findViewById(R.id.isi_jenis);
        txjumlah=findViewById(R.id.isi_jumlah);
        txtanggal=findViewById(R.id.isi_tanggal);
        txketerangan=findViewById(R.id.isi_keterangan);
        del=findViewById(R.id.bt_del);
        updt=findViewById(R.id.bt_update);
        id = getIntent().getStringExtra("key_extra_id");

        SQLiteDatabase db = dbcenter.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM transaksi WHERE id_transaksi = '" + id + "'",null);
        cursor.moveToFirst();
        if (cursor.getCount()>0) //jika hasil query tidak kosong
        {   //isi variabel textview dengan hasil query sesuai indeks
            cursor.moveToPosition(0);
            idtran = cursor.getString(0).toString();
            judul = cursor.getString(1).toString();
            jenis = cursor.getString(4).toString();
            jumlah = cursor.getString(3).toString();
            tanggal = cursor.getString(2).toString();
            keterangan = cursor.getString(5).toString();

            txjudul.setText(cursor.getString(1).toString());
            txjenis.setText(cursor.getString(4).toString());
            txjumlah.setText(cursor.getString(3).toString());
            txtanggal.setText(cursor.getString(2).toString());
            txketerangan.setText(cursor.getString(5).toString());
        }

        updt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent m = new Intent(getApplicationContext(), UpdateCatatan.class);
                //melewatkan data dari class ini ke class UpdateCatatan
                m.putExtra("idTran",idtran);
                m.putExtra("judul",judul);
                m.putExtra("jenis",jenis);
                m.putExtra("jumlah", jumlah);
                m.putExtra("tanggal", tanggal);
                m.putExtra("keterangan",keterangan);
                startActivity(m);
            }
        });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirm(Integer.parseInt(id));
            }
        });
    }
    private void showConfirm(final int id){
        new AlertDialog.Builder(this)
                .setTitle("Hapus Data")
                .setMessage("Hapus?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteData(id);
                    }
                })
                .setNegativeButton("Tidak",null)
                .show();
    }
    private void deleteData(int id){
        dbcenter.delete(id);
        onBackPressed();
    }
}