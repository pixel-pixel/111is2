package com.bartish.oooist.utils;

import com.badlogic.gdx.graphics.Color;

public class GameColors {
    public static final Color BACK = new Color(70/255f,70/255f,70/255f, 1);
    public static final Color X = new Color(70/255f,70/255f,70/255f, 1);

    public static final Color ONE = new Color(210/255f, 51/255f, 134/255f, 1);
    public static final Color TWO = new Color(210/255f, 51/255f, 51/255f, 1);
    public static final Color THREE = new Color(210/255f, 134/ 255f, 51/255f, 1);
    public static final Color FOUR = new Color(210/255f, 210/255f, 51/255f, 1);
    public static final Color FIVE = new Color(134/255f, 210/255f, 51/255f, 1);
    public static final Color SIX = new Color(51/255f, 210/255f, 51/255f, 1);

    public static final Color A = new Color(51/255f, 210/255f, 134/255f, 1);
    public static final Color B = new Color(51/255f, 210/255f, 210/255f, 1);
    public static final Color C = new Color(51/255f, 134/255f, 210/255f, 1);
    public static final Color D = new Color(51/255f, 51/255f, 210/255f, 1);
    public static final Color E = new Color(134/255f, 51/255f, 210/255f, 1);
    public static final Color F = new Color(210/255f, 51/255f, 210/255f, 1);

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
            case 100: return TWO;
            default: return X;
        }
    }
}

