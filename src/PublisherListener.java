
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * @author Sagar Shah
 *
 */
public class PublisherListener extends Thread {

	ServerSocket ss;
	Publisher p;

	/**
	 * Constructor
	 * 
	 * @param p
	 */
	public PublisherListener(Publisher p) {
		try {
			this.p = p;
			ss = new ServerSocket(Constants.PUBLISH_LISTEN);
			System.out.println("Created Server...listening to Subscribers");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				Socket soc = ss.accept();
				p.addSubscriber(soc);
				ObjectInputStream ois = new ObjectInputStream(
						soc.getInputStream());
				ObjectOutputStream oos = new ObjectOutputStream(
						soc.getOutputStream());

				String command = (String) ois.readObject();
				if (command.equals("TopicListRequest")) {
					oos.writeObject(p.topiclist);
					oos.flush();
				} else if (command.equals("addmetothistopic")) {
					String topic = (String) ois.readObject();
					p.addSubscribertoTopic(soc, topic);
				} else if (command.equals("removemefromthistopic")) {
					String topic = (String) ois.readObject();
					p.removeSubscriberfromTopic(soc, topic);
				}
				ois.close();
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

	}

}
