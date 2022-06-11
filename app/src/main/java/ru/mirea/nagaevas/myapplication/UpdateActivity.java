package ru.mirea.nagaevas.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    EditText sum_edit;
    Button save_button, delete_button;
    String id, category, sum;
    String[] categories = {"Еда", "Транспорт", "Дом", "Развлечения", "Одежда", "Другое"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        sum_edit = findViewById(R.id.sum_edit);
        save_button = findViewById(R.id.save_button);
        delete_button = findViewById(R.id.delete_button);
        intentDataManage(); //получаем даннные...

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDb = new MyDatabaseHelper(UpdateActivity.this);
                sum = sum_edit.getText().toString().trim();
                if (sum_edit.getText().length() > 0 && sum_edit.getText().toString().matches("\\d+\\.?\\d*"))
                    myDb.updateData(id, category, sum); //... затем сохраняем их
            }
        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                myDB.deleteData(id);
                finish();
            }
        });

        Spinner spinner = findViewById(R.id.categories);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = (String)parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        spinner.setOnItemSelectedListener(itemSelectedListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    void intentDataManage() { //получение и использование данных с интента
        if (getIntent().hasExtra("id") && getIntent().hasExtra("category") &&
                getIntent().hasExtra("sum")) {
            id = getIntent().getStringExtra("id");
            category = getIntent().getStringExtra("category");
            sum = getIntent().getStringExtra("sum");
            sum_edit.setText(sum);

        } else { //если не получено данных с интента
            Toast.makeText(this, "Нет данных для обновления", Toast.LENGTH_SHORT).show();
        }
    }



}