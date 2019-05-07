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
public class ChallengeResponse {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@ManyToOne
	private Player player;
	@ManyToOne
	private Challenge challenge;
	@Column
	@CreationTimestamp
	private LocalDateTime createDateTime;
}
