package com.frsarker.medicineApp;

import androidx.appcompat.app.AppCompatActivity;
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
    ArrayList<Medicine_object> medicine_objects, medicineObjectsForShow;
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
        SetAdapterSpinnerCitys(medicineTypes);
        SetAdapterForRecycleView();

    }
    private void FindById(){
        spinnerMediacineType = findViewById(R.id.spinnerMediacineType);
        spinnerMediacineType.setOnItemSelectedListener(this);
        recyclerView = findViewById(R.id.recyclerView);
    }

    private void SetAdapterSpinnerCitys(ArrayList<String> citiesCode){

        ArrayAdapter spinnerAdapter = new ArrayAdapter(this,R.layout.simple_spinner_item_custom, citiesCode);

        spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_custom);
        // Set the ArrayAdapter (ad) data on the
        // Spinner which binds data to spinner
        spinnerMediacineType.setAdapter(spinnerAdapter);

    }

    private  void SetAdapterForRecycleView(){
        medicineAdapter = new MedicineAdapter(getApplicationContext(), medicineObjectsForShow);
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
        medicineAdapter.notifyDataSetChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private ArrayList<String>GetListMediacineType(ArrayList<Medicine_object> medicine_objects){
        ArrayList<String> listMediacineTypes = new ArrayList<>();
        for (Medicine_object medicine_object: medicine_objects){
            if(!listMediacineTypes.contains(medicine_object.getType()))
                listMediacineTypes.add(medicine_object.getType());
        }
        return  listMediacineTypes;
    }
    private ArrayList<Medicine_object> GetListForShow(String selectType){
        ArrayList<Medicine_object> medicine_objects_for_show= new ArrayList<>();
        if(medicine_objects == null)
            return null;
        for (Medicine_object medicine_object: medicine_objects){
            if(medicine_object.getType() == selectType)
                medicine_objects_for_show.add(medicine_object);
        }
        return medicine_objects_for_show;
    }


    class weatherTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            /* Showing the ProgressBar, Making the main design GONE */
            findViewById(R.id.loader).setVisibility(View.VISIBLE);
            findViewById(R.id.mainContainer).setVisibility(View.GONE);
        }

        protected String doInBackground(String... args) {
            String response ="" ;//= HttpRequest.excuteGet("https://api.openweathermap.org/data/2.5/weather?q=" + selectMediacineType+ Data.contryCode + "&units=metric&appid=" + API);
            return response;
        }

        @Override
        protected void onPostExecute(String result) {


            try {
                JSONObject jsonObj = new JSONObject(result);
                JSONObject main = jsonObj.getJSONObject("main");
                JSONObject sys = jsonObj.getJSONObject("sys");
                JSONObject wind = jsonObj.getJSONObject("wind");
                JSONObject weather = jsonObj.getJSONArray("weather").getJSONObject(0);

                Long updatedAt = jsonObj.getLong("dt");
                String updatedAtText = "Updated at: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(new Date(updatedAt * 1000));
                String temp = main.getString("temp") + "°C";
                String tempMin = "Min Temp: " + main.getString("temp_min") + "°C";
                String tempMax = "Max Temp: " + main.getString("temp_max") + "°C";
                String pressure = main.getString("pressure");
                String humidity = main.getString("humidity");

                Long sunrise = sys.getLong("sunrise");
                Long sunset = sys.getLong("sunset");
                String windSpeed = wind.getString("speed");
                String weatherDescription = weather.getString("description");

                String address = jsonObj.getString("name") + ", " + sys.getString("country");

//
//                /* Populating extracted data into our views */
//                //addressTxt.setText(address);
//                updated_atTxt.setText(updatedAtText);
//                statusTxt.setText(weatherDescription.toUpperCase());
//                tempTxt.setText(temp);
//                temp_minTxt.setText(tempMin);
//                temp_maxTxt.setText(tempMax);
//                sunriseTxt.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(sunrise * 1000)));
//                sunsetTxt.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(sunset * 1000)));
//                windTxt.setText(windSpeed);
//                pressureTxt.setText(pressure);
//                humidityTxt.setText(humidity);

                /* Views populated, Hiding the loader, Showing the main design */
                findViewById(R.id.loader).setVisibility(View.GONE);
                findViewById(R.id.mainContainer).setVisibility(View.VISIBLE);


            } catch (JSONException e) {
                //findViewById(R.id.loader).setVisibility(View.GONE);
                //findViewById(R.id.errorText).setVisibility(View.VISIBLE);
                findViewById(R.id.loader).setVisibility(View.GONE);
                findViewById(R.id.mainContainer).setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), "Không tìm thấy dữ liệu của thành phố này", Toast.LENGTH_LONG).show();
            }

        }
    }


}
