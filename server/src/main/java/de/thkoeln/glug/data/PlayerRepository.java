package de.thkoeln.glug.data;

import org.springframework.data.repository.CrudRepository;

public interface PlayerRepository extends CrudRepository<Player, Integer> {
	Iterable<Player> findByName(String name);
}
