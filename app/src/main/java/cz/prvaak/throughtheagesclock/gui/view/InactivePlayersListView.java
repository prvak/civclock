package cz.prvaak.throughtheagesclock.gui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.util.List;

import cz.prvaak.throughtheagesclock.gui.PlayerData;

/**
 * View of inactive players.
 */
public class InactivePlayersListView extends LinearLayout {

	public InactivePlayersListView(Context context) {
		super(context);
	}

	public InactivePlayersListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public InactivePlayersListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public void setPlayers(List<PlayerData> players) {
		// TODO: add players to the layout
	}

	public void updateTimes(long now) {

	}
}