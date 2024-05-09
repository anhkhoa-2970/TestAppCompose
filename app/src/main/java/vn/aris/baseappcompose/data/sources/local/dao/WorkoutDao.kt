package vn.aris.baseappcompose.data.sources.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import vn.aris.baseappcompose.data.models.TABLE_WORKOUT
import vn.aris.baseappcompose.data.models.WorkoutEntity

@Dao
interface WorkoutDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWorkout(workout: WorkoutEntity)

    @Query("Select * from $TABLE_WORKOUT")
    fun getAllWorkout() : Flow<WorkoutEntity>
}