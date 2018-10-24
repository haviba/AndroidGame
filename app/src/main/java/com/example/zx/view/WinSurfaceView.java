package com.example.zx.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import com.example.zx.R;
import com.example.zx.common.ViewSwitch;

public class WinSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private ViewSwitch viewSwitch;
    private int screenWidth = 480;               //屏幕宽度
    private int screenHeight = 320;              //屏幕高度
    private int picWidth = 112;                   //返回按钮图片宽度
    private int picHeight = 40;                   //返回按钮图片高度
    private Bitmap bgWin;                        //背景图片
    private Bitmap gameBack;                       //返回按钮图片

    public WinSurfaceView(ViewSwitch viewSwitch) {
        super(viewSwitch);
        this.viewSwitch = viewSwitch;
        initBitmap();                                 //加载图片
        this.getHolder().addCallback(this);          //设置回调方法
    }

    public void initBitmap() {
        //加载背景图片
        bgWin = BitmapFactory.decodeResource(getResources(), R.mipmap.winbg);
        //加载返回按钮图片
        gameBack = BitmapFactory.decodeResource(getResources(), R.mipmap.back);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawBitmap(bgWin, 0, 0, null);        //画背景图片
        //画返回按钮图片
        canvas.drawBitmap(gameBack, (screenWidth-(1*picWidth)), screenHeight-2*picHeight,null);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Canvas canvas = null;                        //画布
        try {
            canvas = holder.lockCanvas();             //画布加锁
            synchronized (holder){
                draw(canvas);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(canvas != null){
                holder.unlockCanvasAndPost(canvas);    //绘制后解锁
            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    //触发事件回调方法
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int)event.getX();
        int y = (int)event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(x<(screenWidth-(1*picWidth))+picWidth&&x>(screenWidth-(1*picWidth))
                        &&y<screenHeight-2*picHeight+picHeight&&y>screenHeight-2*picHeight){

                }
                break;
        }
        return  super.onTouchEvent(event);                //其他情况交给系统处理
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                return true;
        }
        return false;
    }
}
