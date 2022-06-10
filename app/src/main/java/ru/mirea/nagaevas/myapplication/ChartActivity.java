package ru.mirea.nagaevas.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


public class ChartActivity extends AppCompatActivity {

    NumberPicker month_picker, year_picker;
    int month, year;
    TextView date_text, chart_sum_text;
    String date_string;
    PieChart pieChart;
    MyDatabaseHelper myDb;
    Float chart_spent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        myDb = new MyDatabaseHelper(ChartActivity.this);
        month_picker = findViewById(R.id.month_picker);
        year_picker = findViewById(R.id.year_picker);
        month_picker.setMaxValue(12);
        month_picker.setMinValue(1);
        year_picker.setMinValue(2022);
        year_picker.setMaxValue(2022);
        date_text = findViewById(R.id.date_text);
        pieChart = findViewById(R.id.pie_chart);
        chart_sum_text = findViewById(R.id.chart_sum_text);
    }

    public void button_build_press(View view) {
            month = month_picker.getValue();
            year = year_picker.getValue();
            if (month < 10) {
                date_string = year + "-0" + month + "-00";
                date_text.setText(date_string.substring(0, date_string.length() - 3));
            }
            else
            {
                date_string = year + "-" + month + "-00";
                date_text.setText(date_string.substring(0, date_string.length() - 3));
            }
            setupPieChart();
            buildChart();

    }

    private void setupPieChart() {
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(12);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterTextSize(24);
        pieChart.getDescription().setEnabled(false);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(true);
    }

    public void buildChart() {
        HashMap<String, Float> dataDict = new HashMap<>();
        List<PieEntry> dataValues = new ArrayList<>();
        Cursor cursor = myDb.readAllData();
        chart_spent = 0f;
        if (cursor.getCount() == 0) { //если нет записей в бд
            Toast.makeText(this, "Нет данных", Toast.LENGTH_SHORT).show();
        }
        else {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                if (date_text.getText().equals(cursor.getString(1).substring(0, cursor.getString(1).length() - 3))) {
                    String category = cursor.getString(2);
                    float sum = cursor.getFloat(3);
                    if (!dataDict.containsKey(category)) {
                        dataDict.put(category, sum);
                        System.out.println(cursor.getString(2));
                    }
                    else {
                        dataDict.replace(category, dataDict.get(category) + sum);
                    }
                    chart_spent += cursor.getFloat(3);
                }
            }
        }

        List<?> keys = new ArrayList<>(dataDict.keySet());
        System.out.println(keys.size());
        for (int i = 0; i < keys.size(); i++)
            dataValues.add(new PieEntry(dataDict.get(keys.get(i)), (String) keys.get(i)));

        chart_sum_text.setText("Потрачено за месяц: " + chart_spent);
        ArrayList<Integer> colors = new ArrayList<>();
        for (int color: ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }
        for (int color: ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }
        PieDataSet dataSet = new PieDataSet(dataValues, "");
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);
        pieChart.invalidate();

        pieChart.animateY(1400, Easing.EaseInOutQuad);
    }


}
