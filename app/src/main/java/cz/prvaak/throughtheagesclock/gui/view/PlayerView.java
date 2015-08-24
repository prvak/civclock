package cz.prvaak.throughtheagesclock.gui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cz.prvaak.throughtheagesclock.R;
import cz.prvaak.throughtheagesclock.TimeAmount;
import cz.prvaak.throughtheagesclock.TimeInstant;
import cz.prvaak.throughtheagesclock.clock.PlayerClock;
import cz.prvaak.throughtheagesclock.gui.PlayerColor;
import cz.prvaak.throughtheagesclock.gui.view.display.PhaseDisplay;
import cz.prvaak.throughtheagesclock.gui.view.display.TimeDisplay;

/**
 * View that displays information about one player.
 */
public abstract class PlayerView extends RelativeLayout implements TimeDisplay, PhaseDisplay {

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

	public void setPlayerClock(PlayerClock playerClock) {
		this.playerClock = playerClock;
		PlayerColor playerColor = (PlayerColor) playerClock.getPlayerId();

		TextView playerName = (TextView) findViewById(R.id.player_name);
		playerName.setText(playerColor.getNameResourceId());

		setBackgroundColor(getResources().getColor(playerColor.getColorResourceId()));
	}

	public void setGamePaused(boolean isGamePaused) {
		TextView pauseHint = (TextView) findViewById(R.id.pause_hint_text);
		if (pauseHint != null) {
			if (isGamePaused) {
				pauseHint.setText(R.string.hint_how_to_resume);
			} else {
				pauseHint.setText(R.string.hint_how_to_pause);
			}
		}
	}

	@Override
	public void updateTime(TimeInstant now) {
		TextView remainingReserveTime = (TextView) findViewById(R.id.remaining_reserve_time);
		if (remainingReserveTime != null) {
			TimeAmount time = playerClock.getRemainingReserveTime(now);
			remainingReserveTime.setText(timeToString(time));
		}

		TextView remainingUpkeepTime = (TextView) findViewById(R.id.remaining_upkeep_time);
		if (remainingReserveTime != null) {
			TimeAmount time = playerClock.getRemainingUpkeepTime(now);
			String text = String.format("(%s)", timeToString(time));
			remainingUpkeepTime.setText(text);
		}
	}

	private String timeToString(TimeAmount time) {
		StringBuilder text = new StringBuilder();
		if (time.isNegative()) {
			text.append("-");
		}
		if (time.getHours() > 0) {
			// eg. 2:07:31
			text.append(String.format("%d:%02d:%02d", time.getHours(), time.getMinutes(),
					time.getSeconds()));
		} else if (time.getMinutes() > 0) {
			// eg. 07:31
			text.append(String.format("%02d:%02d", time.getMinutes(), time.getSeconds()));
		} else {
			// eg. 0:31.6
			text.append(String.format("0:%02d.%01d", time.getSeconds(),
					time.getMilliseconds() / 100));
		}
		return text.toString();
	}
}
