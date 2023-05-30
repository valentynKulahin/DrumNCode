package com.example.drumncode.ui.screens.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.domain.models.EDomainModel
import com.example.domain.models.GameDomainModel
import com.example.drumncode.R
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneOffset
import kotlin.time.Duration.Companion.days
import kotlin.time.DurationUnit

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(mainScreenViewModel: MainScreenViewModel) {

    val uiState by mainScreenViewModel.uiState.collectAsStateWithLifecycle()
    val gamesList = remember(uiState) { mutableStateOf(uiState.result) }

    Scaffold(modifier = Modifier.fillMaxSize()) { contentPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            color = MaterialTheme.colorScheme.background
        ) {
            MainScreenLazyCategory(
                gamesList.value,
                mainScreenViewModel = mainScreenViewModel
            )
        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreenLazyCategory(
    games: List<GameDomainModel>,
    mainScreenViewModel: MainScreenViewModel
) {
    LazyColumn(modifier = Modifier.fillMaxWidth(), userScrollEnabled = true) {
        games.forEach { game ->
            item {
                MainScreenLazyHeader(
                    game = games.filter { it.d == game.d }[0],
                    mainScreenViewModel = mainScreenViewModel
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreenLazyHeader(
    game: GameDomainModel,
    mainScreenViewModel: MainScreenViewModel
) {

    val expanded = remember { mutableStateOf(true) }
    val e_state = remember { mutableStateOf(game.e) }
    e_state.value = sortedList(list = e_state.value)

    Column(modifier = Modifier.padding(5.dp)) {
        TextHeader(header = game.d, expanded = expanded)
        if (expanded.value) {
            MainScreenLazyElements(
                matches = e_state,
                mainScreenViewModel = mainScreenViewModel
            )
        }
    }
}

@Composable
fun TextHeader(header: String, expanded: MutableState<Boolean>) {
    Surface(
        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
        color = Color.DarkGray      //MaterialTheme.colorScheme.primary,
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize()
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null
                ) { expanded.value = !expanded.value },
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = header,
                fontWeight = FontWeight.Bold
            )
            Icon(
                imageVector = if (expanded.value) ImageVector.vectorResource(id = R.drawable.arrow_up) else ImageVector.vectorResource(
                    id = R.drawable.arrow_down
                ),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(15.dp)
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreenLazyElements(
    matches: MutableState<List<EDomainModel>>,
    mainScreenViewModel: MainScreenViewModel
) {
    val matches_sort = sortedList(list = matches.value)

    LazyRow(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize(),
        userScrollEnabled = true
    ) {
        matches_sort.forEach { info ->
            item {
                val starActiv = remember { mutableStateOf(info.favourite) }

                MainScreenLazyInfo(
                    info = info,
                    starActiv = starActiv,
                    addToFafourite = {
                        mainScreenViewModel.addToFavourite(info.i)
                    }
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreenLazyInfo(info: EDomainModel, starActiv: MutableState<Boolean>, addToFafourite: () -> Unit) {

    val text = info.sh.split("-")

    Surface(modifier = Modifier.padding(all = 5.dp), color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier
                .padding(5.dp)
                .size(width = 130.dp, height = 110.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextTimeInfo(time = info.tt)
            StarFavouriteInfo(
                expanded = starActiv,
                addToFafourite = { addToFafourite() }
            )
            TextPlayers(player = text[0])
            TextPlayers(player = text[1])
        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TextTimeInfo(time: Long) {

    val result = getDayAndTimeDifference(
        startTimestamp = convertDateToLong(LocalDateTime.now()),
        endTimestamp = time
    )

    Surface(
        modifier = Modifier,
        color = MaterialTheme.colorScheme.background,
        shape = MaterialTheme.shapes.small,
        border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.secondary)
    ) {
        Text(
            text = result,
            modifier = Modifier
                .padding(5.dp),
            color = MaterialTheme.colorScheme.secondary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun StarFavouriteInfo(
    expanded: MutableState<Boolean>,
    addToFafourite: () -> Unit
) {
    Image(
        imageVector = if (expanded.value) ImageVector.vectorResource(id = R.drawable.star_favourite) else ImageVector.vectorResource(
            id = R.drawable.star
        ),
        contentDescription = null,
        modifier = Modifier
            .clickable {
                expanded.value = !expanded.value
                addToFafourite()
            }
            .size(25.dp)
    )
}

@Composable
fun TextPlayers(player: String) {
    Text(text = player, modifier = Modifier, maxLines = 1, overflow = TextOverflow.Ellipsis)
}

fun sortedList(list: List<EDomainModel>): List<EDomainModel> {
    return list.sortedBy { it.tt }.sortedByDescending { it.favourite }
}

@RequiresApi(Build.VERSION_CODES.O)
fun getDayAndTimeDifference(startTimestamp: Long, endTimestamp: Long): String {

    val startDateTime = LocalDateTime.ofEpochSecond(startTimestamp, 0, ZoneOffset.UTC)
    val endDateTime = LocalDateTime.ofEpochSecond(endTimestamp, 0, ZoneOffset.UTC)

    val duration = Duration.between(startDateTime, endDateTime)
//    val months = duration.toDays().days
    val days = duration.toDays().days
    val hours = duration.toHours() % 24
    val minutes = duration.toMinutes() % 60
    val seconds = duration.seconds % 60

    return "${days.toInt(unit = DurationUnit.DAYS)}, $hours:$minutes:$seconds"
}

@RequiresApi(Build.VERSION_CODES.O)
private fun convertDateToLong(date: LocalDateTime): Long {
    return date.toEpochSecond(ZoneOffset.UTC)
}