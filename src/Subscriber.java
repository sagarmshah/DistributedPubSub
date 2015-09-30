import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 * @author Sagar Shah
 *
 */
public class Subscriber {

	Socket publisher;
	public ArrayList<String> topicList;
	public ArrayList<String> sublist;
	SubscriberApp sapp;

	/**
	 * Constructor
	 */
	Subscriber() {

	}

	/**
	 * Constructor
	 * 
	 * @param sapp
	 */
	Subscriber(SubscriberApp sapp) {
		try {
			this.sapp = sapp;
			sublist = new ArrayList<>();
			topicList = new ArrayList<>();
			publisher = new Socket(Constants.PUBLISH_HOST,
					Constants.PUBLISH_LISTEN);
			ObjectOutputStream oos = new ObjectOutputStream(
					publisher.getOutputStream());
			oos.writeObject("TopicListRequest");
			oos.flush();
			ObjectInputStream ois = new ObjectInputStream(
					publisher.getInputStream());
			topicList = (ArrayList<String>) ois.readObject();
			oos.close();
			ois.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * function to update topics that a subscriber is interested in
	 * 
	 * @param topics
	 */
	public void updateTopics(ArrayList<String> topics) {
//		System.out.println("Updating topics");
		topicList = topics;
		sapp.updateCombo();
	}

	/**
	 * function to subscribe to a topic
	 * 
	 * @param topic
	 */
	public void subscribeTopic(String topic) {
		try {
			publisher = new Socket(Constants.PUBLISH_HOST,
					Constants.PUBLISH_LISTEN);
			ObjectOutputStream oos = new ObjectOutputStream(
					publisher.getOutputStream());

			oos.writeObject("addmetothistopic");
			oos.writeObject(topic);
			oos.flush();
			oos.close();
			sublist.add(topic);
			sapp.updateStatus("Subscribed to Topic: " + topic);
			sapp.updateRemoveCombo();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * function to get a new message
	 * 
	 * @param message
	 */
	public void printNewMessage(String message) {
		sapp.updateTextPane(message);
	}

	/**
	 * function to remove/unsubscribe a topic
	 * 
	 * @param topic
	 */
	public void removeTopic(String topic) {
		try {
			publisher = new Socket(Constants.PUBLISH_HOST,
					Constants.PUBLISH_LISTEN);
			ObjectOutputStream oos = new ObjectOutputStream(
					publisher.getOutputStream());

			oos.writeObject("removemefromthistopic");
			oos.writeObject(topic);
			oos.flush();
			oos.close();
			sublist.remove(topic);
			sapp.updateStatus("Successfully unsubscribed topic " + topic);
			sapp.updateRemoveCombo();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * main program
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		Subscriber sub = new Subscriber();
		SubscriberListener sublistener = new SubscriberListener(sub);
		sublistener.start();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			// System.out.println("1.Subscribe topic\n2.Remove topic");

			int choice;
			try {
				choice = Integer.parseInt(br.readLine());
				if (choice == 1) {
					// System.out.println("Add the topic from following list");
					// for (String t : sub.topicList) {
					// System.out.println(t);
					// }
					String topic = br.readLine();
					if (sub.topicList.contains(topic)
							&& !sub.sublist.contains(topic)) {
						sub.subscribeTopic(topic);
						sub.sublist.add(topic);
					}
				}

				if (choice == 2) {
					// System.out.println("Remove the topic from following list");
					// for (String t : sub.sublist) {
					// System.out.println(t);
					// }
					String topic = br.readLine();
					if (sub.sublist.contains(topic)) {
						sub.removeTopic(topic);
						sub.sublist.remove(topic);
					}
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

}
