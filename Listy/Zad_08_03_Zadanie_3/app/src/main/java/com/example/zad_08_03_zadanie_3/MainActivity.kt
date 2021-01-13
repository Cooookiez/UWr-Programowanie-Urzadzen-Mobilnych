package com.example.zad_08_03_zadanie_3

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.math.BigInteger
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger

class MainActivity : AppCompatActivity() {

    private companion object {
        val MAX_TIMEOUT: Int = 500
        val LOCK: Object = Object()
        val nNumberOfFinishedThreads: AtomicInteger = AtomicInteger(0)
    }

    private val mUiHandler = Handler(Looper.getMainLooper())

    private lateinit var usrInputEditText: EditText
    private lateinit var computeBtn: Button
    private lateinit var resultTextView: TextView
    
    private val mAbortComputation: AtomicBoolean = AtomicBoolean(false)
    private var mComputationTimeout: Long = 0
    private var mNumberOfThreads: Int = 0
    @Volatile
    private var mThreadsResults = ArrayList<BigInteger>(0)
    private var mThreads = ArrayList<Thread>(0)

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        usrInputEditText = findViewById(R.id.usrInputEditText)
        computeBtn = findViewById(R.id.computeBtn)
        resultTextView = findViewById(R.id.resultTextView)
        mThreads.clear()

        computeBtn.setOnClickListener {
            val sFactor: String = usrInputEditText.text.toString().trim()
            if (!sFactor.isEmpty()) {
                computeBtn.isEnabled = false
                computeBtn.text = "Counting"
                val iFactor: Int = sFactor.toInt()
                countFactor(iFactor, MAX_TIMEOUT)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        mAbortComputation.set(false)
    }

    private fun countFactor(factor_of: Int, timeout: Int) {
        Log.d("CountFactor", "Start method")
        Thread{
            initParams(factor_of, timeout)
            startComputation(factor_of)
            waitForCompletionOrAbort()
            processResults()
        }.start()

    }

    private fun initParams(factor_of: Int, timeout: Int) {
        Log.d("CountFactor", Runtime.getRuntime().availableProcessors().toString())
        mNumberOfThreads = if (factor_of < 20) {
            1
        } else {
            Runtime.getRuntime().availableProcessors()
        }
        nNumberOfFinishedThreads.set(0)
        mAbortComputation.set(false)
        mThreadsResults.clear()
        for (i in 0 until mNumberOfThreads) {
            mThreadsResults.add(1.toBigInteger())
        }
        mComputationTimeout = System.currentTimeMillis() + timeout
    }

    private fun startComputation(factor_of: Int) {
        for (i in 0 until mNumberOfThreads) {
            mThreads.add(Thread {
                for (j in (1 + i)..(factor_of) step mNumberOfThreads) {
//                    if (i == 0)
//                        Log.d("CountFactor ||", "W(${i.toString()})\tP(${j})")
                    mThreadsResults[i] = mThreadsResults[i].multiply(j.toBigInteger())
                }
                nNumberOfFinishedThreads.getAndIncrement()
                synchronized(LOCK) {
                    LOCK.notifyAll()
                }
            })
            mThreads.last().start()
        }
    }

    private fun waitForCompletionOrAbort() {
        synchronized(LOCK) {
            while (nNumberOfFinishedThreads.get() != mNumberOfThreads &&
                    !mAbortComputation.get() && !isTimeout()) {
                try {
                    LOCK.wait(maxTimeout())
                } catch (e: InterruptedException) {
                    return
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun processResults() {
        var result: String = when {
            mAbortComputation.get() -> {
                "ABORTED"
            }
            isTimeout() -> {
                "TIMEOUT"
            }
            else -> {
                computeFinalResult().toString()
            }
        }
        val finalResult: String = result
        Log.d("CountFactor", "To Jest już wynik")
        Log.d("CountFactor", "A wynik to ...")
        Log.d("CountFactor", finalResult)
        mUiHandler.post {
            computeBtn.text = "Count Again"
            computeBtn.isEnabled = true
            resultTextView.text = finalResult
        }
    }

    private fun computeFinalResult(): BigInteger {
        var result: BigInteger = 1.toBigInteger()
        for (i in 0 until mNumberOfThreads) {
            if(isTimeout()) { break }
            Log.d("CountFactor _$i", mThreadsResults[i].toString())
            try {
                mThreads.get(i).join()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            result = result.multiply(mThreadsResults[i])
        }
        return result
    }

    private fun maxTimeout(): Long {
        return mComputationTimeout - System.currentTimeMillis()
    }

    private fun isTimeout(): Boolean {
        return System.currentTimeMillis() >= mComputationTimeout
    }

}