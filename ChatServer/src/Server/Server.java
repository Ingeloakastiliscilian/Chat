package Server;

import java.io.IOException;
import java.net.ServerSocket;

public class Server implements Runnable{
	
	public static final String AUTH_CMD = "/auth";
	private static int DEF_PORT = 6000;
	
	private int port;
	private boolean serverState = false;
	private ServerSocket serverSocket;
	private Authenticator authenticator;
	
	public Server() {
		this(DEF_PORT);
	}
	
	public Server(int port) {
		this.port = port;
		authenticator = new Authenticator();
	}
	
	ServerSocket getSocket(){
		return this.serverSocket;
	}
	
	@Override
	public void run() {
		try {
			this.serverSocket = new ServerSocket(port);
			this.serverState = true;
			authenticator.run();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getPort() {
		return serverSocket.getLocalPort();
	}
	
	private class Connector implements Runnable {
		private boolean alive;
		
		@Override
		public void run() {
			alive = true;
			while (alive) {
				try {
					serverSocket.accept();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		public void stop() {
			alive = false;
		}
	}
}
