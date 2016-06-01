package com.example.envy_m6.kpsstercih;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class IlceDetay extends Activity {
    TextView tv1, tv2, tv3, tv4;
    Button btnSil;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ilcedetay);

        tv1 = (TextView)findViewById(R.id.textilceDetay1);
        tv2 = (TextView)findViewById(R.id.textilceDetay2);
        tv3 = (TextView)findViewById(R.id.textilceDetay3);
        tv4 = (TextView)findViewById(R.id.textilceDetay4);
        btnSil = (Button)findViewById(R.id.btnYDSil);

        //dokunduğumuz ilçe idsinin detayına geldik.
        Intent intent=getIntent();
        id = intent.getIntExtra("ilce_id", 0);

        //veritabanı bağlantısını açtık.
        //hashmapı çağırdık.
        Database db = new Database(getApplicationContext());
        HashMap<String, String> map = db.ilceDetay(id);

        //hashmapteki bilgileri tanımladığımız textviewlere yansıttık.
        tv2.setText(map.get("sehir_adi"));
        tv4.setText(map.get("ilce_adi").toString());

        //ilçe sil metodunu burada kullandık.
        btnSil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(IlceDetay.this);
                alertDialog.setTitle("Uyarı");
                alertDialog.setMessage("İlçe Silinsin mi?");
                alertDialog.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        Database db = new Database(getApplicationContext());
                        db.ilceSil(id);
                        Toast.makeText(getApplicationContext(), "İlçe Başarıyla Silindi", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), IlceListele.class);
                        startActivity(intent);
                        finish();
                    }
                });
                alertDialog.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {

                    }
                });
                alertDialog.show();
            }
        });
    }
}

