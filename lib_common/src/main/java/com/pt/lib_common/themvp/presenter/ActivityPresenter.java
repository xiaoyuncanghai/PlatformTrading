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

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.pt.lib_common.R;
import com.pt.lib_common.base.BaseActivity;
import com.pt.lib_common.themvp.view.IDelegate;
import com.pt.lib_common.util.Utils;


/**
 * Presenter base class for Activity
 * Presenter层的实现基类
 *
 * @param <T> View delegate class type
 * @author kymjs (http://www.kymjs.com/) on 10/23/15.
 */
public abstract class ActivityPresenter<T extends IDelegate> extends BaseActivity {
    public T viewDelegate;
    public TextView tv_left;
    public TextView tv_title;
    public TextView tv_right;
    public TextView tv_right_text;
    private Typeface typeface;

    public ActivityPresenter() {
        try {
            viewDelegate = getDelegateClass().newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("create IDelegate error");
        } catch (IllegalAccessException e) {
            throw new RuntimeException("create IDelegate error");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewDelegate.create(getLayoutInflater(), null, savedInstanceState);
        setContentView(viewDelegate.getRootView());
        initToolbar();
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= 19) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        initStatusbar();
        initActionbar();
        viewDelegate.setActivity(this);
        viewDelegate.initWidget(savedInstanceState);
        bindEventListener();
    }

    private void initActionbar() {
        typeface = Typeface.createFromAsset(this.getAssets(),
                "iconfont/iconfont.ttf");
        LinearLayout view = (LinearLayout) findViewById(R.id.ll_action_bar);
        if (view != null) {
            tv_left = (TextView) findViewById(R.id.tv_left);
            tv_title = (TextView) findViewById(R.id.tv_title);
            tv_right = (TextView) findViewById(R.id.tv_right);
            tv_right_text = (TextView) findViewById(R.id.tv_right_text);
            if (tv_left != null) {
                tv_left.setOnClickListener(clickListener);
            }
            if (tv_right != null) {
                tv_right.setOnClickListener(clickListener);
            }
        }
    }

    public void setLeft(String unicodeText) {
        tv_left.setTypeface(typeface);
        tv_left.setText(unicodeText);
    }

    public void setLeftTextColor(int color) {
        tv_left.setTextColor(color);
    }

    public void setRightTextColor(int color) {
        tv_right.setTextColor(color);
    }

    public void setTitleTextColor(int color) {
        tv_title.setTextColor(color);
    }

    public void setTitle(String text) {
        tv_title.setText(text);
    }

    public void setRight(String unicodeText) {
        tv_right.setTypeface(typeface);
        tv_right.setText(unicodeText);
    }

    public void setRightText(String text) {
        tv_right.setText(text);
    }

    public void setRightTextSize(float textSize) {
        tv_right.setTextSize(textSize);
    }


    public void onLeftClick(View v) {
    }

    public void onRightClick(View v) {

    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.tv_left) {
                onLeftClick(v);
            } else if (v.getId() == R.id.tv_right) {
                onRightClick(v);
            }
        }
    };


    protected void bindEventListener() {
    }

    protected void initToolbar() {
        Toolbar toolbar = viewDelegate.getToolbar();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (viewDelegate == null) {
            try {
                viewDelegate = getDelegateClass().newInstance();
            } catch (InstantiationException e) {
                throw new RuntimeException("create IDelegate error");
            } catch (IllegalAccessException e) {
                throw new RuntimeException("create IDelegate error");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (viewDelegate.getOptionsMenuId() != 0) {
            getMenuInflater().inflate(viewDelegate.getOptionsMenuId(), menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewDelegate = null;
    }

    protected abstract Class<T> getDelegateClass();


    private void initStatusbar() {
        View view = findViewById(R.id.v_status_bar);
        if (view != null) {
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            param.height = Utils.getStatusBarHeight(this);
            view.setLayoutParams(param);
        }
    }
}
