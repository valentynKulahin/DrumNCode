package com.example.data

import com.example.data.models.EDataModel
import com.example.data.models.GameDataModel
import com.example.domain.models.EDomainModel
import com.example.domain.models.GameDomainModel

@JvmName("Games_toDomain")
fun LinkedHashSet<GameDataModel>.mapToDomain(): List<GameDomainModel> {
    return mutableListOf<GameDomainModel>().apply {
        this@mapToDomain.forEach {
            add(it.mapToDomain())
        }
    }.toList()
}

fun GameDataModel.mapToDomain(): GameDomainModel {
    return GameDomainModel(i = i, d = d, e = e.mapToDomain())
}

@JvmName("E_toDomain")
fun List<EDataModel>.mapToDomain(): List<EDomainModel> {
    return mutableListOf<EDomainModel>().apply {
        this@mapToDomain.forEach {
            add(it.mapToDomain())
        }
    }.toList()
}

fun EDataModel.mapToDomain(): EDomainModel {
    return EDomainModel(d = d, i = i, si = si, sh = sh, tt = tt)
}