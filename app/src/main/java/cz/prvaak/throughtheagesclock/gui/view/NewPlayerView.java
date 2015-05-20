package cz.prvaak.throughtheagesclock.gui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import cz.prvaak.throughtheagesclock.R;
import cz.prvaak.throughtheagesclock.gui.PlayerData;
import cz.prvaak.throughtheagesclock.gui.widget.TimePicker;

/**
 * Created by michal on 5/18/15.
 */
public class NewPlayerView extends LinearLayout {

	public NewPlayerView(Context context) {
		super(context);
	}

	public NewPlayerView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NewPlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public void setPlayerData(PlayerData playerData) {
		TimePicker baseTimePicker = (TimePicker) findViewById(R.id.base_time_picker);
		baseTimePicker.setTime(playerData.baseTime);
		TimePicker turnBonusTimePicker = (TimePicker) findViewById(R.id.turn_bonus_time_picker);
		turnBonusTimePicker.setTime(playerData.turnBonusTime);
		TimePicker upkeepTimePicker = (TimePicker) findViewById(R.id.upkeep_time_picker);
		upkeepTimePicker.setTime(playerData.upkeepTime);

		TextView timeOverview = (TextView) findViewById(R.id.time_overview_text);
		timeOverview.setText(String.format("%s + %s + (%s)", playerData.baseTime.format(),
				playerData.turnBonusTime.format(), playerData.upkeepTime.format()));

		TextView playerName = (TextView) findViewById(R.id.player_name);
		playerName.setText(playerData.playerColor.getNameResourceId());
		setBackgroundColor(getResources().getColor(playerData.playerColor.getColorResourceId()));
	}
}
