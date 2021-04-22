package com.vsimpleton.wanandroid.api

import android.util.Log
import com.vsimpleton.wanandroid.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiFactory {

    private const val LOG_MAX_LENGTH = 2000

    private val mClient: OkHttpClient by lazy { newClient() }

    fun <T> create(baseUrl: String, clazz: Class<T>): T =
        Retrofit.Builder().baseUrl(baseUrl).client(mClient)
            .addConverterFactory(GsonConverterFactory.create()).build().create(clazz)

    private fun newClient(): OkHttpClient = OkHttpClient.Builder().apply {
        connectTimeout(30, TimeUnit.SECONDS)
        readTimeout(10, TimeUnit.SECONDS)
        writeTimeout(10, TimeUnit.SECONDS)
        addInterceptor(logInterceptor())
    }.build()

    private fun logInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                val strLength = message.length
                var start = 0
                var end: Int = LOG_MAX_LENGTH
                for (i in 0..99) {
                    //剩下的文本还是大于规定长度则继续重复截取并输出
                    if (strLength > end) {
                        if (BuildConfig.DEBUG) {
                            Log.i("requestUrl", "log: " + message.substring(start, end))
                        }
                        start = end
                        end += LOG_MAX_LENGTH
                    } else {
                        if (BuildConfig.DEBUG) {
                            Log.i("requestUrl", "log: " + message.substring(start, strLength))
                        }
                        break
                    }
                }
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY) //设置打印数据的级别
    }

}