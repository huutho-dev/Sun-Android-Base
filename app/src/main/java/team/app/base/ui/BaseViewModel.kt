package team.app.base.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import org.koin.core.KoinComponent

abstract class BaseViewModel : ViewModel(), KoinComponent, CoroutineScope {

    open val loadingLiveData = MutableLiveData<Boolean>().apply { value = false }

}