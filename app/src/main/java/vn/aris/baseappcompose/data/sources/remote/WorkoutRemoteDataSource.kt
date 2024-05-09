package vn.aris.baseappcompose.data.sources.remote

import kotlinx.coroutines.flow.Flow
import vn.aris.baseappcompose.data.base.NetworkResult
import vn.aris.baseappcompose.data.base.getNetworkResult
import vn.aris.baseappcompose.data.base.resultFlow
import vn.aris.baseappcompose.data.mappers.WorkoutMapper
import vn.aris.baseappcompose.domain.models.WorkoutModel
import javax.inject.Inject

class WorkoutRemoteDataSource @Inject constructor(private val apiService: APIService) {
    fun fetchWorkout(): Flow<NetworkResult<WorkoutModel>> = resultFlow(
        networkCall = {
            getNetworkResult {
                apiService.fetchWorkout()
            }
        }, mapper = WorkoutMapper()
    )
}