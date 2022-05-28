package com.app.asma;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.TimeZone;

public class Task4Activity extends AppCompatActivity {

    TextView  textViewHumidity,textViewTemperature;
    EditText editText;
    Button buttonGet;
    ImageView imageViewWeather;
    String weatherWebserviceURL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task4);

        textViewTemperature = (TextView) findViewById(R.id.txtTemprature);
        textViewHumidity = (TextView) findViewById(R.id.txtHumidity);

        imageViewWeather = (ImageView) findViewById(R.id.ivWeather);

        editText = (EditText) findViewById(R.id.etCity);

        buttonGet = (Button) findViewById(R.id.btnSearch);
        buttonGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url="http://api.openweathermap.org/data/2.5/weather?q="+editText.getText().toString()+"&appid=1f1d6588819771e3d082ba37cbf28916&units=metric";
                weather(url);
            }
        });


    }

    public void weather(String url) {

        JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(JSONObject response) {
                        String icon = "";
                        Log.d("Asma","Succeeded retrieving URL");
                        Log.d("Asma", response.toString());

                        try {


                            //nested object
                            JSONObject jsonMain=response.getJSONObject("main");

                            double temp=jsonMain.getDouble("temp");

                            Log.d("Asma-temp",String.valueOf(temp));
                            textViewTemperature.setText(temp+"");

                            JSONArray jsonArray = response.getJSONArray("weather");
                            for (int i=0; i<jsonArray.length();i++){
                                Log.d("Asma-array",jsonArray.getString(i));
                                JSONObject oneObject = jsonArray.getJSONObject(i);
                                String weather = oneObject.getString("main");
                                Log.d("Asma-w",weather);
                                JSONObject twoObject = jsonArray.getJSONObject(i);
                                icon=twoObject.getString("icon");
                                Log.d("Asma-icon",icon);

                                if(weather.equals("Clear")){
                                    Glide.with(Task4Activity.this).load("http://openweathermap.org/img/w/"+icon+".png").into(imageViewWeather);
                                }
                                else if(weather.equalsIgnoreCase("Rain") || weather.equalsIgnoreCase( "Thunderstorm") || weather.equalsIgnoreCase("Drizzle")){
                                    Glide.with(Task4Activity.this).load("http://openweathermap.org/img/w/"+icon+".png").into(imageViewWeather);
                                }
                                else if(weather.equals("Clouds")){
                                    Glide.with(Task4Activity.this).load("http://openweathermap.org/img/w/"+icon+".png").into(imageViewWeather);
                                }
                                else if (weather.equalsIgnoreCase("mist") || weather.equalsIgnoreCase("haze") ){
                                    Glide.with(Task4Activity.this).load("http://openweathermap.org/img/w/"+icon+".png").into(imageViewWeather);
                                }
                                else{
                                    Glide.with(Task4Activity.this).load("http://openweathermap.org/img/w/"+icon+".png").into(imageViewWeather);
                                }

                            }

                            JSONObject jsonM=response.getJSONObject("main");

                            double humid=jsonM.getDouble("humidity");

                            Log.d("Asma-humidity",String.valueOf(humid));
                            textViewHumidity.setText(humid+"%");

                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Task4Activity.this);
                            SharedPreferences.Editor editor = preferences.edit();
                             editor.putString("Temprature",textViewTemperature.getText().toString());
                             editor.putString("City",editText.getText().toString());
                             editor.putString("Image","http://openweathermap.org/img/w/"+icon+".png");
                             editor.apply();



                        }


                        catch (JSONException e){
                            e.printStackTrace();
                            Log.d("Receive Errror",e.toString());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Asma","Error retrieving URL");
                Toast.makeText(Task4Activity.this, "Incorrect Spelling", Toast.LENGTH_SHORT).show();

            }
        }
        );
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObj);

    }
}