package vn.aris.baseappcompose.data.repositories

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import vn.aris.baseappcompose.data.base.NetworkResult
import vn.aris.baseappcompose.data.mappers.WorkoutMapper
import vn.aris.baseappcompose.data.sources.local.WorkoutLocalDataSource
import vn.aris.baseappcompose.data.sources.remote.WorkoutRemoteDataSource
import vn.aris.baseappcompose.domain.models.WorkoutModel
import vn.aris.baseappcompose.domain.repositories.WorkoutRepository
import vn.aris.baseappcompose.presentation.di.IoDispatcher
import javax.inject.Inject

class WorkoutRepositoryImpl @Inject constructor(
    private val remoteDataSource: WorkoutRemoteDataSource,
    private val localDataSource: WorkoutLocalDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : WorkoutRepository {
    override suspend fun fetchWorkout(): Flow<NetworkResult<WorkoutModel>> {
        return remoteDataSource.fetchWorkout().map { networkResult ->
            if (networkResult is NetworkResult.Success) {
                networkResult.data?.let {
                    localDataSource.insertWorkout(WorkoutMapper().toEntity(it))
                }
            }
            networkResult
        }.flowOn(ioDispatcher)
    }

    override fun getAllWorkout(): Flow<WorkoutModel?> {
        return localDataSource.getAllWorkout().map { workoutEntity ->
            workoutEntity?.let {
                WorkoutMapper().fromEntity(it)
            }
        }
    }
}