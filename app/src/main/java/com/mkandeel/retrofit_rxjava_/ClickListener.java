package com.mkandeel.retrofit_rxjava_;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

public interface ClickListener {
    void onItemClickListener(int position, @Nullable Bundle extra);

    void onLongItemClickListener(int position, @Nullable Bundle extra);
}