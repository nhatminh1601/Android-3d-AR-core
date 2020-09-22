package com.example.location.helpers;

import android.content.Context;

public class CustomAlertDialog {
    Context context;

    public CustomAlertDialog(Context context) {
        this.context = context;
    }

    public void show(String message){
        new android.app.AlertDialog.Builder(context)
                .setTitle("Thông báo")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }
}
