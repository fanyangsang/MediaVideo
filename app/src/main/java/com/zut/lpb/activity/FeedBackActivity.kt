package com.zut.lpb.activity

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import android.view.Window

import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.zut.lpb.R
import com.zut.lpb.base.BaseActivity
import kotlinx.android.synthetic.main.activity_feedback.*

class FeedBackActivity : BaseActivity() {
    private var dialog: ProgressDialog? = null
    var handler: Handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                100 -> {
                    if (dialog != null && dialog!!.isShowing) {
                        dialog!!.dismiss()
                    }
                    ToastUtils.showShort("提交成功")
                    finish()
                }
            }
        }
    }
    override fun getLayout(): Int {
        return R.layout.activity_feedback
    }

    override fun initView(savedInstanceState: Bundle?) {
        SPUtils.getInstance().put("isWeb", true)
    }

    override fun initListener() {
        iv_back!!.setOnClickListener { finish() }
        tv_sumbit!!.setOnClickListener { submitQuestion(ed_opinion!!.text.toString()) }
    }

    override fun onResume() {
        super.onResume()
        //初始化dialog
        if (dialog == null) {
            dialog = ProgressDialog(this)
            dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog!!.setCanceledOnTouchOutside(false)
            dialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
            dialog!!.setMessage("loading...")
        }
    }

    override fun onPause() {
        super.onPause()
        dialog = null
    }

    override fun initData() {}

    /**
     * 像后台提交问题反馈
     */
    private fun submitQuestion(text: String) {
        if (dialog != null && !dialog!!.isShowing) {
            dialog!!.show()
        }
        val message = Message()
        message!!.what = 100
        handler.sendMessageDelayed(message, 2000)

    }
}