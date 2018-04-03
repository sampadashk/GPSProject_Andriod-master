package com.example.unsan.gpstracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Unsan on 28/3/18.
 */

public class MainPage extends AppCompatActivity {
    Button startButton,onGoingButton,historyButton;


    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        startButton=(Button)findViewById(R.id.start_trip);
        onGoingButton=(Button)findViewById(R.id.ongoing);
        historyButton=(Button)findViewById(R.id.history);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainPage.this,HomePage.class);
                startActivity(intent);
            }
        });
        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainPage.this,HistoryActivity.class);
                startActivity(intent);
            }
        });
    }
}
