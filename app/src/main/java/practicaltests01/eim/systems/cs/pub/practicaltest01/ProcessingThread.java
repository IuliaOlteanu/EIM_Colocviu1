package practicaltests01.eim.systems.cs.pub.practicaltest01;

import android.content.Context;
import android.content.Intent;

import java.util.Date;
import java.util.Random;

public class ProcessingThread extends Thread{
    private boolean isRunning = true;
    private Context context = null;
    private double media_aritmetica = 0;
    private double media_geometrica = 0;

    public ProcessingThread(Context context, int firstNumber, int secondNumber) {
        this.context = context;
        media_aritmetica = (double) (firstNumber + secondNumber) / 2;
        media_geometrica = Math.sqrt(firstNumber * secondNumber);
    }

    @Override
    public void run() {
        while (isRunning) {
            sendMessage();
            sleep();
        }
    }

    private void sendMessage() {
        Intent intent = new Intent();
        intent.setAction(Constants.actionTypes[new Random().nextInt(Constants.actionTypes.length)]);

        intent.putExtra("message", new Date(System.currentTimeMillis()) + " " + media_aritmetica + " " + media_geometrica);

        context.sendBroadcast(intent);
    }

    private void sleep() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public void stopThread() {
        isRunning = false;
    }
}
