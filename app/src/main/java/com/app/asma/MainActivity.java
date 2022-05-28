package com.app.asma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button button1,button2,button3,button4,button5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        button1 = (Button) findViewById(R.id.btnTask1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Task1Activity.class);
                startActivity(intent);
            }
        });

        button2 = (Button) findViewById(R.id.btnTask2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Task2Activity.class);
                startActivity(intent);

            }
        });

        button3 = (Button) findViewById(R.id.btnTask3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, Task3Activity.class);
                startActivity(intent);

            }
        });

        button4 = (Button) findViewById(R.id.btnTask4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, Task4Activity.class);
                startActivity(intent);
            }
        });

        button5 = (Button) findViewById(R.id.btnTask5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Task5Activity.class);
                startActivity(intent);

            }
        });

    }
}