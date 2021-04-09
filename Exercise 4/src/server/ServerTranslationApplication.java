package server;

import database.Connect;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ServerTranslationApplication {

	int lang = 0;
	String word = null;
	
	public void setLanguage(int x) {
		this.lang = x;
	}
	
	public int getLanguage() {
		return lang;
	}	
	
	public void setWord(String y) {
		this.word = y;
	}
	
	public String getWord() {
		return word;
	}	
	
	public String retrieve() throws Exception {	
		
		Connect db = new Connect();
		String output = "";		
		try {
			Connection conn = db.getConnection();			
			String word = getWord();	
			int languages = getLanguage();
			String query = "SELECT translate from DICTIONARY WHERE word = '"+ word +"' and did = '"+ String.valueOf(languages) +"'";			
			PreparedStatement select = conn.prepareStatement(query);			
			ResultSet result = select.executeQuery();			
			while(result.next()) {
				output = result.getString("translate");
			}	
		} catch (Exception e) {
			System.out.println(e);
		}		
		return output;		
	}
	
	public static void main(String[] args) throws Exception {
		
		int pointer = 0;
		String res = "";
		ServerSocket serverSocket = null;
		ServerSocket serverSocket2 = null;
		
		String[] malay = {"Selamat pagi","Selamat malam","Apa khabar?","Terima kasih","Selamat tinggal","Ada apa?"};
		String[] arab = {"صباح الخير", "طاب مساؤك", "كيف حالك؟", "شكرا لك", "مع السلامة", "ما أخبارك؟"};
		String[] korean = {"좋은 아침", "안녕히 주무세요", "어떻게 지내세요?", "감사합니다", "안녕", "뭐야?"};
		
		try {			
			//Bind server socket to a port
			int portNo = 4228;
			int portNo2 = 4229;
			serverSocket = new ServerSocket(portNo);
			serverSocket2 = new ServerSocket(portNo2);
			
			System.out.println("Waiting for request");
						
			while(true) {
				
				//Accept client request for connection
				Socket clientSocket = serverSocket.accept();
				
				//get stream from the network
				InputStream inputStream = clientSocket.getInputStream();
				
				//Lets an application read primitive Java data types from an underlying input stream
				DataInputStream dataInputStream = new DataInputStream(inputStream);
				
				String message = dataInputStream.readUTF();
				
				System.out.println("The message sent from the socket was: " + message);
	            System.out.println("Closing sockets.");
				
	            /*           
				//Create stream to write data on the network
				DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());				
				//Send current date back to the client
				//outputStream.writeUTF(text1);
				outputStream.writeInt(getLanguage());				
				//Close the socket
				clientSocket.close();
				
				Good morning
				*/
				
	            //Accept client request for connection
				Socket clientSocket2 = serverSocket2.accept();
				
				//get stream from the network
				InputStream inputStream2 = clientSocket2.getInputStream();
				
				//Lets an application read primitive Java data types from an underlying input stream
				DataInputStream dataInputStream2 = new DataInputStream(inputStream2);
				
				String message2 = dataInputStream2.readUTF();
				
				System.out.println("The message sent from the socket was: " + message2);
	            System.out.println("Closing sockets.");
				
	            clientSocket2.close();
	            
	            DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());
	            
	            if (message.equals("Good morning"))
	            	pointer = 0;
	            else if (message.equals("Good night"))
	            	pointer = 1;
	            else if (message.equals("How are you?"))
	            	pointer = 2;
	            else if (message.equals("Thank you"))
	            	pointer = 3;
	            else if (message.equals("Goodbye"))
	            	pointer = 4;
	            else if (message.equals("What’s up?"))
	            	pointer = 5;	    
	            	
	            if (message2.equals("KOREA"))
	            	res = korean[pointer];
	            else if (message2.equals("ARAB"))
	            	res = arab[pointer];
	            else if (message2.equals("BAHASA"))
	            	res = malay[pointer];
	            
	            System.out.println(res);
	    		outputStream.writeUTF(res); 
	            
	    		clientSocket.close();
				
			}					
			//Closing is not necessary because the code is unreachable			
		} catch (IOException e){	
			
			if(serverSocket != null)
				serverSocket.close();
			
			e.printStackTrace();			
		}
	}
}
