package ru.mirea.nagaevas.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class StoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MyDatabaseHelper myDb;
    ArrayList<String> spending_id, spending_date, spending_category, spending_sum;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
        recyclerView = findViewById(R.id.recyclerView);
        myDb = new MyDatabaseHelper(StoryActivity.this);
        spending_id = new ArrayList<>();
        spending_date = new ArrayList<>();
        spending_category = new ArrayList<>();
        spending_sum = new ArrayList<>();

        displayDataArrays();
        customAdapter = new CustomAdapter(StoryActivity.this, spending_id, spending_date,
                spending_category, spending_sum);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(StoryActivity.this));

    }

    void displayDataArrays() {
        Cursor cursor = myDb.readAllData();
        if (cursor.getCount() == 0) { //если нет записей в бд
            Toast.makeText(this, "No spendings", Toast.LENGTH_SHORT).show();
        }
        else {
            while (cursor.moveToNext()) {
                spending_id.add(cursor.getString(0));
                spending_date.add(cursor.getString(1));
                spending_category.add(cursor.getString(2));
                spending_sum.add(cursor.getString(3));
            }
        }
    }
}