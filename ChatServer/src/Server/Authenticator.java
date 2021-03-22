package Server;

import Client.Client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class Authenticator implements Runnable {
	
	private static final long SLEEP_TIME = 1000;
	private static final String AUTH_INVINTATION_MESS = "Please login. Use \"" + Server.AUTH_CMD + "\" command";
	
	private volatile boolean isAlive;
	private volatile LinkedList<Socket> socketsQueue;
	private volatile LinkedHashMap<String, Client> authQueue;
	
	private ServerSocket listenableSocket;
	private AuthService authService;
	private ClientAcceptor acceptor;
	
	Authenticator(ServerSocket serverSocket) {
		this.listenableSocket = serverSocket;
	}
	
	private void addLog(String logMessage) {
		System.currentTimeMillis();
	}
	
	void authorize(Socket socket) {
		try {
			new Client(socket);
		} catch (IOException e) {
			addLog(e.getMessage());
		}
	}
	
	@Override
	public void run() {
		isAlive = true;
		acceptor = new ClientAcceptor();
		authService = new AuthService();
	}
	
	public void stop() {
		isAlive = false;
		if (null != this.acceptor)
			this.acceptor.stop();
		if (null != this.authService)
			this.authService.stop();
	}
	
	boolean disconnectClient(Client cl) {
		cl.sendMessage(AUTH_INVINTATION_MESS);
		return true;
	}
	
	
	private class ClientAcceptor implements Runnable {
		
		private volatile boolean isAlive = false;
		
		ClientAcceptor() {
			socketsQueue = new LinkedList<>();
		}
		
		@Override
		public void run() {
			isAlive = true;
			while (isAlive) {
				try {
					socketsQueue.addLast(listenableSocket.accept());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		public void stop() {
			isAlive = false;
		}
		
	}
	
	
	private class AuthService implements Runnable {
		
		private volatile boolean isAlive;
		
		@Override
		public void run() {
			isAlive = null != socketsQueue;
			while (isAlive) {
				while (0 < socketsQueue.size()) {
					authorize(socketsQueue.poll());
				}
				try {
					Thread.sleep(SLEEP_TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			addLog("auth service stopped");
		}
		
		public void stop() {
			isAlive = false;
		}
	}
}
