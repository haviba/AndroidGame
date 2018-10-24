package com.example.zx.view;

import android.opengl.GLSurfaceView;

import com.example.zx.common.ViewSwitch;
import com.example.zx.util.DrawCylinder;
import com.example.zx.util.TextureRect;

public class RunSurfaceView extends GLSurfaceView {
    private ViewSwitch viewSwitch;
    private boolean overFlag = false;      //是否结束的标志
    private boolean isWin = false;          //是否胜利的标志
    private boolean isFail = false;         //是否失败的标志

    public RunSurfaceView(ViewSwitch viewSwitch){
        super(viewSwitch);
        this.viewSwitch = viewSwitch;
    }
}

/*
创建场景渲染器类
 */
class SceneRender implements GLSurfaceView.Renderer{
    TextureRect trExplo[] = new TextureRect[6];         //爆炸动画纹理矩形
    int anmiIndex = 0;                                  //爆炸动画索引
    DrawCylinder land;                                   //陆地圆柱引用
}