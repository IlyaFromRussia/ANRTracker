package com.ilrcompany.anrtracker.trackers

import android.os.MessageQueue
import android.util.Log

/**
 * Подходит только для экранов с постоянно изменяющимся UI
 */
class IdleHandlerTracker(private val queue: MessageQueue): TrackerVariant{

    @Volatile
    private var needStop = false
    @Volatile
    private var anrTrigger = ::nothing

    override fun initANRTracker() {
        Thread {
            while (needStop.not()) {

                // Handler сработает до обработки всех Message и после того, как будет обработан последний Message.
                // Если Messages нет, то хендлеры будут складываться в массив
                // и вы́полняться после обработки последнего Message.
                val idleHandler = MessageQueue.IdleHandler {
                    Log.d("TEST", "Handler! is idle = ${queue.isIdle}")
                    anrTrigger = ::nothing
                    false
                }

                anrTrigger = ::showANR
                queue.addIdleHandler(idleHandler)
                Thread.sleep(2000)
                anrTrigger.invoke()
            }
        }.start()
    }

    override fun stop() {
        needStop = true
    }

    private fun showANR() {
        Log.d("TEST", "ANR! is idle = ${queue.isIdle}")
    }

    private fun nothing() {}
}