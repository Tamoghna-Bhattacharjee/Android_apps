package weatherforcast.weatherforcast;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText txt;
    TextView textView;

    public class DownloadData extends AsyncTask<String , Void , String>{

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            //Log.i("tamoghna" , urls[0]);
            try {

                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream in = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                while(data >= 0)
                {
                    result += (char) data;
                    data = reader.read();
                }

            }catch (Exception e)
            {
                Log.i("tamoghna" , e.toString());
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //Log.i("tamoghna" , s);
            try {
                JSONObject jsonObject = new JSONObject(s);

                JSONArray weatherArr = jsonObject.getJSONArray("weather");
                JSONObject weather = weatherArr.getJSONObject(0);

                JSONObject main = jsonObject.getJSONObject("main");

                textView.setText("Description: " + weather.getString("main"));
                textView.append("\nTemperature: " + main.getString("temp") + "degree" +
                        "\nPressure: " + main.getString("pressure") + " bar"+
                        "\nHumidity: " + main.getString("humidity") + "%"
                );

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void getWeather(View view)
    {
        DownloadData downloadData = new DownloadData();
        String cityName = txt.getText().toString();

        downloadData.execute("https://openweathermap.org/data/2.5/weather?q="+ cityName.trim().toLowerCase() +"&appid=b6907d289e10d714a6e88b30761fae22");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt = (EditText)  findViewById(R.id.editText);
        textView = (TextView) findViewById(R.id.textView3);
    }
}
