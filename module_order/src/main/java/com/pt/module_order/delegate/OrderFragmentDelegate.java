package com.pt.module_order.delegate;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
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
    private String[] mTitles = new String[]{ "买入","卖出","资金"};
    public List<SupportFragment> fragments = new ArrayList<SupportFragment>();
    private TabTitlePager adapter;

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
        //fragments.add(Order1AllFragment.newInstance());
        fragments.add(Order2BuyFragment.newInstance());
        fragments.add(Order3SaleFragment.newInstance());
        fragments.add(Order4MoneyFragment.newInstance());
        adapter = new TabTitlePager(fm, 1);
        //adapter.addPage(Order1AllFragment.newInstance(), "全部");
        adapter.addPage(Order2BuyFragment.newInstance(), "买入");
        adapter.addPage(Order3SaleFragment.newInstance(), "卖出");
        adapter.addPage(Order4MoneyFragment.newInstance(), "资金");
        adapter.notifyDataSetChanged();
        vp_order.setAdapter(adapter);
        vp_order.setOffscreenPageLimit(100);
        tl_order.setupWithViewPager(vp_order);
        vp_order.setCurrentItem(0);
        tl_order.getTabAt(0).select();
    }


    public class TabTitlePager extends FragmentStatePagerAdapter {
        private ArrayList<SupportFragment> mPageFragment = new ArrayList<>();
        private ArrayList<String> mTitles = new ArrayList<String>();

        public TabTitlePager(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        void addPage(SupportFragment fgt, String name){
            mPageFragment.add(fgt);
            mTitles.add(name);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return mPageFragment.get(position);
        }

        @Override
        public int getCount() {
            return mPageFragment.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }
    }

}

