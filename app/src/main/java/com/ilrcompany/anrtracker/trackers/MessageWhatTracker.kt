package com.ilrcompany.anrtracker.trackers

import android.os.Handler
import android.os.Message
import android.util.Log

class MessageWhatTracker(private val mainHandler: Handler): TrackerVariant {

    companion object {
        private const val WHAT = 1999
    }

    @Volatile
    private var needStop = false

    override fun initANRTracker() {
        needStop = false
        Thread {
            while (needStop.not()) {

                val message = Message.obtain()
                message.what = WHAT

                mainHandler.sendMessage(message)

                Thread.sleep(2000)
                if (mainHandler.hasMessages(WHAT)) {
                    Log.d("TEST", "ANR!")
                }
            }

        }.start()
    }

    override fun stop() {
        needStop = true
    }
}