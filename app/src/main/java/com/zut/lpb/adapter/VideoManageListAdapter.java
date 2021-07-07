package com.zut.lpb.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zut.lpb.R;
import com.zut.lpb.bean.VideoBean;

import java.util.List;

import taobe.tec.jcc.JChineseConvertor;

public class VideoManageListAdapter extends BaseQuickAdapter<VideoBean, BaseViewHolder> {

    public VideoManageListAdapter(@Nullable List<VideoBean> data) {
        super(R.layout.item_video_manage, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, VideoBean item) {

        try{
            Glide.with(mContext).load(R.drawable.icon_header).into((ImageView) helper.getView(R.id.iv_goods_image));
            helper.setText(R.id.tv_product_name, JChineseConvertor.getInstance().s2t(item.getName()))
                    .setText(R.id.tv_product_desc,JChineseConvertor.getInstance().s2t("创建时间："+item.getCreateTime()) )
                    .setVisible(R.id.tv_video_tag,item.getType().equals("0"))
                    .setVisible(R.id.tv_music_tag,item.getType().equals("1"))
                    .addOnClickListener(R.id.tv_remove);
        }catch (Exception e){

        }

    }
}
