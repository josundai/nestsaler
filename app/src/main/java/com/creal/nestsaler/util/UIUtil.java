package com.creal.nestsaler.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.creal.nestsaler.R;

public class UIUtil {
    /**
     * 得到自定义的progressDialog
     * @param context
     * @param msg
     * @return
     */
    public static Dialog showLoadingDialog(Context context, String msg, boolean cancelable) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.loading_dialog, null);// 得到加载view
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
        // main.xml中的ImageView
        ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
        TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
        // 加载动画
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(context, R.anim.loading_animation);
        // 使用ImageView显示动画
        spaceshipImage.startAnimation(hyperspaceJumpAnimation);
        tipTextView.setText(msg);// 设置加载信息

        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog

        loadingDialog.setCancelable(cancelable);// 不可以用“返回键”取消
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
        loadingDialog.show();
        return loadingDialog;
    }


    public static void showConfirmDialog(Context context, int msgResId, int positiveBtnResId, DialogInterface.OnClickListener positiveBtnListener, int negativeBtnResId, DialogInterface.OnClickListener negativeBtnListener){
        AlertDialog.Builder builder;
        if(Build.VERSION.SDK_INT > 10) {
            builder = new AlertDialog.Builder(context, R.style.Theme_CustomDialog_Alert);
        }else
            builder = new AlertDialog.Builder(context);
        builder.setMessage(msgResId);
        builder.setPositiveButton(positiveBtnResId, positiveBtnListener);

        if(negativeBtnResId !=0 && negativeBtnListener != null)
            builder.setNegativeButton(negativeBtnResId,negativeBtnListener);
        builder.create().show();
    }

}
