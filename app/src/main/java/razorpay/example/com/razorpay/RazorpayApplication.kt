package razorpay.example.com.razorpay

import android.app.Application
import com.example.mayank.kwizzapp.dependency.components.ApplicationComponent
import com.example.mayank.kwizzapp.dependency.components.DaggerApplicationComponent
import com.example.mayank.kwizzapp.dependency.modules.AppContextModule
import retrofit2.Retrofit

class RazorpayApplication : Application() {

    companion object {

            lateinit var applicationComponent: ApplicationComponent
            lateinit var retrofit : Retrofit

    }

    override fun onCreate() {
        super.onCreate()



        applicationComponent = DaggerApplicationComponent.builder()
            .appContextModule(AppContextModule(applicationContext))
            .build()
    }
}