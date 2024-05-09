package vn.aris.baseappcompose.data.models

enum class EventType {
	CLEAR_DATA_GO_TO_LOGIN
}

class MessageEvent(val eventType: EventType) {
	var data: Any? = null
}