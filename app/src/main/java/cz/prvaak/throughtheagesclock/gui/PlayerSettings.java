package cz.prvaak.throughtheagesclock.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

import cz.prvaak.throughtheagesclock.TimeAmount;

/**
 * Which players, in which order and with what times will be playing the game.
 */
public class PlayerSettings implements Iterable<PlayerColor> {

	private static final int MIN_PLAYERS = 2;
	private static final int MAX_PLAYERS = 4;

	private PlayerData defaultData;
	private ArrayList<PlayerColor> playersOrder = new ArrayList<>(PlayerColor.values().length);
	private HashMap<PlayerColor, PlayerData> activePlayers = new LinkedHashMap<>(
			PlayerColor.values().length);
	private Set<PlayerColor> inactivePlayers = new HashSet<>(PlayerColor.values().length);

	public PlayerSettings(PlayerColor color1, PlayerColor color2) {
		inactivePlayers.addAll(Arrays.asList(PlayerColor.values()));
		activatePlayer(color1);
		activatePlayer(color2);
	}

	public PlayerSettings(PlayerColor color1, PlayerColor color2, PlayerColor color3) {
		inactivePlayers.addAll(Arrays.asList(PlayerColor.values()));
		activatePlayer(color1);
		activatePlayer(color2);
		activatePlayer(color3);
	}

	public PlayerSettings(PlayerColor color1, PlayerColor color2, PlayerColor color3,
			PlayerColor color4) {
		inactivePlayers.addAll(Arrays.asList(PlayerColor.values()));
		activatePlayer(color1);
		activatePlayer(color2);
		activatePlayer(color3);
		activatePlayer(color4);
	}

	/** Constructor that does not care for players order. */
	public PlayerSettings(int numberOfPlayers) {
		if (numberOfPlayers < MIN_PLAYERS || numberOfPlayers > MAX_PLAYERS) {
			throw new IllegalArgumentException(String.format(
					"Number of players must be from %d to %d", MIN_PLAYERS, MAX_PLAYERS));
		}

		inactivePlayers.addAll(Arrays.asList(PlayerColor.values()));
		for (int i = 0; i < numberOfPlayers; i++) {
			activateNextPlayer();
		}
	}

	public boolean addPlayer() {
		if (activePlayers.size() >= MAX_PLAYERS) {
			return false;
		}

		activateNextPlayer();
		return true;
	}

	public boolean removePlayer(PlayerColor playerColor) {
		if (activePlayers.size() <= MIN_PLAYERS) {
			return false;
		}

		deactivatePlayer(playerColor);
		return true;
	}

	public void changeColor(PlayerColor oldColor, PlayerColor newColor) {
		if (!activePlayers.containsKey(oldColor) && !activePlayers.containsKey(newColor)) {
			throw new IllegalArgumentException(
					"Cannot change inactive color to another inactive color!");
		} else if (!activePlayers.containsKey(oldColor)) {
			// If one color is inactive, always change the active color to inactive color.
			PlayerColor tmpColor = oldColor;
			oldColor = newColor;
			newColor = tmpColor;
		}

		if (activePlayers.containsKey(newColor)) {
			// Switching between active colors.
			PlayerData newData = activePlayers.get(newColor);
			PlayerData oldData = activePlayers.get(oldColor);
			activePlayers.put(newColor, oldData);
			activePlayers.put(oldColor, newData);
			Collections.swap(playersOrder,
					playersOrder.indexOf(oldColor), playersOrder.indexOf(newColor));
		} else {
			// Switching active color to an inactive color.
			PlayerData oldData = activePlayers.get(oldColor);
			activePlayers.put(newColor, oldData);
			activePlayers.remove(oldColor);
			playersOrder.set(playersOrder.indexOf(oldColor), newColor);
		}
	}

	public void changeData(PlayerColor playerColor, PlayerData playerData) {
		defaultData = playerData;
		activePlayers.put(playerColor, playerData);
	}

	public PlayerData getPlayerData(PlayerColor playerColor) {
		PlayerData playerData = activePlayers.get(playerColor);
		if (playerData == null) {
			throw new IllegalArgumentException("Player is not active!");
		}

		return playerData;
	}

	public int getPlayerCount() {
		return activePlayers.size();
	}

	public boolean isPlayerActive(PlayerColor playerColor) {
		return activePlayers.containsKey(playerColor);
	}

	@Override
	public Iterator<PlayerColor> iterator() {
		return playersOrder.iterator();
	}

	private PlayerData newDefaultPlayer() {
		if (defaultData == null) {
			defaultData = new PlayerData(new TimeAmount(600000L),
					new TimeAmount(10000L), new TimeAmount(30000L));
		}
		return new PlayerData(defaultData);
	}

	private void activateNextPlayer() {
		PlayerColor nextColor = getNextColor();
		activatePlayer(nextColor);
	}

	private void activatePlayer(PlayerColor playerColor) {
		PlayerData playerData = activePlayers.get(playerColor);
		if (playerData != null) {
			throw new IllegalStateException(
					String.format("Player %s is already active!", playerColor));
		}

		if (!inactivePlayers.remove(playerColor)) {
			throw new IllegalStateException(
					String.format("Player %s is neither active nor inactive!", playerColor));
		}

		activePlayers.put(playerColor, newDefaultPlayer());
		playersOrder.add(playerColor);
	}

	private void deactivatePlayer(PlayerColor playerColor) {
		activePlayers.remove(playerColor);
		inactivePlayers.add(playerColor);
		playersOrder.remove(playerColor);
	}

	private PlayerColor getNextColor() {
		if (inactivePlayers.isEmpty()) {
			throw new IllegalStateException("There are no more players to activate!");
		}

		return inactivePlayers.iterator().next();
	}
}
