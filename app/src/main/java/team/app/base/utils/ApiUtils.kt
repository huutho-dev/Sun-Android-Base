package team.app.base.utils

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import team.app.base.data.DataResult
import team.app.base.data.Status

fun <T> CoroutineScope.call(resultLiveData: MutableLiveData<DataResult<T>>, execute: () -> T) {
    launch(Dispatchers.Main) {
        resultLiveData.value = DataResult.loading()
        try {
            withContext(Dispatchers.IO) {
                resultLiveData.value = DataResult.success(execute.invoke())
            }
        } catch (ex: Throwable) {
            ex.printStackTrace()
            resultLiveData.value = DataResult.error(ex)
        }
        resultLiveData.value = DataResult.complete()
    }
}

fun <T> CoroutineScope.call(
    onStartLoading: () -> Unit,
    onSuccess: (data: T) -> Unit,
    onError: (ex: Throwable) -> Unit,
    onComplete: () -> Unit,
    execute: () -> T
) {
    launch(Dispatchers.Main) {
        onStartLoading.invoke()
        try {
            withContext(Dispatchers.IO) {
                onSuccess.invoke(execute.invoke())
            }
        } catch (ex: Throwable) {
            ex.printStackTrace()
            onError.invoke(ex)
        }
        onComplete.invoke()
    }
}


fun <T> handleResponse(
    dataResult: DataResult<T>,
    onStartLoading: () -> Unit,
    onSuccess: (data: T?) -> Unit,
    onError: (ex: Throwable?) -> Unit,
    onComplete: () -> Unit
) {
    when (dataResult.status) {
        Status.LOADING -> onStartLoading.invoke()
        Status.SUCCESS -> onSuccess.invoke(dataResult.data)
        Status.ERROR -> onError.invoke(dataResult.error)
        Status.COMPLETE -> onComplete.invoke()
    }
}