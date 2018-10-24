package com.example.zx.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import com.example.zx.R;
import com.example.zx.common.ViewSwitch;

public class LoadSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private ViewSwitch viewSwitch;          //activity的引用
    private int screenWidth = 480;         //屏幕宽度
    private int screenHeight = 320;        //屏幕高度
    private int picWidth = 112;             //返回按钮图片宽度
    private int picHeight = 40;             //返回按钮图片高度
    private Bitmap bgAbout;                  //背景图片

    public LoadSurfaceView(ViewSwitch viewSwitch){
        super(viewSwitch);
        this.viewSwitch = viewSwitch;
        initBitmap();                          //加载图片
        this.getHolder().addCallback(this);   //设置回调方法
    }

    public void initBitmap(){
        //加载背景图片
        bgAbout = BitmapFactory.decodeResource(getResources(), R.mipmap.loadbg);
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        canvas.drawBitmap(bgAbout, 0, 0, null);    //绘制背景图片
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Canvas canvas = null;          //画布
        try{
            canvas = holder.lockCanvas();            //画图之前先锁定画布
            synchronized (holder){
                draw(canvas);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(canvas != null){
                holder.unlockCanvasAndPost(canvas);
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
    public boolean onKeyDown(int keyCode, KeyEvent event){
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                return true;
        }
        return false;               //false,其他键交给系统处理
    }
}
