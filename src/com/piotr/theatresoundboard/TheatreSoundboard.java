package com.piotr.theatresoundboard;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Context;
import android.widget.TextView;

// Import classes needed for the sounds
import android.media.MediaPlayer;
import android.media.AudioManager;
import java.io.IOException;

// Import the resources (stored in res/raw)
import com.piotr.theatresoundboard.R;

// Import classes needed for the timer
import android.os.CountDownTimer;


/**
 * Main activity class for playing different sound effects and music.
 *
 * @author Piotr Mirowski
 * Copyright (c) 2015 Piotr Mirowski, piotr.mirowski@computer.org
 */
public class TheatreSoundboard extends ActionBarActivity {

  // Media players (one per song) used by the app
  protected MediaPlayer[] players;
  protected MediaPlayer[] playersInterruption;
  protected TextView textField;

  // Current song and current volume
  protected int currentSong;
  protected int volume;
  protected final int MAX_VOLUME = 20;

  // Create a fast timer
  CountDownTimer timerFast = new CountDownTimer(1000, 100) {
    // Set the volume down from 20 to 0, by decrements of 2 every 0.1s
    public void onTick(long millisUntilFinished) {
      int vol = (int)(millisUntilFinished / 50);
      setVolume(vol);
    }
    // Stop all music and sound once the counter expires
    public void onFinish() {
      setVolume(0);
      stopAll();
    }
  };

  // Create a slow timer
  CountDownTimer timerSlow = new CountDownTimer(10000, 100) {
    // Set the volume down from 20 to 0, by decrements of 1 every 0.5s
    public void onTick(long millisUntilFinished) {
      int vol = (int)(millisUntilFinished / 500);
      setVolume(vol);
    }
    // Stop all music and sound once the counter expires
    public void onFinish() {
      setVolume(0);
      stopAll();
    }
  };

  
  /**
   * Callback called when app is created.
   * This is were 
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_theatre_soundboard);
    textField = (TextView) this.findViewById(R.id.textField);
    // Initialize the player for the ringtone interruption
    // Modify the lines below to change the number of sound effects
    // and the filenames in res/raw.
    // If you change the number of sound effects, you need to add/delete
    // callbacks playSound00, playSound01, playSound02, etc...
    playersInterruption = new MediaPlayer[2];
    playersInterruption[0] = MediaPlayer.create(this, R.raw.sound00);
    playersInterruption[1] = MediaPlayer.create(this, R.raw.sound01);
    // Initialize all the players for all the songs
    // Modify the lines below to change the number of sound effects
    // and the filenames in res/raw
    // If you change the number of sound effects, you need to add/delete
    // callbacks playMusic00, playMusic01, playMusic02, etc...
    players = new MediaPlayer[16];
    players[0] = MediaPlayer.create(this, R.raw.music00);
    players[1] = MediaPlayer.create(this, R.raw.music01);
    players[2] = MediaPlayer.create(this, R.raw.music02);
    players[3] = MediaPlayer.create(this, R.raw.music03);
    players[4] = MediaPlayer.create(this, R.raw.music04);
    players[5] = MediaPlayer.create(this, R.raw.music05);
    players[6] = MediaPlayer.create(this, R.raw.music06);
    players[7] = MediaPlayer.create(this, R.raw.music07);
    players[8] = MediaPlayer.create(this, R.raw.music08);
    players[9] = MediaPlayer.create(this, R.raw.music09);
    players[10] = MediaPlayer.create(this, R.raw.music10);
    players[11] = MediaPlayer.create(this, R.raw.music11);
    players[12] = MediaPlayer.create(this, R.raw.music12);
    players[13] = MediaPlayer.create(this, R.raw.music13);
    players[14] = MediaPlayer.create(this, R.raw.music14);
    players[15] = MediaPlayer.create(this, R.raw.music15);
    // Current song is set to undefined
    currentSong = -1;
    // Volume is set to minimum
    volume = 0;
  }

  /**
   * Unused callback
   */
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.theatre_soundboard, menu);
    return true;
  }


  /**
   * Unused callback
   */
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();
    if (id == R.id.action_settings) {
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  
  /**
   * Callback called when app exits
   */
  @Override
  protected void onStop() {
    // Stop all the music and the sound effects
    stopAll();
    super.onStop();
  }
  @Override
  protected void onDestroy() {
    // Stop all the music and the sound effects
    stopAll();
    super.onDestroy();
  }

  
  /**
   * Set the volume to a specific value between 0 and 20
   */
  public void setVolume(int vol) {
    volume = Math.min(Math.max(vol, 0), MAX_VOLUME);
    AudioManager audioManager =
        (AudioManager)getSystemService(Context.AUDIO_SERVICE);
    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
    textField.setText("Volume: " + vol);
  }

  
  /**
   * Stop current song and play new one
   */
  private void playMusic(int idx) {
    textField.setText("Music " + idx);
    timerSlow.cancel();
    timerFast.cancel();
    // Stop all the songs and the ringtone but the new one
    for (int i = 0; i < players.length; i++) {
      if (i != idx) {
        stopMusic(i);
      }
    }
    // Play new song from start
    if ((idx >= 0) && (idx < players.length)) {
      currentSong = idx;
      players[idx].seekTo(0);
      players[idx].start();
      setVolume(MAX_VOLUME);
    }
  }

  
  /**
   * Play a specific sound effect
   */
  public void playSound(int idx) {
    if ((idx >= 0) && (idx < playersInterruption.length)) {
      // Play ringtone from start
      playersInterruption[idx].seekTo(0);
      playersInterruption[idx].start();
      setVolume(MAX_VOLUME);
    }
  }

  
  /**
   * Stop a specific sound effect
   */
  private void stopSound(int idx) {
    if ((idx >= 0) && (idx < playersInterruption.length)) {
      // Stop the selected song
      if (playersInterruption[idx].isPlaying()) {
        playersInterruption[idx].stop();
        try {
          playersInterruption[idx].prepare();
        } catch (IOException e) {
        }
      }
    }
  }

  
  /**
   * Stop a specific song
   */
  private void stopMusic(int idx) {
    timerSlow.cancel();
    timerFast.cancel();
    // Stop the selected song
    if ((idx >= 0) && (idx < players.length) &&
        (players[idx].isPlaying())) {
      players[idx].stop();
      try {
        players[idx].prepare();
      } catch (IOException e) {
      }
    }
  }
  
  
  /**
   * Stop all sound
   */
  private void stopAll() {
    timerSlow.cancel();
    timerFast.cancel();
    for (int idx = 0; idx < playersInterruption.length; idx++) {
      stopSound(idx);
    }
    for (int idx = 0; idx < players.length; idx++) {
      stopMusic(idx);
    }
  }
  public void stopAll(View view) {
    stopAll();
  }

  
  /**
   * Callbacks called when the user touches the "play music" buttons.
   * This code looks repetitive but I did not know if one can pass arguments
   * from the XML.
   * Copy/paste these functions and change their names if you need
   * to add additional callbacks.
   */
  public void playMusic00(View view) {
    playMusic(0);
  }
  public void playMusic01(View view) {
    playMusic(1);
  }
  public void playMusic02(View view) {
    playMusic(2);
  }
  public void playMusic03(View view) {
    playMusic(3);
  }
  public void playMusic04(View view) {
    playMusic(4);
  }
  public void playMusic05(View view) {
    playMusic(5);
  }
  public void playMusic06(View view) {
    playMusic(6);
  }
  public void playMusic07(View view) {
    playMusic(7);
  }
  public void playMusic08(View view) {
    playMusic(8);
  }
  public void playMusic09(View view) {
    playMusic(9);
  }
  public void playMusic10(View view) {
    playMusic(10);
  }
  public void playMusic11(View view) {
    playMusic(11);
  }
  public void playMusic12(View view) {
    playMusic(12);
  }
  public void playMusic13(View view) {
    playMusic(13);
  }
  public void playMusic14(View view) {
    playMusic(14);
  }
  public void playMusic15(View view) {
    playMusic(15);
  }


  /**
   * Callbacks called when the user touches the "play sound" buttons.
   * Play a sound effect/interruption.
   * Copy/paste these functions and change their names if you need
   * to add additional callbacks.
   */
  public void playSound00(View view) {
    playSound(0);
  }
  public void playSound01(View view) {
    playSound(1);
  }

  
  /**
   * Mute fast (in 1sec)
   */
  public void muteFast(View view) {
    timerSlow.cancel();
    timerFast.start();
  }


  /**
   * Mute slowly (in 5sec)
   */
  public void muteSlow(View view) {
    timerFast.cancel();
    timerSlow.start();
  }
}
