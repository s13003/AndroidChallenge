package jp.ac.it_college.std.s13003.androidchallenge;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.os.Handler;
import android.widget.LinearLayout;

import java.util.Random;

/**
 * Created by s13003 on 14/08/07.
 */
public class GameActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new Block(this));
        String difficulty = getIntent().getStringExtra("Difficulty");
        Log.v("difficulty", difficulty);

        if (difficulty.
                equals("EASY")) {
            Block.setFallSpeed(100);
        }else if (difficulty.equals("NORMAL")) {
            Block.setFallSpeed(70);
        }else if (difficulty.equals("HARD")) {
            Block.setFallSpeed(10);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        Block.Loop();
    }
}