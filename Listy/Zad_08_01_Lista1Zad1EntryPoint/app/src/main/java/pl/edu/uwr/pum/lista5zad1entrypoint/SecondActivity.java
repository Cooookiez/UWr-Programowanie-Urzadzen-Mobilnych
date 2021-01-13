package pl.edu.uwr.pum.lista5zad1entrypoint;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        countScreenTime();
    }

    private void countScreenTime() {
        new Thread(() -> {
            int screenTimeSeconds = 0;
            while (true) {
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