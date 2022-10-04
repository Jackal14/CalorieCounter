package com.example.caloriecounter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.caloriecounter.Data.CustomDataAdapter;
import com.example.caloriecounter.Data.DataBaseHandler;
import com.example.caloriecounter.model.Food;

import java.util.ArrayList;

//Class to get all the stuff in our database and display it

public class DetailFoodActivity extends AppCompatActivity {

    private DataBaseHandler dba;
    private ArrayList<Food> foodArrayList = new ArrayList<>();
    private CustomDataAdapter foodAdapter;
    private ListView listView;
    private Food myFood;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_food);

        listView = findViewById(R.id.listView);
        refreshData();



//        listView.setAdapter(foodAdapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
//                removeFood(v, position, id);
//            }
//        });

    }

//    private void removeFood(View v, int position, long id)
//    {
//        dba = new DataBaseHandler(getApplicationContext());
//        AlertDialog.Builder adb=new AlertDialog.Builder(DetailFoodActivity.this);
//        adb.setTitle("Delete?");
//        adb.setMessage("Are you sure you want to delete " + position);
//        final int positionToRemove = position;
//        adb.setNegativeButton("Cancel", null);
//        adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                dba.deleteFood(String.valueOf(positionToRemove));
//                foodAdapter.notifyDataSetChanged();
//            }});
//        adb.show();
//    }

    private void refreshData() {
        foodArrayList.clear();
        dba = new DataBaseHandler(getApplicationContext());
        ArrayList<Food> foodsFromDB = dba.getAllFood();

        for (int i = 0; i < foodsFromDB.size(); i++) {
            String name = foodsFromDB.get(i).getFoodName();
            String dateText = foodsFromDB.get(i).getRecordDate();
            int cals = foodsFromDB.get(i).getCalories();
            int foodId = foodsFromDB.get(i).getFoodId();
            Bitmap photo = foodsFromDB.get(i).getPhoto();

            myFood = new Food();
            myFood.setFoodName(name);
            myFood.setCalories(cals);
            myFood.setRecordDate(dateText);
            myFood.setFoodId(foodId);
            myFood.setPhoto(photo);

            foodArrayList.add(myFood);
            //dba.deleteFood(name);
        }
        dba.close();

        //setup Adapter
        foodAdapter = new CustomDataAdapter(DetailFoodActivity.this, R.layout.list_row, foodArrayList);
        listView.setAdapter(foodAdapter);
        foodAdapter.setNotifyOnChange(true);
    }


}
