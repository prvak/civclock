package cz.prvaak.throughtheagesclock.gui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import cz.prvaak.throughtheagesclock.R;
import cz.prvaak.throughtheagesclock.clock.PlayerClock;
import cz.prvaak.throughtheagesclock.gui.Player;
import cz.prvaak.throughtheagesclock.gui.PlayerColor;
import cz.prvaak.throughtheagesclock.gui.PlayerData;

/**
 * View that displays information about one player.
 */
public class PlayerView extends LinearLayout {

	private PlayerClock playerClock;

	public PlayerView(Context context) {
		super(context);
	}

	public PlayerView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public void setPlayer(Player player) {
		this.playerClock = player;
		PlayerColor playerColor = player.getPlayerColor();

		TextView playerName = (TextView) findViewById(R.id.player_name);
		if (playerName != null) {
			playerName.setText(playerColor.getNameResourceId());
		}

		setBackgroundColor(getResources().getColor(playerColor.getColorResourceId()));
	}

	public void updateTimes(long now) {
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
		boolean isNegative = time < 0;
		if (isNegative) {
			time = -time;
		}
		long hours = time / TimeUnit.HOURS.toMillis(1);
		time -= hours * TimeUnit.HOURS.toMillis(1);
		long minutes = time / TimeUnit.MINUTES.toMillis(1);
		time -= minutes * TimeUnit.MINUTES.toMillis(1);
		long seconds = time / TimeUnit.SECONDS.toMillis(1);
		time -= seconds * TimeUnit.SECONDS.toMillis(1);
		long milliseconds = time;

		StringBuilder text = new StringBuilder();
		if (isNegative) {
			text.append("-");
		}
		if (hours > 0) {
			text.append(String.format("%d:%02d:%d", hours, minutes, seconds));
		} if (minutes > 0) {
			text.append(String.format("%02d:%02d", minutes, seconds));
		} else {
			text.append(String.format("%02d.%02d", seconds, milliseconds / 10));
		}
		return text.toString();
	}
}
