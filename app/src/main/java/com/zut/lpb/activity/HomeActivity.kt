package com.zut.lpb.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.blankj.utilcode.util.SPUtils
import com.zut.lpb.R
import com.zut.lpb.base.BaseActivity
import com.zut.lpb.fragment.VideoListFragment
import com.zut.lpb.fragment.MineFragment
import com.zut.lpb.fragment.ManageFragment
import com.zut.lpb.fragment.MusicListFragment
import kotlinx.android.synthetic.main.activity_home.*
import java.util.ArrayList

class HomeActivity : BaseActivity() {
    var rbMainMine: RadioButton? = null
    private var fragmentManager: FragmentManager? = null
    private var manageFragment: Fragment? = null
    private var musicListFragment: Fragment? = null
    private var videoListFragment: Fragment? = null
    private var mineFragment :Fragment? = null
    private val fragments: MutableList<Fragment> = ArrayList()
    override fun getLayout(): Int {
        return R.layout.activity_home
    }

    override fun initView( savedInstanceState: Bundle?) {
        //判断缓存的token是否为空
        fragmentManager = supportFragmentManager
        initFragment()

    }
    override fun initListener() {
        rg_main!!.setOnCheckedChangeListener { radioGroup, i ->
            when (i) {
                R.id.main_pk -> onTab(0)
                R.id.main_train -> onTab(1)
                R.id.main_message -> {
                    if (TextUtils.isEmpty(SPUtils.getInstance().getString("token"))) {
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }else{
                        onTab(2)
                    }
                }
                R.id.main_mine -> {
                    if (TextUtils.isEmpty(SPUtils.getInstance().getString("token"))) {
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }else{
                        onTab(3)
                    }
                }
            }
        }
    }
    override fun initData() {}

    //默认页面的首页
    private fun initFragment() {
        rg_main!!.check(R.id.main_pk) //默认选中首页
        if (videoListFragment == null) {
            videoListFragment = VideoListFragment()
        }

        if (musicListFragment == null) {
            musicListFragment = MusicListFragment()
        }
        
        if (manageFragment == null){
            manageFragment = ManageFragment()
        }

        if (mineFragment == null){
            mineFragment = MineFragment()
        }
        fragments.clear()
        fragments.add(videoListFragment!!)
        fragments.add(musicListFragment!!)
        fragments.add(manageFragment!!)
        fragments.add(mineFragment!!)

        //将fragment文件放入FrameLayout中
        fragmentManager!!.beginTransaction()
                .add(R.id.fl_main, fragments[0])
                .add(R.id.fl_main, fragments[1])
                .add(R.id.fl_main, fragments[2])
                .add(R.id.fl_main, fragments[3])
                .hide(fragments[1])
                .hide(fragments[2])
                .hide(fragments[3])
                .commit()
    }
    /**
     * 切换tab
     */
    private fun onTab(position: Int) {
        fragmentManager!!.beginTransaction()
                .show(fragments[position])
                .hide(fragments[(position + 1) % 4])
                .hide(fragments[(position + 2) % 4])
                .hide(fragments[(position + 3) % 4])
                .commit()
    }
}