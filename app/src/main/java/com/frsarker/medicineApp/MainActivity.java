package com.frsarker.medicineApp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.frsarker.medicineApp.model.FileManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {


    ArrayList<String>medicineTypes;

    Spinner spiner_citys;
    String selectCityCode= "Hà Nội";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FindById();
    }

    @Override
    protected void onStart() {
        super.onStart();
        medicineTypes = InitialCityCode();
        SetAdapterSpinnerCitys(medicineTypes);

    }
    private void FindById(){
        spiner_citys = findViewById(R.id.address);
        spiner_citys.setOnItemSelectedListener(this);
    }

    private void SetAdapterSpinnerCitys(ArrayList<String> citiesCode){

        ArrayAdapter spinnerAdapter = new ArrayAdapter(this,R.layout.simple_spinner_item_custom, citiesCode);

        spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_custom);
        // Set the ArrayAdapter (ad) data on the
        // Spinner which binds data to spinner
        spiner_citys.setAdapter(spinnerAdapter);
    }
    private ArrayList<String> InitialCityCode(){
        ArrayList<String>cities_code= new ArrayList<>();
        FileManager fileManager = new FileManager(getApplicationContext());
        String medicineImageFolderPath = fileManager.GetMedicineImageFolderPath();
        String medicineInfoFolderPath = fileManager.GetMedicineInfoFolderPath();

//        String [] citys = Data.city_list_String.split(",");
//        for (String city: citys){
//            String city_str = city.trim();
//            if(!city_str.isEmpty())
//                cities_code.add(city_str);
//        }
        return  cities_code;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectCityCode = spiner_citys.getAdapter().getItem(position).toString();
        new weatherTask().execute();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
            String response ="" ;//= HttpRequest.excuteGet("https://api.openweathermap.org/data/2.5/weather?q=" + selectCityCode+ Data.contryCode + "&units=metric&appid=" + API);
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
