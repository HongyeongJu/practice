package com.hfad.threadex1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    int mainValue = 0;
    int backValue = 0;
    TextView mainText;
    TextView backText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainText = (TextView)findViewById(R.id.mainvalue);
        backText = (TextView)findViewById(R.id.backvalue);

        //일반적인 자바 프로그래밍에서는 메인 스레드가 종료되면, 작업 스레드가 잘 종료가됨
        //안드로이드 액티비티에선 메인 스레드가 종료되도 작업스레드가 종료되지 않는 경우가 있음
        // 그래서 setDaemon(true) 메소드를 통해 메인 스레드와 종료 동기화 시킴
        BackThread thread = new BackThread();
        thread.setDaemon(true);
        thread.start();



    }

    public void mOnClick(View v){
        mainValue++;
        mainText.setText("메인스레드 값:" +mainValue);
        backText.setText("작업스레드 값:"+ backValue);
    }

    class BackThread extends Thread{
        @Override
        public void run() {
            while(true){
                backValue++;
                try{
                    Thread.sleep(1000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
