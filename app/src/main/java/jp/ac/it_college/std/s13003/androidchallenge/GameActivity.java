package jp.ac.it_college.std.s13003.androidchallenge;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.os.Handler;
import java.util.Random;

/**
 * Created by s13003 on 14/08/07.
 */
public class GameActivity extends Activity {

    private static final int INVALIDATE = 1;
    private static final int DROPBLOCK = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
    }

    }