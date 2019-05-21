package de.thkoeln.glug.communication.response;

import java.util.List;
import java.util.Set;

import de.thkoeln.glug.data.Player;

public class PlayerJoinedMessage {
	String action = "PLAYER_JOINED";
	PlayerBean joinedPlayer;
	Set<PlayerBean> inGamePlayers;

	public PlayerJoinedMessage(Player joinedPlayer, Set<Player> inGamePlayers, Player gamemaster) {
		Integer gamemasterId = gamemaster.getId();
		inGamePlayers.forEach(player -> {
			PlayerBean playerBean = new PlayerBean(player.getId(), player.getName(), player.getId() == gamemasterId);
			this.inGamePlayers.add(playerBean);
		});
		this.joinedPlayer = new PlayerBean(joinedPlayer.getId(), joinedPlayer.getName(), joinedPlayer.getId() == gamemasterId);
	}

	public PlayerJoinedMessage(PlayerBean joinedPlayer, Set<PlayerBean> inGamePlayers) {
		this.joinedPlayer = joinedPlayer;
		this.inGamePlayers = inGamePlayers;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public PlayerBean getJoinedPlayer() {
		return joinedPlayer;
	}

	public void setJoinedPlayer(PlayerBean joinedPlayer) {
		this.joinedPlayer = joinedPlayer;
	}

	public Set<PlayerBean> getInGamePlayers() {
		return inGamePlayers;
	}

	public void setInGamePlayers(Set<PlayerBean> inGamePlayers) {
		this.inGamePlayers = inGamePlayers;
	}
}
