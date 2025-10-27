//package com.artis.artis.domain.drawing.usecase
//
//import com.artis.artis.domain.drawing.model.Stroke
//import com.artis.artis.domain.drawing.repository.DrawingRepository
//import javax.inject.Inject
//
//class DrawingUseCases @Inject constructor(
//    private val repository: DrawingRepository
//) {
//    suspend fun addStroke(stroke: Stroke) = repository.addStroke(stroke)
//    suspend fun undo() = repository.undo()
//    suspend fun redo() = repository.redo()
//    suspend fun getStrokes(): List<Stroke> = repository.getStrokes()
//}