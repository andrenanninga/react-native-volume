# react-native-volume

A react-native bridge for controlling media volume on Android.

## Instalation

**android/settings.gradle**
```
include ':react-native-volume'
project(':react-native-volume').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-volume/android')
```

**android/app/build.gradle**
```
dependencies {
   ...
   compile project(':react-native-volume')
}
```

**MainActivity.java**
On top, where imports are:
```java
import com.tapme.RNVolume.VolumePackage;
```

Under `.addPackage(new MainReactPackage())`:

```java
.addPackage(new VolumePackage())
```

### Note: In react-native >= 0.29.0 you have to edit `MainApplication.java`

**MainApplication.java** (react-native >= 0.29.0)

On top, where imports are:

```java
import com.tapme.RNVolume.VolumePackage;
```

Under `.addPackage(new MainReactPackage())`:

```java
.addPackage(new VolumePackage())
```

## Usage

```
import { getVolume, setVolume, getMaxVolume, onVolumeChange } from 'react-native-volume';

// Get the maximum value for the media stream
const maxVolume = getMaxVolume();
console.log(maxVolume);

// Get the current volume level as integer, returns a promise
getVolume().then(volume => console.log(volume));

// Set the current volume, (doesn't show system popup)
setVolume(10);

// Listen to volume changes
onVolumeChange(volume => console.log(volume));
```
