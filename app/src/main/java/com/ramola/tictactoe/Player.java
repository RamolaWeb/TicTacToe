package com.ramola.tictactoe;


import java.util.ArrayList;

public class Player  {

    private String name;
    private int color;
    private boolean hasPlayed;
    public ArrayList<point> list=new ArrayList<>();
    private  int [] row,column,diagonal,rdiagonal;

    public Player(String name, int color) {
        this.name = name;
        this.color = color;
        row=new int[3];
        column=new int[3];
        diagonal=new int[1];
        rdiagonal=new int[1];
    }

    public boolean isHasPlayed() {
        return hasPlayed;
    }

    public void setHasPlayed(boolean hasPlayed) {
        this.hasPlayed = hasPlayed;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public STATE result(){
        if(list.size()>0) {
            point p = this.list.get(list.size() - 1);
            int r = p.x;
            int c = p.y;

            if (++row[r] == 3)
                return STATE.WIN;
            if (++column[c] == 3)
                return STATE.WIN;
            if (r == c) {
                if (++diagonal[0] == 3)
                    return STATE.WIN;
            }
            for (int i = 0; i <= 2; i++) {
                if (r == i && c == (2 - i)) {
                    if (++rdiagonal[0] == 3)
                        return STATE.WIN;
                }
            }
        }
        return STATE.PLAY;
    }

    public  enum STATE{
        WIN,PLAY
    }
}
