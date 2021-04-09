package client;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ClientUserInterface implements ActionListener {
		
	public int lang = 0;
	public JComboBox combo;
	public JTextField wordInput;
	public JButton button1;
	public static JLabel label, answers;
	public JFrame frame;
	
	public void createAndShowGUI() {
	
		frame = new JFrame("Online Translator");
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.pack();
		frame.setSize(800,100);		
		frame.setLayout(new FlowLayout());
		
		label = new JLabel("Please enter English word to be translated:");
		answers = new JLabel("-");
		
		wordInput = new JTextField(20);
		
		String[] choices = {"BAHASA", "ARAB", "KOREA"};
		
		combo = new JComboBox(choices);
		combo.setSelectedIndex(0);
		
		button1 = new JButton("Translate");
		button1.addActionListener(this);
		
		JPanel panel = new JPanel();
		panel.setSize(100,200);
		
		panel.add(label);
		panel.add(wordInput);
		panel.add(combo);
		panel.add(button1);
		panel.add(answers);
		
		frame.add(panel);
		frame.setVisible(true);
		
	}
	
	public static void updateResult(String result) throws UnsupportedEncodingException {
		
		answers.setText(result);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		try {
			
			ClientTranslationApplication.ClientSendMessage(wordInput.getText(), String.valueOf(combo.getSelectedItem()));
			
		} catch (UnknownHostException e1) {
			
			e1.printStackTrace();
			
		} catch (IOException e1) {
			
			e1.printStackTrace();
			
		}
		
	}
	
	public static void main(String[] args){
		ClientUserInterface CUI = new ClientUserInterface();
		CUI.createAndShowGUI();
	}
}
