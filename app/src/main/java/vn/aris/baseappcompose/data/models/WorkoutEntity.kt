package vn.aris.baseappcompose.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

const val TABLE_WORKOUT = "Workout"
@Entity(tableName = TABLE_WORKOUT)
data class WorkoutEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @TypeConverters(DayDataConverter::class)
    @SerializedName("day_data")
    val dayData: List<DayDataEntity>?
)

data class AssignmentEntity(
    @SerializedName("_id")
    val id: String?,
    val client: String?,
    val date: String?,
    val day: String?,
    val duration: Int?,
    @SerializedName("end_date")
    val endDate: String?,
    @SerializedName("exercises_completed")
    val exercisesCompleted: Int?,
    @SerializedName("exercises_count")
    val exercisesCount: Int?,
    val rating: Int?,
    @SerializedName("start_date")
    val startDate: String?,
    val status: Int?,
    val title: String?
)

data class DayDataEntity(
    @SerializedName("_id")
    val id: String?,
    val assignments: List<AssignmentEntity>?,
    val client: String?,
    val date: String?,
    val day: String?,
    val trainer: String?
)

class DayDataConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromString(value: String): List<DayDataEntity> {
        val listType = object : TypeToken<List<DayDataEntity>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<DayDataEntity>): String {
        return gson.toJson(list)
    }
}