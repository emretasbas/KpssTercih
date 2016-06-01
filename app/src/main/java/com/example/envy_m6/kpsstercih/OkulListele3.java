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

public class OkulListele3 extends Activity {
    ListView lv;
    ArrayAdapter<String> adapter;
    ArrayList<HashMap<String, String>> okul_liste;
    String okul_adlari[];
    int okul_idler[];
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okullistele3);

        //bir önceki sayfadan aldığımız ilçe bilgisi ile bu ilçenin okullarını listeliyoruz.
        Intent intent=getIntent();
        id = intent.getIntExtra("ilcenin_idi", 0);

    }

    //ilçenin tüm okullarını burada listviewe yansıtıyoruz. hangi id'li okulun üzerine tıklarsakta onun detayına ulaşırız.
    public void onResume()
    {
        super.onResume();
        Database db = new Database(getApplicationContext());

        okul_liste = db.okullar(id);
        if(okul_liste.size()==0){
            Toast.makeText(getApplicationContext(), "Henüz Okul Eklenmemiş.", Toast.LENGTH_LONG).show();
        }else{
            okul_adlari = new String[okul_liste.size()];
            okul_idler = new int[okul_liste.size()];
            for (int i=0;i<okul_liste.size();i++){
                okul_adlari[i] = okul_liste.get(i).get("okul_adi");

                okul_idler[i] = Integer.parseInt(okul_liste.get(i).get("okul_id"));

            }
            lv = (ListView) findViewById(R.id.listokul3);

            adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.textItem, okul_adlari);
            lv.setAdapter(adapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
                                        long arg3) {
                    Intent intent = new Intent(getApplicationContext(), OkulDetay.class);
                    intent.putExtra("okul_id", (int)okul_idler[arg2]);
                    startActivity(intent);

                }
            });
        }
    }
}
