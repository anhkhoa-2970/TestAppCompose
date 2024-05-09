package vn.aris.baseappcompose.utils

import androidx.annotation.StringRes
import vn.aris.baseappcompose.R


object Constants {
	//region HTTP Code
	const val CODE_ERROR_INTERNAL_SERVER = 500
	const val CODE_EXPIRED_TOKEN = 401
	const val CODE_EXPIRED_ACCOUNT = 419
	const val CODE_UNKNOWN = 1000
	const val HTTP_RESULT = "ok"
	const val HTTP_HEADER_TOKEN = "token"
	const val MESSAGE = "message"
	//endregion

	//region SaveStore Keys
	const val PREFS_LANGUAGE_MODEL = "PREFS_LANGUAGE_MODEL"
	const val PREFS_LOGIN = "PREFS_LOGIN"
	//endregion

	enum class CupcakeScreen(@StringRes val title: Int) {
		Start(title = R.string.app_name),
		Flavor(title = R.string.choose_flavor),
		Pickup(title = R.string.choose_pickup_date),
		Summary(title = R.string.order_summary)
	}

	/** Price for a single cupcake */
	const val PRICE_PER_CUPCAKE = 2.00

	/** Additional cost for same day pickup of an order */
	const val PRICE_FOR_SAME_DAY_PICKUP = 3.00

	enum class MessageType {
		ERROR, SUCCESS
	}

	const val TIME_DISPLAY_TOAST = 3000L

	enum class StatusWorkout{
		PASS_MISSED, PASS_COMPLETED, TODAY_ASSIGNED, TODAY_COMPLETED, FUTURE
	}
}