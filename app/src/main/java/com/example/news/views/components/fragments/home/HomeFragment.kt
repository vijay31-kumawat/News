package com.example.news.views.components.fragments.home


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
import com.example.news.databinding.FragmentHomeBinding
import com.example.news.dialogs.ProgressDialog
import com.example.news.network.ApiResponse
import com.example.news.pojo.GetArticlesListDataItem
import com.example.news.utils.UtilityMethod
import com.example.news.views.base.BaseFragment
import com.example.news.views.components.activities.home.HomeActivity
import com.example.news.views.components.adapters.ArticlesListAdapter
import com.example.news.views.components.fragments.details.DetailFragment
import com.google.gson.Gson
import com.google.gson.JsonElement
import org.json.JSONArray
import org.json.JSONObject


class HomeFragment : BaseFragment() {
    private var binding: FragmentHomeBinding? = null
    private var onClickListener: View.OnClickListener? = null
    private var viewModel: HomeViewModel? = null
    private var homeActivity: HomeActivity? = null
    private var articlesListAdapter : ArticlesListAdapter? = null
    private var articlesListData : ArrayList<GetArticlesListDataItem>? = null


    override fun setBinding(inflater: LayoutInflater, container: ViewGroup?): ViewDataBinding {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        onClickListener = this
        binding!!.lifecycleOwner = this
        return binding!!
    }

    companion object {
        private val TAG = HomeFragment::class.qualifiedName
    }

    override fun createActivityObject() {
        mActivity = activity
    }

    override fun initializeObject() {
        articlesListData = ArrayList<GetArticlesListDataItem>()
        getArticleListApi()
    }

    override fun initializeOnCreateObject() {
        homeActivity = activity as HomeActivity?
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        viewModel!!.responseLiveData.observe(this, Observer {
            handleResult(it)
        })
    }


    override fun setListeners() {
    }

    override fun onClick(q0: View?) {
        when (q0!!.id) {
            R.id.rowArticlesListItem -> {
                val position = q0.tag as Int
                articlesListAdapter!!.selectedPos = position

                 var bundle = Bundle()
                 bundle.putString("articlesId", articlesListData!!.get(position)!!.id)
                  homeActivity!!.changeFragment(DetailFragment(), true, bundle)
            }
        }
    }

    private fun getArticleListApi() {
        if (!UtilityMethod.isOnline(mActivity!!)) {
            UtilityMethod.showNoInternetConnectedDialog(mActivity!!)
        } else {
            viewModel!!.articleListApi()
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
                    articlesListData!!.clear()

                    try {
                        val jArray = JSONArray(result.data.toString())
                                for (i in 0 until jArray.length()) {
                                    val json = jArray.getJSONObject(i)

                                    articlesListData!!.add(
                                        GetArticlesListDataItem(
                                            json.getString("summary"),
                                            false,json.getString("publishedAt"), json.getString("imageUrl"),"",json.getString("id"),json.getString("title"),"",null,null,""
                                        )
                                    )
                                }

                        //Log.e(TAG, "response - " + Gson().toJson(articlesListData))
                    } catch (e: Exception) {
                        Log.e(TAG, "error - " + e.toString())
                    }
                    if (articlesListData!!.size != 0) {
                        articlesListAdapter = ArticlesListAdapter(mActivity!!, onClickListener!!, articlesListData!!)
                        binding!!.rvNewsList.adapter = articlesListAdapter
                    } else {
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












