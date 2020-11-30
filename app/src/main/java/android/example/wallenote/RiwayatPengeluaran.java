package android.example.wallenote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

public class RiwayatPengeluaran extends AppCompatActivity {
    DBHelper dbcenter;
    ListView listView;
    ArrayList<Map<String, String>> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_pengeluaran);
        listView=findViewById(R.id.r_list_view);
        dbcenter = new DBHelper(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String val = arrayList.get(i).get("id_transaksi");
                Intent n = new Intent(RiwayatPengeluaran.this, LihatCatatan.class);
                n.putExtra("key_extra_id", val);
                startActivity(n);
            }
        });
    }

    private void loadData() {
        arrayList = dbcenter.getPengeluaran();

        Collections.sort(arrayList, new Comparator<Map<String, String>>() {
            @Override
            public int compare(Map<String, String> t1, Map<String, String> t2) {
                return t2.get("id_transaksi").compareTo(t1.get("id_transaksi"));
            }
        });

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, arrayList,
                android.R.layout.simple_expandable_list_item_2, new String[]{"judul", "tanggal"},
                new int[]{android.R.id.text1, android.R.id.text2});
        listView.setAdapter(simpleAdapter);
        simpleAdapter.notifyDataSetChanged();
    }

    protected void onResume(){
        super.onResume();
        loadData();
    }
}