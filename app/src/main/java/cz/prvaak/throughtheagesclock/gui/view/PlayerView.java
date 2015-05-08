package cz.prvaak.throughtheagesclock.gui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import cz.prvaak.throughtheagesclock.R;
import cz.prvaak.throughtheagesclock.clock.PlayerClock;
import cz.prvaak.throughtheagesclock.gui.PlayerColor;

/**
 * View of an active player.
 */
public class PlayerView extends LinearLayout {

	private PlayerClock playerClock;
	private PlayerColor playerColor;


	public PlayerView(Context context) {
		super(context);
	}

	public PlayerView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public void setPlayer(PlayerClock playerClock, PlayerColor playerColor) {
		this.playerClock = playerClock;
		this.playerColor = playerColor;
		TextView playerName = (TextView) findViewById(R.id.player_name);

		if (playerName != null) {
			playerName.setText(playerColor.getNameResourceId());
		}
		updateTimes();
	}

	private void updateTimes() {
		long now = System.currentTimeMillis();
		TextView remainingReserveTime = (TextView) findViewById(R.id.remaining_reserve_time);
		if (remainingReserveTime != null) {
			long time = playerClock.getRemainingReserveTime(now);
			remainingReserveTime.setText(timeToString(time));
		}

		TextView remainingUpkeepTime = (TextView) findViewById(R.id.remaining_upkeep_time);
		if (remainingReserveTime != null) {
			long time = playerClock.getRemainingUpkeepTime(now);
			remainingUpkeepTime.setText(timeToString(time));
		}
	}

	private String timeToString(long time) {
		long hours = time / TimeUnit.HOURS.toMillis(1);
		time -= hours * TimeUnit.HOURS.toMillis(1);
		long minutes = time / TimeUnit.MINUTES.toMillis(1);
		time -= minutes * TimeUnit.MINUTES.toMillis(1);
		long seconds = time / TimeUnit.SECONDS.toMillis(1);
		time -= seconds * TimeUnit.SECONDS.toMillis(1);
		long milliseconds = time;

		if (hours > 0) {
			return String.format("%d:%d:%d", hours, minutes, seconds);
		} if (minutes > 0) {
			String res = String.format("%d:%d.%01d", minutes, seconds, milliseconds);
			return res;
		} else {
			String res = String.format("%d.%03d", seconds, milliseconds);
			return res;
		}
	}
}
