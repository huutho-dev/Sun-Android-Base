package team.app.base.repository

import kotlinx.coroutines.*
import org.koin.core.KoinComponent

open class BaseRepository : KoinComponent, CoroutineScope {

    override val coroutineContext =
        Dispatchers.Main + SupervisorJob() + CoroutineExceptionHandler { _, throwable -> throwable.printStackTrace() }

    val uiScope = CoroutineScope(Dispatchers.Main) + SupervisorJob() +
            CoroutineExceptionHandler { _, throwable -> throwable.printStackTrace() }

    val bgScope = CoroutineScope(Dispatchers.IO) + SupervisorJob() +
            CoroutineExceptionHandler { _, throwable -> throwable.printStackTrace() }

    fun onCleared() {
        coroutineContext.cancelChildren()
    }
}