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
        story_button = findViewById(R.id.history);
        story_button.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, StoryActivity.class);
            startActivity(intent);
        });
<<<<<<< Updated upstream

    }
=======
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
        if (enter_sum.getText().length() > 0 && !enter_sum.getText().toString().equals(".")) {
            myDb.addSpending(date, category_input.trim(),
                    Float.valueOf(enter_sum.getText().toString().trim()));
        }
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

>>>>>>> Stashed changes
}