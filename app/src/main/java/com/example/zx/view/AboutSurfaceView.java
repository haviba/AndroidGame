package com.example.zx.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.view.View;

import com.example.zx.R;
import com.example.zx.common.ViewSwitch;

public class AboutSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private ViewSwitch viewSwitch;
    private int screenWidth = 480;                 //屏幕宽度
    private int screenHeight = 320;                //屏幕高度
    private int picWidth = 112;                     //返回按钮图片宽度
    private int picHeight = 40;                     //返回按钮图片高度
    private Bitmap bgAbout;                          //背景图片
    private Bitmap gameBack;                         //返回按钮图片

    public AboutSurfaceView(ViewSwitch viewSwitch){
        super(viewSwitch);
        this.viewSwitch = viewSwitch;
        initBitmap();
        this.getHolder().addCallback(this);
    }

    public void initBitmap(){
        //加载背景图片
        bgAbout = BitmapFactory.decodeResource(getResources(), R.mipmap.aboutbg);
        //加载返回按钮
        gameBack = BitmapFactory.decodeResource(getResources(), R.mipmap.back);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawBitmap(bgAbout, 0, 0, null);    //画背景图片
        //画返回按钮图片
        canvas.drawBitmap(gameBack, (screenWidth- (1 * picWidth)), screenHeight - 2 * picHeight, null);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Canvas canvas = null;
        try {
            canvas = holder.lockCanvas();        //画图之前先锁定画布
            synchronized (holder) {
                draw(canvas);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(canvas != null){
                holder.unlockCanvasAndPost(canvas);      //画图完成后给画布解锁
            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int)event.getX();         //触点x坐标
        int y = (int)event.getY();         //触点y坐标
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:          //按下时
                if(x < (screenWidth - (1 * picWidth)) + picWidth && x > (screenWidth - 1 * picWidth)
                        && y < screenHeight - 2 * picHeight + picHeight && y > screenHeight - 2 * picHeight){

                }
                break;
        }
        return super.onTouchEvent(event);          //其他情况交给系统处理
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:              //如果按下返回键
                return true;
        }
        return false;
    }
}
