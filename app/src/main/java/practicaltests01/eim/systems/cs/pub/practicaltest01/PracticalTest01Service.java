package practicaltests01.eim.systems.cs.pub.practicaltest01;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.security.Provider;

public class PracticalTest01Service extends Service {
    private ProcessingThread processingThread = null;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int firstNumber = intent.getIntExtra("edit_left_text", -1);
        int secondNumber = intent.getIntExtra("edit_right_text", -1);

        processingThread = new ProcessingThread(this, firstNumber, secondNumber);
        processingThread.start();

        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        processingThread.stopThread();
    }
}
