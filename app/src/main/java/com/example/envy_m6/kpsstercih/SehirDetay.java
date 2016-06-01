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

public class SehirDetay extends Activity {
    Button btnSil;
    TextView tv, tv2;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sehirdetay);

        btnSil = (Button)findViewById(R.id.btnSDSil);

        tv = (TextView)findViewById(R.id.textSD1);
        tv2= (TextView)findViewById(R.id.textSD2);

        //Bir önceki sayfada listviewde tıkladığımız nesnenin id'si çağrılır.
        Intent intent=getIntent();
        id = intent.getIntExtra("sehir_id", 0);

        //Database açılır ve ilgili hashmap çağrılır.
        Database db = new Database(getApplicationContext());
        HashMap<String, String> map = db.sehirDetay(id);

        //hashmap içindeki veri yansıtılır. bu tabloda sadece şehir adı tutuyoruz.
        tv2.setText(map.get("sehir_adi"));

        //veritabanındaki silme metodu kullanılır.
        btnSil.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SehirDetay.this);
                alertDialog.setTitle("Uyarı");
                alertDialog.setMessage("Şehir Silinsin mi?");
                alertDialog.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        Database db = new Database(getApplicationContext());
                        db.sehirSil(id);
                        Toast.makeText(getApplicationContext(), "Şehir Başarıyla Silindi", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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
