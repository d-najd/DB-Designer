package com.umldesigner;

import android.content.Context;
import android.widget.Toast;
import androidx.annotation.WorkerThread;

public class Message {
    @WorkerThread
    public static void message(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
    @WorkerThread
    public static void defErrMessage(Context context){
        Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show();
    }
}