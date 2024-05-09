package vn.aris.baseappcompose.data.sources.local

import kotlinx.coroutines.flow.Flow
import vn.aris.baseappcompose.data.models.WorkoutEntity
import javax.inject.Inject

class WorkoutLocalDataSource @Inject constructor(private val appDatabase: AppDatabase) {
    fun insertWorkout(data: WorkoutEntity) = appDatabase.workoutDao().insertWorkout(data)
    fun getAllWorkout(): Flow<WorkoutEntity?> = appDatabase.workoutDao().getAllWorkout()
}