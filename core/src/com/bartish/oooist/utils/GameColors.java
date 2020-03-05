package com.bartish.oooist.utils;

import com.badlogic.gdx.graphics.Color;

public class GameColors {
    public static final Color BACK = new Color(70/255f,70/255f,70/255f, 1);
    public static final Color X = new Color(70/255f,70/255f,70/255f, 1);

    public static final Color ONE = new Color(170/255f, 70/255f, 190/255f, 1);
    public static final Color TWO = new Color(60/255f,80/255f,180/255f, 1);
    public static final Color THREE = new Color(30/255f, 150/255f, 240/255f, 1);
    public static final Color FOUR = new Color(0/255f, 190/255f, 210/255f, 1);
    public static final Color FIVE = new Color(0/255f, 150/255f, 140/255f, 1);
    public static final Color SIX = new Color(80/255f, 180/255f, 80/255f, 1);

    public static final Color A = new Color(230/255f, 30/255f, 100/255f, 1);
    public static final Color B = new Color(240/255f, 70/255f, 50/255f, 1);
    public static final Color C = new Color(250/255f, 150/255f, 90/255f, 1);
    public static final Color D = new Color(250/255f, 240/255f, 60/255f, 1);
    public static final Color E = new Color(200/255f, 220/255f, 60/255f, 1);
    public static final Color F = new Color(140/255f, 200/255f, 70/255f, 1);

//    A,
//    B(),
//    C(),
//    D(),
//    E(),
//    F();
//
//    public Color color;
//
//    GameColors(Color c) {
//        color = c;
//    }
//
    public static Color getColor(int index){
        switch (index){
            case 1: return ONE;
            case 2: return A;
            case 3: return TWO;
            case 4: return B;
            case 5: return THREE;
            case 6: return C;
            case 7: return FOUR;
            case 8: return D;
            case 9: return FIVE;
            case 10: return E;
            case 11: return SIX;
            case 12: return F;
            default: return X;
        }
    }
//    public boolean equals(GameColors minorColor){
//        if(minorColor.r - r < 0.001 &&
//        minorColor.g - g < 0.001 &&
//        minorColor.b - b < 0.001){
//            return true;
//        }else {
//            return false;
//        }
//    }
}

