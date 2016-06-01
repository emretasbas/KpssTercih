package com.example.envy_m6.kpsstercih;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class OkulEkle extends Activity {
    Spinner sp;
    ArrayAdapter<String> adapter;
    ArrayList<HashMap<String, String>> sehir_liste;
    String sehir_adlari[];
    int sehir_idler[];
    Button btnSec;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okulekle);

    }

    //Burada ekleme yapmadan önce şehir seçip, intent ile bir sonraki sayfaya seçtiğimiz şehri gönderiyoruz.
    //Çünkü diğer sayfada da bu şehrin ilçelerini seçeceğimiz bir spinner daha olacak.
    //Burada seçip diğer sayfada ekleme yapıyoruz bilgileri.
    public void onResume()
    {
        super.onResume();
        Database db = new Database(getApplicationContext());
        sehir_liste = db.sehirler();
        if(sehir_liste.size()==0){
            Toast.makeText(getApplicationContext(), "Henüz Şehir Eklenmemiş.", Toast.LENGTH_LONG).show();
        }else{
            sehir_adlari = new String[sehir_liste.size()];
            sehir_idler = new int[sehir_liste.size()];
            for(int i=0;i<sehir_liste.size();i++){
                sehir_adlari[i] = sehir_liste.get(i).get("sehir_adi");

                sehir_idler[i] = Integer.parseInt(sehir_liste.get(i).get("sehir_id"));

            }

            sp = (Spinner) findViewById(R.id.spinXSec);
            btnSec = (Button) findViewById(R.id.btnXSec);

            adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.textItem, sehir_adlari);

            sp.setAdapter(adapter);
            sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1, final int arg2, long arg3) {
                    btnSec.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(), OkulEkle2.class);
                            intent.putExtra("sehirin_idi", (int) sehir_idler[arg2]);
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
