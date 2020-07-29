package com.gs.seekhelp20;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<CompDet> complients;

    public MyAdapter(Context c,ArrayList<CompDet> d){
        context = c;
        complients = d;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview3, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        holder.victimName.setText("VICTIM NAME"+"  :  "+complients.get(position).victimName);
//        holder.area.setText("AREA"+"  :  "+complients.get(position).area);
//        holder.landmark.setText("LANDMARK"+"  :  "+complients.get(position).landmark);
//        holder.pincode.setText("PINCODE"+"  :  "+complients.get(position).pincode);
//        holder.city.setText("CITY"+"  :  "+complients.get(position).city);
//        holder.description.setText("DESC :  "+complients.get(position).description);
//        holder.date.setText("DATE" + " : " + complients.get(position).date);
//        holder.time.setText("TIME" + " : " + complients.get(position).time);
//        Picasso.get().load(complients.get(position).getImageUrl()).into(holder.imageView);

        holder.victimName.setText(complients.get(position).victimName);
        holder.area.setText(complients.get(position).area);
        holder.landmark.setText(complients.get(position).landmark);
        holder.pincode.setText(complients.get(position).pincode);
        holder.city.setText(complients.get(position).city);
        holder.description.setText(complients.get(position).description);
        holder.date.setText(complients.get(position).date);
        holder.time.setText(complients.get(position).time);
        Picasso.get().load(complients.get(position).getImageUrl()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return complients.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView victimName,area,landmark,pincode,city,description,date,time;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            victimName = itemView.findViewById(R.id.textView25);
            area = itemView.findViewById(R.id.textView27);
            landmark = itemView.findViewById(R.id.textView29);
            pincode = itemView.findViewById(R.id.textView31);
            city = itemView.findViewById(R.id.textView33);
            description = itemView.findViewById(R.id.textView38);
            imageView = itemView.findViewById(R.id.imageView3);
            date = itemView.findViewById(R.id.textView35);
            time = itemView.findViewById(R.id.textView37);
        }
    }
}
