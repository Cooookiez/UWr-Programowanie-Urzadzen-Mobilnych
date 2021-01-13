package pl.edu.uwr.pum.lista5zad1entrypoint;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class SecondActivity extends AppCompatActivity {

    private AtomicBoolean bRun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        bRun = new AtomicBoolean(true);
        countScreenTime();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bRun.set(false);
    }

    private void countScreenTime() {
        new Thread(() -> {
            int screenTimeSeconds = 0;
            while (bRun.get()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                screenTimeSeconds++;
                Log.d("LISTA5ZAD1", "czas: " + screenTimeSeconds + " s");
            }
        }).start();
    }
}