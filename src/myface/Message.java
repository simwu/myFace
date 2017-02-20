package myface;

import java.sql.Date;
import java.sql.Time;

public class Message {

	/*
	 * Class Instance Variables
	 */
	
	private int		messageId;
	private int		senderProfileId;
	private int		receiverProfileId;
	private String	text;
	private	Date	date;
	private Time	time;
	
	/*
	 * Getters and Setters
	 */
	
	public int getMessageId() {
		return messageId;
	}

	public int getSenderProfileId() {
		return senderProfileId;
	}

	public void setSenderProfileId(int senderProfileId) {
		this.senderProfileId = senderProfileId;
	}

	public int getReceiverProfileId() {
		return receiverProfileId;
	}

	public void setReceiverProfileId(int receiverProfileId) {
		this.receiverProfileId = receiverProfileId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	/*
	 * Constructor
	 */
	
	public Message() {
		
		messageId			= 0;
		senderProfileId		= 0;
		receiverProfileId	= 0;
		text				= "";
		date				= new Date(0);
		time				= new Time(0);
	}
}
