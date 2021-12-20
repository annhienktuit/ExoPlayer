package com.annhienktuit.demo_nhien.utils;

import android.app.Application;
import android.util.Log;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.database.ExoDatabaseProvider;
import com.google.android.exoplayer2.database.StandaloneDatabaseProvider;
import com.google.android.exoplayer2.offline.Download;
import com.google.android.exoplayer2.offline.DownloadManager;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;
import java.util.concurrent.Executor;

public class CacheUtils extends Application {
  public static SimpleCache simpleCache;
  public static Long cacheSize;
  public static LeastRecentlyUsedCacheEvictor leastRecentlyUsedCacheEvictor;
  public static StandaloneDatabaseProvider standaloneDatabaseProvider;
  public static DownloadManager downloadManager;
  final String TAG = "CacheDownloadManager";
  @Override
  public void onCreate() {
    super.onCreate();
    cacheSize = (long) (90 * 1024 * 1024);
    leastRecentlyUsedCacheEvictor = new LeastRecentlyUsedCacheEvictor(cacheSize);
    standaloneDatabaseProvider = new StandaloneDatabaseProvider(this);
    simpleCache = new SimpleCache(getCacheDir(), leastRecentlyUsedCacheEvictor, standaloneDatabaseProvider);
  }
}
