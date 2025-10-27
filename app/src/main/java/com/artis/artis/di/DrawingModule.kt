package com.artis.artis.di

import com.artis.artis.data.drawing.DrawingRepositoryImpl
import com.artis.artis.domain.drawing.repository.DrawingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DrawingModule {

    @Binds
    @Singleton
    abstract fun bindDrawingRepository(
        impl: DrawingRepositoryImpl
    ): DrawingRepository
}