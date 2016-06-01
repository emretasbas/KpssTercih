package com.example.envy_m6.kpsstercih;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SehirEkle extends Activity {
    Button btnKaydet;
    EditText et;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sehirekle);

        btnKaydet = (Button)findViewById(R.id.btnSehirEkle);
        et = (EditText)findViewById(R.id.editAEPlaka);

        //Kullanıcının girdiği şehir bilgisini burada kaydeder.
        btnKaydet.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String sehirAdi;
                sehirAdi = et.getText().toString();
                if(sehirAdi.matches("")){
                    Toast.makeText(getApplicationContext(), "Boş Bırakılamaz", Toast.LENGTH_LONG).show();
                }else{
                    Database db = new Database(getApplicationContext());//veritabanı çağrılır.
                    db.sehirEkle(sehirAdi);// bu metod çağrılır ve ilgili tabloya ekleme yapılır.
                    db.close();
                    Toast.makeText(getApplicationContext(), "Şehir Başarıyla Eklendi.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), SehirListele.class);
                    startActivity(intent);
                    finish();
                    et.setText("Eklendi");
                }
            }
        });
    }
}
