package com.example.counterjc.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.counterjc.feature_counter.data.data_source.ProductDatabase
import com.example.counterjc.feature_counter.data.repository.ProductRepositoryImpl
import com.example.counterjc.feature_counter.domain.repository.ProductRepository
import com.example.counterjc.feature_counter.domain.use_case.AddProduct
import com.example.counterjc.feature_counter.domain.use_case.DeleteProduct
import com.example.counterjc.feature_counter.domain.use_case.GetProduct
import com.example.counterjc.feature_counter.domain.use_case.GetProducts
import com.example.counterjc.feature_counter.domain.use_case.LoadBackgroundImage
import com.example.counterjc.feature_counter.domain.use_case.ProductUseCases
import com.example.counterjc.feature_counter.presentation.datastore.StoreSettings
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
    fun provideProductDatabase(app: Application): ProductDatabase {
        return Room.databaseBuilder(
            app,
            ProductDatabase::class.java,
            ProductDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideProductRepository(db: ProductDatabase): ProductRepository {
        return ProductRepositoryImpl(db.dao)
    }

    @Provides
    @Singleton
    fun provideProductUseCases(repository: ProductRepository): ProductUseCases {
        return ProductUseCases(
            getProducts = GetProducts(repository),
            deleteProduct = DeleteProduct(repository),
            addProduct = AddProduct(repository),
            getProduct = GetProduct(repository),
        )
    }

    @Provides
    @Singleton
    fun provideStoreSettings(@ApplicationContext appContext: Context): StoreSettings {
        return StoreSettings(appContext)
    }

    @Provides
    @Singleton
    fun provideBackgroundImageUseCase(@ApplicationContext context: Context): LoadBackgroundImage {
        return LoadBackgroundImage(context);
    }
}