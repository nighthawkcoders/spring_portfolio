package com.nighthawk.spring_portfolio.mvc.chathistory;

import java.sql.Date;

import org.json.simple.JSONObject;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Chat {
	 // automatic unique identifier for Person records
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
	//attributes of a chat messsage
    public Long getId() {
		return id;
	}
	private String chatMessage;
    private String chatReponse;
	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss", timezone="PDT")
    private Date timestamp;
    private Long personId;
    
	public Long getPersonId() {
		return personId;
	}
	public String getChatMessage() {
		return chatMessage;
	}
	public String getChatReponse() {
		return chatReponse;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public Chat(String chatMessage, String chatReponse, Date timestamp, Long personId) {
		super();
		this.chatMessage = chatMessage;
		this.chatReponse = chatReponse;
		this.timestamp = timestamp;
		this.personId = personId;
	}
    
	public Chat() {
		
	}
	
	@Override
	public String toString() {
		JSONObject obj = new JSONObject();
		obj.put(idStr, id);
		obj.put(chat_message, chatMessage);
		obj.put(chat_response, chatReponse);
		obj.put(timestamp_str, timestamp.toString());
		obj.put(person_id, personId);
		
		return obj.toString();
	}
	
	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();
		obj.put(idStr, id);
		obj.put(chat_message, chatMessage);
		obj.put(chat_response, chatReponse);
		obj.put(timestamp_str, timestamp.toString());
		obj.put(person_id, personId);
		
		return obj;
	}
	
	private static final String chat_message="chat_message";
	private static final String idStr="id";
	private static final String chat_response="chat_response";
	private static final String timestamp_str="timestamp";
	private static final String person_id="person_id";
	

}
