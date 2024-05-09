package vn.aris.baseappcompose.data.sources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import vn.aris.baseappcompose.data.models.DayDataConverter
import vn.aris.baseappcompose.data.models.WorkoutEntity
import vn.aris.baseappcompose.data.sources.local.dao.WorkoutDao

@TypeConverters(DayDataConverter::class)
@Database(entities = [ WorkoutEntity::class],
    version = 1,
    exportSchema = true,
    autoMigrations = [
        //AutoMigration(from = 1, to = 2)
    ])
abstract class AppDatabase : RoomDatabase() {
        abstract  fun workoutDao(): WorkoutDao
}