package com.bartish.oooist.utils;

public enum GameColor {
    BACK(70/255f,70/255f,70/255f),
    X(70/255f,70/255f,70/255f),

    ONE(170/255f, 70/255f, 190/255f),
    TWO(60/255f,80/255f,180/255f),
    THREE(30/255f, 150/255f, 240/255f),
    FOUR(0/255f, 190/255f, 210/255f),
    FIVE(0/255f, 150/255f, 140/255f),
    SIX(80/255f, 180/255f, 80/255f),

    A(230/255f, 30/255f, 100/255f),
    B(240/255f, 70/255f, 50/255f),
    C(250/255f, 150/255f, 90/255f),
    D(250/255f, 240/255f, 60/255f),
    E(200/255f, 220/255f, 60/255f),
    F(140/255f, 200/255f, 70/255f);

    public float r, g, b;

    GameColor(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public static GameColor getColor(int index){
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
    public boolean equals(GameColor minorColor){
        if(minorColor.r - r < 0.001 &&
        minorColor.g - g < 0.001 &&
        minorColor.b - b < 0.001){
            return true;
        }else {
            return false;
        }
    }
}

