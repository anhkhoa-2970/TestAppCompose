package vn.aris.baseappcompose.domain.usecases

import kotlinx.coroutines.flow.Flow
import vn.aris.baseappcompose.data.base.NetworkResult
import vn.aris.baseappcompose.domain.models.WorkoutModel
import vn.aris.baseappcompose.domain.repositories.WorkoutRepository
import javax.inject.Inject

class WorkoutUseCase @Inject constructor(private val workoutRepository: WorkoutRepository) {
    suspend fun fetchWorkout(): Flow<NetworkResult<WorkoutModel>> = workoutRepository.fetchWorkout()

    fun getAllWorkout(): Flow<WorkoutModel?> = workoutRepository.getAllWorkout()
}