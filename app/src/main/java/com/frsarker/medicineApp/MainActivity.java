package com.frsarker.medicineApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.frsarker.medicineApp.adapter.MedicineAdapter;
import com.frsarker.medicineApp.data.Medicine_object;
import com.frsarker.medicineApp.model.FileManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {


    ArrayList<String>medicineTypes;
    ArrayList<Medicine_object> medicine_objects, medicineObjectsForShow = new ArrayList<>();
    Spinner spinnerMediacineType;
    String selectMediacineType;
    MedicineAdapter medicineAdapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FindById();
    }

    @Override
    protected void onStart() {
        super.onStart();
        InitialCityCode();
        SetAdapterSpinnerMedicineType(medicineTypes);
        SetAdapterForRecycleView();

    }
    private void FindById(){
        spinnerMediacineType = findViewById(R.id.spinnerMediacineType);
        spinnerMediacineType.setOnItemSelectedListener(this);
        recyclerView = findViewById(R.id.recyclerView);
    }

    private void SetAdapterSpinnerMedicineType(ArrayList<String> citiesCode){

        ArrayAdapter spinnerAdapter = new ArrayAdapter(this,R.layout.simple_spinner_item_custom, citiesCode);

        spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_custom);
        // Set the ArrayAdapter (ad) data on the
        // Spinner which binds data to spinner
        spinnerMediacineType.setAdapter(spinnerAdapter);

    }

    private  void SetAdapterForRecycleView(){
        medicineAdapter = new MedicineAdapter(this, medicineObjectsForShow);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(medicineAdapter);
    }
    private void InitialCityCode(){
        FileManager fileManager = new FileManager(getApplicationContext());
        medicine_objects = fileManager.ReadMediacineInfoFromFile();
        medicineTypes = GetListMediacineType(medicine_objects);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectMediacineType = spinnerMediacineType.getAdapter().getItem(position).toString();
        medicineObjectsForShow = GetListForShow(selectMediacineType);
        if (medicineObjectsForShow == null)
            return;
        medicineAdapter.SetMedicineObject(medicineObjectsForShow);
        medicineAdapter.notifyDataSetChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private ArrayList<String>GetListMediacineType(ArrayList<Medicine_object> medicine_objects){
        ArrayList<String> listMediacineTypes = new ArrayList<>();
        for (Medicine_object medicine_object: medicine_objects){
            if(!listMediacineTypes.contains(medicine_object.getType()))
                listMediacineTypes.add(medicine_object.getType().trim());
        }
        return  listMediacineTypes;
    }
    private ArrayList<Medicine_object> GetListForShow(String selectType){
        ArrayList<Medicine_object> medicine_objects_for_show= new ArrayList<>();
        if(medicine_objects == null)
            return null;
        for (Medicine_object medicine_object: medicine_objects){
            String type = medicine_object.getType();
            if(medicine_object.getType().contains(selectType))
                medicine_objects_for_show.add(medicine_object);
        }
        return medicine_objects_for_show;
    }

}
