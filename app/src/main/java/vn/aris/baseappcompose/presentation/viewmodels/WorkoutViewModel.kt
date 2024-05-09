package vn.aris.baseappcompose.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import vn.aris.baseappcompose.data.base.handleNetworkResult
import vn.aris.baseappcompose.domain.usecases.WorkoutUseCase
import vn.aris.baseappcompose.presentation.ui.states.MessageState
import vn.aris.baseappcompose.presentation.ui.states.ProgressState
import vn.aris.baseappcompose.presentation.ui.states.WorkoutUiState
import vn.aris.baseappcompose.presentation.viewmodels.base.BaseViewModel
import vn.aris.baseappcompose.utils.Constants
import javax.inject.Inject

@HiltViewModel
class WorkoutViewModel @Inject constructor(private val useCase: WorkoutUseCase) : BaseViewModel() {
    val workoutState = useCase.getAllWorkout().map { WorkoutUiState(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(1000L),
            initialValue = WorkoutUiState()
        )

    fun fetchWorkout() {
        viewModelScope.launch {
            useCase.fetchWorkout().collectLatest { networkResult ->
                networkResult.handleNetworkResult(
                    success = {
                        updateProgressState(ProgressState.NoLoading)
                    },
                    fail = {
                        it.message?.let { errorMsg ->
                            updateMessageState(MessageState(errorMsg, Constants.MessageType.ERROR))
                        }
                        updateProgressState(ProgressState.NoLoading)
                    },
                    loading = {
                        updateProgressState(if (it) ProgressState.Loading else ProgressState.NoLoading)
                    }
                )
            }
        }
    }
}