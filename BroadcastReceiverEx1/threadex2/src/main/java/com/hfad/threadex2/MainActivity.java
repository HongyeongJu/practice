package com.hfad.threadex2;

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
        backText = (TextView)findViewById(R.id.backValue);

        BackRunnable runnable = new BackRunnable();

        Thread thread =new Thread(runnable);
        thread.setDaemon(true);
        thread.start();
    }

    public void mOnClick(View v){
        mainValue++;
        mainText.setText("메인스레드 값: "+ mainValue);
        backText.setText("작업스레드 값: " + backValue);
    }

    class BackRunnable implements Runnable {
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
