package com.example.yu7rin.crazybear;

/**
 * Created by yu7rin on 16/1/12.
 */
public class Dot {

    int x,y;
    int status;

    public static final int STATUS_LAMB =1;
    public static final int STATUS_TIGER =-1 ;
    public static final int STATUS_NONE =0;
    public static final int STATUS_SELECTT=2;
    public static final int STATUS_SELECTL=3;
    public static final int STATUS_EDGE=4;



    public Dot(int x,int y){
        this.x=x;
        this.y=y;
        status = STATUS_NONE;

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getStatus() {
        return status;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    public void setXY(int x,int y){
        this.y=y;
        this.x=x;

    }
}
