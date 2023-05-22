# Note-AG
## Download

<a href="https://play.google.com/store/apps/details?id=com.note.noteapp&pli=1" target="_blank">
<img src="https://play.google.com/intl/en_us/badges/images/generic/en-play-badge.png" alt="Get it on Google Play" height="100"/></a>

## Plugin

```groovy
plugins {
    id 'com.android.application'
    id 'org.jetbrains.kotlin.android'
    id 'kotlin-android'
    id 'kotlin-android-extensions'
    id 'kotlin-kapt'
}
```
## Dependencies
```groovy
 implementation 'androidx.core:core-ktx:1.7.0'
    implementation 'androidx.appcompat:appcompat:1.5.1'
    implementation 'com.google.android.material:material:1.7.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.4'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.0'
    implementation 'androidx.navigation:navigation-fragment-ktx:2.5.1'
    implementation 'androidx.navigation:navigation-ui-ktx:2.5.1'
    implementation 'androidx.lifecycle:lifecycle-extensions:2.2.0'
    implementation 'androidx.legacy:legacy-support-v4:1.0.0'
    implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1'
    implementation "com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2"
    implementation 'com.google.android.material:material:1.3.0-alpha02'

// Timber
    implementation 'com.jakewharton.timber:timber:4.7.1'
// Retrofit
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
// Gson
    implementation 'com.google.code.gson:gson:2.8.9'
// Coroutine
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1'
// ViewModel
    implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1"
// LiveData
    implementation "androidx.lifecycle:lifecycle-livedata-ktx:2.5.1"
// Glide
    implementation 'com.github.bumptech.glide:glide:4.12.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.12.0'
// Dagger2
    implementation "com.google.dagger:dagger:2.35.1"
    implementation "com.google.dagger:dagger-android:2.35.1"
    implementation "com.google.dagger:dagger-android-support:2.26"
    kapt 'com.google.dagger:dagger-compiler:2.31.2'
    kapt 'com.google.dagger:dagger-android-processor:2.26'
// Room
    implementation "androidx.room:room-runtime:2.4.3"
    kapt "androidx.room:room-compiler:2.4.3"
    implementation "androidx.room:room-ktx:2.4.3"
    testImplementation "androidx.room:room-testing:2.4.3"
// LeakCanary
    debugImplementation 'com.squareup.leakcanary:leakcanary-android:2.5'

// for mac m1 comment dong nay
    kapt "org.xerial:sqlite-jdbc:3.36.0"

//preference
    implementation 'androidx.preference:preference-ktx:1.2.0'
    implementation 'com.klinkerapps:link_builder:2.0.5'
    implementation "androidx.recyclerview:recyclerview:1.2.1"
    // For control over item selection of both touch and mouse driven selection
    implementation "androidx.recyclerview:recyclerview-selection:1.1.0"
    implementation "com.github.Spikeysanju:MotionToast:1.4"
```
## License

Copyright © 2022, [Nguyen Duong The Vi](https://github.com/thevi31415).
