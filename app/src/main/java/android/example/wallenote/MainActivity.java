package android.example.wallenote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<Map<String, String>> arrayList;
    TextView tvIncome, tvExpanses, tvBalance;
    Button tambah;
    DBHelper dbcenter;
    Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.listView);
        dbcenter = new DBHelper(this);
        tambah = findViewById(R.id.tambah_catatan);
        tvIncome = findViewById(R.id.income);
        tvExpanses = findViewById(R.id.expenses);
        tvBalance=findViewById(R.id.balance);

        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent m = new Intent(getApplicationContext(),TambahCatatan.class);
                startActivity(m);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String val = arrayList.get(i).get("id_transaksi");
                Intent n = new Intent(getApplicationContext(),LihatCatatan.class);
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
        cursor = db.rawQuery("SELECT SUM(jumlah) FROM transaksi WHERE  jenis = 'Income'", null);
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
        cursor = db.rawQuery("SELECT SUM(jumlah) FROM transaksi WHERE jenis = 'Expenses'", null);
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