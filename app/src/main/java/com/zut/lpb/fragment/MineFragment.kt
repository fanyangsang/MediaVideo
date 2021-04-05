package com.zut.lpb.fragment

import android.app.AlertDialog
import android.content.Intent
import android.view.View
import com.blankj.utilcode.util.SPUtils
import com.zut.lpb.R
import com.zut.lpb.activity.AboutActivity
import com.zut.lpb.activity.FeedBackActivity
import com.zut.lpb.activity.LoginActivity
import com.zut.lpb.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_mine.*

class MineFragment : BaseFragment() {
    override fun getLayout(): Int {
      return R.layout.fragment_mine
    }

    override fun initView(view: View?) {

    }

    override fun initListener(view: View?) {
        rl_about_us!!.setOnClickListener {
            val intent = Intent(context, AboutActivity::class.java)
            startActivity(intent)
        }

        rl_feedback!!.setOnClickListener {
            val intent1 = Intent(context, FeedBackActivity::class.java)
            startActivity(intent1)
        }

        rl_exit.setOnClickListener { quitDialog() }
    }

    override fun initData() {
    }

    /**
     * 返回按钮对应的弹窗
     */
    private fun quitDialog() {
        val builder = AlertDialog.Builder(context)
        builder.setMessage("确认退出登录")
        builder.setPositiveButton("确定") { dialogInterface, i -> //将登录状态设为未登录
            SPUtils.getInstance().put("token", "")
            val intent2 = Intent(context, LoginActivity::class.java)
            startActivity(intent2)
        }
        builder.setNeutralButton("取消", null)
        val dialog = builder.create()
        dialog.show()
    }
}