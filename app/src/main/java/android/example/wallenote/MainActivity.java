package android.example.wallenote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<Map<String, String>> arrayList;
    TextView tvIncome, tvExpanses, tvBalance;
    Button tambah, rwytPem, rwytPen;
    DBHelper dbcenter;
    Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.listView);
        dbcenter = new DBHelper(this);
        tvIncome = findViewById(R.id.income);
        tvExpanses = findViewById(R.id.expenses);
        tvBalance=findViewById(R.id.balance);
        tambah = findViewById(R.id.tambah_catatan);
        rwytPem = findViewById(R.id.riwayat_pemasukan);
        rwytPen = findViewById(R.id.riwayat_pengeluaran);

        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent m = new Intent(MainActivity.this, TambahCatatan.class);
                startActivity(m);
            }
        });

        rwytPem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent m = new Intent(MainActivity.this, RiwayatPemasukan.class);
                startActivity(m);
            }
        });

        rwytPen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent m = new Intent(MainActivity.this, RiwayatPengeluaran.class);
                startActivity(m);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String val = arrayList.get(i).get("id_transaksi");
                Intent n = new Intent(MainActivity.this, LihatCatatan.class);
                n.putExtra("key_extra_id", val);
                startActivity(n);
            }
        });


    }
    private void getSum(){
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        int i = 0;
        int e = 0;
        //mengambil jumlah income user dari sqlite
        cursor = db.rawQuery("SELECT SUM(jumlah) FROM transaksi WHERE  jenis = 'Pemasukan'", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) //jika hasil query tidak kosong
        {
            cursor.moveToPosition(0);
            if (cursor.isNull(0)) {
                tvIncome.setText("0"); //jika jumlah nya null isi text view dengan angka 0
                i = 0;
            } else { //jika tidak null isi sesuai databasw
                tvIncome.setText(cursor.getString(0).toString());
                i = Integer.parseInt(cursor.getString(0).toString());
            }

        }
        //mengambil jumlah expenses user dari sqlite
        cursor = db.rawQuery("SELECT SUM(jumlah) FROM transaksi WHERE jenis = 'Pengeluaran'", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) //jika hasil query tidak kosong
        {
            cursor.moveToPosition(0);
            if (cursor.isNull(0)) { //jika jumlah nya null isi text view dengan angka 0
                tvExpanses.setText("0");
                e = 0;
            } else{
                tvExpanses.setText(cursor.getString(0).toString());
                e = Integer.parseInt(cursor.getString(0).toString());
            }
        }
        int total = i-e; //total diperoleh dari income - expenses
        tvBalance.setText(Integer.toString(total));
    }

    private void loadData(){
        arrayList=dbcenter.getTransaksi();

        Collections.sort(arrayList, new Comparator<Map<String, String>>() {
            @Override
            public int compare(Map<String, String> t1, Map<String, String> t2) {
                return t2.get("id_transaksi").compareTo(t1.get("id_transaksi"));
            }
        });

        SimpleAdapter simpleAdapter=new SimpleAdapter(this, arrayList,
                android.R.layout.simple_expandable_list_item_2,new String[]{"jenis","jumlah"},
                new int[]{android.R.id.text1,android.R.id.text2});
        listView.setAdapter(simpleAdapter);
        simpleAdapter.notifyDataSetChanged();
    }
    protected void onResume(){
        super.onResume();
        loadData();
        getSum();
    }
}