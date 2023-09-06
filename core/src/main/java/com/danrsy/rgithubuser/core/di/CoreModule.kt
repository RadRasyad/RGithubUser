package com.danrsy.rgithubuser.core.di

import androidx.room.Room
import com.danrsy.rgithubuser.core.BuildConfig
import com.danrsy.rgithubuser.core.data.GURepository
import com.danrsy.rgithubuser.core.data.local.LocalDataSource
import com.danrsy.rgithubuser.core.data.local.datastore.GUDataStore
import com.danrsy.rgithubuser.core.data.local.room.UsersDatabase
import com.danrsy.rgithubuser.core.data.remote.RemoteDataSource
import com.danrsy.rgithubuser.core.data.remote.network.ApiService
import com.danrsy.rgithubuser.core.domain.repository.IGURepository
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.Interceptor
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
        val passphrase: ByteArray = SQLiteDatabase.getBytes("danrsy".toCharArray())
        val factory = SupportFactory(passphrase)

        Room.databaseBuilder(
            androidContext(),
            UsersDatabase::class.java,
            "Users.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}

val networkModule = module {
    single {
        val hostname = "api.github.com"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/jFaeVpA8UQuidlJkkpIdq3MPwD0m8XbuCRbJlezysBE=")
            .add(hostname, "sha256/Jg78dOE+fydIGk19swWwiypUSR6HWZybfnJG/8G7pyM=")
            .build()

        val loggingInterceptor = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }

        val authInterceptor = Interceptor { chain ->
            val originalRequest = chain.request()
            val requestWithAuth = originalRequest.newBuilder()
                .header("Authorization", BuildConfig.KEY)
                .build()

            chain.proceed(requestWithAuth)
        }

        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
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
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    single { GUDataStore(get()) }
    single<IGURepository> { GURepository(get(), get(), get()) }
}


