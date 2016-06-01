package com.example.envy_m6.kpsstercih;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class IlceEkle extends Activity {
    EditText et;
    Button btnKaydet;
    Spinner sp;
    ArrayAdapter<String> adapter;
    ArrayList<HashMap<String, String>> sehir_liste;
    String sehir_adlari[];
    int sehir_idler[];
    int id, sehirin_idi;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ilceekle);

        sp = (Spinner)findViewById(R.id.spinIE);
        et = (EditText)findViewById(R.id.editIE);
        btnKaydet = (Button)findViewById(R.id.btnIEKaydet);

        btnKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ilceAdi, sehirAdi;

                sehirAdi = sp.getSelectedItem().toString();
                ilceAdi = et.getText().toString();

                if(ilceAdi.matches("")){
                    Toast.makeText(getApplicationContext(), "Boş Bırakılamaz.", Toast.LENGTH_LONG).show();
                }
                else{
                    Database db = new Database(getApplicationContext());
                    Intent i = getIntent();
                    id = i.getIntExtra("ilce_id", 0);

                    //edittexten gelen ilçeyi, spinnerdan gelen şehri ve burda tanımladığımız intent nesnesini birlikte ekliyoruz.
                    //şehri tekrar eklememin sebebi, her ilçe bir şehre bağlı olmak zorundadır.
                    db.ilceEkle(ilceAdi, sehirAdi, sehirin_idi);
                    db.close();
                    Toast.makeText(getApplicationContext(), "İlçe Başarıyla Eklendi.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), IlceListele2.class);
                    intent.putExtra("sehirin_idi", sehirin_idi);
                    startActivity(intent);
                    finish();
                    et.setText("Eklendi");
                }
            }
        });
    }

    //Buradaki işlem, daha önce eklediğimiz şehirleri spinner'a çağırmaktır.
    //Yukarıda tanımladığımız ekleme metoduna bu spinnerdaki şehir bilgisinide eklicez.
    public void onResume()
    {
        super.onResume();
        Database db = new Database(getApplicationContext());
        sehir_liste = db.sehirler();
        if(sehir_liste.size()==0){
            Toast.makeText(getApplicationContext(), "Henüz Şehir Eklenmemiş. Önce Şehir Ekleyin.", Toast.LENGTH_LONG).show();
        }else{
            sehir_adlari = new String[sehir_liste.size()];
            sehir_idler = new int[sehir_liste.size()];
            for(int i=0;i<sehir_liste.size();i++){
                sehir_adlari[i] = sehir_liste.get(i).get("sehir_adi");

                sehir_idler[i] = Integer.parseInt(sehir_liste.get(i).get("sehir_id"));
            }

            sp = (Spinner) findViewById(R.id.spinIE);

            adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.textItem, sehir_adlari);

            sp.setAdapter(adapter);
            sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1, final int arg2, long arg3) {
                    Intent intent=getIntent();
                    id = intent.getIntExtra("sehir_id", 0);

                    sehirin_idi = sehir_idler[arg2];
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }
}
