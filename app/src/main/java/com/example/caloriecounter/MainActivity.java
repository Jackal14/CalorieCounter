package com.example.caloriecounter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.caloriecounter.Data.DataBaseHandler;
import com.example.caloriecounter.model.Food;

public class MainActivity extends AppCompatActivity {

    private EditText foodName, calories;
    private Button submit;
    private DataBaseHandler dba;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dba = new DataBaseHandler(MainActivity.this);
        foodName = findViewById(R.id.CaloriesFood);
        calories = findViewById(R.id.caloriesNumber);
        submit = findViewById(R.id.submitButton);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDataTODB();
                //String food1 = foodName.getText().toString();
                //String cal = calories.getText().toString();
            }
        });

    }
    private void saveDataTODB(){
        Food food = new Food();
        String name = foodName.getText().toString().trim();
        String cal = calories.getText().toString().trim();
        //int calInt = Integer.parseInt(cal);
        int calInt = 0;
        try {
             calInt=Integer.parseInt(cal);
        }catch (NumberFormatException e){
            System.out.println("not a number");
        }

        if(name.equals("") && cal.equals("")){

            Toast.makeText(getApplicationContext(),"Please enter some value",Toast.LENGTH_LONG).show();
        }else{
            food.setFoodName(name);
            food.setCalories(calInt);

            dba.addFood(food);
            dba.close();

            foodName.setText("");
            calories.setText("");
            Toast.makeText(getApplicationContext(),"Food " + name + ", Calories" + calInt, Toast.LENGTH_LONG).show();
            startActivity((new Intent(MainActivity.this, DetailFoodActivity.class)));
        }
    }
}