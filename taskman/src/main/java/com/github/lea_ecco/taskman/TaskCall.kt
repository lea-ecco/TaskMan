package com.github.lea_ecco.taskman

abstract class TaskCall<E> {
    var progressOnUi: ((Any) -> Unit)? = null
    var progress: ((Any) -> Unit)? = null
    var finish: ((E) -> Unit)? = null
    var start: (() -> Unit)? = null
    var error: ((Exception) -> Unit)? = null

    abstract fun doWork(): E

    fun onProgress(prog: (Any) -> Unit) : TaskCall<E> {
        progress = prog
        return this
    }

    fun onStart(st: () -> Unit) : TaskCall<E> {
        start = st
        return this
    }

    fun onError(er: (Exception) -> Unit) : TaskCall<E> {
        error = er
        return this
    }

    fun execute(fin: ((E) -> Unit)?) : TaskCall<E> {
        finish = fin
        TaskRunner.execute(this)
        return this
    }
}