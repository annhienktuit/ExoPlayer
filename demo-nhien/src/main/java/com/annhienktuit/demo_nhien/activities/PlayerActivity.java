package com.annhienktuit.demo_nhien.activities;

import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.annhienktuit.demo_nhien.R;
import com.annhienktuit.demo_nhien.utils.CacheUtils;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.offline.DownloadManager;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;

public class PlayerActivity extends AppCompatActivity {

  final String mediaURL = "https://filesamples.com/samples/audio/mp3/Symphony%20No.6%20(1st%20movement).mp3";
  final String mp4URL = "https://file-examples-com.github.io/uploads/2017/04/file_example_MP4_1920_18MG.mp4";
  PlayerView playerView;
  ExoPlayer exoPlayer;
  MediaSource mediaSource;
  SimpleCache simpleCache = CacheUtils.simpleCache;
  HttpDataSource.Factory httpDataSourceFactory;
  DefaultDataSource.Factory defaultDataSourceFactory;
  DataSource.Factory cacheDataSourceFactory;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_player);
    attachView();
    initializeDataSource();
    initializeMedia();
    initializePlayer();
  }


  private void initializeDataSource() {
    httpDataSourceFactory = new DefaultHttpDataSource.Factory().setAllowCrossProtocolRedirects(true);
    defaultDataSourceFactory = new DefaultDataSource.Factory(this, httpDataSourceFactory);
    cacheDataSourceFactory = new CacheDataSource.Factory()
        .setCache(simpleCache)
        .setUpstreamDataSourceFactory(httpDataSourceFactory)
        .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR);
  }

  //TODO: kiem tra flow tu setMediaSource
  private void initializePlayer() {
    exoPlayer = new ExoPlayer.Builder(this)
        .setMediaSourceFactory(new DefaultMediaSourceFactory(cacheDataSourceFactory))
        .setLoadControl(new DefaultLoadControl())
        .setTrackSelector(new DefaultTrackSelector(this))
        .build();
    exoPlayer.setPlayWhenReady(true);
    exoPlayer.setMediaSource(mediaSource);
    exoPlayer.prepare();
    playerView.setPlayer(exoPlayer);
  }


  private void initializeMedia() {
    MediaItem mediaItem = MediaItem.fromUri(mp4URL);
    mediaSource = new ProgressiveMediaSource.Factory(cacheDataSourceFactory)
        .createMediaSource(mediaItem);
  }



  private void attachView() {
    playerView = findViewById(R.id.player_view);
  }
}
