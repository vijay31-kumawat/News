package com.example.news.views.components.adapters


import android.app.Activity
import android.util.Log
import android.view.View
import androidx.databinding.ViewDataBinding
import com.example.news.R
import com.example.news.pojo.GetArticlesListDataItem
import com.example.news.utils.UtilityMethod
import com.example.news.views.base.RecyclerBaseAdapter
import kotlinx.android.synthetic.main.row_articles_list.view.*


class ArticlesListAdapter(
    private val mActivity: Activity?,
    private val onClickListener: View.OnClickListener?,
    private val list: ArrayList<GetArticlesListDataItem>?
) : RecyclerBaseAdapter() {
    var selectedPos = 0
    override fun getLayoutIdForPosition(position: Int): Int = R.layout.row_articles_list

    override fun getViewModel(position: Int): Any? = list!![position]

    override fun putViewDataBinding(viewDataBinding: ViewDataBinding, position: Int) {
        viewDataBinding.root.rowArticlesListItem.tag = position
        viewDataBinding.root.rowArticlesListItem.setOnClickListener(onClickListener)
        viewDataBinding.root.news_title.text = list!!.get(position)?.title
        viewDataBinding.root.news_description.text = list!!.get(position)?.summary
        UtilityMethod.loadImage(viewDataBinding.root.news_img, list!!.get(position)!!.imageUrl)
        viewDataBinding.root.news_time.text = UtilityMethod.getTimeStampToDate(list!!.get(position)?.publishedAt!!)
        Log.e("Check","date - "+UtilityMethod.getTimeStampToDate(list!!.get(position)?.publishedAt!!))

    }

    override fun getItemCount(): Int = if (list == null) 0 else list.size
}






















