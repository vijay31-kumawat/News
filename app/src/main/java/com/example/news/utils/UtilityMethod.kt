@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.example.news.utils;

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.news.R
import com.example.news.databinding.DialogNoInternetConnectedBinding
import com.example.news.views.components.activities.home.HomeActivity
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object UtilityMethod {

    fun setTitleBarViews(
        mActivity: Activity,
        isImageViewRightVisible: Boolean,
        titleText: String
    ) {
        if (mActivity is HomeActivity) {

            mActivity.binding!!.layoutMain.appBar.imvCart.visibility =
                if (isImageViewRightVisible) View.VISIBLE else View.INVISIBLE
            mActivity.binding!!.layoutMain.appBar.txtTitle.text = titleText
        } else {
            Log.e("Error : ", "Error : mActivity is not instance of HomeActivity")
        }
    }


    @SuppressLint("MissingPermission")
    fun hasInternet(context: Context): Boolean {
        try {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnectedOrConnecting
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }


    fun showToastMessageDefault(activity: Activity, message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    fun showToastMessageSuccess(activity: Activity, message: String) {
        val toast = Toast(activity)
        toast.duration = Toast.LENGTH_LONG;
        val toastView = LayoutInflater.from(activity).inflate(R.layout.custom_toast, null)
        toastView.background = ContextCompat.getDrawable(activity, R.drawable.bg_round_green)

        val txtMessage = toastView.findViewById<TextView>(R.id.txtMessage)
        txtMessage.text = message

        toast.view = toastView
        toast.show()
    }

    fun showToastMessageFailed(activity: Activity, message: String) {
        val toast = Toast(activity)
        toast.duration = Toast.LENGTH_LONG;
        val toastView = LayoutInflater.from(activity).inflate(R.layout.custom_toast, null)
        toastView.background = ContextCompat.getDrawable(activity, R.drawable.bg_round_red)

        val txtMessage = toastView.findViewById<TextView>(R.id.txtMessage)
        txtMessage.text = message

        toast.view = toastView
        toast.show()
    }

    fun showToastMessageError(activity: Activity, message: String) {
        val toast = Toast(activity)
        toast.duration = Toast.LENGTH_LONG;
        val toastView = LayoutInflater.from(activity).inflate(R.layout.custom_toast, null)
        toastView.background = ContextCompat.getDrawable(activity, R.drawable.bg_round_red)

        val txtMessage = toastView.findViewById<TextView>(R.id.txtMessage)
        txtMessage.text = message

        toast.view = toastView
        toast.show()
    }

    @JvmStatic
    fun loadImage(view: ImageView?, imageUrl: String?) {
        Glide.with(view!!.context)
            .load(imageUrl)
            .placeholder(R.color.color_view)
            .error(ContextCompat.getDrawable(view.context, R.color.color_view))
            .into(view)
    }

    fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun showKeyboard(activity: Activity) {
        val imm: InputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.toggleSoftInputFromWindow(view.windowToken, InputMethodManager.SHOW_FORCED, 0)
    }

    fun showSnackBarMsgError(activity: Activity, message: String?) {
        val parentLayout = activity.findViewById<View>(android.R.id.content)
        Snackbar.make(parentLayout, message!!, Snackbar.LENGTH_LONG)
            .setAction("CLOSE") {
                //                        finish();
            }
            .setActionTextColor(activity.resources.getColor(R.color.colorRed))
            .show()
    }

    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            (context as Activity).getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = connectivityManager.activeNetworkInfo
        val isConnected = netInfo != null && netInfo.isConnected
        return isConnected
    }

    @SuppressLint("SimpleDateFormat")
    fun getTimeStampToDate(date: String): String {
         var newDate: String? = ""
        try {
            val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            val past: Date = format.parse(date)
            val now = Date()
            val seconds: Long = TimeUnit.MILLISECONDS.toSeconds(now.time - past.time)
            val minutes: Long = TimeUnit.MILLISECONDS.toMinutes(now.time - past.time)
            val hours: Long = TimeUnit.MILLISECONDS.toHours(now.time - past.time)
            val days: Long = TimeUnit.MILLISECONDS.toDays(now.time - past.time)

            if (seconds < 60) {
                println("$seconds seconds ago")
                newDate = "$seconds seconds ago"
            } else if (minutes < 60) {
                println("$minutes minutes ago")
                newDate = "$minutes minutes ago"
            } else if (hours < 24) {
                println("$hours hours ago")
                newDate = "$hours hours ago"
            } else {
                println("$days days ago")
                newDate = "$days days ago"
            }
        } catch (j: java.lang.Exception) {
            j.printStackTrace()
            newDate=""
        }

        return newDate!!
    }

    fun showNoInternetConnectedDialog(mActivity: Activity) {
        val dialog = Dialog(mActivity, R.style.Theme_Dialog)
        val dialogBinding: DialogNoInternetConnectedBinding = DataBindingUtil.inflate(
            LayoutInflater.from(mActivity),
            R.layout.dialog_no_internet_connected,
            null,
            false
        )
        dialogBinding.tvTitle.text = mActivity.getText(R.string.internet_issue)
        dialogBinding.tvMessage.text = mActivity.getText(R.string.please_check_your_internet)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.window!!.setGravity(Gravity.CENTER)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
        dialog.setContentView(dialogBinding.getRoot())

        dialogBinding.btnOK.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
        })
        dialog.show()
    }
}