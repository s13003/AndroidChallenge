package jp.ac.it_college.std.s13003.androidchallenge;

import android.content.Context;
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
public class Board extends View {

    public Board(Context context) {
        super(context);
    }

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
        /*Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(0,0,200,400,paint);*/


    }
}