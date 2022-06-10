package ru.mirea.nagaevas.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {
    String category_input;
    TextView enter_sum, spent_today;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    MyDatabaseHelper myDb;
    private String date;
    Float total_sum = 0f;
    Button story_button, add_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDb = new MyDatabaseHelper(MainActivity.this);
        setContentView(R.layout.activity_main);
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date = dateFormat.format(calendar.getTime());
        enter_sum = findViewById(R.id.enter_sum);
        add_button = findViewById(R.id.add_button);
        story_button = findViewById(R.id.history);
        spent_today = findViewById(R.id.spent_today);
        category_input = "Другое";
        countTodaySpending();

        story_button.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, StoryActivity.class);
            startActivityForResult(intent, 1);
            recreate();
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }


    public void button_1_press(View view) {
        enter_sum.setText(enter_sum.getText() + "1");
    }

    public void button_2_press(View view) {
        enter_sum.setText(enter_sum.getText() + "2");
    }

    public void button_3_press(View view) {
        enter_sum.setText(enter_sum.getText() + "3");
    }

    public void button_4_press(View view) {
        enter_sum.setText(enter_sum.getText() + "4");
    }

    public void button_5_press(View view) {
        enter_sum.setText(enter_sum.getText() + "5");
    }

    public void button_6_press(View view) {
        enter_sum.setText(enter_sum.getText() + "6");
    }

    public void button_7_press(View view) {
        enter_sum.setText(enter_sum.getText() + "7");
    }

    public void button_8_press(View view) {
        enter_sum.setText(enter_sum.getText() + "8");
    }

    public void button_9_press(View view) {
        enter_sum.setText(enter_sum.getText() + "9");
    }

    public void button_0_press(View view) {
        enter_sum.setText(enter_sum.getText() + "0");
    }

    public void button_dot_press(View view) {
        enter_sum.setText(enter_sum.getText() + ".");
    }

    public void button_back_press(View view) {
        enter_sum.setText(enter_sum.getText().subSequence(0, enter_sum.getText().length()-1));
    }

    public void add_button_press(View view) {
        myDb.addSpending(date, category_input.trim(), Float.valueOf(enter_sum.getText().toString().trim()));
        recreate();
    }

    public void transport_button_press(View view) {
        category_input = "Транспорт";
    }
    public void clothes_button_press(View view) {
        category_input = "Одежда";
    }
    public void fun_button_press(View view) {
        category_input = "Развлечения";
    }
    public void house_button_press(View view) {
        category_input = "Дом";
    }
    public void food_button_press(View view) {
        category_input = "Еда";
    }
    public void other_button_press(View view) {
        category_input = "Другое";
    }

    void countTodaySpending () {
        Cursor cursor = myDb.readAllData();
        if (cursor.getCount() == 0) { //если нет записей в бд
            total_sum = 0f;
        }
        else {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                if (date.trim().equals(cursor.getString(1).trim()))
                {
                    total_sum += Float.valueOf(cursor.getString(3));
                }
            }
        }
        DecimalFormat df = new DecimalFormat("#.##");
        total_sum = Float.valueOf(df.format(total_sum));
        spent_today.setText(spent_today.getText() + " " + total_sum.toString().trim() + "₽");


    }

}