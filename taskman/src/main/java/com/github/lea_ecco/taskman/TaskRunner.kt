package com.github.lea_ecco.taskman

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executors

object TaskRunner {
    private val executor = Executors.newCachedThreadPool()
    private val handler = Handler(Looper.getMainLooper())

    fun <R> execute(call: TaskCall<R>) {
        executor.execute {
            try {
                if(call.progress != null)
                    call.progressOnUi = {
                        handler.post { call.progress!!.invoke(it) }
                    }
                handler.post { call.start?.invoke() }
                val result = call.doWork()
                handler.post { call.finish?.invoke(result) }
            } catch (ex: Exception) {
                handler.post { call.error?.invoke(ex) }
            }
        }
    }
}