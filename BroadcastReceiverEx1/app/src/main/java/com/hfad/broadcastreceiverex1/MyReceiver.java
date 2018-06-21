package com.hfad.broadcastreceiverex1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        String action = intent.getAction();         // 액션 값을 받고

        Log.d("Hi","Hi");
        Intent intent1 = new Intent(context, MyService.class);      // Service.class를 실행하는 인텐트
        intent1.putExtra("what", MyService.START);
        if(action.equals("android.intent.action.BOOT_COMPLETED")){      //부팅 했을때
            intent1.putExtra("what", MyService.START);
        }else if(action.equals("android.intent.action.ACTION_SHUTDOWN")){       // 꺼졌을때
            intent1.putExtra("what", MyService.OFF);
        }

        // 이 조건을 줘야 오류가 나지 않습니다.
        // O버전 이후로는 서비스를 ForegroundService로 주어야됨
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            context.startForegroundService(intent1);
        }else {// 그 이후에는 startService로 줘도 됨
            context.startService(intent1);
        }

    }
}
