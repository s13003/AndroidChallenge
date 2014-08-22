package jp.ac.it_college.std.s13003.androidchallenge;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.TextView;
import java.util.Random;

/**
 * Created by s13003 on 14/08/12.
 */
    public class Block extends SurfaceView
            implements GestureDetector.OnGestureListener, SurfaceHolder.Callback,
                Runnable, GestureDetector.OnDoubleTapListener {
    Random mRand = new Random(System.currentTimeMillis());
    private final static int BLOCK_HEIGHT = 53;
    private final static int BLOCK_WIDTH = 45;
    private int mapWidth =12;
    private int mapHeight = 21;
    private int posx = mapWidth / 2, posy;
    private int[][] blockMap = new int[mapHeight][];
    private GestureDetector gestureDetector;
    protected static Thread mThread;
    private SurfaceHolder mHolder;
    public static boolean mIsAttached;
    private Canvas mCanvas;
    public static int FallSpeed;
    public static int frame;
    private TextView scoreText;
    private int ORANGE = Color.rgb(243,152, 0);
    private int score;
    private final int[] COLORS = {Color.RED, Color.BLUE,
            Color.CYAN, Color.GREEN, ORANGE,
            Color.YELLOW};
    private int blockColor;
    private static int mdifficulty;
    private static final int EASY = 0;
    private static final int NORMAL = 1;
    private static final int HARD = 2;
    private final int EASY_SCORE = 100;
    private final int NORMAL_SCORE = 200;
    private final int HARD_SCORE = 300;
    int[][][] blocks = {
            {
                    {1},
                    {1},
                    {1},
                    {1},
                    {1}
            },
            {
                    {1, 1},
                    {0, 1},
                    {0, 1}
            },
            {
                    {1, 1},
                    {1, 0},
                    {1, 0}
            },
            {
                    {1, 1},
                    {1, 1}
            },
            {
                    {1, 0},
                    {1, 1},
                    {1, 0}
            },
            {
                    {1, 0},
                    {1, 1},
                    {0, 1}
            },
            {
                    {0, 1},
                    {1, 1},
                    {1, 0}
            },
            {
                    {1},
                    {1},
                    {1},
                    {1}
            }
    };
    private static boolean isStop = false;

    private int[][] block = blocks[mRand.nextInt(blocks.length)];

    public Block(Context context) {
        super(context);
        Display display = ((GameActivity)context).getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int D_width = point.x;
        int D_height = point.y;
        Log.v("width", String.valueOf(D_width));
        Log.v("height", String.valueOf(D_height));
        gestureDetector = new GestureDetector(context, this);
        mHolder = getHolder();
        mHolder.addCallback(this);
        initGame();
        scoreText = (TextView)((GameActivity)context).findViewById(R.id.score);
    }

    public void initGame() {
        for (int y = 0; y < mapHeight; y++) {
            blockMap[y] = new int[mapWidth];
            for (int x = 0; x < mapWidth; x++) {
                if (y == mapHeight - 1 || x == mapWidth - 1 || x == 0) {
                    blockMap[y][x] = 1;
                } else {
                    blockMap[y][x] = 0;
                }
            }
        }
    }

    boolean check(int[][] block, int offsetx, int offsety) {
        if (offsetx < 0 || offsety < 0 ||
                mapHeight < offsety + block.length ||
                mapWidth < offsetx + block[0].length) {
            return false;
        }
        for (int y = 0; y < block.length; y++) {
            for (int x = 0; x < block[y].length; x++) {
                if (block[y][x] != 0 && blockMap[y + offsety][x + offsetx] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

/*
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
*/

    void mergeMatrix(int[][] block, int offsetx, int offsety) {
        for (int y = 0; y < block.length; y++) {
            for (int x = 0; x < block[0].length; x++) {
                if (block[y][x] != 0) {
                    blockMap[offsety + y][offsetx + x] = block[y][x];
                }
            }
        }
    }

    void clearRows() {
        for (int y = 0; y < mapHeight - 1; y++) {
            boolean full = true;
            for (int x = 1; x < mapWidth - 1; x++) {
                if (blockMap[y][x] == 0) {
                    full = false;
                    break;
                }
            }
            if (full) blockMap[y] = null;
        }
        int[][] newMap = new int[mapHeight][];
        int y2 = mapHeight - 1;
        for (int y = mapHeight - 1; y >= 0; y--) {
            if (blockMap[y] == null) {
                continue;
            } else {
                newMap[y2--] = blockMap[y];
            }
        }
        for (int i = 0; i <= y2; i++) {
            int[] newRow = new int[mapWidth];
            for (int j = 0; j < mapWidth; j++) {
                if (j == 0 || j == mapWidth - 1) {
                    newRow[j] = 1;
                } else {
                    newRow[j] = 0;
                }
            }
            newMap[i] = newRow;
            switch (mdifficulty) {
                case EASY:mHandler.sendEmptyMessage(EASY_SCORE);
                    break;

                case NORMAL:mHandler.sendEmptyMessage(NORMAL_SCORE);
                    break;

                case HARD:mHandler.sendEmptyMessage(HARD_SCORE);
                    break;
            }
        }
        blockMap = newMap;
    }


    int[][] rotate(final int[][] block) {
        int[][] rotated = new int[block[0].length][];
        for (int x = 0; x < block[0].length; x++) {
            rotated[x] = new int[block.length];
            for (int y = 0; y < block.length; y++) {
                rotated[x][block.length - y - 1] = block[y][x];
            }
        }
        return rotated;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent)

    {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {
        int[][] newBlock = rotate(block);
        if (check(newBlock, posx, posy)) {
            block = newBlock;
        }
    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
/*        int[][] newBlock = rotate(block);
        if (check(newBlock, posx, posy)) {
            block = newBlock;*/
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float v, float v2) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2,
                           float v, float v2) {
        if (motionEvent.getX() < motionEvent2.getX()) {
            if (check(block, posx + 1, posy)) {
                posx = posx + 1;
            }
        } else {
            if (check(block, posx - 1, posy)) {
                posx = posx - 1;
            }
        }
        return true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mHandler.sendEmptyMessage(0);
        blockColor = COLORS[mRand.nextInt(COLORS.length)];
        frame = 0;
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
        while (mThread.isAlive()) ;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {
        int y = posy;
        while (check(block, posx, y)) {
            y++;
        }
        if (y > 0) {
            posy = y - 1;
        }
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void run() {
        while (mIsAttached) {
            if (!isStop) {
                mCanvas = getHolder().lockCanvas();
                mCanvas.drawColor(Color.WHITE);

                drawMap(block, posx, posy, blockColor);
                drawMap(blockMap, 0, 0, Color.GRAY);

                if (frame % FallSpeed == 0) {
                    FallBlock();
                }
                frame++;
                getHolder().unlockCanvasAndPost(mCanvas);
                if (gameOver()) {
                    Intent intent = new Intent(getContext(), ResultActivity.class);
                    intent.putExtra("score", score);
                    getContext().startActivity(intent);
                }
            }
        }
    }
    public void FallBlock() {
        if (check(block, posx, posy + 1)) {
            posy++;
        } else {
            mergeMatrix(block, posx, posy);
            clearRows();
            posx = mapWidth / 2;
            posy = 0;
            block = blocks[mRand.nextInt(block.length)];
            blockColor = COLORS[mRand.nextInt(COLORS.length)];
        }
    }

    public boolean gameOver() {
        if (posy == 0 && !check(block, posx, posy)) {
            return true;
        }
        return false;
    }

    /*public void paintBoard() {
        ShapeDrawable rect = new ShapeDrawable(new RectShape());
        rect.setBounds(0, 0, mCanvas.getWidth(), mCanvas.getHeight());
        //ボード枠
        rect.getPaint().setColor(0xFF000000);
        rect.draw(mCanvas);
        mCanvas.translate(5, 5);
        rect.setBounds(0, 0, 200, 400);
        //ボード背景
        rect.getPaint().setColor(0xFFFFFFFF);
        rect.draw(mCanvas);

    }*/

    private void drawMap(int[][] matrix, int offsetx, int offsety, int color) {
        ShapeDrawable rect = new ShapeDrawable(new RectShape());
        rect.getPaint().setColor(color);

        int h = matrix.length;
        int w = matrix[0].length;

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                if (matrix[y][x] != 0) {
                    int px = (x + offsetx) * BLOCK_WIDTH;
                    int py = (y + offsety) * BLOCK_HEIGHT;
                    rect.setBounds(px, py, px + BLOCK_WIDTH, py + BLOCK_HEIGHT);
                    rect.draw(mCanvas);
                }
            }
        }
    }

    public static void Loop() {
        synchronized (mThread) {
            mIsAttached = false;
        }
        try {
            mThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void setDifficulty(String difficulty) {
        if (difficulty.equals("EASY")) {
            FallSpeed = 100;
            mdifficulty = EASY;
        } else if (difficulty.equals("NORMAL")) {
            FallSpeed = 70;
            mdifficulty = NORMAL;
        } else if (difficulty.equals("HARD")) {
            FallSpeed = 30;
            mdifficulty = HARD;
        }
    }

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            score += msg.what;
            scoreText.setText("score" + String.valueOf(score));
        }
    };

    public static void stopLoop() {
        isStop = !isStop;
    }

}