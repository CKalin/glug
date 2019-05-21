package de.thkoeln.glug.data;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.CreationTimestamp;

@Entity
public class RoundResult {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@ManyToOne
	private Player player;
	@ManyToOne
	private Round round;
	private Integer slugCountReceived;
	private Integer slugCountToSpend;
	@Column
	@CreationTimestamp
	private LocalDateTime createDateTime;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public Round getRound() {
		return round;
	}
	public void setRound(Round round) {
		this.round = round;
	}
	public Integer getSlugCountReceived() {
		return slugCountReceived;
	}
	public void setSlugCountRecieved(Integer slugCountReceived) {
		this.slugCountReceived = slugCountReceived;
	}
	public Integer getSlugCountToSpend() {
		return slugCountToSpend;
	}
	public void setSlugCountToSpend(Integer slugCountToSpend) {
		this.slugCountToSpend = slugCountToSpend;
	}
	public LocalDateTime getCreateDateTime() {
		return createDateTime;
	}
	public void setCreateDateTime(LocalDateTime createDateTime) {
		this.createDateTime = createDateTime;
	}

}
