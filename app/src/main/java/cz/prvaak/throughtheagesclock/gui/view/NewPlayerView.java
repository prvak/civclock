package cz.prvaak.throughtheagesclock.gui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cz.prvaak.throughtheagesclock.R;
import cz.prvaak.throughtheagesclock.TimeAmount;
import cz.prvaak.throughtheagesclock.gui.Player;
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
		TimePicker turnBonusTimePicker1 = (TimePicker) findViewById(R.id.turn_bonus_time_picker1);
		turnBonusTimePicker1.setTime(playerData.turnBonusTimes[0]);
		turnBonusTimePicker1.setOnChangeListener(onChangeListener);
		TimePicker turnBonusTimePicker2 = (TimePicker) findViewById(R.id.turn_bonus_time_picker2);
		turnBonusTimePicker2.setTime(playerData.turnBonusTimes[1]);
		turnBonusTimePicker2.setOnChangeListener(onChangeListener);
		TimePicker turnBonusTimePicker3 = (TimePicker) findViewById(R.id.turn_bonus_time_picker3);
		turnBonusTimePicker3.setTime(playerData.turnBonusTimes[2]);
		turnBonusTimePicker3.setOnChangeListener(onChangeListener);
		TimePicker upkeepTimePicker = (TimePicker) findViewById(R.id.upkeep_time_picker);
		upkeepTimePicker.setTime(playerData.upkeepTime);
		upkeepTimePicker.setOnChangeListener(onChangeListener);

		TextView baseTime = (TextView) findViewById(R.id.base_time_text);
		baseTime.setText(String.format("%s", playerData.baseTime.format()));
		TextView turnBonusTime = (TextView) findViewById(R.id.turn_bonuses_text);
		turnBonusTime.setText(String.format("I: %s II: %s III: %s",
				playerData.turnBonusTimes[0].format(), playerData.turnBonusTimes[1].format(),
				playerData.turnBonusTimes[2].format()));

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

		CheckBox uniqueTimeCheckbox = (CheckBox) findViewById(R.id.unique_time_checkbox);
		uniqueTimeCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				buttonListener.onHasUniqueTimeChanged(playerColor, isChecked);
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
		TimePicker turnBonusTimePicker1 = (TimePicker) findViewById(R.id.turn_bonus_time_picker1);
		TimePicker turnBonusTimePicker2 = (TimePicker) findViewById(R.id.turn_bonus_time_picker2);
		TimePicker turnBonusTimePicker3 = (TimePicker) findViewById(R.id.turn_bonus_time_picker3);
		TimePicker upkeepTimePicker = (TimePicker) findViewById(R.id.upkeep_time_picker);

		TimeAmount baseTime = baseTimePicker.getTime();
		TimeAmount[] turnBonusTimes = new TimeAmount[Player.TIMES_PER_EPOCH];
		turnBonusTimes[0] = turnBonusTimePicker1.getTime();
		turnBonusTimes[1] = turnBonusTimePicker2.getTime();
		turnBonusTimes[2] = turnBonusTimePicker3.getTime();
		TimeAmount upkeepTime = upkeepTimePicker.getTime();

		return new PlayerData(baseTime, turnBonusTimes, upkeepTime);
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
		void onHasUniqueTimeChanged(PlayerColor playerColor, boolean hasUniqueTime);
	}
}
