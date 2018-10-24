package com.example.zx.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.zx.R;
import com.example.zx.common.ViewSwitch;

public class SoundSurfaceView extends SurfaceView implements SurfaceHolder.Callback{
    private ViewSwitch viewSwitch;     //界面切换Activity引用
    private Bitmap background;         //背景图片

    private int screenWidth = 480;              //屏幕宽度
    private int screenHeight = 320;             //屏幕高度

    public SoundSurfaceView(ViewSwitch viewSwitch){
        super(viewSwitch);
        this.viewSwitch = viewSwitch;
        initBitmap();
        this.getHolder().addCallback(this);
    }

    /*
    初始化图片加载
     */
    public void initBitmap(){
        background = BitmapFactory.decodeResource(getResources(), R.mipmap.soundbg);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawBitmap(background, 0, 0, null);//绘制背景
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();   //获得屏幕被触控点的x坐标
        int y = (int) event.getY();   //获得屏幕被触控点的y坐标
        switch ((event.getAction())){
            case MotionEvent.ACTION_DOWN:
                if(x < 32 && x > 0 && y < screenHeight && y > screenHeight - 32){

                }
                else if(x < screenWidth && x > screenWidth - 32 && y < screenHeight && y > screenHeight -32){

                }
                break;
        }
        return  true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Canvas canvas = null;      //画布
        try {
            canvas = holder.lockCanvas(); //画布加锁
            synchronized (holder){
                draw(canvas);          //重绘
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(canvas != null){
                holder.unlockCanvasAndPost(canvas);  //绘制后解锁
            }
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                return true;
            case KeyEvent.KEYCODE_DPAD_CENTER:
                return true;
        }
        return false;
    }
}
