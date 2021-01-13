package com.example.zad_08_01_lista1zad1entrypoint

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.atomic.AtomicBoolean

class SecondActivity : AppCompatActivity() {

    private lateinit var bRun: AtomicBoolean

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        bRun = AtomicBoolean(true)
        countScreenTime()
    }

    override fun onDestroy() {
        super.onDestroy()
        bRun.set(false)
    }

    override fun onPause() {
        super.onPause()
        bRun.set(false)
    }

    private fun countScreenTime() {
        Thread {
            var screenTimeSeconds: Int = 0
            while (bRun.get()) {
                try {
                    Thread.sleep(1000)
                    // nie jestem pewien czy w kotlinie
                    // musi być try/catch
                    // bo jak w javie mi podkreślało
                    // i automatycznie tworzyło
                    // to w kotlinie nic mi nie mówiło
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                screenTimeSeconds++
                Log.d("LISTA5ZAD1", "czas: $screenTimeSeconds s")
            }
        }.start()
    }

//    private void countScreenTime()
//    {
//        Thread {
//            var screenTimeSeconds = 0
//            while (bRun.get()) {
//                try {
//                    Thread.sleep(1000)
//                } catch (e: InterruptedException) {
//                    e.printStackTrace()
//                }
//                screenTimeSeconds++
//                Log.d("LISTA5ZAD1", "czas: $screenTimeSeconds s")
//            }
//        }.start()

}