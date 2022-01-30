package com.ifgoiano.mvcpattern.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ifgoiano.mvcpattern.R;
import com.ifgoiano.mvcpattern.model.Country;
import com.ifgoiano.mvcpattern.controller.CountriesLocalRepositoryController;

import java.util.ArrayList;
import java.util.List;

public class SQLActivity extends AppCompatActivity {

    CountriesLocalRepositoryController CountriesLocalRepositoryController;
    TextView datalist;
    TextView datalist_count;
    private List<String> listValues = new ArrayList<>();
    private List<String> listValuesIDs = new ArrayList<>();
    private ListView list;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql);
        setTitle("MVC Activity - LOCAL");

        list = findViewById(R.id.list_sql);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(SQLActivity.this, "ID: "+listValuesIDs.get(position)+ " - "+listValues.get(position), Toast.LENGTH_SHORT).show();
            }
        });


        CountriesLocalRepositoryController =new CountriesLocalRepositoryController(SQLActivity.this);
        Button delete=findViewById(R.id.delete_data);
        Button insert=findViewById(R.id.insert_data);
        Button update=findViewById(R.id.update_data);
        Button read=findViewById(R.id.refresh_data);
       // datalist=findViewById(R.id.all_data_list);
        datalist_count=findViewById(R.id.data_list_count);

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshData();

            }
        });

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowInputDialog();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateIdDialog();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog();
            }
        });

    }

    private void refreshData() {
        datalist_count.setText("ALL DATA COUNT : " + CountriesLocalRepositoryController.getToltalRegistersCount());

        List<Country> studentModelList= CountriesLocalRepositoryController.getAllCountries();
//        datalist.setText("");
        listValues = new ArrayList<>();
        for(Country studentModel:studentModelList){
            //"ID : "+studentModel.getId()+" | Name : "+studentModel.getCountryName()+" \n\n"
            //datalist.append("DB ID: "+studentModel.getId()+" - Country: "+studentModel.getCountryName()+" \n\n");
            //datalist.setPadding(30, 30, 30, 30);
            listValues.add(studentModel.getCountryName());
            listValuesIDs.add(studentModel.getId());
        }
        adapter = new ArrayAdapter<>(this, R.layout.row_layout, R.id.listText, listValues);
        list.setAdapter(adapter);
    }

    private void showDeleteDialog() {
        AlertDialog.Builder al=new AlertDialog.Builder(SQLActivity.this);
        View view=getLayoutInflater().inflate(R.layout.delete_dialog,null);
        al.setView(view);
        final EditText id_input=view.findViewById(R.id.id_input);
        Button delete_btn=view.findViewById(R.id.delete_btn);
        final AlertDialog alertDialog=al.show();

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CountriesLocalRepositoryController.deleteCountry(id_input.getText().toString());
                alertDialog.dismiss();
                refreshData();

            }
        });


    }

    private void showUpdateIdDialog() {
        AlertDialog.Builder al=new AlertDialog.Builder(SQLActivity.this);
        View view=getLayoutInflater().inflate(R.layout.update_id_dialog,null);
        al.setView(view);
        final EditText id_input=view.findViewById(R.id.id_input);
        Button fetch_btn=view.findViewById(R.id.update_id_btn);
        final AlertDialog alertDialog=al.show();
        fetch_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDataDialog(id_input.getText().toString());
                alertDialog.dismiss();
                refreshData();
            }
        });

    }

    private void showDataDialog(final String id) {
        Country studentModel= CountriesLocalRepositoryController.getCountry(Integer.parseInt(id));
        AlertDialog.Builder al=new AlertDialog.Builder(SQLActivity.this);
        View view=getLayoutInflater().inflate(R.layout.update_dialog,null);
        final EditText name=view.findViewById(R.id.name);
        Button update_btn=view.findViewById(R.id.update_btn);
        al.setView(view);

        name.setText(studentModel.getCountryName());

        final AlertDialog alertDialog=al.show();
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Country studentModel=new Country();
                studentModel.setCountryName(name.getText().toString());
                studentModel.setId(id);
                CountriesLocalRepositoryController.updateCountry(studentModel);
                alertDialog.dismiss();
                refreshData();
            }
        });
    }

    private void ShowInputDialog() {
        AlertDialog.Builder al=new AlertDialog.Builder(SQLActivity.this);
        View view=getLayoutInflater().inflate(R.layout.insert_dialog,null);
        final EditText name=view.findViewById(R.id.name);
        Button insertBtn=view.findViewById(R.id.insert_btn);
        al.setView(view);

        final AlertDialog alertDialog=al.show();

        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Country studentModel=new Country();
                studentModel.setCountryName(name.getText().toString());
                CountriesLocalRepositoryController.addCountry(studentModel);
                alertDialog.dismiss();
                refreshData();
            }
        });
    }
}