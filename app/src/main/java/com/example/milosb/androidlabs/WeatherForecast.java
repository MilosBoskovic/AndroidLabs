package com.example.milosb.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherForecast extends Activity {

    protected static final String ACTIVITY_NAME = "WeatherForecast";

    private TextView currentTemp;
    private TextView minTemp;
    private TextView maxTemp;
    private TextView windSpd;
    private ImageView weatherImage;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        Log.i(ACTIVITY_NAME, "In onCreate");

        currentTemp = (TextView) findViewById(R.id.current_temp);
        minTemp = (TextView) findViewById(R.id.min_temp);
        maxTemp = (TextView) findViewById(R.id.max_temp);
        windSpd = (TextView) findViewById(R.id.wind_speed);
        weatherImage = (ImageView) findViewById(R.id.weather_pic);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);


        ForecastQuery forecast = new ForecastQuery();
        String url = "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric";

        forecast.execute(url);

    }


    public class ForecastQuery extends AsyncTask<String, Integer, String> {

        String min;
        String max;
        String current;
        String windSpeed;
        String iconName;
        String weatherValue;
        String degree = "\u00b0";
        Bitmap icon;


        @Override
        protected String doInBackground(String... args){
            Log.i(ACTIVITY_NAME, " subclass ForecastQuery: In doInBackground");

            try{
                URL url = new URL(args[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();

                InputStream stream = conn.getInputStream();

                XmlPullParser parser = Xml.newPullParser();
                parser.setInput(stream, null);

                while(parser.next() != XmlPullParser.END_DOCUMENT) {

                    // only need to look for starting tags
                    if(parser.getEventType() != XmlPullParser.START_TAG){
                        continue;
                    }

                    if(parser.getName().equals("temperature")){

                        current = parser.getAttributeValue(null, "value");
                        publishProgress(20);
                        min = parser.getAttributeValue(null, "min");
                        publishProgress(40);
                        max = parser.getAttributeValue(null, "max");
                        publishProgress(60);

                    }

                    if(parser.getName().equals("speed")){

                        windSpeed = parser.getAttributeValue(null, "value");
                        publishProgress(80);

                    }

                    if(parser.getName().equals("weather")){

                        iconName = parser.getAttributeValue(null, "icon");
                        weatherValue = parser.getAttributeValue(null, "value");

                        String iconFile = iconName+".png";

                        Log.i(ACTIVITY_NAME, " subclass ForecastQuery: searching for image " + iconFile);


                        if(fileExistance(iconFile)) {

                            FileInputStream fis = null;
                            try {
                                fis = new FileInputStream(getBaseContext().getFileStreamPath(iconFile));
                                Log.i(ACTIVITY_NAME, " subclass ForecastQuery: image found.");
                            }
                            catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            icon = BitmapFactory.decodeStream(fis);

                        } else {

                            Log.i(ACTIVITY_NAME, " subclass ForecastQuery: downloading image from server");

                            icon = getImage("http://openweathermap.org/img/w/" + iconName + ".png");

                            FileOutputStream outputStream = openFileOutput(iconName + ".png", Context.MODE_PRIVATE);
                            icon.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                            outputStream.flush();
                            outputStream.close();

                        }

                        publishProgress(100);

                    }
                }
            } catch (Exception exc) {
                exc.getMessage();
            }

            return null;
        } // end of doInBackground

        @Override
        public void onProgressUpdate(Integer... value){

            Log.i(ACTIVITY_NAME, "subclass ForecastQuery: In onProgressUpdate");
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(value[0]);

        } // end of onProgressUpdate

        @Override
        public void onPostExecute(String value){

            Log.i(ACTIVITY_NAME, "subclass ForecastQuery: In onPostExecute");


            currentTemp.setText(currentTemp.getText()+current+degree+"C");
            minTemp.setText(minTemp.getText()+min+degree+"C");
            maxTemp.setText(maxTemp.getText()+max+degree+"C");
            windSpd.setText(windSpd.getText()+ windSpeed+" km/h");
            weatherImage.setImageBitmap(icon);
            progressBar.setVisibility(View.INVISIBLE);

        } // end of onPostExecute
    } // end of class ForecastQuery

    public boolean fileExistance(String fname){
        Log.i(ACTIVITY_NAME, "In fileExistance");
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();
    } // end of fileExistance

    public static Bitmap getImage(URL url) {
        Log.i(ACTIVITY_NAME, "In getImage(URL url)");
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                return BitmapFactory.decodeStream(connection.getInputStream());
            } else
                return null;
        } catch (Exception e) {
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    } // end of getImage(URL url)

    public static Bitmap getImage(String urlString) {
        Log.i(ACTIVITY_NAME, "In getImage(String urlString)");
        try {
            URL url = new URL(urlString);
            return getImage(url);
        } catch (MalformedURLException e) {
            return null;
        }
    } // end of getImage(String urlString)

} //end of class WeatherForecast
