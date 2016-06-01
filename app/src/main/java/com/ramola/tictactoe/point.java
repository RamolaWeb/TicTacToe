package com.ramola.tictactoe;


public class point {

    public  int x,y;
    public point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        point p=(point)o;
        if(this.x==p.x&&this.y==p.y)
            return true;
        return false;
    }


}
