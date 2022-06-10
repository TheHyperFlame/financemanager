package ru.mirea.nagaevas.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    EditText category_edit, sum_edit;
    Button save_button, delete_button;
    String id, category, sum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        category_edit = findViewById(R.id.category_edit);
        sum_edit = findViewById(R.id.sum_edit);
        save_button = findViewById(R.id.save_button);
        delete_button = findViewById(R.id.delete_button);
        intentDataManage(); //получаем даннные...

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDb = new MyDatabaseHelper(UpdateActivity.this);
                category = category_edit.getText().toString().trim();
                sum = sum_edit.getText().toString().trim();
                if (sum_edit.getText().length() > 0 && !sum_edit.getText().toString().equals("."))
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
            category_edit.setText(category);
            sum_edit.setText(sum);

        } else { //если не получено данных с интента
            Toast.makeText(this, "Нет данных для обновления", Toast.LENGTH_SHORT).show();
        }
    }



}