package cz.prvaak.throughtheagesclock.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cz.prvaak.throughtheagesclock.TimeAmount;

/**
 * Which players, in which order and with what times will be playing the game.
 */
public class PlayerSettings implements Iterable<PlayerColor> {

	private static final int MIN_PLAYERS = 2;
	private static final int MAX_PLAYERS = 4;

	private PlayerData defaultData = new PlayerData(
			new TimeAmount(600000L),
			new TimeAmount[]{
					new TimeAmount(10000L), new TimeAmount(10000L), new TimeAmount(10000L)},
			new TimeAmount(30000L));
	private List<PlayerColor> playersOrder = new ArrayList<>(PlayerColor.values().length);
	private Map<PlayerColor, PlayerData> playersData = new HashMap<>(PlayerColor.values().length);
	private Set<PlayerColor> uniquePlayers = new HashSet<>(PlayerColor.values().length);
	private Set<PlayerColor> activePlayers = new HashSet<>(PlayerColor.values().length);
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
		if (oldColor.equals(newColor)) {
			throw new IllegalArgumentException(
					"Cannot change color to itself!");
		} else if (!activePlayers.contains(oldColor) && !activePlayers.contains(newColor)) {
			throw new IllegalArgumentException(
					"Cannot change inactive color to another inactive color!");
		} else if (!activePlayers.contains(oldColor)) {
			// If one color is inactive, always change the active color to inactive color.
			PlayerColor tmpColor = oldColor;
			oldColor = newColor;
			newColor = tmpColor;
		}

		if (activePlayers.contains(newColor)) {
			// Switching between active colors.
			PlayerData newData = playersData.get(newColor);
			PlayerData oldData = playersData.get(oldColor);
			playersData.put(newColor, oldData);
			playersData.put(oldColor, newData);
			boolean newUnique = uniquePlayers.contains(newColor);
			boolean oldUnique = uniquePlayers.contains(oldColor);
			uniquePlayers.remove(newColor);
			uniquePlayers.remove(oldColor);
			if (newUnique) {
				uniquePlayers.add(oldColor);
			}
			if (oldUnique) {
				uniquePlayers.add(newColor);
			}
			Collections.swap(playersOrder,
					playersOrder.indexOf(oldColor), playersOrder.indexOf(newColor));
		} else {
			// Switching active color to an inactive color.
			PlayerData oldData = playersData.get(oldColor);
			playersData.put(newColor, oldData);
			activePlayers.remove(oldColor);
			activePlayers.add(newColor);
			inactivePlayers.remove(newColor);
			inactivePlayers.add(oldColor);
			if (uniquePlayers.remove(oldColor)) {
				uniquePlayers.add(newColor);
			}
			playersOrder.set(playersOrder.indexOf(oldColor), newColor);
		}
	}

	public void setPlayerData(PlayerColor playerColor, PlayerData playerData) {
		activePlayers.add(playerColor);
		playersData.put(playerColor, playerData);
		if (!uniquePlayers.contains(playerColor)) {
			defaultData = playerData;
		}
	}

	public void setHasUniqueTime(PlayerColor playerColor, boolean hasUniqueTime) {
		if (hasUniqueTime) {
			uniquePlayers.add(playerColor);
		} else {
			uniquePlayers.remove(playerColor);
		}
	}

	public PlayerData getPlayerData(PlayerColor playerColor) {
		if (!activePlayers.contains(playerColor)) {
			throw new IllegalArgumentException("Player is not active!");
		}
		if (uniquePlayers.contains(playerColor)) {
			return playersData.get(playerColor);
		} else {
			return defaultData;
		}
	}

	public int getPlayerCount() {
		return activePlayers.size();
	}

	public boolean isPlayerActive(PlayerColor playerColor) {
		return activePlayers.contains(playerColor);
	}

	@Override
	public Iterator<PlayerColor> iterator() {
		return playersOrder.iterator();
	}

	private PlayerData newDefaultPlayer() {
		return new PlayerData(defaultData);
	}

	private void activateNextPlayer() {
		PlayerColor nextColor = getNextColor();
		activatePlayer(nextColor);
	}

	private void activatePlayer(PlayerColor playerColor) {
		if (activePlayers.contains(playerColor)) {
			throw new IllegalStateException(
					String.format("Player %s is already active!", playerColor));
		}

		if (!inactivePlayers.remove(playerColor)) {
			throw new IllegalStateException(
					String.format("Player %s is neither active nor inactive!", playerColor));
		}

		activePlayers.add(playerColor);
		playersData.put(playerColor, newDefaultPlayer());
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
