//package com.artis.artis.di
//
//import com.artis.artis.domain.drawing.repository.DrawingRepository
//import com.artis.artis.domain.drawing.usecase.DrawingUseCase
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
//import javax.inject.Singleton
//
//@Module
//@InstallIn(SingletonComponent::class)
//object UseCaseModule {
//
//    @Provides
//    @Singleton
//    fun provideDrawingUseCases(
//        repository: DrawingRepository
//    ): DrawingUseCase {
//        return DrawingUseCases(repository)
//    }
//}
