package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.util.Objects;

public class SettingActivity extends AppCompatActivity {
    private Spinner currencySpinner ;
    private Spinner weatherSpinner ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);



        currencySpinner = findViewById(R.id.currencySpinner);
        weatherSpinner = findViewById(R.id.weatherSpinner);

        //weatherSpinner.setSelection(0,false);
        //currencySpinner.setSelection(0,false);
    }

    public void onClickHomeBtn(View view){
        int position = currencySpinner.getSelectedItemPosition();
        int cityPosition = weatherSpinner.getSelectedItemPosition();
        Intent intent = getIntent();
        intent.putExtra("position",position);
        intent.putExtra("cityPosition",cityPosition);
        setResult(RESULT_OK,intent);
        finish();
    }
}
