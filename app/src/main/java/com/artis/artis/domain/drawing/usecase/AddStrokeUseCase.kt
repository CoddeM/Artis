package com.artis.artis.domain.drawing.usecase

import com.artis.artis.domain.drawing.model.Stroke
import com.artis.artis.domain.drawing.repository.DrawingRepository
import javax.inject.Inject

class AddStrokeUseCase @Inject constructor(
    private val repository: DrawingRepository
) {
    suspend operator fun invoke(stroke: Stroke) {
        println("DEBUG: AddStrokeUseCase received color: ${stroke.color}")
        repository.addStroke(stroke)
    }
}