package com.mkandeel.retrofit_rxjava_.Helper;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;

import com.mkandeel.retrofit_rxjava_.R;

import java.util.Objects;

public class Helper {
    private static Helper helper;
    private Fragment fragment;
    private Context context;

    private Helper(Fragment fragment) {
        this.context = fragment.requireContext();
        this.fragment = fragment;
    }

    public static Helper getInstance(Fragment fragment) {
        if (helper == null) {
            helper = new Helper(fragment);
        }
        return helper;
    }

    public Dialog showDialog(Context context, int dialogLayout, int gravity, boolean useAnimation) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogLayout);
        dialog.show();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.fragment.requireActivity().
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());

        // setting width to 100% of display
        layoutParams.width = (int) (displayMetrics.widthPixels * 1.0f);

        // setting height to 100% of display
        layoutParams.height = (int) (displayMetrics.heightPixels * 1.0f);
        dialog.getWindow().setAttributes(layoutParams);
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if (useAnimation) {
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        }
        dialog.getWindow().setGravity(gravity);

        return dialog;
    }
}
