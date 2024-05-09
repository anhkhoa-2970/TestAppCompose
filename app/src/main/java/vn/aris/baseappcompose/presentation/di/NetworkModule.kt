package vn.aris.baseappcompose.presentation.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import vn.aris.baseappcompose.BuildConfig
import vn.aris.baseappcompose.R
import vn.aris.baseappcompose.data.sources.remote.APIService
import vn.aris.baseappcompose.domain.models.CommonItemModel
import vn.aris.baseappcompose.utils.Constants
import vn.aris.baseappcompose.utils.SavedStore
import vn.aris.baseappcompose.utils.Utils
import vn.aris.baseappcompose.utils.toJson
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideGsonConvertFactory(): GsonConverterFactory {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        return GsonConverterFactory.create(gson)
    }

    @Singleton
    @Provides
    fun provideTrustManager(): X509TrustManager {
        return object : X509TrustManager {

            override fun getAcceptedIssuers(): Array<X509Certificate> = emptyArray()

            override fun checkClientTrusted(chain: Array<X509Certificate>?, authType: String?) = Unit

            override fun checkServerTrusted(chain: Array<X509Certificate>?, authType: String?) = Unit
        }
    }

    @Singleton
    @Provides
    fun provideSSLSocketFactory(trustManager: X509TrustManager): SSLSocketFactory {
        val trustAllCerts: Array<TrustManager> = arrayOf(trustManager)
        val sslContext = SSLContext.getInstance("SSL")
            .apply { init(null, trustAllCerts, SecureRandom()) }
        return sslContext.socketFactory
    }

    @Singleton
    @Provides
    fun provideAPIClient(
        socketFactory: SSLSocketFactory,
        trustManager: X509TrustManager
    ): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .addInterceptor(Interceptor { chain ->
                val ongoing: Request.Builder = chain.request().newBuilder().apply {
                    header("Accept", "application/json")
                    header("Content-Type", "application/json")
                }
                chain.proceed(ongoing.build())
            })
            .addNetworkInterceptor {
                val request: Request =
                    it.request().newBuilder().addHeader("Connection", "Close").build()
                it.proceed(request)
            }
            .sslSocketFactory(socketFactory, trustManager)
            .hostnameVerifier { _, _ -> true }
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofitApi(client: OkHttpClient): APIService {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(provideGsonConvertFactory())
            .baseUrl(BuildConfig.SERVER_NORMAL_URL)
            .client(client)
            .build()
        return retrofit.create(APIService::class.java)
    }
}

