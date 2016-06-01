package com.example.envy_m6.kpsstercih;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class IlceListele2 extends Activity {
    ListView lv;
    ArrayAdapter<String> adapter;
    ArrayList<HashMap<String, String>> ilce_liste;
    String ilce_adlari[];
    int ilce_idler[];
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ilcelistele2);

        //bir önceki sayfada tanımladığımız intent nesnesini çağırdık (sehirin_idi)
        Intent intent=getIntent();
        id = intent.getIntExtra("sehirin_idi", 0);
    }

    //listview e bu ilin ilçelerini yansıtmış olduk.
    //listviewde hangisinin üzerine tıklarsak onun detayına ulaşırız.
    //dokunulan id detayına gider.
    public void onResume()
    {
        super.onResume();
        Database db = new Database(getApplicationContext());

        ilce_liste = db.ilceler(id);
        if(ilce_liste.size()==0){
            Toast.makeText(getApplicationContext(), "Henüz İlçe Eklenmemiş.", Toast.LENGTH_LONG).show();
        }else{
            ilce_adlari = new String[ilce_liste.size()];
            ilce_idler = new int[ilce_liste.size()];
            for (int i=0;i<ilce_liste.size();i++){
                ilce_adlari[i] = ilce_liste.get(i).get("ilce_adi");

                ilce_idler[i] = Integer.parseInt(ilce_liste.get(i).get("ilce_id"));

            }
            lv = (ListView) findViewById(R.id.listilce2);

            adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.textItem, ilce_adlari);
            lv.setAdapter(adapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
                                        long arg3) {
                    Intent intent = new Intent(getApplicationContext(), IlceDetay.class);
                    intent.putExtra("ilce_id", (int)ilce_idler[arg2]);
                    startActivity(intent);

                }
            });
        }
    }
}
