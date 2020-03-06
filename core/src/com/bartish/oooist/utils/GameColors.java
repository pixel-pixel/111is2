package com.bartish.oooist.utils;

import com.badlogic.gdx.graphics.Color;

public class GameColors {
    public static final Color BACK = new Color(72/255f,72/255f,74/255f, 1);
    public static final Color X = new Color(72/255f,72/255f,74/255f, 1);

    public static final Color ONE = new Color(88/255f, 86/255f, 214/255f, 1);
    public static final Color TWO = new Color(175/255f,82/255f,222/255f, 1);
    public static final Color THREE = new Color(0/255f, 122/255f, 255/255f, 1);
    public static final Color FOUR = new Color(90/255f, 200/255f, 250/255f, 1);
    public static final Color FIVE = new Color(52/255f, 199/255f, 89/255f, 1);
    public static final Color SIX = new Color(80/255f, 180/255f, 80/255f, 1);

    public static final Color A = new Color(255/255f, 59/255f, 48/255f, 1);
    public static final Color B = new Color(255/255f, 55/255f, 95/255f, 1);
    public static final Color C = new Color(255/255f, 149/255f, 0/255f, 1);
    public static final Color D = new Color(255/255f, 204/255f, 0/255f, 1);
    public static final Color E = new Color(200/255f, 220/255f, 60/255f, 1);
    public static final Color F = new Color(140/255f, 200/255f, 70/255f, 1);

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
}

