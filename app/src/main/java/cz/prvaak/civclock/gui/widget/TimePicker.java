package cz.prvaak.civclock.gui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cz.prvaak.civclock.R;
import cz.prvaak.civclock.TimeAmount;

/**
 * Widget for selecting time.
 */
public class TimePicker extends RelativeLayout {

	/** Listener for reporting changes within this widget. */
	public interface Listener {
		void onValueChanged();
	}

	private EditText timePickerReference;
	private TimeAmount lastValidTime;

	public TimePicker(Context context) {
		super(context);
	}

	public TimePicker(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TimePicker(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public TimeAmount getTime() {
		EditText timePicker = getTimePicker();
		String text = timePicker.getText().toString();
		try {
			lastValidTime = new TimeAmount(text);
		} catch (IllegalArgumentException e) {
			timePicker.setError(getContext().getResources().getText(R.string.wrong_time_format));
		}
		return lastValidTime;
	}

	public void setTime(TimeAmount amount) {
		if (amount.equals(lastValidTime)) {
			return;
		}
		EditText timePicker = getTimePicker();
		timePicker.setText(amount.format(TimeAmount.Formatting.SIMPLE));
		timePicker.setError(null);
		lastValidTime = amount;
	}

	public void setOnChangeListener(final Listener listener) {
		final EditText timePicker = getTimePicker();

		timePicker.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					onValueChanged(listener);
				}
				return false;
			}
		});
		timePicker.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus) {
					onValueChanged(listener);
				}
			}
		});
	}

	private EditText getTimePicker() {
		if (timePickerReference == null) {
			timePickerReference = (EditText) findViewById(R.id.raw_time_picker);
		}
		if (timePickerReference == null) {
			throw new IllegalArgumentException("Time Picker view not found!");
		}
		return timePickerReference;
	}

	private void onValueChanged(Listener listener) {
		getTime();
		listener.onValueChanged();
	}
}
