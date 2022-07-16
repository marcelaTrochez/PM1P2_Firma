package com.example.PM1_firma;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.PM1_firma.configuraciones.TablaImagenes;
import com.example.PM1P2_firma.R;
import com.example.PM1_firma.configuraciones.SQLiteConexion;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    Bitmap bitmap;
    EditText txtnombre;
    byte[] blob = null;

    private CaptureBitmapView mSig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Añadir Firma");
        txtnombre = (EditText)findViewById(R.id.txtNombre);

        LinearLayout mContent = (LinearLayout) findViewById(R.id.signLayout);
        mSig = new CaptureBitmapView(this, null);
        mContent.addView(mSig, LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);

        findViewById(R.id.btnIngresar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (txtnombre.getText().toString().trim().length() == 0) {
                     txtnombre.setError("Ingrese este campo");
                } else
                {
                    bitmap = mSig.getBitmap();
                    try
                    {
                        setToImageView(getResizeBitmap(bitmap, 200));
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "fallo en tener firmas", Toast.LENGTH_SHORT).show();
                    }

                }


            }
        });

        findViewById(R.id.btnLimpiar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtnombre.setText("");
                mSig.ClearCanvas();
            }
        });

        findViewById(R.id.btnlistado).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),ActivityListado.class);
                startActivity(in);
            }
        });


    }

    private Bitmap getResizeBitmap(Bitmap bitmap, int maxSize) {
        int wi = bitmap.getWidth();
        int he = bitmap.getHeight();

        if (wi<= maxSize && he<=maxSize){
            return bitmap;
        }

        float fabradio = (float)wi/(float)he;

        if(fabradio > 1){
            wi=maxSize;
            he = (int) (wi/fabradio);
        }else{
            he=maxSize;
            wi= (int) (he*fabradio);
        }

        return  Bitmap.createScaledBitmap(bitmap,wi,he,true);
    }

    private void setToImageView(Bitmap bitmap){
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        bitmap = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));
        byte[] blob = bytes.toByteArray();
        setBlob(blob);

        uploadImage();
    }

    public byte[] getBlob() {
        return blob;
    }

    public void setBlob(byte[] blob) {
        this.blob = blob;
    }

    private void uploadImage() {

        if (getBlob() != null) {
            SQLiteConexion conexion = new SQLiteConexion(this, TablaImagenes.NameDataBase, null, 1);
            SQLiteDatabase db = conexion.getWritableDatabase();

            ContentValues valores = new ContentValues();
            valores.put(TablaImagenes.imagen, getBlob());
            valores.put(TablaImagenes.nombre, txtnombre.getText().toString());


            Long res = db.insert(TablaImagenes.tablaimagenes, TablaImagenes.id, valores);
            db.close();


            Toast.makeText(getApplicationContext(), "Se ha guardado su Firma", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Seleccione la imagen." , Toast.LENGTH_SHORT).show();

        }
}

}