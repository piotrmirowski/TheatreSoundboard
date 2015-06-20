# Theatre Soundboard
This repository stores Android Java code that can be used for building a soundboard app. That code can be used to handle music and sound effects in a theatrical play, directly from Android phone.

Copyright (c) 2014-2015 Piotr Mirowski

# How to modify the code 
The code was developed using the Eclipse IDE on a Mac. Please refer to the following resources to make your own edits:
* Android Development Tools (ADT): http://developer.android.com/tools/help/adt.html
* Eclipse: http://www.eclipse.org/
* ADT plugin for Eclipse: http://developer.android.com/tools/sdk/eclipse-adt.html

# Requirements for running the app
Minimum Android version 4.1 (Jelly Bean)

# Changing the sound files
The music and sound effect files are stored in res/raw as `.mp3` files.
If you want to add new sounds, remember that filenames need to be lowercase and alphanumeric only, e.g.: `**music01.mp3**`
Any resource file, e.g., `**newfile.mp3**`, can then be references in the code in the following object:
```
R.raw.newfile
```

# Changing the code source
The code source is in:
```
src/com/piotr/theatresoundboard/TheatreSoundboard.java
```

# Changing the number of sound files and the layout of the app
The layout of the app is stored in an `.xml` file at:
```
res/layout/activity_theatre_soundboard.xml
```
You can see that individual buttons have callbacks, e.g.:
```xml
<Button
    android:id="@+id/button01"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:hint="Music 01"
    android:onClick="playMusic01"
    android:text="Music 01" />
```
The text and click hint on the button can be easily modified.
The callback function `**playMusic01**` needs to be implemented in the main code as following:
```java
public void playMusic15(View view) {
  // some code
}
```

# Installing the modified code on the phone
The Eclipse ADT plugin handles it in a transparent way. You just need to plugin the phone by USB, activate the developer options on the phone, and press "PLAY" in the Eclipse run/debug environment to transfer the build binary and start running the app.
You can also manually install the app binary, which is stored at:
```
bin/TheatreSoundboard.apk
```
