package com.example.wb_week_6_3.viewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wb_week_6_3.utills.calculatePi
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class PiViewModel : ViewModel() {
    val liveData: MutableLiveData<String> = MutableLiveData()
    private var resultPi = ""
    private var countLengthString = 1
    private var isRunningCalculate = false
    private val maxLengthString = 2147483647
    val viewModelCoroutineScope = CoroutineScope(
        Dispatchers.Main
    )



    fun startCalculatePi() {
        isRunningCalculate = true
        viewModelCoroutineScope.launch {
            getCalculatePi().collect {
                liveData.value = it
            }
        }
    }

    private fun getCalculatePi(): Flow<String> = flow{
        while (isRunningCalculate) {
            isRunningCalculate = countLengthString < maxLengthString
            countLengthString += 1
            resultPi = calculatePi(countLengthString)
            emit(resultPi)
        }
    }.flowOn(Dispatchers.Default)
        .catch { e ->
            println(e.message)
        }

    fun pauseCalculatePi() {
        isRunningCalculate = false
        viewModelCoroutineScope.coroutineContext.cancelChildren()
    }

    fun resetCalculatePi() {
        countLengthString = 0
        resultPi = ""
        if (!isRunningCalculate) {
            startCalculatePi()
        }
    }
}