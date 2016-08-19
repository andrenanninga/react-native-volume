package com.tapme.RNVolume;

import android.content.Context;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.os.Handler;
import android.util.Log;

public class SettingsContentObserver extends ContentObserver {
	int previousVolume;
	Context context;

	public SettingsContentObserver(Context c, Handler handler) {
		super(handler);
		context = c;

		AudioManager audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		previousVolume = audio.getStreamVolume(AudioManager.STREAM_MUSIC);
	}

	@Override
	public boolean deliverSelfNotifications() {
		return super.deliverSelfNotifications();
	}

	@Override
	public void onChange(boolean selfChange) {
		super.onChange(selfChange);

		AudioManager audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		int currentVolume = audio.getStreamVolume(AudioManager.STREAM_MUSIC);

		int delta = previousVolume - currentVolume;

		if(delta > 0) {
			Log.d("POEP", "Decreased");
			Log.d("POEP", Integer.toString(currentVolume));
			previousVolume = currentVolume;
		}
		else if(delta < 0) {
			Log.d("POEP", "Increased");
			Log.d("POEP", Integer.toString(currentVolume));
			previousVolume = currentVolume;
		}
	}
}