package android.example.wallenote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UpdateCatatan extends AppCompatActivity {
    TextView txjenis, txtanggal;
    EditText edjudul, edjumlah, edketerangan;
    Button simpan, batal;
    DBHelper dbcenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_catatan);
        dbcenter = new DBHelper(this);
        txjenis = findViewById(R.id.lht_jenis);
        edjudul = findViewById(R.id.isi_judul);
        edjumlah = findViewById(R.id.isi_jumlah);
        txtanggal = findViewById(R.id.isi_tanggal);
        edketerangan = findViewById(R.id.isi_keterangan);
        simpan = findViewById(R.id.bt_simpan);
        batal = findViewById(R.id.bt_batal);

        //mengatur isi dari text view sesuai data
        txjenis.setText(getIntent().getStringExtra("jenis"));
        txtanggal.setText(getIntent().getStringExtra("tanggal"));
        edjumlah.setText(getIntent().getStringExtra("jumlah"));
        edketerangan.setText(getIntent().getStringExtra("keterangan"));
        edjudul.setText(getIntent().getStringExtra("judul"));


        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //menyimpan data hasil update catatan
                String id = getIntent().getStringExtra("idTran");
                dbcenter.updateTransaksi(Integer.parseInt(id), edjudul.getText().toString(), Integer.parseInt(edjumlah.getText().toString()), edketerangan.getText().toString());
                String val = id;
                Intent n = new Intent(UpdateCatatan.this, LihatCatatan.class);
                n.putExtra("key_extra_id", val);
                startActivity(n);
            }
        });

        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}