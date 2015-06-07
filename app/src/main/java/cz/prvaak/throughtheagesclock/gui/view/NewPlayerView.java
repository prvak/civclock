package cz.prvaak.throughtheagesclock.gui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cz.prvaak.throughtheagesclock.R;
import cz.prvaak.throughtheagesclock.TimeAmount;
import cz.prvaak.throughtheagesclock.gui.PlayerColor;
import cz.prvaak.throughtheagesclock.gui.PlayerData;
import cz.prvaak.throughtheagesclock.gui.widget.TimePicker;

/**
 * View that displays new player's settings.
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

	public void setPlayerData(final PlayerColor playerColor, PlayerData playerData,
			final Listener buttonListener) {
		TimePicker.Listener onChangeListener = new TimePicker.Listener() {
			@Override
			public void onValueChanged() {
				buttonListener.onDataChanged(playerColor, getPlayerData());
			}
		};
		TimePicker baseTimePicker = (TimePicker) findViewById(R.id.base_time_picker);
		baseTimePicker.setTime(playerData.baseTime);
		baseTimePicker.setOnChangeListener(onChangeListener);
		TimePicker turnBonusTimePicker = (TimePicker) findViewById(R.id.turn_bonus_time_picker);
		turnBonusTimePicker.setTime(playerData.turnBonusTime);
		turnBonusTimePicker.setOnChangeListener(onChangeListener);
		TimePicker upkeepTimePicker = (TimePicker) findViewById(R.id.upkeep_time_picker);
		upkeepTimePicker.setTime(playerData.upkeepTime);
		upkeepTimePicker.setOnChangeListener(onChangeListener);

		TextView timeOverview = (TextView) findViewById(R.id.time_overview_text);
		timeOverview.setText(String.format("%s + %s (%s)", playerData.baseTime.format(),
				playerData.turnBonusTime.format(), playerData.upkeepTime.format()));

		TextView playerName = (TextView) findViewById(R.id.player_name);
		playerName.setText(playerColor.getNameResourceId());
		setBackgroundColor(getResources().getColor(playerColor.getColorResourceId()));

		Button removePlayerButton = (Button) findViewById(R.id.remove_player_button);
		removePlayerButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				buttonListener.onRemovePlayer(playerColor);
			}
		});

		// Set change color buttons.
		List<PlayerColor> otherColors = new ArrayList<>(Arrays.asList(PlayerColor.values()));
		otherColors.remove(playerColor);
		setChangeColorButton(R.id.change_color_button_0, playerColor, otherColors.get(0),
				buttonListener);
		setChangeColorButton(R.id.change_color_button_1, playerColor, otherColors.get(1),
				buttonListener);
		setChangeColorButton(R.id.change_color_button_2, playerColor, otherColors.get(2),
				buttonListener);

		// Expand/Collapse when clicked on the overview.
		View playerOverview = findViewById(R.id.player_overview);
		playerOverview.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				View playerDetailsView = findViewById(R.id.player_details_view);
				if (playerDetailsView.getVisibility() == GONE) {
					playerDetailsView.setVisibility(VISIBLE);
				} else {
					playerDetailsView.setVisibility(GONE);
				}
			}
		});
	}

	public PlayerData getPlayerData() {
		TimePicker baseTimePicker = (TimePicker) findViewById(R.id.base_time_picker);
		TimePicker turnBonusTimePicker = (TimePicker) findViewById(R.id.turn_bonus_time_picker);
		TimePicker upkeepTimePicker = (TimePicker) findViewById(R.id.upkeep_time_picker);

		TimeAmount baseTime = baseTimePicker.getTime();
		TimeAmount turnBonusTime = turnBonusTimePicker.getTime();
		TimeAmount upkeepTime = upkeepTimePicker.getTime();

		return new PlayerData(baseTime, turnBonusTime, upkeepTime);
	}

	private void setChangeColorButton(int buttonId, final PlayerColor currentColor,
			final PlayerColor newColor, final Listener buttonListener) {
		if (newColor == currentColor) {
			throw new IllegalArgumentException("New and current color cannot be the same!");
		}
		Button button = (Button) findViewById(buttonId);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				buttonListener.onChangeColor(currentColor, newColor);
			}
		});

		button.setText(newColor.getNameResourceId());
	}

	public interface Listener {
		void onRemovePlayer(PlayerColor playerColor);
		void onChangeColor(PlayerColor playerColor, PlayerColor newColor);
		void onDataChanged(PlayerColor playerColor, PlayerData playerData);
	}
}
