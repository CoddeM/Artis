package com.artis.artis.domain.drawing.usecase

import com.artis.artis.domain.drawing.repository.DrawingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentStrokeWidthUseCase @Inject constructor(
    private val repository: DrawingRepository
) {
    operator fun invoke(): Flow<Float> {
        return repository.getCurrentStrokeWidth()
    }
}