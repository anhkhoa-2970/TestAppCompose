package vn.aris.baseappcompose.presentation.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import vn.aris.baseappcompose.data.repositories.WorkoutRepositoryImpl
import vn.aris.baseappcompose.domain.usecases.WorkoutUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Singleton
    @Provides
    fun provideWorkoutUseCase(workoutRepositoryImpl: WorkoutRepositoryImpl): WorkoutUseCase {
        return WorkoutUseCase(workoutRepositoryImpl)
    }
}