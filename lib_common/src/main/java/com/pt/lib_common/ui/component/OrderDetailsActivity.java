package com.pt.lib_common.ui.component;

import com.pt.lib_common.themvp.presenter.ActivityPresenter;
import com.pt.lib_common.ui.delegate.OrderDetailsActDelegate;

public class OrderDetailsActivity extends ActivityPresenter<OrderDetailsActDelegate> {
    @Override
    protected Class<OrderDetailsActDelegate> getDelegateClass() {
        return OrderDetailsActDelegate.class;
    }
}
