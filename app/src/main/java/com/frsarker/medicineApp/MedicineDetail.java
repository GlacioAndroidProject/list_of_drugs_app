package com.frsarker.medicineApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.frsarker.medicineApp.data.Medicine_object;

import java.io.IOException;
import java.io.InputStream;

public class MedicineDetail extends AppCompatActivity {

    TextView medicine_name, medicine_type, medicine_content, medicine_uses;
    ImageView imAvata;
    Medicine_object medicine_object;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_detail);
        FindById();
        GetIntent();
    }

    @Override
    protected void onStart() {
        super.onStart();
        SetView(medicine_object);

    }

    private void FindById(){
        medicine_name = findViewById(R.id.medicine_name);
        medicine_type = findViewById(R.id.medicine_type);
        medicine_content = findViewById(R.id.medicine_content);
        medicine_uses = findViewById(R.id.medicine_uses);
        imAvata = findViewById(R.id.imAvata);

    }
    private void GetIntent(){
        Intent intent = getIntent();
        medicine_object = intent.getExtras().getParcelable("medicine_object");
    }


    private void SetView(Medicine_object medicine_object){
        if (medicine_object== null)
            return;
        medicine_name.setText(medicine_object.getName());
        medicine_type.setText(medicine_object.getType());
        medicine_content.setText(medicine_object.getContent());
        medicine_uses.setText(medicine_object.getUses());
        // get input stream
        InputStream ims = null;
        try {
            System.out.println("Imageurl: "+ medicine_object.getImageUrl());
            if(medicine_object.getImageUrl()==null)
            {
                imAvata.setImageResource(R.drawable.missing_image);
                return;
            }
            ims = this.getAssets().open(medicine_object.getImageUrl());
            // load image as Drawable
            Drawable image = Drawable.createFromStream(ims, null);
            // set image to ImageView
            imAvata.setImageDrawable(image);
            ims .close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}