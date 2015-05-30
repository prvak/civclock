package cz.prvaak.throughtheagesclock.gui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import cz.prvaak.throughtheagesclock.R;
import cz.prvaak.throughtheagesclock.TimeAmount;

/**
 * Widget for selecting time.
 */
public class TimePicker extends RelativeLayout {

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
		TimePartPicker hoursPicker = (TimePartPicker) findViewById(R.id.time_picker_hours);
		TimePartPicker minutesPicker = (TimePartPicker) findViewById(R.id.time_picker_minutes);
		TimePartPicker secondsPicker = (TimePartPicker) findViewById(R.id.time_picker_seconds);
		return new TimeAmount(hoursPicker.getValue(), minutesPicker.getValue(),
				secondsPicker.getValue(), 0L);
	}

	public void setTime(TimeAmount amount) {
		TimePartPicker hoursPicker = (TimePartPicker) findViewById(R.id.time_picker_hours);
		TimePartPicker minutesPicker = (TimePartPicker) findViewById(R.id.time_picker_minutes);
		TimePartPicker secondsPicker = (TimePartPicker) findViewById(R.id.time_picker_seconds);
		hoursPicker.setValue((int)amount.getHours());
		minutesPicker.setValue((int)amount.getMinutes());
		secondsPicker.setValue((int)amount.getSeconds());
	}
}