/*
 * Copyright (c) 2015, 张涛.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.pt.lib_common.themvp.presenter;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.apkfuns.logutils.LogUtils;
import com.pt.lib_common.R;
import com.pt.lib_common.base.BaseFragment;
import com.pt.lib_common.themvp.view.IDelegate;
import com.pt.lib_common.util.Utils;


/**
 * Presenter base class for Fragment
 * Presenter层的实现基类
 *
 * @param <T> View delegate class type
 * @author kymjs (http://www.kymjs.com/) on 10/23/15.
 */
public abstract class FragmentPresenter<T extends IDelegate> extends BaseFragment {
    public T viewDelegate;
    public TextView tv_left;
    public TextView tv_title;
    public TextView tv_right;
    public TextView tv_right_text;
    private Typeface typeface;


    //Fragment的View加载完毕的标记
    private boolean isViewCreated;

    //Fragment对用户可见的标记
    private boolean isUIVisible;
    private boolean isFirst = true;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            viewDelegate = getDelegateClass().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        }
    }

    protected void lazyLoad() {

        if (isViewCreated && isUIVisible) {
            if (isFirst){
                initData();
            }
            //数据加载完毕,恢复标记,防止重复加载
            isViewCreated = false;
            isUIVisible = false;
            isFirst = false;
            LogUtils.d(getClass().getName() + "->initData()");
        }

    }

   /* @Override
    public void onResume() {
        super.onResume();
        if (tv_right_tip!=null){
            if (com.yuu1.p2p.utils.T.getUser()!=null&& com.yuu1.p2p.utils.T.getUser().isLogin()&&tv_right_tip.getVisibility()==View.VISIBLE) {
                String param = "uid=" + MyApplication.getInstance().getUser().getUid() + "&page=1&token=" + MyApplication.getInstance().getUser().getToken() + "&platform=1";
                Map<String, Object> fieldMap = new LinkedHashMap<>();
                fieldMap.put("param", RSAUtil.encryptByPublicKeyWithBase64ForSplit(param));
                Mango.getInstance().baseUrl(Constant.BASE_URL_PRODUCTION).postWithCache(param, this.getActivity(), Constant.API_MSG_LIST, fieldMap, MsgBean.class, new NetCallback<MsgBean>() {

                    @Override
                    public void onBefore(Activity activity) {

                    }

                    @Override
                    public void onResponse(Call<ResponseBody> call, MsgBean data) {
                        super.onResponse(call, data);
                        if ("1".equals(data.getStatus())) {
                            tv_right_tip.setBadgeCount(data.getResult().getCount(), true);
                        } else {
                            tv_right_tip.setBadgeCount(0, true);
                        }
                    }

                    @Override
                    public void onLoadFromCache(MsgBean msgBean) {
                        super.onLoadFromCache(msgBean);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, CommonException exception) {
                        super.onFailure(call, exception);
                        tv_right_tip.setBadgeCount(0, true);
                    }
                });
            }
        }
    }*/

    //  统一处理消息未读条数显示
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){


        }
    }

    //do something
    protected void onInvisible() {


    }


    public abstract void initData();




    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isUIVisible = true;
            lazyLoad();
        } else {
            isUIVisible = false;
            onInvisible();
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        if (viewDelegate.getRootView()==null){
            viewDelegate.create(inflater, container, savedInstanceState);
            initStatusbar(viewDelegate.getRootView());
            initActionbar(viewDelegate.getRootView());
            bindEventListener();
            viewDelegate.initWidget(savedInstanceState);
        }
        return viewDelegate.getRootView();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogUtils.d(getClass().getName().toString()+"onViewCreated");
        isViewCreated = true;
        lazyLoad();


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //页面销毁,恢复标记
        isViewCreated = false;
        isUIVisible = false;

        LogUtils.d(getClass().getName() + "页面销毁了");
    }

    private void initActionbar(View view) {
            typeface= Typeface.createFromAsset(getActivity().getAssets(),
                    "iconfont/iconfont.ttf");
            LinearLayout ll_actionbar = (LinearLayout) view.findViewById(R.id.ll_action_bar);
            if (ll_actionbar!=null){
                tv_left = (TextView)view.findViewById(R.id.tv_left);
                tv_title = (TextView)view.findViewById(R.id.tv_title);
                tv_right = (TextView)view.findViewById(R.id.tv_right);
                tv_right_text = (TextView)view.findViewById(R.id.tv_right_text);
                if (tv_left!=null){
                    tv_left.setOnClickListener(clickListener);
                }
                if (tv_right!=null){
                    tv_right.setOnClickListener(clickListener);
                }
            }

    }

    public void setLeft(String unicodeText) {
        tv_left.setTypeface(typeface);
        tv_left.setText(unicodeText);
    }
    public void setRightTextColor(int color){
        tv_right.setTextColor(color);
    }
    public void setLeftTextColor(int color){
        tv_left.setTextColor(color);
    }

    public void setTitle(String text) {
        tv_title.setText(text);
    }

    public void setRight(String unicodeText) {
        tv_right.setTypeface(typeface);
        tv_right.setText(unicodeText);

    }

    public void setRightTextSize(float textSize) {
        tv_right.setTextSize(textSize);
    }

    public void setRightText(String text) {
        tv_right.setText(text);

    }

    public void onLeftClick(View v) {

    }
    public void onRightClick(View v) {


    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.tv_left){
                onLeftClick(v);
            }else if (v.getId() == R.id.tv_right){
                onRightClick(v);
            }
        }
    };




    protected void bindEventListener() {
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (viewDelegate.getOptionsMenuId() != 0) {
            inflater.inflate(viewDelegate.getOptionsMenuId(), menu);
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (viewDelegate == null) {
            try {
                viewDelegate = getDelegateClass().newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewDelegate = null;
    }

    protected abstract Class<T> getDelegateClass();


    private void initStatusbar(View rootView) {
        View view = rootView.findViewById(R.id.v_status_bar);
        if (view!=null){
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            param.height = Utils.getStatusBarHeight(this.getActivity());
            view.setLayoutParams(param);
        }
    }
}
