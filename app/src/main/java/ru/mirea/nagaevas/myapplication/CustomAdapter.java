package ru.mirea.nagaevas.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<String> spending_id, spending_date, spending_category, spending_sum;

    CustomAdapter(Context context,
                  ArrayList spending_id,
                  ArrayList spending_date,
                  ArrayList spending_category,
                  ArrayList spending_sum) {
        this.context = context;
        this.spending_id = spending_id;
        this.spending_date = spending_date;
        this.spending_category = spending_category;
        this.spending_sum = spending_sum;
    }

    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //при создании окна
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) { //отображение текста в textview
        holder.spending_id_txt.setText(String.valueOf(spending_id.get(position)));
        holder.spending_date_txt.setText(String.valueOf(spending_date.get(position)));
        holder.spending_category_txt.setText(String.valueOf(spending_category.get(position)));
        holder.spending_sum_txt.setText(String.valueOf(spending_sum.get(position)));
    }

    @Override
    public int getItemCount() { //количество строк
        return spending_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView spending_id_txt, spending_date_txt, spending_category_txt, spending_sum_txt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            spending_id_txt = itemView.findViewById(R.id.spending_id_txt);
            spending_date_txt = itemView.findViewById(R.id.spending_date_txt);
            spending_category_txt = itemView.findViewById(R.id.spending_category_txt);
            spending_sum_txt = itemView.findViewById(R.id.spending_sum_txt);
        }
    }
}
