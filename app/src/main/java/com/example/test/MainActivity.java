package com.example.test;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    private final int DELAY_TIME = 1000;
    private Handler handler;
    private EditText fromAmountText;
    private TextView currencyView;
    private TextView weatherView;
    private TextView[] textViewArray;
    private String[] toAmountStrArr;
    MyUtilityModel myUtilityModel = new MyUtilityModel();


    public void showWeather(){
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                final String[] tempDesc = Helper.getTemp(myUtilityModel.getFavCity());
                //Log.i("temperature", tempDesc[1]);
                double tempDescDouble =  Double.parseDouble(tempDesc[0]);
                int tempDescInt = (int) tempDescDouble;
                String tempDescStr = String.valueOf(tempDescInt);
                weatherView.setText(String.format("%s %s° %ss", tempDesc[2], tempDescStr, tempDesc[1]));
            }

        };

        new Thread(runnable).start();


    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        handler = new Handler();
        int textViewCount = 4;
        textViewArray = new TextView[textViewCount];
        int stringArrCount = 4;
        toAmountStrArr = new String[stringArrCount];

        for(int i = 0; i < textViewCount; i++) {
            textViewArray[i] = new TextView(this);
        }

        textViewArray[0] = findViewById(R.id.toAmountView1);
        textViewArray[1] = findViewById(R.id.toAmountView2);
        textViewArray[2] = findViewById(R.id.toAmountView3);
        textViewArray[3] = findViewById(R.id.toAmountView4);

        //myUtilityModel = new MyUtilityModel();

        fromAmountText   = findViewById(R.id.fromAmountText);
        currencyView     = findViewById(R.id.currencyView);
        weatherView      = findViewById(R.id.weatherView);

        showWeather();



        handler.post(new Runnable() {
            @Override
            public void run() {
                String currentTime = Helper.getCurrentTime();
                TextView timeView = findViewById(R.id.timeView);
                timeView.setText(currentTime);
                handler.postDelayed(this, DELAY_TIME);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            if (resultCode == RESULT_OK){
                if (data != null){
                    Bundle bundle = data.getExtras();
                    if (bundle != null){
                        int position = bundle.getInt("position",1);
                        int city = bundle.getInt("cityPosition",1);
                        String[] fromCurrencies = getResources().getStringArray(R.array.FromCurrencies);
                        String[] cities = getResources().getStringArray(R.array.Cities);

                        //updating fromcurreny and textView
                        myUtilityModel.setFromCurrency(fromCurrencies[position]); //= fromCurrencies[position];
                        //Log.i("position",myUtilityModel.getFromCurrency());
                        currencyView.setText(myUtilityModel.getFromCurrency());


                        //updating favCity and textview
                        myUtilityModel.setFavCity(cities[city]); //= cities[city];
                        Log.i("cityPosition",myUtilityModel.getFavCity());
                        showWeather();
                    }
                }
            }
        }
    }



    public void convertClicked(View v){
        final String fromAmountStr = fromAmountText.getText().toString().trim();
        if(fromAmountStr.length() == 0){
            Toast.makeText(MainActivity.this, "Please enter an amount",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final double[] rates = Helper.getRates(myUtilityModel.getFromCurrency(), myUtilityModel.getToCurrencies());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        double fromAmount = Double.parseDouble(fromAmountStr);
                        for (int i = 0; i < textViewArray.length; i++ ){
                            textViewArray[i].setText("");
                            toAmountStrArr[i] = String.format(Locale.getDefault(),
                                    "%.2f", fromAmount * rates[i]);
                            textViewArray[i].setText(String.format("%s   %s", myUtilityModel.getToCurrencies()[i], toAmountStrArr[i]));
                        }
                    }
                });
            }
        };
        new Thread(runnable).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingActivity.class);
            startActivityForResult(intent, 1);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
