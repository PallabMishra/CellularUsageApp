/*PALLAB KANTI MISHRA*/
package com.example.cellular.di

import com.example.cellular.data.repository.CellularRepository
import com.example.cellular.data.repository.FakeCellularRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindCellularRepository(
        impl: FakeCellularRepository
    ): CellularRepository
}
