package com.xiangying.fighting.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.Hashtable;

/**
 * Created by HJ on 2016/1/7.
 */
public class BitmapCache {

    static private BitmapCache cache;
    /** 用于Chche内容的存储 */
    private Hashtable<Integer, MySoftRef> hashRefs;
    /** 垃圾Reference的队列（所引用的对象已经被回收，则将该引用存入队列中） */
    private ReferenceQueue<Bitmap> q;

    /**
     * 继承SoftReference，使得每一个实例都具有可识别的标识。
     */
    private class MySoftRef extends SoftReference<Bitmap> {
        private Integer _key = 0;

        public MySoftRef(Bitmap bmp, ReferenceQueue<Bitmap> q, int key) {
            super(bmp, q);
            _key = key;
        }
    }


    public int getBitmapSize(){
        return hashRefs.size();
    }

    private BitmapCache() {
        hashRefs = new Hashtable<Integer, MySoftRef>();
        q = new ReferenceQueue<Bitmap>();
    }

    /**
     * 取得缓存器实例
     */
    public static BitmapCache getInstance() {
            cache = new BitmapCache();
        return cache;
    }

    /**
     * 以软引用的方式对一个Bitmap对象的实例进行引用并保存该引用
     */
    private void addCacheBitmap(Bitmap bmp, Integer key) {
        cleanCache();// 清除垃圾引用
        MySoftRef ref = new MySoftRef(bmp, q, key);
        hashRefs.put(key, ref);
    }

    /**
     * 依据所指定的drawable下的图片资源ID号（可以根据自己的需要从网络或本地path下获取），重新获取相应Bitmap对象的实例
     */
    public Bitmap getBitmap(int resId, Context context) {
        Bitmap bmp = null;
        // 缓存中是否有该Bitmap实例的软引用，如果有，从软引用中取得。
        if (hashRefs.containsKey(resId)) {
            MySoftRef ref = hashRefs.get(resId);
            bmp = ref.get();
        }
        // 如果没有软引用，或者从软引用中得到的实例是null，重新构建一个实例，
        // 并保存对这个新建实例的软引用
        if (bmp == null) {
            // 传说decodeStream直接调用JNI>>nativeDecodeAsset()来完成decode，
            // 无需再使用java层的createBitmap，从而节省了java层的空间。
            bmp = BitmapFactory.decodeStream(context.getResources()
                    .openRawResource(resId));
            this.addCacheBitmap(bmp, resId);
        }
        return bmp;
    }

    /**
     * 依据所指定的drawable下的图片资源ID号（可以根据自己的需要从网络或本地path下获取），重新获取相应Bitmap对象的实例
     */
    public Bitmap getBitmapFromUrl(String url,int position) {
        Bitmap bmp = null;
        // 缓存中是否有该Bitmap实例的软引用，如果有，从软引用中取得。
        if (hashRefs.containsKey(position)) {
            MySoftRef ref = hashRefs.get(position);
            bmp = ref.get();
        }
        // 如果没有软引用，或者从软引用中得到的实例是null，重新构建一个实例，
        // 并保存对这个新建实例的软引用
        if (bmp == null) {
            // 传说decodeStream直接调用JNI>>nativeDecodeAsset()来完成decode，
            // 无需再使用java层的createBitmap，从而节省了java层的空间。
            bmp = getBitmapFromFile(new File(url),640,640);
            this.addCacheBitmap(bmp, position);
        }
        return bmp;
    }

    private void cleanCache() {
        MySoftRef ref = null;
        while ((ref = (MySoftRef) q.poll()) != null) {
            hashRefs.remove(ref._key);
        }
    }


    /**
     * 从网络加载图片
     * @param imageUrl
     * @param position
     * @param context
     * @return
     */
    public Bitmap getBitmapFromnet(String imageUrl,int position, Context context){
        Bitmap bmp = null;
        // 缓存中是否有该Bitmap实例的软引用，如果有，从软引用中取得。
        if (hashRefs.containsKey(position)) {
            MySoftRef ref = hashRefs.get(position);
            bmp = ref.get();
        }
        // 如果没有软引用，或者从软引用中得到的实例是null，重新构建一个实例，
        // 并保存对这个新建实例的软引用
        if (bmp == null) {
            // 传说decodeStream直接调用JNI>>nativeDecodeAsset()来完成decode，
            // 无需再使用java层的createBitmap，从而节省了java层的空间。

            new Task(bmp,position).execute(imageUrl);

        }
        return bmp;
    }

    public class Task extends AsyncTask<String,Bitmap,Bitmap>{

        Bitmap bit = null;
        int position = 0;
        Handler handler = null;

        public Task(Bitmap bitmap,int position) {
            bit = bitmap;
            this.position = position;
            this.handler = handler;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
//            Bitmap bitmap = ImageLoader.getInstance().loadImageSync(params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            bit = bitmap;
            BitmapCache.this.addCacheBitmap(bit,position);
//            handler.sendMessage(handler.obtainMessage(position,bit));
        }
    }


    /**
     * 清除Cache内的全部内容
     */
    public void clearCache() {
        cleanCache();
        hashRefs.clear();
        System.gc();
        System.runFinalization();
    }

    public Bitmap getBitmapFromFile(File dst, int width, int height) {
        if (null != dst && dst.exists()) {
            BitmapFactory.Options opts = null;
            if (width > 0 && height > 0) {
                opts = new BitmapFactory.Options();            //设置inJustDecodeBounds为true后，decodeFile并不分配空间，此时计算原始图片的长度和宽度
                        opts.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(dst.getPath(), opts);
                // 计算图片缩放比例
                final int minSideLength = Math.min(width, height);
                opts.inSampleSize = computeSampleSize(opts, minSideLength,
                        width * height);           //这里一定要将其设置回false，因为之前我们将其设置成了true
                        opts.inJustDecodeBounds = false;
                opts.inInputShareable = true;
                opts.inPurgeable = true;
            }
            try {
                return BitmapFactory.decodeFile(dst.getPath(), opts);
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static int computeSampleSize(BitmapFactory.Options options,
                                        int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength,
                maxNumOfPixels);

        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }

        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options,
                                                int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;

        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
                .sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math
                .floor(w / minSideLength), Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }


    public static Bitmap revitionImageSize(String path) throws IOException {
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(
                new File(path)));
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(in, null, options);
        in.close();
        int i = 0;
        Bitmap bitmap = null;
        while (true) {
            if ((options.outWidth >> i <= 1000)
                    && (options.outHeight >> i <= 1000)) {
                in = new BufferedInputStream(
                        new FileInputStream(new File(path)));
                options.inSampleSize = (int) Math.pow(2.0D, i);
                options.inJustDecodeBounds = false;
                bitmap = BitmapFactory.decodeStream(in, null, options);
                break;
            }
            i += 1;
        }
        return bitmap;
    }
}