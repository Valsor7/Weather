package com.weather.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;

/**
 * Created by Yaroslav on 23-Feb-16.
 */
public class MainFragment extends android.support.v4.app.Fragment {

    private final static String LOG_TAG = MainFragment.class.getSimpleName();
    private final static String URL = "http://api.openweathermap.org/data/2.5/weather";
    private final static String KEY = "071a8af0738082654026a44ed2ec0b6a";
    private static final String UNITS = "metric";
    private static final String STORED_CITY = "city";
    private static final String FILE_NAME = "my_city";

    private RequestQueue queue;
    private CoordListener coordListener;
    private TextView windText;
    private TextView tempText;
    private TextView minMaxTempText;
    private TextView descrText;
    private TextView detailText;
    private String iconId;
    private ImageView weatherImg;
    private TextView humidityText;
    private TextView pressText;
    private TextView sunUpText;
    private TextView sunDownText;
    private TextView cloudsText;
    private TextView infoText;
    private TextView seaText;
    private TextView groundText;
    private String currCity;
    private SharedPreferences prefs;
    private Weather weather;
    Context c;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmView = inflater.inflate(R.layout.fragment_main, null);

        infoText = (TextView) fragmView.findViewById(R.id.updInfo);
        tempText = (TextView) fragmView.findViewById(R.id.temp);
        minMaxTempText = (TextView) fragmView.findViewById(R.id.minMaxTemp);
        descrText = (TextView) fragmView.findViewById(R.id.descr);
        detailText = (TextView) fragmView.findViewById(R.id.detailDescr);
        windText = (TextView) fragmView.findViewById(R.id.wind);
        humidityText = (TextView) fragmView.findViewById(R.id.humidity);
        pressText = (TextView) fragmView.findViewById(R.id.pressure);
        sunUpText = (TextView) fragmView.findViewById(R.id.sunUp);
        sunDownText = (TextView) fragmView.findViewById(R.id.sunDown);
        cloudsText = (TextView) fragmView.findViewById(R.id.clouds);
        seaText = (TextView) fragmView.findViewById(R.id.seaLvl);
        groundText = (TextView) fragmView.findViewById(R.id.grndLvl);
        weatherImg = (ImageView) fragmView.findViewById(R.id.weatherIcon);

        c = getActivity();
        prefs = c.getSharedPreferences(FILE_NAME, c.MODE_PRIVATE);
        search(prefs.getString(STORED_CITY, "Uzhhorod"));

        return fragmView;
    }

    public void search(String city){
        currCity = city;
        if (queue == null) queue = Volley.newRequestQueue(getActivity());
        queue.add(loadData(city));
    }

    private JsonObjectRequest loadData(String city){
        Uri uri = Uri.parse(URL).buildUpon()
                             .appendQueryParameter("q", city)
                             .appendQueryParameter("units", UNITS)
                             .appendQueryParameter("APPID", KEY)
                             .build();
        Log.d(LOG_TAG, uri.toString());

        JsonObjectRequest request =  new JsonObjectRequest(Request.Method.GET, uri.toString(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        weather = new Weather();
                        weather.parseWeather(jsonObject);
                        fillViews();
                        if (weather.longitude != null && weather.latitude != null) {
                            coordListener = (CoordListener) c;
                            coordListener.onLatLngRes(weather.latitude, weather.longitude);
                        }
                        queue.add(loadImg(weather.iconId));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }
        });

        return request;
    }

    private ImageRequest loadImg(String iconId){

        String searchUrl = String.format("http://openweathermap.org/img/w/%1$s.png", iconId);
        Log.d(LOG_TAG, searchUrl);

        ImageRequest imgReq = new ImageRequest(searchUrl, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                    weatherImg.setImageBitmap(response);
            }
        }, 0, 0, ImageView.ScaleType.FIT_XY, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        return imgReq;
    }

    @Override
    public void onStop() {
        super.onStop();
        prefs = getActivity().getSharedPreferences(FILE_NAME, 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(STORED_CITY, currCity);
        editor.apply();
    }

    private void fillViews(){
        infoText.setText(c.getString(R.string.format_info, Utility.simpleDate(weather.timeUpd), weather.city, weather.country));
        tempText.setText(c.getString(R.string.format_temperature, weather.temp));
        minMaxTempText.setText(c.getString(R.string.format_maxmin, weather.maxTemp, weather.minTemp));
        detailText.setText(weather.detail);
        descrText.setText(weather.descr);
        windText.setText(c.getString(R.string.format_wind, weather.speed, Utility.windDirect(weather.degree)));
        humidityText.setText(c.getString(R.string.format_humidity, weather.humidity));
        pressText.setText(c.getString(R.string.format_pressure, weather.pressure));
        sunUpText.setText(c.getString(R.string.format_sunrise, Utility.simpleDate(weather.sunUp)));
        sunDownText.setText(c.getString(R.string.format_sunset, Utility.simpleDate(weather.sunDn)));
        cloudsText.setText(c.getString(R.string.format_clouds, weather.clouds));

        if (weather.seaLvl != null && weather.grndLvl != null){
            seaText.setText(c.getString(R.string.format_seaLvl, weather.seaLvl));
            groundText.setText(c.getString(R.string.format_grndLvl, weather.grndLvl));
            seaText.setVisibility(View.VISIBLE);
            groundText.setVisibility(View.VISIBLE);
        } else {
            seaText.setVisibility(View.GONE);
            groundText.setVisibility(View.GONE);
        }
    }
}
