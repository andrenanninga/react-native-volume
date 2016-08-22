package com.tapme.RNVolume;

import android.content.Context;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.RCTNativeAppEventEmitter;

public class SettingsContentObserver extends ContentObserver {
	int previousVolume;
	ReactContext context;

	public SettingsContentObserver(ReactContext c, Handler handler) {
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

    WritableMap data = Arguments.createMap();
		AudioManager audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		int currentVolume = audio.getStreamVolume(AudioManager.STREAM_MUSIC);

		int delta = previousVolume - currentVolume;

		if(delta != 0) {
			previousVolume = currentVolume;

			data.putInt("volume", currentVolume);
			sendEvent(context, "volumeChange", data);
		}
	}

	private void sendEvent(ReactContext reactContext, String eventName, @Nullable WritableMap params) {
		reactContext
			.getJSModule(RCTNativeAppEventEmitter.class)
			.emit(eventName, params);
	}
}