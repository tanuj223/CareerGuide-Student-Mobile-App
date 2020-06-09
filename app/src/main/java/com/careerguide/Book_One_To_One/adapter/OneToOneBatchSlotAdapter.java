package com.careerguide.Book_One_To_One.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.careerguide.Book_One_To_One.model.OneToOneBatchSlotModel;
import com.careerguide.R;

import java.util.ArrayList;

public class OneToOneBatchSlotAdapter extends RecyclerView.Adapter<OneToOneBatchSlotAdapter.MyViewHolder> {


    private OneToOneTimeSlotAdapter oneToOneTimeSlotAdapter;
    private ArrayList<OneToOneBatchSlotModel> oneToOneBatchSlotModelArrayList ;
    private LinearLayoutManager linearLayoutManager;
    private Context mContext;
    private View currentlyClicked;
    private OneToOneBatchSlotAdapter ownAdapter;

    public OneToOneBatchSlotAdapter(Context mContext, ArrayList<OneToOneBatchSlotModel> oneToOneBatchSlotModelList) {

        this.oneToOneBatchSlotModelArrayList=oneToOneBatchSlotModelList;
        this.mContext=mContext;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerViewTimeSlot;
        TextView textViewBatchSlot;

        MyViewHolder(View view) {
            super(view);
            recyclerViewTimeSlot=view.findViewById(R.id.recyclerViewTimeSlot);
            textViewBatchSlot=view.findViewById(R.id.textViewBatchSlot);



        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.one_to_one_batch_slot, parent, false);



        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        OneToOneBatchSlotModel batchObj=oneToOneBatchSlotModelArrayList.get(position);
        Log.i("position",position+"");
        holder.textViewBatchSlot.setText(batchObj.getBatchTiming());
        oneToOneTimeSlotAdapter = new OneToOneTimeSlotAdapter(mContext,this,position, batchObj.getBookingSlotsList());
        linearLayoutManager = new GridLayoutManager(mContext, 3);
        holder.recyclerViewTimeSlot.setLayoutManager(linearLayoutManager);
        holder.recyclerViewTimeSlot.setAdapter(oneToOneTimeSlotAdapter);

    }

    @Override
    public int getItemCount() {
        return oneToOneBatchSlotModelArrayList.size();
    }

    void updateClickedTimeSlot(View clickedView){
        if (currentlyClicked!=null)
            currentlyClicked.setBackground(mContext.getResources().getDrawable(R.drawable.round_corner_white));

            //currentlyClicked.setBackground(mContext.getResources().getDrawable(R.drawable.round_corner_grey));//use for booked slots
        currentlyClicked=clickedView;
        currentlyClicked.setBackground(mContext.getResources().getDrawable(R.drawable.round_corner_blue));


    }


}
