package com.example.upesh.timetable;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by upesh on 26/9/17.
 */

public class show_dialog {


    public void dialogbox(Context context, String string,String str)
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage(string);
        builder1.setTitle(str);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder1.show();
    }

}

