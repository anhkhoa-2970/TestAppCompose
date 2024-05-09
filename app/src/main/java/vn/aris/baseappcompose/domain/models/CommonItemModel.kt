package vn.aris.baseappcompose.domain.models

import androidx.annotation.DrawableRes
import vn.aris.baseappcompose.R

data class CommonItemModel(
	val id: Int,
	val name: String,
	@DrawableRes val icon: Int
) {
	fun getLanguageCode() = LanguageType.entries.find { it.type == id }?.code ?: LanguageType.VN.code

	companion object{
		fun getAreaPhoneCodeModel():List<CommonItemModel>{
			return listOf(
				CommonItemModel(1, "+84", R.drawable.flag_viet_nam),
				CommonItemModel(2, "+65", R.drawable.flag_singapore),
				CommonItemModel(3, "+91", R.drawable.flag_india),
				CommonItemModel(4, "+62", R.drawable.flag_indonesia),
				CommonItemModel(5, "+60", R.drawable.flag_malaysia),
			)
		}

		fun getLanguageList(): List<CommonItemModel> {
			return listOf(
				CommonItemModel(1, "VN",  R.drawable.flag_viet_nam),
				CommonItemModel(2, "English", R.drawable.flag_american)
			)
		}
	}
}

enum class LanguageType(val type: Int, val code: String) {
	VN(1, "vi"), ENGLISH(2, "en")
}
