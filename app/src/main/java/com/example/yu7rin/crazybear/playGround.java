package com.example.yu7rin.crazybear;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import static java.lang.Math.abs;

/**
 * Created by yu7rin on 16/1/12.
 */
public class playGround extends SurfaceView implements View.OnTouchListener{

    private static int WIDTH =0;
    int time=0,selectMark=0;
    int w=0, h=0,jw=0, jh=0;
    private static final int COL =5;
    private static final int ROW =5;
    int dir;

    private Dot matrix[][];
    private Dot tiger1 = new Dot(1,1);
    private Dot tiger2 = new Dot(1,1);
    private Dot tiger3 = new Dot(1,1);
    private Dot lamb;




    public playGround(Context context) {
        super(context);
        getHolder().addCallback(callback);
        matrix = new Dot[ROW+2][COL+2];
        for (int i=0;i<ROW+2;i++){
            for (int j=0;j<COL+2;j++){
                matrix[i][j]=new Dot(j,i);
            }
        }
        setOnTouchListener(this);
        initGame();

    }



    private  Dot getDot(int x,int y){
        return matrix[y][x];
    }

    private void redraw() {
        Canvas c = getHolder().lockCanvas();
        c.drawColor(Color.LTGRAY);
        Paint paint = new Paint();
        for(int i=1;i<ROW+1;i++)  {
            for (int j=1;j<COL+1;j++){
                Dot one= getDot(i,j);
                switch (one.getStatus())
                {
                    case Dot.STATUS_NONE :
                        paint.setColor(0xFFEEEEEE);
                        break;
                    case Dot.STATUS_LAMB :
                        paint.setColor(0xFFFF0000);
                        break;
                    case Dot.STATUS_TIGER :
                        paint.setColor(0xFFFFAA00);
                        break;
                    case Dot.STATUS_SELECTT :
                        paint.setColor(0xFFFFAA99);
                        break;
                    case Dot.STATUS_SELECTL :
                        paint.setColor(0xFFFF0099);
                        break;
                    default:
                        break;

                }
                c.drawOval(new RectF((one.getX() - 1) * WIDTH, (one.getY() - 1) * WIDTH, (one.getX()) * WIDTH, (one.getY()) * WIDTH), paint);
                //c.drawRect(one.getX()*WIDTH,one.getY()*WIDTH,(one.getX()+1)*WIDTH,(one.getY()+1)*WIDTH,paint);
            }
        }
        getHolder().unlockCanvasAndPost(c);
    }

    SurfaceHolder.Callback callback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {

            redraw();
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            WIDTH = width/COL;
            redraw();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    };
    private void initGame(){
        for (int i=1;i<ROW+1;i++){
            for (int j=1;j<COL+1;j++){
                matrix[i][j].setStatus(Dot.STATUS_NONE);
            }
        }

        for (int i=1;i<3;i++){
            for (int j=1;j<6;j++){
                matrix[i][j].setStatus(Dot.STATUS_LAMB);
            }
        }
        for (int i=5;i<6;i++){
            for (int j=2;j<5;j++){
                matrix[i][j].setStatus(Dot.STATUS_TIGER);

            }
        }
        for (int i=0;i<1;i++){
            for (int j=0;j<7;j++){
                matrix[i][j].setStatus(Dot.STATUS_EDGE);

            }
        }
        for (int i=0;i<7;i++){
            for (int j=0;j<1;j++){
                matrix[i][j].setStatus(Dot.STATUS_EDGE);

            }
        }
        for (int i=6;i<7;i++){
            for (int j=0;j<7;j++){
                matrix[i][j].setStatus(Dot.STATUS_EDGE);

            }
        }
        for (int i=0;i<7;i++){
            for (int j=6;j<7;j++){
                matrix[i][j].setStatus(Dot.STATUS_EDGE);

            }
        }
        selectMark=0;
        time=0;

    }
    private void pending(){

        if (time==1){

            lambMove();
            System.out.println("into pending lamb");

        }else if(time==0){

            tigerMove();
            System.out.println("into pending tiger");

        }else{

            System.out.println("fale into pending");

        }
    }
    private int distance(){
        int d=0;
        if (abs(jw-w)==0){
            d=abs(jh-h);
            if (jh-h<0){
                System.out.println("JW  ="+jw+w);
            dir=3;
            }else{
                dir=1;
            }
        }else if (abs(jh-h)==0){
            d=abs(jw-w);
            if (jw-w<0){
                dir=4;
            }else{
                dir=2;
            }
        }else{
            Toast.makeText(getContext(), "Wrong Step", Toast.LENGTH_SHORT).show();
        }
            return d;
    }

    private Dot tiger_lamb_distance(Dot one,int driction){

        switch(driction){
            case 1:
                System.out.println("dir1");
                if (one.getX()<=5&&one.getY()<=5)
                return getDot(one.getX()-1,one.getY());
                else
                return getDot(one.getX(),one.getY());


            case 2:
                System.out.println("dir2");
                if (one.getX()<=5&&one.getY()<=5)
                return getDot(one.getX(),one.getY()-1);
                else
                    return getDot(one.getX(),one.getY());

            case 3:
                System.out.println("dir3");
                if (one.getX()<=5&&one.getY()<=5)
                return getDot(one.getX()+1,one.getY());
                else
                    return getDot(one.getX(),one.getY());

            case 4:
                System.out.println("dir4");
                if (one.getX()<=5&&one.getY()<=5)
                return getDot(one.getX(),one.getY()+1);
                else
                    return getDot(one.getX(),one.getY());

            default:
                break;

        }
        return null;
    }
    private void lambMove(){

        if(selectMark==0&&getDot(h,w).getStatus()==Dot.STATUS_LAMB){
            matrix[w][h].setStatus(Dot.STATUS_SELECTL);
            jw=w;jh=h; System.out.println("into pending lamb Select");
            redraw();
            selectMark=1;

        }else if(selectMark==1&&getDot(h,w).getStatus()==Dot.STATUS_LAMB){
            matrix[w][h].setStatus(Dot.STATUS_SELECTL);
            matrix[jw][jh].setStatus(Dot.STATUS_LAMB);
            jw=w;jh=h;
            redraw();


        }else if (selectMark==1&&getDot(h, w).getStatus()==Dot.STATUS_NONE&&distance()==1 ){
            matrix[w][h].setStatus(Dot.STATUS_LAMB);
            matrix[jw][jh].setStatus(Dot.STATUS_NONE);
            time=0;
            redraw();
            selectMark=0;
            System.out.println("into pending lamb NONE");
        }
        Lambwin();
        Lambloss();
        redraw();


    }

    private void tigerMove(){
        if(selectMark==0&&getDot(h,w).getStatus()==Dot.STATUS_TIGER){
            matrix[w][h].setStatus(Dot.STATUS_SELECTT);
            jw=w;jh=h; System.out.println("into pending tiger Select");
        redraw();
        selectMark=1;

        }else if(selectMark==1&&getDot(h,w).getStatus()==Dot.STATUS_TIGER){
            matrix[w][h].setStatus(Dot.STATUS_SELECTT);
            matrix[jw][jh].setStatus(Dot.STATUS_TIGER);
            jw=w;jh=h;
            System.out.println("into pending tiger Select");
            redraw();


        }else if (selectMark==1&&getDot(h, w).getStatus()==Dot.STATUS_NONE&&distance()==1){
            matrix[w][h].setStatus(Dot.STATUS_TIGER);
            matrix[jw][jh].setStatus(Dot.STATUS_NONE);
            time=1;
            redraw();
            selectMark=0;
            System.out.println("into pending tiger NONE");

        }else if (selectMark==1&&getDot(h, w).getStatus()==Dot.STATUS_LAMB&&distance()==2&&tiger_lamb_distance(matrix[jw][jh],dir).getStatus()==Dot.STATUS_NONE){
            matrix[w][h].setStatus(Dot.STATUS_TIGER);
            matrix[jw][jh].setStatus(Dot.STATUS_NONE);
            time=1;
            redraw();
            selectMark=0;
        }
        Lambloss();
        
        redraw();
    }


    private void Lambwin(){
        int t=0;
        int z=0;
        for (int i=1;i<ROW+1;i++){
            for (int j=1;j<COL+1;j++){
                System.out.println("t no"+t);
                if(matrix[i][j].getStatus()==Dot.STATUS_TIGER){
                    t++;
                    if (t==1){
                        tiger1.setXY(j,i);
                    }else if (t==2){
                        tiger2.setXY(j,i);
                    }else if (t==3){
                        tiger3.setXY(j,i);
                    }
                    System.out.println("n1 no"+tiger1.getX()+":"+tiger1.getY());System.out.println("n2 no"+tiger2.getX()+":"+tiger2.getY());System.out.println("n3 no"+tiger3.getX()+":"+tiger3.getY());
                }

            }
        }

        for (int m =1;m<5;m++){

        //int m=4;
            if (tiger_lamb_distance(tiger1,m).getStatus()==Dot.STATUS_NONE)
                z++;
            if (tiger_lamb_distance(tiger2,m).getStatus()==Dot.STATUS_NONE)
                z++;
            if (tiger_lamb_distance(tiger3,m).getStatus()==Dot.STATUS_NONE)
                z++;
            System.out.println("distance second for no"+tiger_lamb_distance(tiger1,m).getStatus()+":"+tiger_lamb_distance(tiger2,m).getStatus()+":"+tiger_lamb_distance(tiger3,m).getStatus());

        }
        if (z==0)
            Toast.makeText(getContext(), "Lamb win", Toast.LENGTH_SHORT).show();



    }


    private void Lambloss(){
        int l=0;
        for (int i=1;i<ROW+1;i++){
            for (int j=1;j<COL+1;j++){
                System.out.println("Lamb no"+l);
                if(matrix[i][j].getStatus()==Dot.STATUS_LAMB){
                    l++;
                }
            }
        }
        System.out.println("Lamb no"+l);
        if (l<4){
            Toast.makeText(getContext(), "Lamb Loss", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (event.getAction()==MotionEvent.ACTION_UP) {
            w = (int) (event.getY() / WIDTH+1);
            h = (int) (event.getX() / WIDTH+1);
            //Toast.makeText(getContext(), w+":"+ h+"zb"+matrix[w][h].getStatus(),Toast.LENGTH_SHORT).show();
            if (w+1>COL+1||h+1>ROW+1){
                redraw();initGame();
                System.out.println("out of screen");
            }else {
                pending();
            }

        }


        return true;
    }



}