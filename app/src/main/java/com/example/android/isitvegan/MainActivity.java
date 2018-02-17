package com.example.android.isitvegan;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.LinkedList;
import java.util.Random;


public class MainActivity extends AppCompatActivity {
    // storing all foods for quiz
    Food[] foods = new Food[10];
    int ind = 0; //index of current food
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Custom text will load on read device
        TextView tx1 = (TextView)findViewById(R.id.title1);
        TextView tx2 = (TextView)findViewById(R.id.title2);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/ConcertOne-Regular.ttf");

        tx1.setTypeface(custom_font);
        tx2.setTypeface(custom_font);

        foods[rand_empty_element(foods)] = new Food("Oreos",true,"The Oreo filling is made with canola oil and corn syrup",R.drawable.oreo);
        foods[rand_empty_element(foods)] = new Food("Chipotle Sofritas",true,"Sofrita is tofu, which is a soy-based meat substitute",R.drawable.sof);
        foods[rand_empty_element(foods)] = new Food("Jell-O Pudding",true,"Jell-O is a non dairy product",R.drawable.jel);
        foods[rand_empty_element(foods)] = new Food("Altoids",false,"Altoids contain Gelatin",R.drawable.alt);
        foods[rand_empty_element(foods)] = new Food("Minute Maid Grapefruit Juice",false,"Contains dyes made from insects",R.drawable.maid);
        foods[rand_empty_element(foods)] = new Food("Macaroni and Cheese",false,"Contains dairy",R.drawable.mac);
        foods[rand_empty_element(foods)] = new Food("Guinness Beer",false,"Clarified using gelatin, isinglass (fish bladders), bone marrow, casein (derived from milk)",R.drawable.guin);
        foods[rand_empty_element(foods)] = new Food("Pancakes",false,"Contains eggs and milk",R.drawable.pan);
        foods[rand_empty_element(foods)] = new Food("Dark Chocolate covered Almonds",true,"Dark Chocolate is not treated with milk, like Milk Chocolate is",R.drawable.choc);
        foods[rand_empty_element(foods)] = new Food("Falafel pita sandwich with hummus",true,"Falafel's are made with chickpeas, herbs, and spices. The Tahini sauce is made with sesame seeds.",R.drawable.fal);

        showFood(foods[ind]);
    }

    /**
     * returns index of empty spot in array
     * @param foods is an array of Food objects
     */
    private int rand_empty_element(Food[] foods){
        int num;
        do {
             num = new Random().nextInt(foods.length);
        }while(foods[num]!=null);
        return num;
    }

    /**
     * shows the content of the food on main activity window
     * @param food is Food object
     */
    public void showFood (Food food){
        ImageView img = (ImageView)findViewById(R.id.img);
        img.setImageResource(food.img);

        TextView food_name = (TextView)findViewById(R.id.food_name);
        food_name.setText(food.name);

    }


    /**
    * Switches "activity" windows by changing each view's visibility
    */
    public void goToMainActivity(View v){
        View intro = findViewById(R.id.intro);
        intro.setVisibility(View.GONE);

        View main= findViewById(R.id.main);
        main.setVisibility(View.VISIBLE);
    }

    /**
     * Switches "activity" windows by changing each view's visibility
     */
    public void goToFinalActivity(){
        View main= findViewById(R.id.main);
        main.setVisibility(View.GONE);

        View end= findViewById(R.id.end);
        end.setVisibility(View.VISIBLE);
    }

    /**
     * Shows food error from quiz when wrong answer given
     * @param food is a Food object from a wrong answer
     */
    public void addError(Food food) {
        TextView end_v = (TextView) findViewById(R.id.end_text);
        String s = end_v.getText().toString();

        s += "\n" + "Food: " + food.name;
        s += "\n" + "Vegan? " + food.vegan;
        s += "\n" + "Reason: " + food.explanation;
        s += "\n" + "===============================";

        end_v.setText(s);
    }

    /**
     * moves onto the next food item
     */
    public void nextFood(View v){
        Food food = foods[ind];
        String s = v.getTag().toString();
//        tracking wrong answers
        if(s.equals("f") && food.vegan || s.equals("t") && !food.vegan){
            Log.v("main","Incorrect! " + food.name);
            addError(food);
        }

        ind++;
        //        ending on last
        if(ind>=foods.length){
            goToFinalActivity();
            return;
        }
        // showing next food
        Food nextFood = foods[ind];
        showFood(nextFood);

        // resetting checkbox
        CheckBox checkBox = (CheckBox) v;
        checkBox.setChecked(false);

//      update question counter
        TextView q= (TextView)findViewById(R.id.q);
        q.setText("Question #"+(ind+1));
    }

}
