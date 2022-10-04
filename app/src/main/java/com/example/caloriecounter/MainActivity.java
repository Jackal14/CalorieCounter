package com.example.caloriecounter;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.MainThread;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.caloriecounter.Data.DataBaseHandler;
import com.example.caloriecounter.model.Food;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static Integer CAMERA_PERMISSION_CODE = 1;
    private static Integer CAMERA = 2;
    private static String[] Array = {
            Manifest.permission.CAMERA
    };
    private Bitmap photo;
    private EditText foodName, calories;
    private ImageView foodPic;
    private Button submit;
    private Button camera;
    private DataBaseHandler dba;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dba = new DataBaseHandler(MainActivity.this);
        foodName = findViewById(R.id.CaloriesFood);
        calories = findViewById(R.id.caloriesNumber);
        submit = findViewById(R.id.submitButton);
        camera = findViewById(R.id.cameraButton);
        foodPic = findViewById(R.id.foodPicture);



        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, CAMERA);

                }
                else
                {
                    ActivityCompat.requestPermissions(MainActivity.this, Array, CAMERA_PERMISSION_CODE);
                }
                //Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //startActivityForResult(intent, 100);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDataTODB();
                //String food1 = foodName.getText().toString();
                //String cal = calories.getText().toString();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        photo = (Bitmap)data.getExtras().get("data");
        foodPic.setImageBitmap(photo);
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
            food.setPhoto(photo);

            dba.addFood(food);
            dba.close();

            foodName.setText("");
            calories.setText("");
            foodPic.setImageResource(0);

            Toast.makeText(getApplicationContext(),"Food " + name + ", Calories" + calInt, Toast.LENGTH_LONG).show();
            startActivity((new Intent(MainActivity.this, DetailFoodActivity.class)));
        }
    }
}