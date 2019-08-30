package com.test.testcatsapp.common.mappers

import com.test.testcatsapp.api.entity.Cat as ApiCat
import com.test.testcatsapp.data.entity.Cat as DbCat

fun List<ApiCat>.toDbEntities(): List<DbCat> =
    map { it.toDbEntity() }

private fun ApiCat.toDbEntity(): DbCat =
    DbCat(
        id = id,
        imageUrl = url
    )
