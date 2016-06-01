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

public class SehirListele extends Activity {
    ListView lv;
    ArrayAdapter<String> adapter;
    ArrayList<HashMap<String, String>> sehir_liste;
    String sehir_adlari[];
    int sehir_idler[];
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sehirlistele);

    }

    public void onResume()
    {
        super.onResume();
        Database db = new Database(getApplicationContext());//veritabanı çağrılır.
        sehir_liste = db.sehirler();//ilgili arraylist çağrılır.
        if(sehir_liste.size()==0){//tablo boş ise kontrol sağlanır.
            Toast.makeText(getApplicationContext(), "Henüz Şehir Eklenmemiş.\nŞehir Ekle Bölümünden Şehir Ekleyiniz.", Toast.LENGTH_LONG).show();
        }else{
            sehir_adlari = new String[sehir_liste.size()];
            sehir_idler = new int[sehir_liste.size()];
            for(int i=0;i<sehir_liste.size();i++){
                sehir_adlari[i] = sehir_liste.get(i).get("sehir_adi");

                sehir_idler[i] = Integer.parseInt(sehir_liste.get(i).get("sehir_id"));
            }

            lv = (ListView) findViewById(R.id.listSehir);

            //listview ve arrayadapter birbirine bağlanır.
            adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.textItem, sehir_adlari);
            lv.setAdapter(adapter);

            //arraylistteki bilgiler listviewe işlenir.
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                        long arg3) {
                    Intent intent = new Intent(getApplicationContext(), SehirDetay.class);
                    intent.putExtra("sehir_id", (int)sehir_idler[arg2]);
                    startActivity(intent);
                }
            });
        }

    }
}
