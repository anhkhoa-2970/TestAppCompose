package vn.aris.baseappcompose.presentation.ui.states

import vn.aris.baseappcompose.utils.Constants

data class UiState(
    var progressState: ProgressState = ProgressState.NoLoading,
    var messageState: MessageState = MessageState(),
)

enum class ProgressState {
    Loading,
    NoLoading
}

data class MessageState(
    var msg: String = "",
    var type: Constants.MessageType = Constants.MessageType.SUCCESS
)