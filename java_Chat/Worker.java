import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Worker implements Runnable{

	Socket sock;
	PrintWriter out;
	BufferedReader in;
	ChatServer chatServer;
	int n;

	public Worker (int n, Socket s, ChatServer cs){
		this.n = n;
		chatServer = cs;
		sock = s;
		out = null;
		in = null;

	}
	@Override
	public void run() {
		// TODO 自動生成されたメソッド・スタブ
		System.out.println("Thread running:" + Thread.currentThread());
		try{
			out = new PrintWriter(sock.getOutputStream(),true);
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			String s = null;
			while((s = in.readLine())!=null){
				chatServer.sendAll( "[" + n + "]" + s);
				
				System.out.println("[" + n + "]" + s);
			}
			chatServer.remove(n);

			sock.close();

		}
		catch(IOException e){
			System.out.println(e);
			chatServer.remove(n);
		}
	}


	public void send(String s){
		out.println(s);

	}
}

class ChatServer{

	Worker workers[];

	public ChatServer(){

		int port = 50000;
		workers = new Worker[100];
		Socket sock;
		try{
			ServerSocket servsock = new ServerSocket(port);
			while(true){
				sock = servsock.accept();
				int i;

				for(i=0;i< workers.length;i++){
					if(workers[i] == null){
						workers[i] = new Worker(i,sock,this);
						new Thread(workers[i]).start();
						break;
					}
				}
				if(i == workers.length){
					System.out.println("Can't serve");

				}
			}
		}
		catch(IOException e){
			System.out.println(e);
		}
	}

	public static void main(String args[]) throws IOException{
		new ChatServer();
	}



	public synchronized void sendAll(String s) {
	// TODO 自動生成されたメソッド・スタブ
		int i;
		for(i=0;i< workers.length;i++){
			if(workers[i]!=null)
				workers[i].send(s);

		}
	}


	public void remove(int n) {
	// TODO 自動生成されたメソッド・スタブ
	workers[n] = null;
	sendAll("quiting ["+ n +"]");
	}
}