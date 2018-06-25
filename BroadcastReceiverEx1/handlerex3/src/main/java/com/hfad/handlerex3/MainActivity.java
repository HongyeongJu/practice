package com.hfad.handlerex3;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/*
작업스레드가 메인스레드와 완전히 분리되어 있어서 메인스레드에서 생성한 핸들러를 작업스레드에서
직접 참조 할수가 없을 때 Message 생성자 함수로 메시지를 생성하여 보내주면 된다.
 */
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

        BackThread thread = new BackThread(handler);
        thread.setDaemon(true);
        thread.start();
    }

    public void mOnClick(View v){
        mainValue++;
        mainText.setText("MainValue:" + mainValue);
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 0){
                //메시지를 통해 받은 값을 BackValue로 출력
                backText.setText("BackValue:" +msg.arg1);
            }
        }
    };

    class BackThread extends Thread {
        int backValue = 0;
        Handler handler;

        BackThread(Handler handler){
            this.handler = handler;
        }

        @Override
        public void run() {
            while(true){
                backValue++;

                Message msg = new Message();
                msg.what = 0;
                msg.arg1 = backValue;
                handler.sendMessage(msg);       //메인스레드의 핸들러에게 메시지를 보내기

                try{
                    Thread.sleep(1000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
