package com.example.bjt2205;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	Globals globals;
	Timer timer;

	private EditText et;
	private LinearLayout mainLayout;
	private LinearLayout scrollLayout;
	private long repeatInterval = 5000;//何ミリ秒間隔で実行するか(default==50)


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Log.v("TAG", "Main");
		//Globals変数取得
		globals = (Globals)this.getApplication();
		//初期化
		//globals.GlobalsAllInit();
		globals.Globalsdefaultsinit();//ポート番号を50000にする


		Toast.makeText(getApplicationContext(), "まずは右上のボタンからIPaddressを選び、サーバのIPaddressを入力してください", Toast.LENGTH_SHORT).show();

		//タイマーのセット
		timer = new Timer();
		TimerTask timertask = new Task1(this);
		timer.scheduleAtFixedRate(timertask, 0, repeatInterval);
		Log.v("TAG", "Main Timer");
		Toast.makeText(getApplicationContext(), "メッセージの受信を開始しました", Toast.LENGTH_SHORT).show();

		//レイアウト---------------------------------------------------------------------------------------

		// 縦に追加
		mainLayout = new LinearLayout(this);
		mainLayout.setOrientation(LinearLayout.VERTICAL);
		setContentView(mainLayout);

		// 横に追加
		LinearLayout linear1 = new LinearLayout(this);
		linear1.setOrientation(LinearLayout.HORIZONTAL);
		mainLayout.addView(linear1);

		//画面表示・動作
		//ユーザ名表示
		TextView tv1 = new TextView(this);
		tv1.setWidth(200);
		tv1.setText(globals.name);
		linear1.addView(tv1);

		et = new EditText(this);
		et.setWidth(500);
		linear1.addView(et);

		Button bt1 = new Button(this);
		bt1.setText("送信");
		bt1.setOnClickListener(new SendAction());
		linear1.addView(bt1);


		Button bt2 = new Button(this);
		bt2.setText("戻る");
		bt2.setOnClickListener(new Escape());
		linear1.addView(bt2);

		ScrollView sv = new ScrollView(this);
		mainLayout.addView(sv);

		scrollLayout = new LinearLayout(this);
		scrollLayout.setOrientation(LinearLayout.VERTICAL);
		sv.addView(scrollLayout);





	}


	//受信部分
	public void ReceiveService(){
		//
		globals = (Globals)this.getApplication();
		new Thread(){
			public void run(){
				try{
					int port = globals.port;
					Log.v("TAG", "receivePort = port:"+globals.port);

					ServerSocket ss = new ServerSocket(port);
					Log.v("TAG", "ServerSocket ss = new ServerSocket(port);");

					Socket s = ss.accept();
					Log.v("TAG", "Socket s = ss.accept();");

					s.setSoLinger(true, 0);


					BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
					Log.v("TAG", "BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));");

					String message;
					final StringBuilder messageBuilder = new StringBuilder();
					Log.v("TAG", "final StringBuilder messageBuilder = new StringBuilder();");
					while((message = br.readLine())!=null){
						messageBuilder.append(message);
						Log.v("messageBuilder.append(message);" + message, message);
					}
					globals.receivetext = message;
					Log.v("TAG", "globals.receivetext = message;"+ globals.receivetext + "=" + message);

//					br.close();
//					Log.v("TAG", "br.close();");
//					ss.close();
//					Log.v("TAG", "ss.close();");
				}
				catch(IOException e){
					e.printStackTrace();
					Log.v("TAG", "IO");
				}
				Log.v("TAG", "Receives");


			}
		}.start();
		//表示する機能
		TextView tv3 = new TextView(this);

		tv3.setText(globals.receivetext);
		Log.v("TAG", "tv2.setText(globals.receivetext);"+globals.receivetext+"="+tv3);
		scrollLayout.addView(tv3);
		Log.v("TAG", "scrollLayout.addView(tv2);"+tv3);
		//globals.receivetextを初期化
		globals.GlobalsTextinit();
		Log.v("TAG", "globals.GlobalsTextinit();");




	}

	//送信部分
	class SendAction implements OnClickListener{



		public void onClick(View v){
			new Thread(){
				public void run(){


					try{
						String host = globals.sip;
						Log.v("TAG", "host = sip:"+globals.sip);
						int port = globals.port;
						Log.v("TAG", "Port = port:"+globals.port);
						String data = globals.name +":" + et.getText().toString();
						Log.v("TAG", "Sendingdata:"+data);
						Socket s = new Socket(host, port);
						Log.v("TAG", "Sock:"+host+":"+port);
						PrintWriter pw = new PrintWriter(s.getOutputStream());

						pw.println(data);
						pw.flush();
						s.shutdownOutput();
						s.close();
						Log.v("TAG", "close sock");

					}
					catch(IOException e){
						e.printStackTrace();
						Toast.makeText(getApplicationContext(), "メッセージの送信に失敗しました", Toast.LENGTH_SHORT).show();

					}
				}
			}.start();
		}
	}

	//Escape
	class Escape implements OnClickListener{
		public void onClick(View v){
			Toast.makeText(getApplicationContext(), "メッセージの受信を終了しました", Toast.LENGTH_SHORT).show();
			finish();
		}
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.ipaddress){
			Intent intent = new Intent(getApplicationContext(), setting.class);
			startActivity(intent);
		}
//		if (id == R.id.action_settings) {
//			return true;
//		}
		return super.onOptionsItemSelected(item);
	}
}
