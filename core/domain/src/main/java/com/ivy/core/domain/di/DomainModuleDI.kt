package com.ivy.core.domain.di

import com.ivy.core.domain.pure.util.DispatcherProvider
import com.ivy.core.domain.pure.util.StandardDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DomainModuleDI {

    @Provides
    fun provideDispatcherProvider(): DispatcherProvider {
        return StandardDispatchers()
    }
}