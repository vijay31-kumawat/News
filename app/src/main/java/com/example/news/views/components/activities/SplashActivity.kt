package com.example.news.views.components.activities


import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.news.R
import com.example.news.databinding.ActivitySplashBinding
import com.example.news.views.base.BaseBindingActivity
import com.example.news.views.components.activities.home.HomeActivity

class SplashActivity : BaseBindingActivity() {
    var binding: ActivitySplashBinding? = null;

    override fun setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
    }

    override fun createActivityObject(savedInstanceState: Bundle?) {
        mActivity = this
    }

    override fun initializeObject() {

        startSplashTimer()
    }

    override fun setListeners() {

    }

    override fun onClick(view: View?) {

    }

    private fun startSplashTimer() {

        Handler().postDelayed({
            HomeActivity.startActivity(this@SplashActivity, null, true)
            finish()

        }, 3000)
    }
}