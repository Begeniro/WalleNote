package android.example.wallenote;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TambahCatatan extends AppCompatActivity {
    Button addincome, addexp, save;
    Context context;
    EditText judul, keterangan, jumlah;
    String jenis = "Income";
    DBHelper dbcenter;
    final int sdk = android.os.Build.VERSION.SDK_INT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_catatan);
        dbcenter = new DBHelper(this);
        addincome = findViewById(R.id.addincome);
        addexp = findViewById(R.id.addexp);
        judul = findViewById(R.id.edtjudul);
        keterangan = findViewById(R.id.edtnote);
        jumlah = findViewById(R.id.edtjumlah);

        addincome.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                jenis = "Income";
                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    addincome.setBackgroundDrawable(ContextCompat.getDrawable(context, R.color.colorPrimary));
                    addincome.setTextColor(ContextCompat.getColor(context, R.color.putih));
                    addexp.setBackgroundDrawable(ContextCompat.getDrawable(context, R.color.putih));
                } else {
                    addincome.setBackground(ContextCompat.getDrawable(TambahCatatan.this, R.color.colorPrimary));
                    addincome.setTextColor(ContextCompat.getColor(TambahCatatan.this, R.color.putih));
                    addincome.setTextColor(ContextCompat.getColor(TambahCatatan.this, R.color.putih));
                    addexp.setBackground(ContextCompat.getDrawable(TambahCatatan.this, R.color.putih));
                    addexp.setTextColor(ContextCompat.getColor(TambahCatatan.this, R.color.hitam));
                }
            }
        });
        addexp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                jenis = "Expenses";
                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    addexp.setBackgroundDrawable(ContextCompat.getDrawable(context, R.color.colorPrimary));
                    addexp.setTextColor(ContextCompat.getColor(context, R.color.putih));
                    addincome.setBackgroundDrawable(ContextCompat.getDrawable(context, R.color.putih));


                } else {
                    addexp.setBackground(ContextCompat.getDrawable(TambahCatatan.this, R.color.colorPrimary));
                    addexp.setTextColor(ContextCompat.getColor(TambahCatatan.this, R.color.putih));
                    addincome.setBackground(ContextCompat.getDrawable(TambahCatatan.this, R.color.putih));
                    addincome.setTextColor(ContextCompat.getColor(TambahCatatan.this, R.color.hitam));

                }
            }
        });
        save = findViewById(R.id.btnsave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbcenter.getWritableDatabase();
                dbcenter.addTransaksi(
                        judul.getText().toString(),
                        jenis,
                        Integer.parseInt(jumlah.getText().toString()),
                        keterangan.getText().toString());
                onBackPressed();

            }
        });
    }
}
