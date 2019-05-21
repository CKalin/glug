package de.thkoeln.glug.communication.response;

public class CountdownMessage {
	String action = "COUNT_DOWN";
	int count;

	public CountdownMessage(int count) {
		super();
		this.count = count;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}



}
