package com.example.zunnorain.noque.AdapterClasses;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zunnorain.noque.ModelClasses.DoctorItem;
import com.example.zunnorain.noque.R;

import java.util.List;

public class DoctorListAdapter extends RecyclerView.Adapter<DoctorListAdapter.MyViewHolder> {

    private Context context;
    private List<DoctorItem> list;
    private MyAdapterListener myAdapterListener;
    private MyViewHolder myViewHolder;

    public DoctorListAdapter(Context context, List<DoctorItem> list, MyAdapterListener myAdapterListener) {
        this.context = context;
        this.list = list;
        this.myAdapterListener = myAdapterListener;
    }


    public interface MyAdapterListener {
        void itemOnClick(View v, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView doctorName, doctorSpecialize;
        private LinearLayout item;

        public MyViewHolder(View view) {
            super(view);
            doctorName = (TextView) view.findViewById(R.id.doctor_name);
            //doctorSpecialize = (TextView) view.findViewById(R.id.doctor_specialize);
            item=(LinearLayout)view.findViewById(R.id.item);

            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myAdapterListener.itemOnClick(view,getAdapterPosition());
                }
            });
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.doctor_list_item, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        this.myViewHolder = myViewHolder;
        DoctorItem a = list.get(i);
        myViewHolder.doctorName.setText(a.getName());
        //myViewHolder.doctorSpecialize.setText(a.getSpecialization());
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}
