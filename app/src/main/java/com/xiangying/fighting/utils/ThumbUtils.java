/*
 * ThumbUtils     2016/12/19 08:05
 * Copyright (c) 2016 Koterwong All right reserved
 */
package com.xiangying.fighting.utils;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.support.v4.util.LruCache;

import java.util.Hashtable;

/**
 * Created by Koterwong on 2016/12/19 08:05
 */
public class ThumbUtils {
  /**
   * Create a video thumbnail for a video. May return null if the video is
   * corrupt or the format is not supported.
   *
   * @param filePath the path of video file
   * @param kind     could be MINI_KIND or MICRO_KIND
   */
  private LruCache<String, Bitmap> mLruCache;
  private static ThumbUtils sThumbUtils;

  private ThumbUtils() {
    int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
    int cacheSize = maxMemory / 16;
    mLruCache = new LruCache<String, Bitmap>(cacheSize) {
      @Override protected int sizeOf(String key, Bitmap value) {
        return value.getByteCount() / 1024;
      }
    };
  }

  public synchronized static ThumbUtils get() {
    if (sThumbUtils == null) {
      sThumbUtils = new ThumbUtils();
    }
    return sThumbUtils;
  }

  public Bitmap createVideoThumbnail(String filePath, int kind) {
    if (mLruCache.get(filePath) != null) {
      return mLruCache.get(filePath);
    }

    Bitmap bitmap = null;
    MediaMetadataRetriever retriever = new MediaMetadataRetriever();
    try {
      if (filePath.startsWith("http://")
          || filePath.startsWith("https://")
          || filePath.startsWith("widevine://")) {
        retriever.setDataSource(filePath, new Hashtable<String, String>());
      } else {
        retriever.setDataSource(filePath);
      }
      bitmap = retriever.getFrameAtTime(-1);
    } catch (IllegalArgumentException ex) {
      // Assume this is a corrupt video file
      ex.printStackTrace();
    } catch (RuntimeException ex) {
      // Assume this is a corrupt video file.
      ex.printStackTrace();
    } finally {
      try {
        retriever.release();
      } catch (RuntimeException ex) {
        // Ignore failures while cleaning up.
        ex.printStackTrace();
      }
    }
    if (bitmap == null)
      return null;

    if (kind == MediaStore.Images.Thumbnails.MINI_KIND) {
      // Scale down the bitmap if it's too large.
      int width = bitmap.getWidth();
      int height = bitmap.getHeight();
      int max = Math.max(width, height);
      if (max > 512) {
        float scale = 512f / max;
        int w = Math.round(scale * width);
        int h = Math.round(scale * height);
        bitmap = Bitmap.createScaledBitmap(bitmap, w, h, true);
      }
    } else if (kind == MediaStore.Images.Thumbnails.MICRO_KIND) {
      bitmap = ThumbnailUtils.extractThumbnail(bitmap,
          96,
          96,
          ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
    }
    if (bitmap != null) {
      mLruCache.put(filePath, bitmap);
    }
    return bitmap;
  }
}
