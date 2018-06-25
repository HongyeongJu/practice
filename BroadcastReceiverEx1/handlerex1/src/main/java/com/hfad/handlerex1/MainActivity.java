package com.hfad.handlerex1;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

        //스레드 생성하고 시작

        BackThread thread = new BackThread();
        thread.setDaemon(true);
        thread.start();

    }

    public void mOnClick(View v){
        mainValue++;
        mainText.setText("MainValue:"+ mainValue);
    }

    class BackThread extends Thread {
        @Override
        public void run() {
            while(true){
                backValue++;
                //메인에서 생성된 Handler 객체의 sendEmptyMessage 를 통해 Message를 전달
                handler.sendEmptyMessage(0);        // 파라미터는 id
                try {
                    Thread.sleep(1000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }

    //메인스레드에서 Handler 객체를 생성한다.
    // Handler 객체를 생성한 스레드만이 다른 스레드가 전송하는 Mesage나 Runnable 객체를 수신할 수 있다
    //아래 생성된 Handler객체는 handlerMessage()를 오버라이딩 하여
    //Message 를 수신합니다.

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 0) {     //Message id가 0이면
                backText.setText("BackValue: " + backValue);
            }
            super.handleMessage(msg);
        }
    };
}
