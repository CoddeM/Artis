package com.artis.artis.domain.drawing.usecase

import com.artis.artis.domain.drawing.model.Stroke
import com.artis.artis.domain.drawing.repository.DrawingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStrokesUseCase @Inject constructor(
    private val repository: DrawingRepository
) {
    operator fun invoke(): Flow<List<Stroke>> {
        return repository.getStrokes()
    }
}