package com.app.instancedownload.ads

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import com.google.android.ads.nativetemplates.TemplateView
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class Adsimpl @Inject constructor(@ActivityContext private val context: Context)  {

    var mInterstitialAd: InterstitialAd? = null

     fun preperad(): AdRequest {

        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            context,
           "ca-app-pub-8270803172827285/7452537196",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    adError.toString().let { Log.d(ContentValues.TAG, it) }
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    Log.d(ContentValues.TAG, "Ad was loaded.")
                    mInterstitialAd = interstitialAd
                }
            })

        return AdRequest.Builder()
            .build()
    }

     fun showad() {
        if (mInterstitialAd != null) {
            mInterstitialAd!!.show(context as Activity)
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.")
        }
    }


    fun loadnativad(template: TemplateView) {
        val adLoader = AdLoader.Builder(context, "ca-app-pub-8270803172827285/3282230033")
            .forNativeAd { ad : NativeAd ->
                template.setNativeAd(ad)
                template.updateLayoutParams {
                    width  =  ViewGroup.LayoutParams.MATCH_PARENT
                    height =  ViewGroup.LayoutParams.WRAP_CONTENT
                }

            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    // Handle the failure by logging, altering the UI, and so on.
                }
            })
            .withNativeAdOptions(
                NativeAdOptions.Builder()
                    // Methods in the NativeAdOptions.Builder class can be
                    // used here to specify individual options settings.
                    .build())
            .build()
        adLoader.loadAd(AdRequest.Builder().build())


    }


}