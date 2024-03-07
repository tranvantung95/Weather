package com.example.core.data

import com.example.core.domain.BusinessModel

interface EntityMapper<BM : BusinessModel, EM : EntityModel> {
    operator fun invoke(entityModel: EM): BM
}