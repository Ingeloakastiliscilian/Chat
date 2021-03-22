package Client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

public class Client {
	
	private static String DEF_USERNAME = "UNKNOWN_USER";
	
	private String userName;
	private Socket socket;
	private BufferedInputStream inputStream;
	private BufferedOutputStream outputStream;
	
	
	
	public Client(Socket socket) throws IOException {
		this.userName = DEF_USERNAME;
		this.socket = socket;
		inputStream = new BufferedInputStream(new DataInputStream(socket.getInputStream()));
		outputStream = new BufferedOutputStream(socket.getOutputStream());
	}
	
	public String getMessage(){
		try {
			return Arrays.toString(inputStream.readAllBytes());
		} catch (IOException e) {
			return e.getMessage();
		}
	}
	
	public boolean sendMessage(String mess){
		try {
			outputStream.write(mess.getBytes());
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public void authAs(String nickname){
		this.userName = nickname;
	}
	
	public void disconnect() {
	
	}
	
	public Socket getSocket(){
		return this.socket;
	}
}
