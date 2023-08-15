package com.example.readerapp.di

import com.example.readerapp.network.BooksApi
import com.example.readerapp.repository.FireRepository
import com.example.readerapp.utils.Constants
import com.example.readerapp.utils.Constants.BASE_URL
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun providerFireBookRepository() =
        FireRepository(queryBook = FirebaseFirestore.getInstance().collection("books"))


    @Singleton
    @Provides
    fun provideBookApi(): BooksApi {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(BooksApi::class.java)
    }
}