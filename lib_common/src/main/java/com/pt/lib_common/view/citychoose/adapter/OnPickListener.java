package com.pt.lib_common.view.citychoose.adapter;


import com.pt.lib_common.view.citychoose.model.City;

public interface OnPickListener {
    void onPick(int position, City data);
    void onLocate();
    void onCancel();
}
