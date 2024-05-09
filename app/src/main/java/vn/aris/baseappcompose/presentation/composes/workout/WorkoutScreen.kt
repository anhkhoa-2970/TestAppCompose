package vn.aris.baseappcompose.presentation.composes.workout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.joda.time.DateTime
import vn.aris.baseappcompose.R
import vn.aris.baseappcompose.domain.models.AssignmentModel
import vn.aris.baseappcompose.domain.models.DayDataModel
import vn.aris.baseappcompose.presentation.composes.CustomToast
import vn.aris.baseappcompose.presentation.composes.ShowProgressDialog
import vn.aris.baseappcompose.presentation.ui.states.MessageState
import vn.aris.baseappcompose.presentation.ui.states.ProgressState
import vn.aris.baseappcompose.presentation.viewmodels.WorkoutViewModel
import vn.aris.baseappcompose.utils.Constants
import vn.aris.baseappcompose.utils.Utils.parseDateToDateOfWeek

@Composable
fun WorkoutScreen(vm: WorkoutViewModel = hiltViewModel()) {
    val workoutUiState by vm.workoutState.collectAsState()
    val systemUiController: SystemUiController = rememberSystemUiController()
    LaunchedEffect(Unit) {
        vm.fetchWorkout()
        systemUiController.setStatusBarColor(color = Color(0Xff13A1BE))
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        LazyColumn {
            items(workoutUiState.workout?.dayData ?: listOf())
            {
                Column {
                    ItemCalendar(it)

                    if (workoutUiState.workout?.dayData?.last() != it) {
                        HorizontalDivider(thickness = 1.dp, color = Color.Gray)
                    }
                }
            }
        }
    }

    vm.apply {
        val uiState by uiState.collectAsState()
        when (uiState.progressState) {
            ProgressState.Loading -> ShowProgressDialog()
            else -> {}
        }
        if (uiState.messageState.msg.isNotEmpty()) {
            CustomToast(message = uiState.messageState.msg, messageType = uiState.messageState.type) {
                vm.updateMessageState(MessageState())
            }
        }
    }
}

@Composable
fun ItemCalendar(data: DayDataModel) {
    Row(
        Modifier
            .wrapContentHeight()
            .padding(start = 20.dp, end = 20.dp, top = 20.dp)
    ) {
        Column(
            Modifier
                .width(36.dp)
                .padding(top = 20.dp)
                .wrapContentHeight()
        ) {
            Text(
                text = parseDateToDateOfWeek(data.date),
                style = TextStyle(fontSize = 12.sp, color = setColorForDay(data.date))
            )
            Text(
                text = "${DateTime.parse(data.date).dayOfMonth}",
                style = TextStyle(fontSize = 16.sp, color = setColorForDate(data.date))
            )
        }

        ItemContentTraining(data = data)
    }
}

@Composable
fun ItemContentTraining(data: DayDataModel) {
    LazyColumn(
        Modifier
            .height(70.dp * (data.assignments?.size ?: 0) + 20 .dp * ((data.assignments?.size ?: 1) - 1) + 36.dp)
            .fillMaxWidth()
    ) {
        items(data.assignments ?: listOf())
        {
            ItemExercise(it)
            Box(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun ItemExercise(data: AssignmentModel) {
    var isChecked by remember { mutableStateOf(data.isCheck ?: false) }
    val backgroundColor = when (workoutStatus(data)) {
        Constants.StatusWorkout.PASS_MISSED -> Color(0xFFF7F8FC)
        Constants.StatusWorkout.PASS_COMPLETED -> Color(0xFF7470EF)
        Constants.StatusWorkout.TODAY_ASSIGNED -> Color(0xFF1E0A3C)
        Constants.StatusWorkout.TODAY_COMPLETED -> Color(0xFF1E0A3C)
        else -> Color(0xFF7B7E91)
    }

    val titleColor = when (workoutStatus(data)) {
        Constants.StatusWorkout.PASS_MISSED -> Color(0xFF1E0A3C)
        Constants.StatusWorkout.PASS_COMPLETED -> Color(0xFFFFFFFF)
        else -> Color(0xFFF7F8FC)
    }

    Column(
        Modifier
            .height(80.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .clickable {
                isChecked = !isChecked
            }
            .padding(start = 20.dp, end = 20.dp)
        ,
        verticalArrangement = Arrangement.Center,
    ) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd){
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
                Text(modifier = Modifier.padding(end = 30.dp), text = "${data.title}", maxLines = 1, overflow = TextOverflow.Ellipsis, color = titleColor, fontWeight = FontWeight.Bold)
                if (workoutStatus(data) == Constants.StatusWorkout.PASS_MISSED) {
                    Row {
                        Text(text = "Missed ", color = Color.Red)
                        Text(text = handleStatus(data))
                    }
                }
            }
            if(isChecked){
                Image(modifier = Modifier.size(30.dp), painter = painterResource(id = R.drawable.ic_check), contentDescription = null)
            }
        }
    }
}

// 1: passMissed 2: passCompleted  - 3: currentAssigned - 4: currentCompleted, 5:

fun workoutStatus(data: AssignmentModel): Constants.StatusWorkout {
    val currentTime = DateTime.now()
    val assignmentTime = DateTime.parse(data.date)
    return when {
        assignmentTime < currentTime -> {
            if (data.exercisesCount == data.exercisesCompleted) {
                Constants.StatusWorkout.PASS_COMPLETED
            } else {
                Constants.StatusWorkout.PASS_MISSED
            }
        }
        assignmentTime.toLocalDate() == currentTime.toLocalDate() -> {
            if (data.status == 0) {
                Constants.StatusWorkout.TODAY_ASSIGNED
            } else {
                Constants.StatusWorkout.TODAY_COMPLETED
            }
        }
        else -> {
            Constants.StatusWorkout.FUTURE
        }
    }
}

fun handleStatus(data: AssignmentModel): String {
    return if (DateTime.parse(data.date) < DateTime.now()) {
        if (data.exercisesCount!! == data.exercisesCompleted!!) {
            "Completed"
        } else {
            val missedCount = data.exercisesCount - data.exercisesCompleted
            "• $missedCount exercises"
        }
    } else {
        "${data.exercisesCount} exercises"
    }
}

fun setColorForDate(date: String?): Color =
    if (DateTime.parse(date) == DateTime.now()) Color(0XFF7470EF) else Color(0XFF1E0A3C)
fun setColorForDay(date: String?): Color =
    if (DateTime.parse(date) == DateTime.now()) Color(0XFF7470EF) else Color(0XFF7B7E91)

