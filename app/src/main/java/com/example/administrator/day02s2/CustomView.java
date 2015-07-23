package com.example.administrator.day02s2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by Administrator on 15-7-14.
 */
public class CustomView extends View implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    private Path path = new Path() ;
    private GestureDetector detector;
    private Bitmap bitmap;
    private Matrix matrix = new Matrix();
    private double dis;
    private double degree;


    public CustomView(Context context) {
        super(context);//在java中调用
    }

    public CustomView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);//调用另外一个重载的构造方法
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);//在xml中调用
        detector = new GestureDetector(context ,this);
        //设置双击监听器
        detector.setOnDoubleTapListener(this);
        bitmap = BitmapFactory.decodeResource(context.getResources(),R.mipmap.sample_5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //用黑色清屏
        canvas.drawColor(Color.BLACK);//黑色画布
        canvas.drawBitmap(bitmap,matrix,null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getPointerCount() ==1){
            detector.onTouchEvent(event);
        }else if (event.getPointerCount() == 2){
            switch (event.getActionMasked()){
                //第二根手指落下
                case MotionEvent.ACTION_POINTER_DOWN:
                    //记录两点间的距离
                    dis = getDis(event);

                    degree = getDegree(event);

                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    break;
                case MotionEvent.ACTION_MOVE:
                    double tdis = getDis(event);
                    matrix.postScale((float)(tdis / dis), (float) (tdis/dis),
                            (event.getX(0) + event.getX(1))/2,
                            (event.getY(0) + event.getY(1))/2);
                    dis = tdis;
                    double tdeg = getDegree(event);
                    matrix.postRotate((float) (degree - tdeg),
                            (event.getX(0) + event.getX(1))/2,
                            (event.getY(0) + event.getY(1))/2);
                    degree = tdeg;
                    break;
            }
        }
        invalidate();
        return true;

    }

    private double getDegree(MotionEvent event) {
        double degree =  Math.toDegrees(Math.atan(
                (event.getY(0)-event.getY(1))/
            (event.getX(1)-event.getX(0))));
        if ((event.getX(1) - event.getX(0))<0){
            degree += 180;
        }
        return degree;
    }

    private double getDis(MotionEvent event) {
        dis =  Math.sqrt(Math.pow(event.getX(0)-event.getX(1),2)
        +Math.pow(event.getY(0)-event.getY(1),2));
        return dis;
    }

    /**
     * 按下
     * @param e
     * @return
     */
    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    /**
     * 短按事件,从短按下开始计时,到300毫秒时触发
     * @param e
     */
    @Override
    public void onShowPress(MotionEvent e) {

    }

    /**
     * 单击事件,没有触发短按事件时,抬起时触发
     * @param e
     * @return
     */
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return true;
    }

    /**
     * 滚动事件
     * @param e1
     * @param e2
     * @param distanceX X轴上的位移
     * @param distanceY Y轴上的位移
     * @return
     */
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        matrix.postTranslate(-distanceX,-distanceY);
        invalidate();
        return true;
    }

    /**
     * 长按事件,从长按下开始计时,到2秒时触发
     * @param e
     */
    @Override
    public void onLongPress(MotionEvent e) {

    }

    /**
     * 抛动事件,
     * @param e1
     * @param e2
     * @param velocityX X轴上的速度,像素/秒
     * @param velocityY Y轴上的速度,像素/秒
     * @return
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return true;
    }

    /**
     * 单击完成
     * @param e
     * @return
     */
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return true;
    }

    /**
     * 双击按下
     * @param e
     * @return
     */
    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return true;
    }

    /**
     * 双击事件
     * @param e
     * @return
     */
    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        matrix.postScale(1.2f,1.2f,e.getX(),e.getY());
        invalidate();
        return true;
    }
}
