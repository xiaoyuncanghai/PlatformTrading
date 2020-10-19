package com.pt.platformtrading_location.delegate;

import android.os.Bundle;

import com.pt.lib_common.themvp.view.AppDelegate;
import com.pt.module_homepage.HomePageFragment;
import com.pt.module_mine.MineFragment;
import com.pt.module_near.NearFragment;
import com.pt.module_order.fragment.OrderFragment;
import com.pt.platformtrading_location.R;

import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageNavigationView;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;
import me.yokeyword.fragmentation.SupportFragment;

public class MainActDelegate extends AppDelegate {
    public PageNavigationView pageNavigationView;
    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FOURTH = 3;

    private SupportFragment[] mFragments = new SupportFragment[4];
    private NavigationController navigationController;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        if (savedInstanceState == null) {
            mFragments[FIRST] = HomePageFragment.newInstance();
            mFragments[SECOND] = NearFragment.newInstance();
            mFragments[THIRD] = OrderFragment.newInstance();
            mFragments[FOURTH] = MineFragment.newInstance();
            loadMultipleRootFragment(R.id.main_content, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD],
                    mFragments[FOURTH]
            );
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
            // 这里我们需要拿到mFragments的引用,也可以通过getSupportFragmentManager.getFragments()自行进行判断查找(效率更高些),用下面的方法查找更方便些
            mFragments[FIRST] = findFragment(HomePageFragment.class);
            mFragments[SECOND] = findFragment(NearFragment.class);
            mFragments[THIRD] = findFragment(OrderFragment.class);
            mFragments[FOURTH] = findFragment(MineFragment.class);
        }
        initView();
    }

    public HomePageFragment getHomepageFragment(){
        return findFragment(HomePageFragment.class);
    }

    public NearFragment getNearFragment() {
        return findFragment(NearFragment.class);
    }

    public OrderFragment getOrder2Fragment() {
        return findFragment(OrderFragment.class);
    }

    private void initView() {
        pageNavigationView = get(R.id.bottom_tab);
        navigationController = pageNavigationView.material()
                .addItem(R.drawable.tab_homepage, R.drawable.tab_homepage_press, "首页")
                .addItem(R.drawable.tab_near, R.drawable.tab_near_press, "附近")
                .addItem(R.drawable.tab_order, R.drawable.tab_order_press, "订单")
                .addItem(R.drawable.tab_mine, R.drawable.tab_mine_press, "我的")
                .build();
        navigationController.addTabItemSelectedListener(new OnTabItemSelectedListener() {
            @Override
            public void onSelected(int index, int old) {
                showHideFragment(mFragments[index]);
                if (index == 1) {
                    getNearFragment().refreshData();
                }
            }

            @Override
            public void onRepeat(int index) {
            }
        });
    }

}
