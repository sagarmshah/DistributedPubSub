import java.io.Serializable;

/**
 * 
 * @author Sagar
 *
 */
public class TopicMessage implements Serializable {

	public String topic;
	public String message;

	/**
	 * Constructor
	 * 
	 * @param topic
	 * @param message
	 */
	public TopicMessage(String topic, String message) {
		this.topic = topic;
		this.message = message;
	}
}
