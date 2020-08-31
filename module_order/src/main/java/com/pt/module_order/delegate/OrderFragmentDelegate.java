package com.pt.module_order.delegate;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.pt.lib_common.themvp.view.AppDelegate;
import com.pt.module_order.R;
import com.pt.module_order.fragment.Order1AllFragment;
import com.pt.module_order.fragment.Order2BuyFragment;
import com.pt.module_order.fragment.Order3SaleFragment;
import com.pt.module_order.fragment.Order4MoneyFragment;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.SupportFragment;

public class OrderFragmentDelegate extends AppDelegate {

    private TabLayout tl_order;
    private ViewPager vp_order;
    private String[] mTitles = new String[]{ "全部","买入","卖出","资金"};
    public List<SupportFragment> fragments = new ArrayList<SupportFragment>();
    private TabTitlePager mAdapter;

    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_order;
    }

    @Override
    public void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        tl_order = get(R.id.tl_order);
        vp_order = get(R.id.vp_order);
    }

    public void initVp(FragmentManager fm) {
        fragments.add(Order1AllFragment.newInstance());
        fragments.add(Order2BuyFragment.newInstance());
        fragments.add(Order3SaleFragment.newInstance());
        fragments.add(Order4MoneyFragment.newInstance());
        for (int i = 1; i < mTitles.length; i++) {
            tl_order.addTab(tl_order.newTab().setText(mTitles[i - 1]));
        }
        mAdapter = new TabTitlePager(fm);
        vp_order.setAdapter(mAdapter);
        tl_order.setupWithViewPager(vp_order);
        vp_order.setCurrentItem(0);
        tl_order.getTabAt(0).select();
    }


    public class TabTitlePager extends FragmentPagerAdapter {
        private LayoutInflater mInflater;

        public TabTitlePager(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return mTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }
    }

}

