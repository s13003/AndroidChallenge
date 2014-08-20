package jp.ac.it_college.std.s13003.androidchallenge;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.LinearLayout;

/**
 * Created by s13003 on 14/08/07.
 */
public class GameActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        String difficulty = getIntent().getStringExtra("Difficulty");
        Log.v("difficulty", difficulty);
        LinearLayout mainLayout = (LinearLayout)findViewById(R.id.tetrisLayout);
        mainLayout.addView(new Block(this));


        if (difficulty.
                equals("EASY")) {
            Block.setDifficulty("EASY");
        }else if (difficulty.equals("NORMAL")) {
            Block.setDifficulty("NORMAL");
        }else if (difficulty.equals("HARD")) {
            Block.setDifficulty("HARD");
        }

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new AlertDialog.Builder(this)
                    .setTitle("終了の確認")
                    .setPositiveButton("はい",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish();
                                }
                            })
                    .setNegativeButton("いいえ",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            })
                    .show();
            return true;
        }else {
            return false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Block.Loop();
    }
}