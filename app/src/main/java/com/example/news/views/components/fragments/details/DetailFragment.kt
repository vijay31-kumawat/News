package com.example.news.views.components.fragments.details


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.news.R
import com.example.news.databinding.FragmentDetailBinding
import com.example.news.dialogs.ProgressDialog
import com.example.news.network.ApiResponse
import com.example.news.utils.UtilityMethod
import com.example.news.views.base.BaseFragment
import com.example.news.views.components.activities.home.HomeActivity
import com.google.gson.Gson
import com.google.gson.JsonElement
import org.json.JSONArray


class DetailFragment : BaseFragment() {
    private var binding: FragmentDetailBinding? = null
    private var onClickListener: View.OnClickListener? = null
    private var viewModel: DetailViewModel? = null
    private var homeActivity: HomeActivity? = null
    private var articlesId: String? = null
    private var bundle: Bundle? = null


    override fun setBinding(inflater: LayoutInflater, container: ViewGroup?): ViewDataBinding {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        onClickListener = this
        binding!!.lifecycleOwner = this
        return binding!!
    }

    companion object {
        private val TAG = DetailFragment::class.qualifiedName
    }

    override fun createActivityObject() {
        mActivity = activity
    }

    override fun initializeObject() {
        getBundleData()
    }

    override fun initializeOnCreateObject() {
        homeActivity = activity as HomeActivity?
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        viewModel!!.responseLiveData.observe(this, Observer {
            handleResult(it)
        })
    }


    override fun setListeners() {
    }

    override fun onClick(q0: View?) {
        when (q0!!.id) {
        }
    }

    private fun getBundleData() {
        bundle = this.arguments
        if (bundle != null) {
            articlesId = bundle!!.getString("articlesId")
            getArticleDetailApi()
        }
    }

    private fun getArticleDetailApi() {
        if(articlesId != null && articlesId!!.isNotEmpty())
        {
            if (!UtilityMethod.isOnline(mActivity!!)) {
                UtilityMethod.showNoInternetConnectedDialog(mActivity!!)
            } else {
                viewModel!!.articleDetailApi(articlesId!!)
            }
        }
    }

    private fun handleResult(result: ApiResponse<JsonElement>?) {
        when (result!!.status) {
            ApiResponse.Status.ERROR -> {
                ProgressDialog.hideProgressDialog()
                UtilityMethod.showToastMessageError(mActivity!!, result.error!!.message!!)
            }
            ApiResponse.Status.LOADING -> {
                ProgressDialog.showProgressDialog(mActivity!!)
            }
            ApiResponse.Status.SUCCESS -> {
                ProgressDialog.hideProgressDialog()
                Log.e(TAG, "response - " + Gson().toJson(result.data))
                if (result.data != null) {

                    try {
                        val jArray = JSONArray(result.data.toString())
                                for (i in 0 until jArray.length()) {
                                    val json = jArray.getJSONObject(i)

                                    binding!!.newsTitle.text = json.getString("title")
                                    binding!!.newsDescription.text = json.getString("summary")
                                    UtilityMethod.loadImage(binding!!.newsImg, json.getString("imageUrl"))
                                    binding!!.newsTime.text = UtilityMethod.getTimeStampToDate(json.getString("publishedAt"))
                                }

                    } catch (e: Exception) {
                        Log.e(TAG, "error - " + e.toString())
                    }

                } else {
                    UtilityMethod.showToastMessageError(mActivity!!, "Data Not Found!")
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        UtilityMethod.setTitleBarViews(mActivity!!, true, "News App")
    }

    override fun onDestroy() {
        viewModel!!.disposeSubscriber()
        ProgressDialog.hideProgressDialog()
        super.onDestroy()
    }

}












