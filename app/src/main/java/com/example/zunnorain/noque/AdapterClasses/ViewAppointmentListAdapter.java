package com.example.zunnorain.noque.AdapterClasses;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zunnorain.noque.ModelClasses.ScheduleItem;
import com.example.zunnorain.noque.R;

import java.util.List;

public class ViewAppointmentListAdapter extends RecyclerView.Adapter<ViewAppointmentListAdapter.MyViewHolder> {

    private Context context;
    private List<ScheduleItem> list;
    private MyAdapterListener myAdapterListener;
    private MyViewHolder myViewHolder;

    public ViewAppointmentListAdapter(Context context, List<ScheduleItem> list, MyAdapterListener myAdapterListener) {
        this.context = context;
        this.list = list;
        this.myAdapterListener = myAdapterListener;
    }


    public interface MyAdapterListener {
        void slotOneOnClick(View v, int position);

        void slotTwoOnClick(View v, int position);

        void slotThreeOnClick(View v, int position);

        void slotFourOnClick(View v, int position);

        void slotFiveOnClick(View v, int position);

        void slotSixOnClick(View v, int position);

        void slotSevenOnClick(View v, int position);

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView date, day, slotOne, slotTwo, slotThree, slotFour, slotFive, slotSix, slotSeven;

        public MyViewHolder(View view) {
            super(view);
            date = (TextView) view.findViewById(R.id.date);
            day = (TextView) view.findViewById(R.id.day);
            slotOne = (TextView) view.findViewById(R.id.slot_one);
            slotTwo = (TextView) view.findViewById(R.id.slot_two);
            slotThree = (TextView) view.findViewById(R.id.slot_three);
            slotFour = (TextView) view.findViewById(R.id.slot_four);
            slotFive = (TextView) view.findViewById(R.id.slot_five);
            slotSix = (TextView) view.findViewById(R.id.slot_six);
            slotSeven = (TextView) view.findViewById(R.id.slot_seven);

        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.schedule_item, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        this.myViewHolder = myViewHolder;
        ScheduleItem a = list.get(i);
        String z = a.getDate();
        myViewHolder.date.setText(z);
        myViewHolder.day.setText(a.getDay());
        myViewHolder.slotOne.setBackgroundResource(a.getSlotOneBackground());
        myViewHolder.slotTwo.setBackgroundResource(a.getSlotTwoBackground());
        myViewHolder.slotThree.setBackgroundResource(a.getSlotThreeBackground());
        myViewHolder.slotFour.setBackgroundResource(a.getSlotFourBackground());
        myViewHolder.slotFive.setBackgroundResource(a.getSlotFiveBackground());
        myViewHolder.slotSix.setBackgroundResource(a.getSlotSixBackground());
        myViewHolder.slotSeven.setBackgroundResource(a.getSlotSevenBackground());

    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}
