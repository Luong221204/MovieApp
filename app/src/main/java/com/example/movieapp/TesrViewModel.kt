package com.example.movieapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class TesrViewModel():ViewModel(){
    private val _eventFlow = MutableSharedFlow<String>(
        replay = 0,
        extraBufferCapacity = 2,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val eventFlow = _eventFlow.asSharedFlow()

    private val _stateFlow = MutableStateFlow<String>("")
    val stateFlow :StateFlow<String> = _stateFlow
    fun triggerSnackbar() {
        viewModelScope.launch {
            _eventFlow.emit("Snack1 bar")
            _eventFlow.emit("Snack2 bar")

            _eventFlow.emit("Snack3 bar")

        }
    }


    val flow= kotlinx.coroutines.flow.flow<String> {
        emit("luong")
    }

    fun trigger():Flow<String>{
        return flow {
            emit("snack")
        }
    }

}