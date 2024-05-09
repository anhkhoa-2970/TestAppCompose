package vn.aris.baseappcompose.presentation.viewmodels.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import vn.aris.baseappcompose.presentation.ui.states.MessageState
import vn.aris.baseappcompose.presentation.ui.states.ProgressState
import vn.aris.baseappcompose.presentation.ui.states.UiState

open class BaseViewModel() : ViewModel() {
    private val _uiState = MutableStateFlow(UiState(progressState = ProgressState.NoLoading, messageState = MessageState()))
    var uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun updateProgressState(state: ProgressState) {
        _uiState.update { currentState ->
            currentState.copy(progressState = state)
        }
    }

    fun updateMessageState(messageState: MessageState) {
        _uiState.update { currentState ->
            currentState.copy(messageState = messageState)
        }
    }
}
