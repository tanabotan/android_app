package com.example.bjt2205;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Menu extends Activity {

	Globals globals;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);

		//Globals_read
		globals = (Globals)this.getApplication();
		//
		globals.GlobalsAllInit();

		Button bt = (Button)findViewById(R.id.button1);
		bt.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				//ユーザ名取得
				EditText ed = (EditText)findViewById(R.id.editText1);
				globals.name = ed.getText().toString();
				CharSequence message = ed.getText();

				//チャット画面に移動
				if(TextUtils.isEmpty(message)){
					Toast.makeText(getApplicationContext(), "ユーザー名を入力してください", Toast.LENGTH_SHORT).show();
//					Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//					startActivity(intent);
				}
				else{
					Intent intent = new Intent(getApplicationContext(), MainActivity.class);
					startActivity(intent);
				}

			}
		});

		//終了
		Button bt2 = (Button)findViewById(R.id.button2);
		bt2.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				finish();
			}
		});

	}

}
