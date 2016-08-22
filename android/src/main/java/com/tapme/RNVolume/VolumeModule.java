package com.tapme.RNVolume;

import android.content.Context;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.RCTNativeAppEventEmitter;

import java.util.HashMap;
import java.util.Map;

public class VolumeModule extends ReactContextBaseJavaModule {

	private Handler handler;
	private AudioManager audioManager;
	private SettingsContentObserver settingsContentObserver;

	public VolumeModule(ReactApplicationContext reactContext) {
		super(reactContext);

		Looper looper = Looper.getMainLooper();
		handler = new Handler(looper) {
			@Override
			public void handleMessage(Message msg) {
				Log.d("POEP", "message");
				Log.d("POEP", msg.toString());
				// sendEvent(reactContext, "volumeChange", msg);
			}
		};

		audioManager = (AudioManager) reactContext.getSystemService(Context.AUDIO_SERVICE);
		settingsContentObserver = new SettingsContentObserver(reactContext, handler);

		reactContext.getContentResolver()
			.registerContentObserver(android.provider.Settings.System.CONTENT_URI, true, settingsContentObserver);
	}

	@Override
	public String getName() {
		return "RNVolume";
	}

	@Override
	public @Nullable Map<String, Object> getConstants() {
    HashMap<String, Object> constants = new HashMap<String, Object>();

    constants.put("maxVolume", audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));

    return constants;
	}

	@ReactMethod
	public void getVolume(Promise promise) {
		int volume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

		promise.resolve(volume);
	}

	@ReactMethod
	public void setVolume(int volume) {
		audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
	}

	@ReactMethod
	public void muteVolume() {
		int volume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

		audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, AudioManager.ADJUST_MUTE);
	}

	@ReactMethod
	public void unmuteVolume() {
		int volume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

		audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, AudioManager.ADJUST_UNMUTE);
	}
}