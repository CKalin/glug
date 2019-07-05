package de.thkoeln.glug.data.service;

import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import de.thkoeln.glug.data.Game;
import de.thkoeln.glug.data.Player;
import de.thkoeln.glug.data.repository.GameRepository;
import de.thkoeln.glug.service.GameService;

public class GameServiceTest {
	private final static String ACCESS_CODE = "84721";
	@InjectMocks
	private GameService gameService;

	@Mock
	private GameRepository gameRepositoryMock;

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	//@Test
	public void assertPlayerAdded() {
		Game game = new Game();
		game.setAccessCode(ACCESS_CODE);
		game.setPlayers(new HashSet<Player>());
		Mockito.when(gameRepositoryMock.findOneByAccessCode(ACCESS_CODE)).thenReturn(Optional.of(game));
		Player newPlayer = new Player();
		newPlayer.setName("helmutchecker");
		newPlayer.setId(1);
		newPlayer.setGames(new HashSet<Game>());
		Set<Player> players = gameService.addPlayer(ACCESS_CODE, newPlayer.getId());
		assertTrue(players.contains(newPlayer));
	}

}
