package com.example.news.views.components.activities.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.news.R
import com.example.news.databinding.ActivityHomeBinding
import com.example.news.views.base.BaseBindingActivity
import com.example.news.views.components.fragments.home.HomeFragment
import kotlinx.android.synthetic.main.app_bar.*

class HomeActivity : BaseBindingActivity() {
    var binding: ActivityHomeBinding? = null
    var onClickListener: View.OnClickListener? = null


    companion object {

        fun startActivity(activity: Activity, bundle: Bundle?, isClear: Boolean) {
            val intent = Intent(activity, HomeActivity::class.java)
            if (bundle != null) intent.putExtra("bundle", bundle)
            if (isClear) intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            activity.startActivity(intent)
        }
    }

    override fun setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
//        viewModel = ViewModelProvider(this).get(OrderDetailViewModel::class.java)
        onClickListener = this
        binding!!.lifecycleOwner = this
    }

    override fun createActivityObject(savedInstanceState: Bundle?) {
        mActivity = this
    }


    override fun initializeObject() {
        replaceFragment(HomeFragment(), null)
    }


    override fun setListeners() {
    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            /*R.id.imvCart -> {

            }*/
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            finish()
        } else {
            super.onBackPressed()
        }
    }


}





