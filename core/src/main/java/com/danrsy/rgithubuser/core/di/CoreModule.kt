package com.danrsy.rgithubuser.core.di

import androidx.room.Room
import com.danrsy.rgithubuser.core.data.local.LocalDataSource
import com.danrsy.rgithubuser.core.data.local.datastore.GUDataStore
import com.danrsy.rgithubuser.core.data.local.room.UsersDatabase
import com.danrsy.rgithubuser.core.data.remote.RemoteDataSource
import com.danrsy.rgithubuser.core.data.remote.network.ApiService
import com.danrsy.rgithubuser.core.data.GURepository
import com.danrsy.rgithubuser.core.domain.repository.IGURepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<UsersDatabase>().userDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            UsersDatabase::class.java,
            "Users.db"
        ).fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { com.danrsy.rgithubuser.core.data.local.LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    single { com.danrsy.rgithubuser.core.data.local.datastore.GUDataStore(get()) }
    single<IGURepository> { com.danrsy.rgithubuser.core.data.GURepository(get(), get(), get()) }
}
