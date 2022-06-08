package ru.mirea.nagaevas.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddActivity extends AppCompatActivity {

    EditText sum_input, category_input;
    Button add_button;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        date = dateFormat.format(calendar.getTime());
        sum_input = findViewById(R.id.sum_input);
        category_input = findViewById(R.id.category_input);
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelper myDb = new MyDatabaseHelper(AddActivity.this);
                myDb.addSpending(date, category_input.getText().toString().trim(), Float.valueOf(sum_input.getText().toString().trim()));

            }
        });
    }
}