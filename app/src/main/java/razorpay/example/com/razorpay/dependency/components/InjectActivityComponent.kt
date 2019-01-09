package com.example.mayank.kwizzapp.dependency.components


import com.example.mayank.kwizzapp.dependency.scopes.ActivityScope
import dagger.Component
import razorpay.example.com.razorpay.helpers.MainActivity

@ActivityScope
@Component(dependencies = arrayOf(ApplicationComponent::class))
interface InjectActivityComponent {
//    fun injectWalletActivity(walletActivity: WalletActivity)
//    fun injectSampleActivity(sampleActivity: SampleActivity)
    fun injectMainActivity(mainActivity: MainActivity)
//    fun injectPlayActivity(playActivity: PlayActivity)
}
