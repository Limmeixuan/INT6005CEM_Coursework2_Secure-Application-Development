package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

public class Medicine extends AppCompatActivity {

    ListView medicineList;
    EditText inputText1;
    Button addBtn, updateBtn;

    ArrayList<String> medicines = new ArrayList<String>();
    ArrayAdapter myAdapter1;

    Integer indexVal;
    String item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);

        medicineList = (ListView) findViewById(R.id.medicineList);
        addBtn = (Button) findViewById(R.id.addBtn);
        updateBtn = (Button) findViewById(R.id.updateBtn);
        inputText1 = (EditText) findViewById(R.id.nameEditText);

        //setup listview
        medicines.add("Amoxicillin");
        medicines.add("Atorvastatin");

        myAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, medicines);
        medicineList.setAdapter(myAdapter1);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stringval = inputText1.getText().toString();
                medicines.add(stringval);
                myAdapter1.notifyDataSetChanged();

                inputText1.setText("");
            }
        });

        medicineList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                item = adapterView.getItemAtPosition(i).toString() + " has been selected";
                indexVal = i;
                Toast.makeText(Medicine.this, item, Toast.LENGTH_SHORT).show();
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stringval = inputText1.getText().toString();
                medicines.set(indexVal, stringval);
                myAdapter1.notifyDataSetChanged();
            }
        });

        medicineList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                item = adapterView.getItemAtPosition(i).toString() + " has been deleted";
                Toast.makeText(Medicine.this, item, Toast.LENGTH_SHORT).show();

                medicines.remove(i);
                myAdapter1.notifyDataSetChanged();

                return true;
            }
        });
    }
}