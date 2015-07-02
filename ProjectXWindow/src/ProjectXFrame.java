//GUI

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class ProjectXFrame extends JFrame
{
	
	private JTextArea inputField;
	private JButton submitButton;
	
	public static void main(String[] args) 
	{
			ProjectXFrame myX = new ProjectXFrame();
			myX.setVisible(true);
	}
	
	public ProjectXFrame()
	{
		setSize(400,500);
		setTitle("Project X");
		setResizable(false);
		
		//Layout
		Container contentPane = getContentPane();
		contentPane.setLayout(null);
		
		//Listener
		ProjectXListener listener = new ProjectXListener();
		
		//TextArea
		inputField = new JTextArea();
		inputField.setWrapStyleWord(true);
		inputField.setLineWrap(true);
		inputField.setBounds(10, 10, 375, 310);
		contentPane.add(inputField);
		
		//Button
		submitButton = new JButton("Submit");
		submitButton.setBounds(150, 350, 100, 80);
		contentPane.add(submitButton);
		
		submitButton.addActionListener(listener);
	}
	
	private class ProjectXListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			if(event.getSource() == submitButton)
			{
				//Pass input to HTTP class.. Will handle thread and creation of dialog.
				ProjectXPOST myPOST = new ProjectXPOST(inputField.getText()); 
		        myPOST.start();
			}
		}
	}
	
	public void createDialog(String message)
	{
        JOptionPane.showMessageDialog(getContentPane(), message);
	}
}
