package vn.aris.baseappcompose.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

const val TABLE_COURSE = "Course"
@Entity(tableName = TABLE_COURSE)
data class CourseModel(
    @PrimaryKey
    val id: Int,
    val name: String,
    val thumbnail: String
)
