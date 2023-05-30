package com.example.drumncode.ui.screens.main

import androidx.lifecycle.ViewModel
import com.example.data.mapToDomain
import com.example.data.models.GameDataModel
import com.example.domain.models.GameDomainModel
import com.example.domain.usecase.LoadGamesUseCase
import com.example.domain.utils.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

//единственное что - не удалось
// вызвать рекомпозицию при
// добавлении в избранные,
// ищу пока варианты

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val loadGamesUseCase: LoadGamesUseCase
) : ViewModel() {

    private val scope = CoroutineScope(Dispatchers.IO)
    private val _uiState = MutableStateFlow(MainScreenContract())
    val uiState = _uiState.asStateFlow()

    init {
        scope.launch {
            updateGames()
//            sortedGames()
        }
    }

    private suspend fun updateGames() {
        val result = loadGamesUseCase.execute()
        when (result) {
            is ApiResult.ApiSuccess -> _uiState.emit(
                _uiState.value.copy(
                    result = mapToDomain(
                        result.data as LinkedHashSet<GameDataModel>
                    )
                )
            )

            is ApiResult.ApiError -> _uiState.emit(_uiState.value.copy(error = "${result.code} ${result.message}"))
            is ApiResult.ApiException -> _uiState.emit(_uiState.value.copy(exception = "${result.e}"))
        }
    }

    private fun mapToDomain(result: LinkedHashSet<GameDataModel>): List<GameDomainModel> {
        return result.mapToDomain().toList()
    }

    fun addToFavourite(id: Long) {
        val result = _uiState.value.result.flatMap { it.e }.find { it.i == id }
        if (result != null) {
            result.favourite = !result.favourite
        }
//        sortedGames()
    }

//    fun sortedGames() {
//        val games = _uiState.value.result
//        games.forEach {
//            it.e = it.e.sortedBy { it.tt }.sortedByDescending { it.favourite }
////                .sortedBy { it.tt }//With(compareBy<EDomainModel>({ it.favourite })) //, { it.favourite }))    //.thenBy { it.favourite })
//        }
//        scope.launch { _uiState.emit(_uiState.value.copy(result = games)) }
//    }

}