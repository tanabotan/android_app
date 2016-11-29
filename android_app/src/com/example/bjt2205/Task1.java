package com.example.bjt2205;

import java.util.TimerTask;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

public class Task1 extends TimerTask {
	private Handler handler;
	private Context context;

	public Task1 (Context context){
		handler = new Handler();
		this.context = context;
	}

	public void run() {
		// TODO 自動生成されたメソッド・スタブ
		handler.post(new Runnable() {
			public void run(){
				((MainActivity)context).ReceiveService();
				//
				Log.v("Task1", "Task1");

			}
		});
	}

}
