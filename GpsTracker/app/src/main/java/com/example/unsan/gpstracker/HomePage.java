package com.example.unsan.gpstracker;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Unsan on 23/3/18.
 */


public class HomePage extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinner;
   ListView listView;
    List<Customer> customerList;
    CustomAdapter customAdapter;
    List<String> carList;
    List<Customer> selectedList;
    ArrayAdapter<String> arrayAdapter;
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        spinner=(Spinner)findViewById(R.id.sp1);
        listView=(ListView) findViewById(R.id.list_view);
        carList= new ArrayList<>();
         customerList=new ArrayList<>();
         selectedList=new ArrayList<>();
        for(int i=1;i<=30;i++)
        {
            carList.add("car "+i);

        }
        for(int i=0;i<50;i++)
        {
            Random rand = new Random();

            // nextInt is normally exclusive of the top value,
            // so add 1 to make it inclusive
            int randomNum = rand.nextInt((30 - 1) + 1) + 1;
            Log.d("checkr",randomNum+"");
            String name="customer "+(i+1);
            String address="Blk "+(50+(i+1))+" Clementi Road";
            String phone="85717485";
            Log.d("cl",name);

            Customer c=new Customer(carList.get(randomNum-1),name,address,phone);
            customerList.add(c);


        }


for(int i=0;i<customerList.size();i++) {
    selectedList.add(customerList.get(i));

}




  arrayAdapter=new ArrayAdapter<String>(HomePage.this,R.layout.support_simple_spinner_dropdown_item,carList);

        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(this);
        customAdapter=new CustomAdapter(HomePage.this,R.layout.simple_display,selectedList);
        listView.setAdapter(customAdapter);
        customAdapter.notifyDataSetChanged();


    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item=adapterView.getItemAtPosition(i).toString();
        Log.d("selecteditem",item);
        selectedList.clear();

        Log.d("checks",customerList.size()+"");



        for(int j=0;j<customerList.size();j++)
        {
            Log.d("checkcarnumber",customerList.get(j).carNumber);
            if(item.equals(customerList.get(j).carNumber))
            {

                selectedList.add(customerList.get(j));
                Log.d("cuctomerdiplayed",customerList.get(j).CustomerName);


                customAdapter.notifyDataSetChanged();
            }
        }
        customAdapter.notifyDataSetChanged();

        Toast.makeText(HomePage.this, "Selected: " + item, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
