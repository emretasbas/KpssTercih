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

public class OkulDetay extends Activity {
    TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9, tv10, tv11, tv12;
    Button btnSil;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okuldetay);

        tv1 = (TextView)findViewById(R.id.text1);
        tv2 = (TextView)findViewById(R.id.text2);
        tv3 = (TextView)findViewById(R.id.text3);
        tv4 = (TextView)findViewById(R.id.text4);
        tv5 = (TextView)findViewById(R.id.text5);
        tv6 = (TextView)findViewById(R.id.text6);
        tv7 = (TextView)findViewById(R.id.text7);
        tv8 = (TextView)findViewById(R.id.text8);
        tv9 = (TextView)findViewById(R.id.text9);
        tv10 = (TextView)findViewById(R.id.text10);
        tv11 = (TextView)findViewById(R.id.text11);
        tv12 = (TextView)findViewById(R.id.text12);
        btnSil = (Button)findViewById(R.id.btnODSil);

        Intent intent=getIntent();
        id = intent.getIntExtra("okul_id", 0);

        //veritabanını açtık ve hashmapı çağırdık.
        Database db = new Database(getApplicationContext());
        HashMap<String, String> map = db.okulDetay(id);

        //hashmapte olan bilgileri burada yansıtıyoruz.
        tv2.setText(map.get("sehir_adi"));
        tv4.setText(map.get("ilce_adi").toString());
        tv6.setText(map.get("okul_adi").toString());
        tv8.setText(map.get("okul_bilgi1").toString());
        tv10.setText(map.get("okul_bilgi2").toString());
        tv12.setText(map.get("okul_bilgi3").toString());

        btnSil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(OkulDetay.this);
                alertDialog.setTitle("Uyarı");
                alertDialog.setMessage("Okul Silinsin mi?");
                alertDialog.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        Database db = new Database(getApplicationContext());
                        db.okulSil(id);
                        Toast.makeText(getApplicationContext(), "Okul Başarıyla Silindi", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), OkulListele.class);
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

