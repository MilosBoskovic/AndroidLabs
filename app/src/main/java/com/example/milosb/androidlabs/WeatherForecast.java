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
    private ImageView weatherImage;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        currentTemp = (TextView) findViewById(R.id.current_temp);
        minTemp = (TextView) findViewById(R.id.min_temp);
        maxTemp = (TextView) findViewById(R.id.max_temp);
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
        Bitmap icon;


        @Override
        protected String doInBackground(String... args){

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
                //parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(stream, null);
                //parser.nextTag();

                while(parser.next() != XmlPullParser.END_DOCUMENT) {

                    // only need to look for starting tags
                    if(parser.getEventType() != XmlPullParser.START_TAG){
                        continue;
                    }

                    if(parser.getName().equals("temperature")){

                        current = parser.getAttributeValue(null, "value");
                        publishProgress(25);
                        min = parser.getAttributeValue(null, "min");
                        publishProgress(50);
                        max = parser.getAttributeValue(null, "max");
                        publishProgress(75);

                    }

                    if(parser.getName().equals("wind")){

                        windSpeed = parser.getAttributeValue(null, "speed");

                    }

                    if(parser.getName().equals("weather")){

                        iconName = parser.getAttributeValue(null, "icon");
                        weatherValue = parser.getAttributeValue(null, "value");

                        String iconFile = iconName+".png";

                        if(fileExistance(iconFile)) {

                            FileInputStream fis = null;
                            try {
                                fis = new FileInputStream(getBaseContext().getFileStreamPath(iconFile));
                            }
                            catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            icon = BitmapFactory.decodeStream(fis);

                        } else {

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

            Log.i(ACTIVITY_NAME, "In onProgressUpdate");
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(value[0]);

        } // end of onProgressUpdate

        @Override
        public void onPostExecute(String value){

            currentTemp.setText(currentTemp.getText()+current+"C");
            minTemp.setText(minTemp.getText()+min+"C");
            maxTemp.setText(maxTemp.getText()+max+"C");
            weatherImage.setImageBitmap(icon);
            progressBar.setVisibility(View.INVISIBLE);

        } // end of onPostExecute
    } // end of class ForecastQuery

    public boolean fileExistance(String fname){
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();
    } // end of fileExistance

    public static Bitmap getImage(URL url) {
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
        try {
            URL url = new URL(urlString);
            return getImage(url);
        } catch (MalformedURLException e) {
            return null;
        }
    } // end of getImage(String urlString)

} //end of class WeatherForecast
