import { NativeModules, NativeAppEventEmitter } from 'react-native';

const RNVolume = NativeModules.RNVolume;

export function getVolume() {
	return RNVolume.getVolume();
}

export function setVolume(volume) {
	RNVolume.setVolume(volume);
}

export function muteVolume() {
	RNVolume.muteVolume();
}

export function unmuteVolume() {
	RNVolume.unmuteVolume();
}

export function getMaxVolume() {
	return RNVolume.maxVolume;
}

export function onVolumeChange(listener) {
	NativeAppEventEmitter.addListener('volumeChange', data => {
		listener(data.volume);
	});
}
