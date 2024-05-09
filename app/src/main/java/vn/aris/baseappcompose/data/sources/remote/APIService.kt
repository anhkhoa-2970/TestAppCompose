package vn.aris.baseappcompose.data.sources.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import vn.aris.baseappcompose.data.base.ApiResponse
import vn.aris.baseappcompose.data.models.CourseEntity
import vn.aris.baseappcompose.data.models.DayDataEntity
import vn.aris.baseappcompose.data.models.RequestLogin
import vn.aris.baseappcompose.data.models.UserInfoEntity
import vn.aris.baseappcompose.data.models.WorkoutEntity


interface APIService {

    @POST("api/login")
    suspend fun login(@Body requestLogin: RequestLogin): Response<ApiResponse<UserInfoEntity>>

    @GET("api/course_list")
    suspend fun getCourseList(): Response<ApiResponse<List<CourseEntity>>>

    @GET("/workouts")
    suspend fun fetchWorkout(): Response<WorkoutEntity>
}
