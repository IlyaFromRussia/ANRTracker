package com.ilrcompany.anrtracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.MessageQueue
import android.widget.Button
import com.ilrcompany.anrtracker.trackers.IdleHandlerTracker
import com.ilrcompany.anrtracker.trackers.MessageWhatTracker
import com.ilrcompany.anrtracker.trackers.TrackerVariant

class MainActivity : AppCompatActivity() {

    private val mainHandler by lazy {
        Handler(mainLooper)
    }

    private var tracker: TrackerVariant? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnStart).setOnClickListener {
            Thread.sleep(3000)
        }

        findViewById<Button>(R.id.btnWhatTracker).setOnClickListener {
            tracker?.stop()
            tracker = MessageWhatTracker(mainHandler)
            tracker?.initANRTracker()
        }

        findViewById<Button>(R.id.btnIdleHandlerTracker).setOnClickListener {
            tracker?.stop()
            tracker = IdleHandlerTracker(Looper.myQueue())
            tracker?.initANRTracker()
        }

        findViewById<Button>(R.id.btnStop).setOnClickListener {
            tracker?.stop()
            tracker = null
        }

    }
}