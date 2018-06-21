package com.hfad.customviewex1;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MyProgress extends View {

    Context context;
    int progressColor;
    int tabsImageRef;
    int progressmax;
    Bitmap tabImage;
    Rect tabImageSrc;
    Rect rectDec;


    int width;
    int height;
    int value = 50;

    DisplayMetrics m;

    public MyProgress(Context context) {
        super(context);
        this.context = context;
        init(null);
    }

    public MyProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs);
    }

    public MyProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(attrs);
    }

    private void init(AttributeSet attrs){
        //만약 커스텀 속성xml 값이 있다면
        if(attrs !=null){
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.myprogress);
            progressColor = a.getColor(R.styleable.myprogress_progressColor, Color.RED);
            progressmax = a.getInteger(R.styleable.myprogress_progressmax, 100);
            tabImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.heart);
        }
        tabImageSrc = new Rect(0,0, tabImage.getWidth(), tabImage.getHeight());

        value = 150;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.alpha(Color.CYAN));      //초기화

        // 첫번째 프로그레스
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.GRAY);
        paint.setStrokeWidth(10* m.density);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setAlpha(70);
        RectF rectf = new RectF((10* m.density), (10* m.density), width- (10* m.density), height - (10 * m.density));
        canvas.drawArc(rectf, 130, 280, false, paint);

        // 두번째 프로그레스
        Paint paint1 = new Paint();
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setStrokeWidth(10* m.density);
        paint1.setStrokeCap(Paint.Cap.ROUND);
        paint1.setColor(progressColor);
        float ratio = (value / (float)progressmax);     // max값과 value값의 비율 즉 1:100의 상황이랑 같게 생각하면된다.
        float improveAngle = ratio*150;                        // 각도의 차와 비율을 곱해서 증가폭을 구한다.
        canvas.drawArc(rectf, 130, improveAngle , false, paint1);
        Log.d("test", "improveAngle: "+improveAngle);

        Log.d("test 3" ,"ang: "+ (230 - improveAngle));
        // 탭 좌표 구하기
        double rad = Math.toRadians(230 - (double)improveAngle);       // 각도를 라디안 값으로 바꾼다.\ (360-130) - improveAngle (각도의 방향이 수학이랑 안드로이드랑 다르기 때문)
        Log.d("test1", "rad:" + rad);
        int tabsX = (int)(((float)width / 2) + (width/2 - 10*m.density ) *(float)Math.cos(rad));        //중심점 값과 cos, sin 값을 더해서 절대 좌표를 얻는다.
        int tabsY = (int)(((float)height / 2) - (width/2 - 10*m.density) * (float)Math.sin(rad));
        Log.d("test2: sin", String.valueOf(Math.sin(rad)));
        rectDec = new Rect( tabsX -tabImage.getWidth() / 2, tabsY - tabImage.getHeight() /2, tabsX +tabImage.getWidth() / 2, tabsY + tabImage.getHeight() /2);
        canvas.drawBitmap(tabImage, tabImageSrc, rectDec, null);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        m = context.getResources().getDisplayMetrics();         // DisplayMetric객체를 얻고 논리적 크기를 얻는다.

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if(widthMode == MeasureSpec.AT_MOST){       // wrap_content
            width = (int)(m.density * 200);         // 200 dp
        }else if(heightMode == MeasureSpec.EXACTLY){        //match나 숫자
            width = (int)(widthSize * m.density);
        }

        if(heightMode ==MeasureSpec.AT_MOST){
            height = (int)(m.density * 200);
        }else if(heightMode == MeasureSpec.EXACTLY){
            height = (int)(m.density * heightSize);
        }

        setMeasuredDimension(width, height);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        // 탭을 클릭했으면
        if(rectDec.contains((int)x, (int)y) && event.getAction() ==MotionEvent.ACTION_MOVE){
            double sin = -(y - height/2) /(width /2 - 10 * m.density);       // sin값 구하기
            double cos = (x - width/2) / (width/ 2 - 10 * m.density);        // cos값 구하기
            double rad =0;
            /*
            // 구한 sin cos으로 사분면 구하기
            if(sin > 0 && cos >0){  // 1
                rad = Math.asin(sin);                                    // rad 각도 구하기
            }else if(sin >0 && cos <0){//2
                rad = Math.asin(sin);                                    // rad 각도 구하기
            }else if(sin < 0 && cos <0){    //3
                rad = Math.acos(cos);
            }else{      //4
                rad = Math.acos(cos);
            }*/
            double ang =0;
            double improveAngle = 0;
            /*
            if(value <80){
                rad = Math.asin(sin);
                Log.d("touch:rad", String.valueOf(rad));
                ang = Math.toDegrees(rad);
                Log.d("touch:ang", String.valueOf(180-ang));
                improveAngle = 230 - (180-ang);
            } else if (value < 200) {
                rad = Math.acos(cos);
                Log.d("touch:rad", String.valueOf(rad));
                ang = Math.toDegrees(rad);
                Log.d("touch:ang", String.valueOf(ang));
                improveAngle = 50 +ang;
            }
            */

            Log.d("touch:improve : ",String.valueOf(improveAngle));

            value = (int)((improveAngle*progressmax)/150);
            Log.d("touch: value" , String.valueOf(value));
            invalidate();
            return true;
        }
        return true;
    }
}
