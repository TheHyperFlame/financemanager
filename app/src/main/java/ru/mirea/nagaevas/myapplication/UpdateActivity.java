package ru.mirea.nagaevas.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    EditText category_edit, sum_edit;
    Button save_button;
    String id, category, sum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        category_edit = findViewById(R.id.category_edit);
        sum_edit = findViewById(R.id.sum_edit);
        save_button = findViewById(R.id.save_button);
        intentDataManage();
        save_button.setOnClickListener(new View.OnClickListener() { //сохранение данных по нажатию
            @Override
            public void onClick(View v) {
                MyDatabaseHelper myDb = new MyDatabaseHelper(UpdateActivity.this);
                category = category_edit.getText().toString().trim();
                sum = sum_edit.getText().toString().trim();
                myDb.updateData(id, category, sum);
            }
        });



    }

    void intentDataManage() { //получение и использование данных с интента
        if (getIntent().hasExtra("id") && getIntent().hasExtra("category") &&
                getIntent().hasExtra("sum")) {
            id = getIntent().getStringExtra("id");
            category = getIntent().getStringExtra("category");
            sum = getIntent().getStringExtra("sum");
            category_edit.setText(category);
            sum_edit.setText(sum);

        } else { //если не получено данных с интента
            Toast.makeText(this, "No data to update", Toast.LENGTH_SHORT).show();
        }
    }
}