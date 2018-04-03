package com.example.unsan.gpstracker;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by KV on 1/4/18.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    Context context;
    List<Delivery> deliveryList;
    HistoryAdapter(Context context,List<Delivery> deliveryList)
    {
        this.context=context;
        this.deliveryList=deliveryList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.history_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Delivery delivery=deliveryList.get(position);
        holder.startingFrom.setText(holder.startingFrom.getText()+" "+delivery.startingAddress);
        holder.startTime.setText(holder.startTime.getText()+" "
                +delivery.startTime);
        holder.deliverdTo.setText(holder.deliverdTo+" "+delivery.destinationAddress);
        holder.reachedTime.setText(holder.reachedTime+" "+delivery.deliveryTime);
        holder.customernm.setText(holder.customernm+" "+ delivery.customer);


    }

    @Override
    public int getItemCount() {
        return deliveryList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView startTime,reachedTime,customernm,deliverdTo,startingFrom;
        public ViewHolder(View itemView) {
            super(itemView);
            startingFrom=(itemView).findViewById(R.id.startt);
            startTime=(itemView).findViewById(R.id.strtime);
            reachedTime=(itemView).findViewById(R.id.reachedtime);
            customernm=(itemView).findViewById(R.id.cust_name);
            deliverdTo=(itemView).findViewById(R.id.delivered);

        }
    }


}
