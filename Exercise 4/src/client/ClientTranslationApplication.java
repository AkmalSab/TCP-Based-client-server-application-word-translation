package client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientTranslationApplication {
	
	public static void ClientSendMessage(String msg, String choice) throws UnknownHostException, IOException {
		
		try {
			
			Socket socket = new Socket(InetAddress.getLocalHost(), 4228);
			
			OutputStream outputStream = socket.getOutputStream();
	        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
	        dataOutputStream.writeUTF(msg);
	        
	        Socket socket2 = new Socket(InetAddress.getLocalHost(), 4229);
	       
			OutputStream outputStream2 = socket2.getOutputStream();
	        DataOutputStream dataOutputStream2 = new DataOutputStream(outputStream2);
	        dataOutputStream2.writeUTF(choice);      
	        
	        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	        String result = bufferedReader.readLine();
	        ClientUserInterface.updateResult(result);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
	}
}
