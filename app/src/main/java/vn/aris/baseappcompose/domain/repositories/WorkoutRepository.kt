package vn.aris.baseappcompose.domain.repositories

import kotlinx.coroutines.flow.Flow
import vn.aris.baseappcompose.data.base.NetworkResult
import vn.aris.baseappcompose.domain.models.WorkoutModel

interface WorkoutRepository {
    suspend fun fetchWorkout(): Flow<NetworkResult<WorkoutModel>>
    fun getAllWorkout(): Flow<WorkoutModel?>
}