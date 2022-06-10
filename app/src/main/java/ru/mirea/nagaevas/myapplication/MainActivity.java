package ru.mirea.nagaevas.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {
    EditText sum_input, category_input;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;
    Context context;
    Button story_button, add_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        date = dateFormat.format(calendar.getTime());
        //sum_input = findViewById(R.id.sum_input);
        //category_input = findViewById(R.id.category_input);
        add_button = findViewById(R.id.add_button);
        /*add_button.setOnClickListener(view -> {
            MyDatabaseHelper myDb = new MyDatabaseHelper(MainActivity.this);
            myDb.addSpending(date, category_input.getText().toString().trim(), Float.valueOf(sum_input.getText().toString().trim()));

        });*/
        //story_button = findViewById(R.id.story_button);
        /*story_button.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, StoryActivity.class);
            startActivity(intent);
        });*/

    }
}