package de.thkoeln.glug.data.repository;

import org.springframework.data.repository.CrudRepository;

import de.thkoeln.glug.data.Player;

public interface PlayerRepository extends CrudRepository<Player, Integer> {
	Iterable<Player> findByName(String name);
}
