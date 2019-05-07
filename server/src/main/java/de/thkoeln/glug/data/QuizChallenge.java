package de.thkoeln.glug.data;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class QuizChallenge {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@ManyToOne
	private Round round;
	@OneToMany(mappedBy = "quizChallenge", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Challenge> challenges;
	@Column
	@CreationTimestamp
	private LocalDateTime createDateTime;
}
