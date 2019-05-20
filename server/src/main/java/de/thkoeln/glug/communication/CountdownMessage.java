package de.thkoeln.glug.communication;

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

}
