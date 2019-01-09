package com.example.mayank.kwizzapp.dependency.modules

import com.example.mayank.kwizzapp.dependency.scopes.ApplicationScope
import dagger.Module
import dagger.Provides
import razorpay.example.com.razorpay.network.IPayment
import retrofit2.Retrofit

@Module(includes = arrayOf(HttpModule::class))
class NetworkApiModule {

//    @Provides
//    @ApplicationScope
//    fun userService(retrofit: Retrofit): IUser {
//        return retrofit.create(IUser::class.java)
//    }
//
    @Provides
    @ApplicationScope
   fun paymentService(retrofit: Retrofit): IPayment {
       return retrofit.create(IPayment::class.java)
   }
//
//    @Provides
//    @ApplicationScope
//    fun questionService(retrofit: Retrofit): IQuestion {
//        return retrofit.create(IQuestion::class.java)
//    }
}