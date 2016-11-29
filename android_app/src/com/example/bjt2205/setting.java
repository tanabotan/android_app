package com.example.bjt2205;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class setting extends Activity {
	Globals globals;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);

		globals = (Globals)this.getApplication();

		Button bt1 = (Button)findViewById(R.id.button1);
		bt1.setOnClickListener(new OnClickListener(){
			public void onClick(View v){

				EditText ed1 = (EditText)findViewById(R.id.editText1);
				Log.v("setting", "EditText");
				String ed = ed1.getText().toString();
				Log.v("setting", "getText");
				globals.Globalssipinit();
				globals.sip = ed;
				Log.v("setting", "sip = ed");
				finish();
			}

		});




	}


}
