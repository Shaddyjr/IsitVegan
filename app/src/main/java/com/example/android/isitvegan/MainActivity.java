package com.example.android.isitvegan;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    // some transient state for the activity instance

    ///////////
    //GLOBALS//
    ///////////
    View intro, main;
    LinearLayout end;
    ImageView img; // one image view showing food image
    TextView food_name, end_v, q;
    Food[] foods = new Food[10];
    int ind = 0; //index of current food
    LayoutInflater layoutInflater;
    TextView rowText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // call the super class onCreate to complete the creation of activity like
        // the view hierarchy
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

//      Custom text will load on real device
        TextView tx1 = (TextView)findViewById(R.id.title1);
        TextView tx2 = (TextView)findViewById(R.id.title2);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/ConcertOne-Regular.ttf");

        tx1.setTypeface(custom_font);
        tx2.setTypeface(custom_font);

//        Establishing globals
        main= findViewById(R.id.main);
        intro = findViewById(R.id.intro);
        end= findViewById(R.id.end);
        img = (ImageView)findViewById(R.id.img);
        food_name = (TextView)findViewById(R.id.food_name);
        end_v = (TextView) findViewById(R.id.end_text);
        q = (TextView)findViewById(R.id.q);

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

        layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        img.setImageResource(food.img);
        food_name.setText(food.name);
        // updating question counter
        q.setText(getString(R.string.q_count, "" + (ind+1)));
    }

    /**
    * Switches "activity" windows by changing each view's visibility (shows main activity)
    */
    public void goToMainActivity(View v){
        intro.setVisibility(View.GONE);

        // showing first food
        showFood(foods[0]);

        main.setVisibility(View.VISIBLE);
    }

    /**
     * Switches "activity" windows by changing each view's visibility (shows end activity)
     */
    public void goToFinalActivity(){
        main.setVisibility(View.GONE);
        end.setVisibility(View.VISIBLE);
    }

    /**
     * Shows food error from quiz when wrong answer given by adding new view to last activity view
     * @param food is a Food object from a wrong answer
     */
    public void addError(Food food) {

        String s = "";

        s += "<br>" + "<b>Food:</b> " + food.name;
        s += "<br>" + "<b>Vegan?</b> " + food.vegan;
        s += "<br>" + "<b>Reason:</b> " + food.explanation;
        s += "<br>" + "===============================";

        addField(Html.fromHtml(s));
    }
    /**
     * Adds food error text to a new, dynamically added view to the last activity view
     * @param str is a Spanned object from a Html.fromHtml call
     */
    public void addField(Spanned str) {
        final View addView = layoutInflater.inflate(R.layout.row, null);

        rowText = (TextView)addView.findViewById(R.id.end_text);

        end.addView(addView);
        rowText.setText(str);
    }

    /**
     * moves onto the next food item
     */
    public void nextFood(View v){
        Food food = foods[ind];
        String s = v.getTag().toString();
        // tracking wrong answers
        if(s.equals("f") && food.vegan || s.equals("t") && !food.vegan){
            addError(food);
        }

        ind++;
        // ending on last
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
    }
}
