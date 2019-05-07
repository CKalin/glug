package de.thkoeln.glug.data;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Player {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String name;
	private String password;
	@OneToMany(mappedBy = "gamemaster", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Game> gamesLeaded;
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Game> games;
	@OneToMany(mappedBy = "player")
	private Set<ChallengeResponse> challengeResponses;
	@OneToMany(mappedBy = "player")
	private Set<RoundResult> roundResults;
	@OneToMany(mappedBy = "fromPlayer")
	private Set<SlugAllocation> slugsAllocated;
	@OneToMany(mappedBy = "toPlayer")
	private Set<SlugAllocation> slugsRecieved;
	@Column
	@CreationTimestamp
	private LocalDateTime createDateTime;
}
