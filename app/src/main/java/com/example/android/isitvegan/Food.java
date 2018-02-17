package com.example.android.isitvegan;

import android.graphics.drawable.Drawable;

/**
 * Created by Mahdi on 2/12/2018.
 */

public class Food {

    String name;
    boolean vegan;
    String explanation;
    int img;

    // constructor
    Food(String a,  boolean b,String c, int d){
        name = a;
        vegan = b;
        explanation = c;
        img = d;
    }

}
