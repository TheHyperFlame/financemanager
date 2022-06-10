package ru.mirea.nagaevas.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {
    String category_input;
    TextView enter_sum, spent_today;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    MyDatabaseHelper myDb;
    public static boolean isLightTheme = true;
    private String date;
    Float total_sum = 0f;
    Button story_button, add_button, diagram;
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
        diagram = findViewById(R.id.diagram);
        category_input = "Другое";
        countTodaySpending();

        story_button.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, StoryActivity.class);
            startActivityForResult(intent, 1);
            recreate();
        });
        diagram.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ChartActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.theme_change).setOnClickListener(view -> {
            if (isLightTheme) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                isLightTheme = false;
            }
            else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                isLightTheme = true;
            }
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

    public void button_press(View view) {
        enter_sum.setText(String.format("%s%s", enter_sum.getText(),
                ((TextView) view).getText().toString()));
    }

    public void button_back_press(View view) {
        enter_sum.setText(enter_sum.getText().subSequence(0, enter_sum.getText().length()-1));
    }

    public void add_button_press(View view) {
        if (enter_sum.getText().length() > 0 && !enter_sum.getText().toString().equals("."))
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