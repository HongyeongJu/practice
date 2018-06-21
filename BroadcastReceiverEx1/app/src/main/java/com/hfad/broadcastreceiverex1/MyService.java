package com.hfad.broadcastreceiverex1;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    final public static int START = 1;
    final public static int OFF = 2;

    MediaPlayer mp;     // 음악 객체

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("test","1");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int what = intent.getIntExtra("what", 0);

        if(what == START){
            mp = MediaPlayer.create(this, R.raw.flash);
        }else if(what == OFF){
            mp = MediaPlayer.create(this, R.raw.eui);
        }
        mp.start();
        return super.onStartCommand(intent, flags, startId);
    }

}
