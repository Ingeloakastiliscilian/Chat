package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WarningDialog extends JDialog {
	private JPanel contentPane;
	private JButton buttonOK;
	JTextArea messageArea;
	
	public WarningDialog() {
		setContentPane(contentPane);
		setModal(true);
		getRootPane().setDefaultButton(buttonOK);
		
		buttonOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onOK();
			}
		});
	}
	
	private void onOK() {
		// add your code here
		dispose();
	}
	
	public static void main(String[] args) {
		WarningDialog dialog = new WarningDialog();
		dialog.pack();
		dialog.setVisible(true);
		System.exit(0);
	}
}
