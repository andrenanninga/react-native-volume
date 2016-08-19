package com.tapme.RNVolume;

import android.widget.Toast;
import android.content.Context;
import android.media.AudioManager;
import android.support.annotation.Nullable;
import android.os.Handler;
import android.os.Message;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.RCTNativeAppEventEmitter;

public class VolumeModule extends ReactContextBaseJavaModule {

	private Handler handler;
	private AudioManager audioManager;
	private SettingsContentObserver settingsContentObserver;

	public VolumeModule(ReactApplicationContext reactContext) {
		super(reactContext);

		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
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

	@ReactMethod
	public void getVolume(Promise promise) {
		int volume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

		promise.resolve(volume);
	}

	private void sendEvent(ReactContext reactContext, String eventName, @Nullable WritableMap params) {
		reactContext
			.getJSModule(RCTNativeAppEventEmitter.class)
			.emit(eventName, params);
	}
}