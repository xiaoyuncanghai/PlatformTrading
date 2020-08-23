package com.pt.lib_common.view.citychoose.adapter;


import com.pt.lib_common.view.citychoose.model.City;

public interface InnerListener {
    void dismiss(int position, City data);
    void locate();
}
