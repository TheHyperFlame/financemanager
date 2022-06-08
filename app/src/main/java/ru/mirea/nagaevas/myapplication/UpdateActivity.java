package ru.mirea.nagaevas.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
            public void onClick(View v) {
                MyDatabaseHelper myDb = new MyDatabaseHelper(UpdateActivity.this);
                category = category_edit.getText().toString().trim();
                sum = sum_edit.getText().toString().trim();
                myDb.updateData(id, category, sum); //... затем сохраняем их
            }
        });
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDeleteDialog();
                finish();

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

    void confirmDeleteDialog() {
        AlertDialog.Builder adBuilder = new AlertDialog.Builder(this);
        adBuilder.setTitle("Delete");
        adBuilder.setMessage("Are you sure you want to delete this spending?");
        adBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                myDB.deleteData(id);
                finish();
            }
        });
        adBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        adBuilder.create().show();
    }
}