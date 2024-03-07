package com.example.core.presentation

import com.example.core.domain.BusinessModel

 interface UiModelMapper<BM : BusinessModel, UM : UiModel> {
    fun mapToUiLayerModel(businessModel: BM): UM
}