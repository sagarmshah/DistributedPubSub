import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
/**
 * 
 * @author Sagar Shah
 *
 */

public class SubscriberApp {

	private JFrame frame;
	Subscriber subscriber;
	JComboBox comboBox ;
	JTextPane textPane;
	JLabel lbl1;
	JComboBox comboBox1;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SubscriberApp window = new SubscriberApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SubscriberApp() {
		subscriber= new Subscriber(this);
		SubscriberListener sublistener = new SubscriberListener(subscriber);
		sublistener.start();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	public void updateCombo()
	{
		comboBox.removeAllItems();
		for(String topic: subscriber.topicList)
		{
			comboBox.addItem(topic);
		}
	}
	
	public void updateTextPane(String message)
	{
		String temp= textPane.getText()+"\n"+message;
		textPane.setText(temp);
		
	}
	
	public void updateStatus(String status)
	{
		lbl1.setText("");
		lbl1.setText(status);
	}
	public void updateRemoveCombo()
	{
		comboBox1.removeAllItems();
		for(String topic: subscriber.sublist)
		{
			comboBox1.addItem(topic);
		}
		
	}
	private void initialize() {
		frame = new JFrame("SUBSCRIBER");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		comboBox= new JComboBox();
		for(String topic: subscriber.topicList)
		{
			comboBox.addItem(topic);
		}

		comboBox.setBounds(30, 23, 110, 27);
		frame.getContentPane().add(comboBox);
		
		JButton btnNewButton = new JButton("Subscribe");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedTopic= (String) comboBox.getSelectedItem();
				subscriber.subscribeTopic(selectedTopic);
			}
		});
		btnNewButton.setBounds(176, 22, 117, 29);
		frame.getContentPane().add(btnNewButton);
		
		
		
		textPane = new JTextPane();
		textPane.setBounds(30, 137, 255, 118);
		frame.getContentPane().add(textPane);
		
		lbl1 = new JLabel("");
		lbl1.setBounds(30, 256, 255, 16);
		frame.getContentPane().add(lbl1);
		
		comboBox1 = new JComboBox();
		
		
		comboBox1.setBounds(30, 98, 110, 27);
		frame.getContentPane().add(comboBox1);
		
		JButton btnNewButton_1 = new JButton("Remove");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedTopic= (String) comboBox1.getSelectedItem();
				subscriber.removeTopic(selectedTopic);
			}
		});
		btnNewButton_1.setBounds(176, 97, 117, 29);
		frame.getContentPane().add(btnNewButton_1);
	}
}
