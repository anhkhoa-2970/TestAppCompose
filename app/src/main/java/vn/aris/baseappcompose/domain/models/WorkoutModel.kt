package vn.aris.baseappcompose.domain.models

data class WorkoutModel(
    val dayData: List<DayDataModel>?
)

data class AssignmentModel(
    val id: String?,
    val client: String?,
    val date: String?,
    val day: String?,
    val duration: Int?,
    val endDate: String?,
    val exercisesCompleted: Int? = 0,
    val exercisesCount: Int? =0,
    val rating: Int?,
    val startDate: String?,
    val status: Int?,
    val title: String?,
    var isCheck : Boolean? = false
)

data class DayDataModel(
    val id: String?,
    val assignments: List<AssignmentModel>?,
    val client: String?,
    val date: String?,
    val day: String?,
    val trainer: String?
)