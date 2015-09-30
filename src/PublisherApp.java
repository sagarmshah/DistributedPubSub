import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JTextField;

public class PublisherApp {

	private JFrame frame;
	private JTextField addTopic;
	private JTextField message;
	Publisher publisher;

	/*
	 * public PublisherApp(Publisher p) { publisher=p; }
	 */

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PublisherApp window = new PublisherApp();
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
	public PublisherApp() {
		publisher = new Publisher();
		PublisherListener publisten = new PublisherListener(publisher);
		publisten.start();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("PUBLISHER");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		addTopic = new JTextField();
		addTopic.setBounds(67, 37, 134, 28);
		frame.getContentPane().add(addTopic);
		addTopic.setColumns(10);
		final JComboBox comboBox = new JComboBox();
		JButton btnAddTopic = new JButton("Add Topic");
		btnAddTopic.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String topic = addTopic.getText();
				addTopic.setText("");
				if (!topic.equals("")) {
					comboBox.addItem(topic);
					publisher.addTopic(topic);
				}
			}
		});
		btnAddTopic.setBounds(232, 38, 117, 29);
		frame.getContentPane().add(btnAddTopic);

		JList list = new JList();
		list.setBounds(84, 118, 1, 1);
		frame.getContentPane().add(list);

		comboBox.setBounds(67, 97, 134, 27);
		frame.getContentPane().add(comboBox);

		message = new JTextField();
		message.setBounds(242, 95, 134, 28);
		frame.getContentPane().add(message);
		message.setColumns(10);

		JButton publish = new JButton("Publish");
		publish.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedTopic = (String) comboBox.getSelectedItem();
				String msg = message.getText();
				// System.out.println("Topic selected is " + selectedTopic);
				if (!msg.equals("")) {
					try {
						publisher.notifyAllSubscribers(selectedTopic, msg);
					} catch (NullPointerException e1) {
						System.out.println("No Subscribers!");
					}
				}
			}
		});
		publish.setBounds(162, 183, 117, 29);
		frame.getContentPane().add(publish);
	}
}
