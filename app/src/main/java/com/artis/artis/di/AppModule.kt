package com.artis.artis.di

import android.content.Context
import com.artis.artis.data.drawing.DrawingRepositoryImpl
import com.artis.artis.data.drawing.local.DrawingLocalDataSource
import com.artis.artis.domain.drawing.repository.DrawingRepository
import com.artis.artis.domain.drawing.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDrawingLocalDataSource(): DrawingLocalDataSource {
        return DrawingLocalDataSource()
    }

//    @Provides
//    @Singleton
//    fun provideDrawingRepository(
//        localDataSource: DrawingLocalDataSource
//    ): DrawingRepository {
//        return DrawingRepositoryImpl(localDataSource)
//    }

    @Provides
    @Singleton
    fun provideAddStrokeUseCase(repository: DrawingRepository): AddStrokeUseCase {
        return AddStrokeUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetStrokesUseCase(repository: DrawingRepository): GetStrokesUseCase {
        return GetStrokesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideUndoUseCase(repository: DrawingRepository): UndoUseCase {
        return UndoUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideRedoUseCase(repository: DrawingRepository): RedoUseCase {
        return RedoUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideClearStrokesUseCase(repository: DrawingRepository): ClearStrokesUseCase {
        return ClearStrokesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSetCurrentDrawingColorUseCase(repository: DrawingRepository): SetCurrentDrawingColorUseCase {
        return SetCurrentDrawingColorUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetCurrentDrawingColorUseCase(repository: DrawingRepository): GetCurrentDrawingColorUseCase {
        return GetCurrentDrawingColorUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSetCurrentStrokeWidthUseCase(repository: DrawingRepository): SetCurrentStrokeWidthUseCase {
        return SetCurrentStrokeWidthUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetCurrentStrokeWidthUseCase(repository: DrawingRepository): GetCurrentStrokeWidthUseCase {
        return GetCurrentStrokeWidthUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSetCanvasBackgroundColorUseCase(repository: DrawingRepository): SetCanvasBackgroundColorUseCase {
        return SetCanvasBackgroundColorUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetCanvasBackgroundColorUseCase(repository: DrawingRepository): GetCanvasBackgroundColorUseCase {
        return GetCanvasBackgroundColorUseCase(repository)
    }

    // New: Provide use cases for stroke alpha
    @Provides
    @Singleton
    fun provideSetCurrentStrokeAlphaUseCase(repository: DrawingRepository): SetCurrentStrokeAlphaUseCase {
        return SetCurrentStrokeAlphaUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetCurrentStrokeAlphaUseCase(repository: DrawingRepository): GetCurrentStrokeAlphaUseCase {
        return GetCurrentStrokeAlphaUseCase(repository)
    }
}