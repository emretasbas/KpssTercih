package com.example.envy_m6.kpsstercih;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class OkulListele2 extends Activity {
    Spinner sp;
    ArrayAdapter<String> adapter;
    ArrayList<HashMap<String, String>> ilce_liste;
    String ilce_adlari[];
    int ilce_idler[];
    int id;
    Button btnSec;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okullistele2);

        //bir önceki sayfadan şehir bilgisi ile ilçelere geldik.
        Intent intent=getIntent();
        id = intent.getIntExtra("sehirin_idi", 0);

    }

    // x şehrinin ilçelerini listeliyoruz. buradan da intent nesnesi(ilcenin_idi) ile y ilçesinin okullarına gideceğiz.
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
            sp = (Spinner) findViewById(R.id.spinOkulListele2);
            btnSec = (Button) findViewById(R.id.btnOLSec2);

            adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.textItem, ilce_adlari);
            sp.setAdapter(adapter);

            sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1, final int arg2, long arg3) {
                    btnSec.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(), OkulListele3.class);
                            intent.putExtra("ilcenin_idi", (int) ilce_idler[arg2]);
                            startActivity(intent);
                        }
                    });
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }
}
