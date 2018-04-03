package com.example.unsan.gpstracker;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Unsan on 23/3/18.
 */

public class CustomAdapter extends ArrayAdapter<Customer> {
    Context context;
    List<Customer> objects;

    @Override
    public int getCount() {
        if(objects!=null)
        return objects.size();
        else
            return -1;
    }

    public CustomAdapter(@NonNull Context context, int resource, @NonNull List<Customer> objects) {
        super(context, resource, 0, objects);
        this.context=context;
        this.objects=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listV=convertView;
        if(listV==null)
       listV= LayoutInflater.from(context).inflate(R.layout.simple_display,parent,false);

        final Customer c=objects.get(position);
        TextView tv=(TextView)listV.findViewById(R.id.custnamedisp);
        if(c!=null)
        tv.setText(c.getCustomerName());
        else
            tv.setText("No customer Found");
        listV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,CustomerDetail.class);
                intent.putExtra("customer",c);
                context.startActivity(intent);



            }
        });
        return listV;

    }
}
