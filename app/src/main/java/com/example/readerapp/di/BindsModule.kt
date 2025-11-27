package com.example.readerapp.di

import com.example.readerapp.data.repository.ReaderRepositoryImpl
import com.example.readerapp.domain.repository.ReaderRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class BindsModule {

    @Binds
    @Singleton
    abstract fun bindRepository(
        impl: ReaderRepositoryImpl
    ): ReaderRepository
}