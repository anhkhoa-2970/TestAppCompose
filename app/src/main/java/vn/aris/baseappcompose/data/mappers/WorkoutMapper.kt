package vn.aris.baseappcompose.data.mappers

import vn.aris.baseappcompose.data.base.Mapper
import vn.aris.baseappcompose.data.models.AssignmentEntity
import vn.aris.baseappcompose.data.models.DayDataEntity
import vn.aris.baseappcompose.data.models.WorkoutEntity
import vn.aris.baseappcompose.domain.models.AssignmentModel
import vn.aris.baseappcompose.domain.models.DayDataModel
import vn.aris.baseappcompose.domain.models.WorkoutModel

class WorkoutMapper :Mapper<WorkoutEntity,WorkoutModel> {
    override fun fromEntity(from: WorkoutEntity): WorkoutModel {
        return WorkoutModel(dayData = from.dayData?.map {dayDataModel ->
            DayDataModel(
                id = dayDataModel.id,
                client = dayDataModel.client,
                day = dayDataModel.day,
                date = dayDataModel.date,
                trainer = dayDataModel.trainer,
                assignments = dayDataModel.assignments?.map {
                    AssignmentModel(
                        id = it.id,
                        client = it.client,
                        date = it.date,
                        day = it.day,
                        duration = it.duration,
                        endDate = it.endDate,
                        exercisesCompleted = it.exercisesCompleted,
                        exercisesCount = it.exercisesCount,
                        rating = it.rating,
                        startDate = it.startDate,
                        status = it.status,
                        title = it.title
                    )
                }
            )

        })
    }

    override fun toEntity(from: WorkoutModel): WorkoutEntity {
        return WorkoutEntity(dayData = from.dayData?.map {dayDataEntity ->
            DayDataEntity(
                id = dayDataEntity.id,
                client = dayDataEntity.client,
                day = dayDataEntity.day,
                date = dayDataEntity.date,
                trainer = dayDataEntity.trainer,
                assignments = dayDataEntity.assignments?.map {
                    AssignmentEntity(
                        id = it.id,
                        client = it.client,
                        date = it.date,
                        day = it.day,
                        duration = it.duration,
                        endDate = it.endDate,
                        exercisesCompleted = it.exercisesCompleted,
                        exercisesCount = it.exercisesCount,
                        rating = it.rating,
                        startDate = it.startDate,
                        status = it.status,
                        title = it.title
                    )
                }
            )

        })
    }
}