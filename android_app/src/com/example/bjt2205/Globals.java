package com.example.bjt2205;

import android.app.Application;

public class Globals extends Application {
	int port;									//ポート番号
	int cip;									//クライアントのIPアドレス
	String sip;									//サーバーのIPアドレス
	int hoge;									//特になし
	String receivetext;							//サーバーから受け取ったもの
	String name;								//ユーザ名
	CharSequence message;						//ユーザ名未入力判定の時に使用



	public void GlobalsAllInit(){
		port = 0;
		cip = 0;
		sip = "";
		hoge = 0;
		receivetext = "";
		name = "";
		message = "";

	}

	public void Globalsdefaultsinit(){
		port = 50000;

	}

	public void Globalsmessageinit(){
		message = "";//ユーザ名入力がなかった場合に一度リセット
	}

	public void GlobalsTextinit(){
		receivetext = "";//受け取ったメッセージを表示した後にリセット
	}
	public void Globalssipinit(){
		sip = "";
	}
}
