package com.jgg.games.view.delegate;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.jgg.games.R;
import com.jgg.games.adapter.BetAdapter;
import com.jgg.games.adapter.RotationBannerAdapter;
import com.jgg.games.model.entity.AnnouncementEntity;
import com.jgg.games.model.entity.IndexBannerEntity;
import com.jgg.games.view.base.RecyclerRefreshDelegate;
import com.jgg.games.widget.MarqueeFactory;
import com.jgg.games.widget.MarqueeView;
import com.jgg.games.widget.NoticeMarquee;

import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * Created by Administrator on 2017/3/20 0020.
 */

public class IndexBetFrgDelegate extends RecyclerRefreshDelegate {

    private MarqueeView marqueeView;
    private BGABanner banner;


    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_recycle_notitle;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        showTitle(false);
    }

    @Override
    public void initValue() {
        super.initValue();
        setEmptyView(R.layout.layout_nodata);
    }

    public void addHead(BetAdapter adapter){
//        View header =  addHeadView(adapter,R.layout.layout_banner);
        View header = LayoutInflater.from(this.getActivity()).inflate(R.layout.layout_banner, null, false);
        banner = get(header,R.id.banner);
        marqueeView = get(header, R.id.tv_marquee);
        marqueeView.setVisibility(View.GONE);

    }


    public void setMarqueeVisiable(boolean show){
        setViewGoneOrVisible(marqueeView,show);
    }

    public void setBannerVisiable(boolean show){
        setViewGoneOrVisible(banner,show);
    }

    public void onResume() {
        if (!marqueeView.isFlipping() && marqueeView.getVisibility() == View.VISIBLE){
            marqueeView.startFlipping();
        }
    }

    public void onStop() {
        if (marqueeView.isFlipping() && marqueeView.getVisibility() == View.VISIBLE){
            marqueeView.stopFlipping();
        }
    }

    public void setFactory(List<AnnouncementEntity> entity){
        MarqueeFactory<TextView, AnnouncementEntity> marqueeFactory = new NoticeMarquee(this.getActivity());
        marqueeView.setMarqueeFactory(marqueeFactory);
        marqueeFactory.resetData(entity);
    }

    public void setBannerAdapter(RotationBannerAdapter adapter, List<IndexBannerEntity> list,List<String> tipList){
        banner.setAdapter(adapter);
        banner.setData(list, tipList);
    }

    public void onStart() {
        marqueeView.startFlipping();
    }
}
