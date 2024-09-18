package com.example.moviedetails.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.moviedetails.data.local.AppDatabase
import com.example.moviedetails.data.local.dao.MovieDao
import com.example.moviedetails.data.remote.popular.PopularApiDetails.BASE_URL
import com.example.moviedetails.data.remote.popular.PopularApiInterface
import com.example.moviedetails.data.remote.show.ShowApiInterface
import com.example.moviedetails.data.remote.upcoming.UpcomingApiInterface
import com.example.moviedetails.data.repository.Repository
import com.example.moviedetails.data.repository.RepositoryImplementation
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun getGson(): Gson { //return type is how the hilt will know what to return
        //Function Name itself serves No purpose
        return Gson()
    }

    @Provides
    fun getHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }


    @Provides
    fun getRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    fun UpcomingApiInterface(
        retrofit: Retrofit
    ): UpcomingApiInterface {
        return retrofit.create(UpcomingApiInterface::class.java)

    }

    @Provides
    fun ShowApiInterface(
        retrofit: Retrofit
    ): ShowApiInterface {
        return retrofit.create(ShowApiInterface::class.java)

    }

    @Provides
    fun PopularApiInterface(
        retrofit: Retrofit
    ): PopularApiInterface {
        return retrofit.create(PopularApiInterface::class.java)

    }
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "movie_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideMovieDao(database: AppDatabase): MovieDao {
        return database.movieDao()
    }

    @Provides
    fun getRepository(
        apiUpcoming: UpcomingApiInterface,
        apiShow: ShowApiInterface,
        apiPopular: PopularApiInterface,
        movieDao: MovieDao,

    ): Repository {
        return RepositoryImplementation(apiUpcoming, apiShow, apiPopular,movieDao)

    }
}