package android.example.wallenote;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Map;

public class RekapPemasukan extends AppCompatActivity {
    DBHelper dbcenter;
    ListView listView;
    ArrayList<Map<String, String>> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rekap_pemasukan);
        listView=findViewById(R.id.r_list_view);
        dbcenter = new DBHelper(this);
        loadData();
    }

    private void loadData() {
        arrayList = dbcenter.getPemasukan();
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, arrayList,
                android.R.layout.simple_expandable_list_item_2, new String[]{"judul", "jumlah"},
                new int[]{android.R.id.text1, android.R.id.text2});
        listView.setAdapter(simpleAdapter);
        simpleAdapter.notifyDataSetChanged();
    }
}