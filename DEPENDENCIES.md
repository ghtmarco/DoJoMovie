# Dependencies

## Android SDK
- **compileSdk**: 34
- **targetSdk**: 34
- **minSdk**: 24

## Core Dependencies

### AndroidX Libraries
```gradle
androidx.core:core-ktx:1.12.0
androidx.appcompat:appcompat:1.6.1
androidx.constraintlayout:constraintlayout:2.1.4
androidx.recyclerview:recyclerview:1.3.1
```

### Material Design
```gradle
com.google.android.material:material:1.10.0
```

### Navigation Components
```gradle
androidx.navigation:navigation-fragment-ktx:2.7.5
androidx.navigation:navigation-ui-ktx:2.7.5
```

### Google Maps
```gradle
com.google.android.gms:play-services-maps:18.2.0
```

### Networking
```gradle
com.android.volley:volley:1.2.1
```

### Image Loading
```gradle
com.github.bumptech.glide:glide:4.16.0
```

### ViewModel and LiveData
```gradle
androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0
androidx.lifecycle:lifecycle-livedata-ktx:2.7.0
```

## Testing Dependencies

### Unit Testing
```gradle
junit:junit:4.13.2
```

### Android Testing
```gradle
androidx.test.ext:junit:1.1.5
androidx.test.espresso:espresso-core:3.5.1
```

## Build Configuration

### Plugins
```gradle
id("com.android.application") version "8.1.2"
id("org.jetbrains.kotlin.android") version "1.9.10"
```

### Java Version
```gradle
sourceCompatibility = JavaVersion.VERSION_1_8
targetCompatibility = JavaVersion.VERSION_1_8
jvmTarget = "1.8"
```

## Permissions
```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name="android.permission.SEND_SMS" />
```

## Build Features
```gradle
buildFeatures {
    viewBinding = true
}
```