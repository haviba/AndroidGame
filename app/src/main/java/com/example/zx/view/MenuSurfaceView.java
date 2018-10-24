package com.example.zx.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.zx.R;
import com.example.zx.common.ViewSwitch;

public class MenuSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private ViewSwitch viewSwitch;
    private Paint paint;                             //画笔
    private Bitmap[] menu = new Bitmap[5];          //菜单项图片数组
    private Bitmap backgroud;                      //背景图片

    private int currentIndex = 2;                 //当前选中的菜单编号
    private float mPreviousX;                     //上次触控的x坐标
    private float mPreviousY;                     //上次触控的Y坐标
    private float changePercent = 0;              //动画进行的百分比
    private int anmiState = 0;                     //0：没有动画 1：向右走  2：向左走

    private int currentSelectWidth;              //当前菜单项宽度
    private int currentSelectHeight;             //当前菜单项高度
    private float currentSelectX;                //当前菜单项的X位置
    private float currentSelectY;                //当前菜单项Y位置

    private float leftWidth;                      //紧邻当前菜单项左侧菜单项的宽度
    private float leftHeight;                     //紧邻当前菜单项左侧菜单项的高度
    private float tempxLeft;                      //紧邻当前菜单项左侧菜单项的X坐标
    private float tempyLeft;                      //紧邻当前菜单项左侧菜单项的Y坐标

    private float rightWidth;                      //紧邻当前菜单项右侧菜单项的宽度
    private float rightHeight;                     //紧邻当前菜单项右侧菜单项的高度
    private float tempxRight;                      //紧邻当前菜单项右侧菜单项的X坐标
    private float tempyRight;                      //紧邻当前菜单项右侧菜单项的Y坐标

    private final int ABOUT_VIEW = 0;              //关于
    private final int OPTION_VIEW = 1;             //设置
    private final int KSMS_VIEW = 2;               //快速模式
    private final int HRLP_VIEW = 3;               //帮助
    private final int EXIT_VIEW = 4;               //退出

    //以下变常量用于菜单动画界面
    static int screenWidth = 480;            //屏幕宽度
    static int screenHeight = 320;           //屏幕高度
    static int bigWidth = 130;               //选中菜单项的宽度
    static int bigHeight = 130;              //选中菜单项的高度
    static int smallWidth = 80;              //未选中菜单项的宽度
    static int smallHeight = (int)(((float)smallWidth/bigWidth)*bigHeight);   //未选中菜单项的高度

    static int selectX = screenWidth/2 - bigHeight/2;  //选中菜单项左侧在屏幕上的X位置
    static int selectY = screenHeight/2 - 80;          //选中菜单项上侧在屏幕上的Y位置
    static int span = 10;                    //菜单项之间的间距
    static int slideSpan = 30;               //滑动的阈值

    static int totalSteps = 10;              //动画的总步数
    static float percentStep = 1.0f/totalSteps;     //每一步的动画百分比
    static int timeSpan = 20;                       //每一步动画的间隔时间；

    public MenuSurfaceView(ViewSwitch viewSwitch) {
        super(viewSwitch);
        this.viewSwitch = viewSwitch;
        this.getHolder().addCallback(this);             //设置生命周期回调接口的实现者
        paint = new Paint();                            //创建画笔
        paint.setAntiAlias(true);                       //打开抗锯齿
        initBitmap();                                    //初始化图片
        init();                                          //初始化当前及紧靠其左右的菜单项的位置大小参数
    }

    public void initBitmap() {
        //初始化图片
        menu[0] = BitmapFactory.decodeResource(viewSwitch.getResources(), R.mipmap.menu1);
        menu[1] = BitmapFactory.decodeResource(viewSwitch.getResources(), R.mipmap.menu2);
        menu[2] = BitmapFactory.decodeResource(viewSwitch.getResources(), R.mipmap.menu3);
        menu[3] = BitmapFactory.decodeResource(viewSwitch.getResources(), R.mipmap.menu4);
        menu[4] = BitmapFactory.decodeResource(viewSwitch.getResources(), R.mipmap.menu5);
        backgroud = BitmapFactory.decodeResource(viewSwitch.getResources(), R.mipmap.background2) ;
    }

    /*
    初始化当前及紧靠其左右的菜单项的位置大小参数
     */
    public void init() {
        currentSelectWidth = bigWidth;          //当前选中菜单的宽度
        currentSelectHeight = bigHeight;        //当前选中菜单的高度
        currentSelectX = selectX;                //当前选中菜单X位置
        currentSelectY = selectY;                //当前选中菜单Y位置
        rightWidth = smallWidth;                  //紧邻右侧的宽度
        leftWidth = smallWidth;                   //紧邻左侧的宽度
        rightHeight = smallHeight;                //紧邻右侧的高度
        leftHeight = smallHeight;                 //紧邻左侧的高度
        tempxLeft = currentSelectX - (span + leftWidth);                     //紧邻左侧的X
        tempyLeft = currentSelectY + (currentSelectHeight - leftHeight);  //紧邻左侧的Y坐标
        tempxRight = currentSelectX + (span + currentSelectWidth);          //紧邻右侧的X
        tempyRight = currentSelectY + (currentSelectHeight - rightHeight);//紧邻右侧的Y坐标
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawBitmap(backgroud, 0, 0, paint);      //绘制背景
        Bitmap selectBM = menu[currentIndex];                      //获取当前菜单项图片
        //根据参数计算出用于绘制当前菜单项的图片
        selectBM = Bitmap.createScaledBitmap(selectBM, currentSelectWidth, currentSelectHeight, false);

        //绘制当前的菜单项
        canvas.drawBitmap(selectBM, currentSelectX, currentSelectY, paint);
        //若当前菜单项不是第一项则绘制紧邻当前菜单项左侧的菜单项
        if(currentIndex > 0) {
            //缩放出绘制用图片
            Bitmap left = Bitmap.createScaledBitmap(
                    menu[currentIndex - 1],
                    (int)leftWidth,
                    (int)leftHeight,
                    false
            );
            //绘制图片
            canvas.drawBitmap(left, tempxLeft, tempyLeft, paint);
        }

        //若当前菜单项不是最后一项则绘制紧邻当前菜单项右侧的菜单项
        if(currentIndex < menu.length - 1) {
            //缩放出绘制用图片
            Bitmap right = Bitmap.createScaledBitmap(menu[currentIndex + 1], (int)rightWidth, (int)rightHeight, false);
            canvas.drawBitmap(right, tempxRight, tempyRight, paint);
        }

        //向左绘制其他未选中的菜单
        for(int i = currentIndex -2; i>=0; i--){
            float tempx = tempxLeft - (span + smallWidth) * (currentIndex -1 -i);  //计算X值
            //若绘制出来不在屏幕上则不用绘制
            if(tempx < -smallWidth) {
                break;
            }
            int tempy = selectY + (bigHeight - smallHeight);                           //计算Y值
            //缩放出绘制用图片
            Bitmap tempbm = Bitmap.createScaledBitmap(menu[i], smallWidth, smallHeight, false);
            canvas.drawBitmap(tempbm, tempx, tempy, paint);
        }

        //向右绘制其他未选中的菜单
        for(int i = currentIndex + 2; i < menu.length; i++){
            float tempx = tempxRight + rightWidth + span + (span + smallWidth)*(i - (currentIndex + 1) - 1); //计算X值
            //若绘制出来不在屏幕上则不用绘制
            if(tempx > screenWidth){
                break;
            }

            int tempy = selectY + (bigHeight - smallHeight);     //计算Y值
            Bitmap tempbm = Bitmap.createScaledBitmap(menu[i], smallWidth, smallHeight, false);
            canvas.drawBitmap(tempbm, tempx, tempy, paint);
        }
    }

    public void repaint(){
        SurfaceHolder holder = this.getHolder();              //SurfaceHolder
        Canvas canvas = holder.lockCanvas();                   //获取画布
        try {
            synchronized (holder){
                draw(canvas);                                  //绘制
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(canvas != null){
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }

    //触发事件回调方法
    @Override
    public boolean onTouchEvent(MotionEvent e){
        //若动画播放中则触控无效
        if(anmiState != 0) {
            return  true;
        }
        //获取当前触控点的XY坐标
        float x = e.getX();
        float y = e.getY();

        //根据触控的不同动作执行不同的业务逻辑
        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_DOWN:
                //若动作为触控则记录XY位置
                mPreviousX = x; //记录触控的x位置
                mPreviousY = y; //记录触控的y位置
                break;
            case MotionEvent.ACTION_UP:
                //若动作为抬起则根据X位移的不同执行左滑、右滑后选中菜单项的业务逻辑
                float dx = x - mPreviousX;   //计算X位移
                if(dx < -slideSpan){
                    //若x位移小于阈值则向左滑动
                    if(currentIndex < menu.length - 1){
                        //若当前菜单项不是最后一个菜单项则向左滑动
                        //计算滑动完成后的当前菜单项编号
                        int afterCurrentIndex  = currentIndex + 1;
                        //动画状态设置为2-向左走
                        anmiState = 2;
                        //启动线程播放动画并更新状态值

                    }
                }else if(dx > slideSpan){
                    //若X位移大于阈值则向右滑动
                }else{

                }
                break;
        }
        return true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        repaint();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
