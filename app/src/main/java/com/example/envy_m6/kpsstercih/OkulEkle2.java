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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class OkulEkle2 extends Activity {
    EditText et, et2, et3, et4;
    Button btnKaydet;
    Spinner sp;
    TextView tv;
    ArrayAdapter<String> adapter, adapter2;
    ArrayList<HashMap<String, String>> sehir_liste, ilce_liste;
    String sehir_adlari[], ilce_adlari[];
    int sehir_idler[], ilce_idler[];
    int id, id2, sehirin_idi, ilcenin_idi, idx;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okulekle2);

        sp = (Spinner)findViewById(R.id.spinOE2);
        tv = (TextView)findViewById(R.id.textOE2Sehir);
        et = (EditText)findViewById(R.id.editOE);
        et2 = (EditText)findViewById(R.id.editOE2);
        et3 = (EditText)findViewById(R.id.editOE3);
        et4 = (EditText)findViewById(R.id.editOE4);
        btnKaydet = (Button)findViewById(R.id.btnOEKaydet);

        //bir önceki sayfada seçtiğimiz şehri intent ile buraya taşıdık.
        Intent intent=getIntent();
        id = intent.getIntExtra("sehirin_idi", 0);

        //Veritabanını çağırdık ve hashmapı açtık.
        Database db = new Database(getApplicationContext());
        HashMap<String, String> map = db.sehirDetay(id); // şehirden gelen id'yi hashmap'e aldık.

        //şehir adını önceki sayfadan taşıyıp gelmiştik.
        //aşağıda okul ekleme yaparken kullanacağız.
        tv.setText(map.get("sehir_adi"));


        btnKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ilceAdi, sehirAdi, okulAdi, bilgi1, bilgi2, bilgi3;

                ilceAdi = sp.getSelectedItem().toString();//ilçe daha önce kayıtlı olan ilçedir. spinnerdan seçilir.
                sehirAdi = tv.getText().toString();//daha önce seçip geldiğimiz şehirdir.
                okulAdi = et.getText().toString();
                bilgi1 = et2.getText().toString();
                bilgi2 = et3.getText().toString();
                bilgi3 = et4.getText().toString();

                if(okulAdi.matches("") || bilgi1.matches("") || bilgi2.matches("") || bilgi3.matches("")){
                    Toast.makeText(getApplicationContext(), "Boş Bırakılamaz.", Toast.LENGTH_LONG).show();
                }
                else{
                    Database db = new Database(getApplicationContext());
                    Intent i = getIntent();
                    id = i.getIntExtra("okul_id", 0);

                    //okul ekle metoduyla yeni bilgileri ve daha önceki bilgileri ekliyoruz.
                    //il ve ilçe tekrar ekleniyor çünkü b ilçesi kesinlikle a şehrindedir ve c okulu kesinlikle b ilçesindedir.
                    db.okulEkle(okulAdi, sehirAdi, ilceAdi, bilgi1, bilgi2, bilgi3, ilcenin_idi);
                    db.close();
                    Toast.makeText(getApplicationContext(), "Okul Başarıyla Eklendi.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), OkulListele2.class);
                    intent.putExtra("ilcenin_idi", ilcenin_idi);
                    startActivity(intent);
                    finish();
                    et.setText("Eklendi");
                }
            }
        });
    }

    //bu metod ilçeyi spinner ile göstermek içindir.
    public void onResume()
    {
        super.onResume();
        Database db = new Database(getApplicationContext());
        ilce_liste = db.ilceler(id);
        if (ilce_liste.size()==0){
            Toast.makeText(getApplicationContext(), "Henüz İlçe Eklenmemiş. İlçe Ekleyin.", Toast.LENGTH_LONG).show();
        }else {
            ilce_adlari = new String[ilce_liste.size()];
            ilce_idler = new int[ilce_liste.size()];
            for(int i=0;i<ilce_liste.size();i++){
                ilce_adlari[i] = ilce_liste.get(i).get("ilce_adi");

                ilce_idler[i] = Integer.parseInt(ilce_liste.get(i).get("ilce_id"));
            }

            sp = (Spinner) findViewById(R.id.spinOE2);

            adapter2 = new ArrayAdapter<String>(this, R.layout.list_item, R.id.textItem, ilce_adlari);

            sp.setAdapter(adapter2);
            sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1, final int arg2, long arg3) {
                    Intent intent=getIntent();
                    id = intent.getIntExtra("ilce_id", 0);

                    ilcenin_idi = ilce_idler[arg2];
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }


    }
}
