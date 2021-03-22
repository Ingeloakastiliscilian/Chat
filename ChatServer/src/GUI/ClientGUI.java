package GUI;

import javax.swing.*;

public class ClientGUI extends JFrame {
	
	private JPanel root;
	JPanel mainPanel;
	
	ClientGUI() {
		setContentPane(root);
		setSize(500, 500);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new ClientGUI();
	}
}
