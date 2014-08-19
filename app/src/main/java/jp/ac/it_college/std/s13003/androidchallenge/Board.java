package jp.ac.it_college.std.s13003.androidchallenge;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.View;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
/**
 * Created by s13003 on 14/08/12.
 */
public class Board extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private SurfaceHolder mHolder;
    static Thread mThread;
    Canvas mCanvas;
    static boolean mIsAttached;
    public Board(Context context) {
        super(context);
        mHolder = getHolder();
        mHolder.addCallback(this);
    }

/*

    @Override
    protected void onDraw(Canvas canvas) {
        ShapeDrawable rect = new ShapeDrawable(new RectShape());
        rect.setBounds(0, 0, 210, 410);
        //ボード枠
        rect.getPaint().setColor(Color.BLACK);
        rect.draw(canvas);
        canvas.translate(5, 5);
        //ボード背景
        rect.setBounds(0, 0, 200, 400);
        rect.getPaint().setColor(Color.CYAN);
        rect.draw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(0, 0, 200, 400, paint);
    }

*/
/*
        @Override
        protected void onDraw(Canvas canvas) {
/*
        Display display =
                Point p = new Point();
        display.getSize(p);
/*
            ShapeDrawable rect = new ShapeDrawable(new RectShape());
            rect.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            //ボード枠
            rect.getPaint().setColor(0xFF000000);
            rect.draw(canvas);
            canvas.translate(5, 5);
            rect.setBounds(0, 0, 200, 400);
            //ボード背景
            rect.getPaint().setColor(0xFFFFFFFF);
            rect.draw(canvas);


            paintMatrix(canvas, block, posx, posy, Color.RED);
            paintMatrix(canvas, map, 0, 0, 0xFF808080);

        }*/

        private void paintMatrix(Canvas canvas, int[][] matrix, int offsetx, int offsety, int color) {
            ShapeDrawable rect = new ShapeDrawable(new RectShape());
            rect.getPaint().setColor(color);
            int h = matrix.length;
            int w = matrix[0].length;

            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    if (matrix[y][x] != 0) {
                        int px = (x + offsetx) * 20;
                        int py = (y + offsety) * 20;
                        rect.setBounds(px, py, px + 20, py + 20);
                        rect.draw(canvas);
                    }
                }
            }
        }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mIsAttached = true;
        mThread = new Thread(this);
        mThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        mIsAttached = false;
        while (mThread.isAlive());
    }

    @Override
    public void run() {
        while (mIsAttached) {
            mCanvas = getHolder().lockCanvas();
        }
    }


}