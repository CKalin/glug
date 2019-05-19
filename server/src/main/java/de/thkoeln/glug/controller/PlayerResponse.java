package de.thkoeln.glug.controller;


public class PlayerResponse {
	private int id;
	private String name;
	private boolean isLeader;

	public PlayerResponse(int id, String name, boolean isLeader) {
		this.id = id;
		this.name = name;
		this.isLeader = isLeader;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public boolean isLeader() {
		return isLeader;
	}
}
