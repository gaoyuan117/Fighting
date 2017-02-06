package com.xiangying.fighting.widget.pulltorefreshview;

/**
 * Created by xiaoniao on 2016/8/24.
 */

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * 分配率通配类
 *
 * @author zw 2012-5-3
 *
 */
public class MyLayoutAdapter
{
    private static final String TAG = "MyLayoutAdapter";

    /**
     * 基准分辨率的宽
     */
    public double STANDARD_SCREEN_WIDTH;

    /**
     * 基准分辨率的高
     */
    public double STANDARD_SCREEN_HEIGHT;

    /**
     * 系统当前的分辨率的宽
     */
    public double CURRENT_SCREEN_WIDTH;

    /**
     * 系统当前的分辨率的高
     */
    public double CURRENT_SCREEN_HEIGHT;

    /**
     * 基准屏幕密度
     */
    public static final double STANDARD_DENSITY = 160;

    /**
     * 屏幕宽度比例
     */
    public double WIDTH_RATIO;

    /**
     * 屏幕高度比例
     */
    public double HEIGHT_RATIO;

    public double RATIO = 1;

    /**
     * 组件基准的宽度
     */
    private double viewStandardWidth;

    /**
     * 组件基准的高度
     */
    private double viewStandardHeight;

    /**
     * 组件基准的距离左边的距离
     */
    private double viewStandardMarginLeft;

    /**
     * 组件基准的距离顶部的距离
     */
    private double viewStandardMarginTop;

    /**
     * 组件基准的距离右边的距离
     */
    private double viewStandardMarginRight;

    /**
     * 组件基准的距离底部的距离
     */
    private double viewStandardMarginBottom;

    /**
     * 组件当前的宽
     */
    private double viewCurrentWidth;

    /**
     * 组件当前的高
     */
    private double viewCurrentHeight;

    /**
     * 组件当前距离左边的距离
     */
    private double viewCurrentMarginLeft = 0;

    /**
     * 组件当前距离顶部的距离
     */
    private double viewCurrentMarginTop = 0;

    /**
     * 组件当前距离右边的距离
     */
    private double viewCurrentMarginRight = 0;

    /**
     * 组件当前距离底部的距离
     */
    private double viewCurrentMarginBottom = 0;

    /**
     * UI组件的对象
     */
    private View view;

    /**
     * 此View的父类布局的类型
     */
    private int parentLayoutType;

    /**
     * 父类布局的类型为相对布局
     */
    private final int LAYOUT_TYPE_RELATiVELAYOUT = LayoutInformation.R;

    /**
     * 父类布局的类型为线性布局
     */
    private final int LAYOUT_TYPE_LINEARLAYOUT = LayoutInformation.L;

    /**
     * 布局属性为wrap_content
     */
    public static final int LAYOUTPARAMS_WARP_CONTENT = LayoutParams.WRAP_CONTENT;

    /**
     * 布局属性为fill_parent
     */
    public static final int LAYOUTPARAMS_FILL_PARENT = LayoutParams.MATCH_PARENT;

    private static Context mContext;

    private static MyLayoutAdapter mMyLayoutAdapter;

    /**
     * 状态栏高度
     */
    private static int statusBarHeight;

    /**
     * 类对象实例化时,设置 基准屏幕宽度,高度
     *
     * @param context
     *            Context

     *            基准屏幕的高
     */
    public MyLayoutAdapter(Context context)
    {
        mMyLayoutAdapter = this;
        MyLayoutAdapter.mContext = context;
        getScreenSize();
        STANDARD_SCREEN_HEIGHT = 480;
        STANDARD_SCREEN_WIDTH = 320;
        // 计算宽高比率
        WIDTH_RATIO = CURRENT_SCREEN_WIDTH / STANDARD_SCREEN_WIDTH;
        HEIGHT_RATIO = CURRENT_SCREEN_HEIGHT / STANDARD_SCREEN_HEIGHT;
    }

    public static MyLayoutAdapter getInstance()
    {
        if (null == mMyLayoutAdapter)
        {
            return new MyLayoutAdapter(mContext);
        }
        return mMyLayoutAdapter;
    }

    public void setStandardParams(double standardWidth, double standardHeight)
    {}

    /**
     * 获取当前屏幕大小和密度
     */
    private void getScreenSize()
    {
        if (null == mContext)
        {
            return;
        }
        // 系统SDK版本号
        int ver = Build.VERSION.SDK_INT;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        Display display = ((Activity) mContext).getWindowManager()
                .getDefaultDisplay();
        display.getMetrics(displayMetrics);
        double w = displayMetrics.widthPixels;
        double h = displayMetrics.heightPixels;
        Log.d(TAG, "DisplayMetrics resolution:" + w + " * " + h + ", ver "
                + Build.VERSION.SDK_INT);
        if (w != 0 && h != 0)
        {
            setPortraitSize(w, h);
        }
        else
        {
            if (ver < 13)
            {
                Log.d(TAG, "ver < 13 Display resolution:" + w + " * " + h
                        + ", ver " + ver);
                setPortraitSize(800, 480);
            }
            else if (ver == 13)
            {
                try
                {
                    Method mt_w = display.getClass().getMethod("getRealWidth");
                    w = (Integer) mt_w.invoke(display);
                    Method mt_h = display.getClass().getMethod("getRealHeight");
                    h = (Integer) mt_h.invoke(display);
                    Log.d(TAG, "ver == 13 Method resolution:" + w + " * " + h
                            + ", ver " + ver);
                    if (w != 0 && h != 0)
                    {
                        setPortraitSize(w, h);
                    }
                    else
                    {
                        setPortraitSize(800, 480);
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            else if (ver > 13)
            {
                try
                {
                    Method mt_w = display.getClass().getMethod("getRawWidth");
                    w = (Integer) mt_w.invoke(display);
                    Method mt_h = display.getClass().getMethod("getRawHeight");
                    h = (Integer) mt_h.invoke(display);
                    Log.d(TAG, "ver > 13 Method resolution:" + w + " * " + h
                            + ", ver " + ver);
                    if (w != 0 && h != 0)
                    {
                        setPortraitSize(w, h);
                    }
                    else
                    {
                        setPortraitSize(800, 480);
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public int getNavigationBarHeight()
    {
        if (null == mContext)
        {
            return 0;
        }
        Resources resources = mContext.getResources();
        // 获取导航栏是否显示true or false
        int rid = resources.getIdentifier("config_showNavigationBar", "bool",
                "android");
        boolean isShow = resources.getBoolean(rid);
        Log.d(TAG, isShow + "");
        int resourceId = resources.getIdentifier("navigation_bar_height",
                "dimen", "android");
        int navgationBarHeight = 0;
        if (resourceId > 0)
        {
            navgationBarHeight = resources.getDimensionPixelSize(resourceId); // 获取高度
        }
        return navgationBarHeight;
    }

    public static boolean hasSmartBar()
    {
        try
        {
            // 新型号可用反射调用Build.hasSmartBar()
            Method method = Class.forName("android.os.Build").getMethod(
                    "hasSmartBar");
            return ((Boolean) method.invoke(null)).booleanValue();
        }
        catch (Exception e)
        {
        }
        // 反射不到Build.hasSmartBar()，则用Build.DEVICE判断
        if (Build.DEVICE.equals("mx2"))
        {
            return true;
        }
        else if (Build.DEVICE.equals("mx") || Build.DEVICE.equals("m9"))
        {
            return false;
        }
        return false;
    }

    /**
     * 为横屏设置分辨率
     *
     * @param w
     * @param h
     */
    public void setOrientationSize(double w, double h)
    {
        // 因为随开机启动安装程序时，有时会出现获取的宽高相反的问题，这里因为是默认横屏，所以
        if (w > h)
        {
            CURRENT_SCREEN_WIDTH = w;
            CURRENT_SCREEN_HEIGHT = h;
        }
        else
        {
            CURRENT_SCREEN_WIDTH = h;
            CURRENT_SCREEN_HEIGHT = w;
        }
    }

    public void setRatio(double ratio)
    {
        RATIO = ratio;
    }
    /**
     * 为竖屏设置分辨率
     *
     * @param w
     * @param h
     */
    public void setPortraitSize(double w, double h)
    {
        // 因为随开机启动安装程序时，有时会出现获取的宽高相反的问题，这里因为是默认横屏，所以
        if (w < h)
        {
            CURRENT_SCREEN_WIDTH = w;
            CURRENT_SCREEN_HEIGHT = h;
        }
        else
        {
            CURRENT_SCREEN_WIDTH = h;
            CURRENT_SCREEN_HEIGHT = w;
        }
    }

    /**
     * 获取屏幕高度比例
     *
     * @return double
     */
    public double getHeightRatio()
    {
        return HEIGHT_RATIO;
    }

    /**
     * 获取屏幕宽度比例
     *
     * @return double
     */
    public double getWidthRatio()
    {
        return WIDTH_RATIO;
    }

    /**
     * 获取屏幕密度比例
     *
     * @return double
     */
    public double getDensityRatio()
    {

        if (null == mContext)
        {
            return RATIO;
        }

        if (RATIO == 1)
        {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            Display display = ((Activity) mContext).getWindowManager()
                    .getDefaultDisplay();
            display.getMetrics(displayMetrics);
            int curDesity = displayMetrics.densityDpi;
            RATIO = curDesity / STANDARD_DENSITY;
        }

        return RATIO;
    }

    public double getScreenWidth()
    {
        if (CURRENT_SCREEN_WIDTH == 0)
        {
            getScreenSize();
        }
        return CURRENT_SCREEN_WIDTH;
    }

    public double getScreenHeight()
    {
        if (CURRENT_SCREEN_HEIGHT == 0)
        {
            getScreenSize();
        }
        return CURRENT_SCREEN_HEIGHT;
    }

    public int getStatusBarHeight()
    {
        if (null == mContext)
        {
            return 0;
        }
        int ver = Build.VERSION.SDK_INT;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        Display display = ((Activity) mContext).getWindowManager()
                .getDefaultDisplay();
        display.getMetrics(displayMetrics);
        double h = displayMetrics.heightPixels;
        Rect frame = new Rect();
        ((Activity) mContext).getWindow().getDecorView()
                .getWindowVisibleDisplayFrame(frame);
        statusBarHeight = frame.top;
        if (statusBarHeight == 0)
        {
            Method mt_h;
            try
            {
                if (ver == 13)
                {
                    mt_h = display.getClass().getMethod("getRealHeight");
                    int real_h = (Integer) mt_h.invoke(display);
                    if (h > 0 && real_h > 0)
                    {
                        statusBarHeight = Math.abs((int) (h - real_h));
                    }
                }
                if (statusBarHeight == 0)
                {
                    if (ver > 13)
                    {
                        Method r_h = display.getClass().getMethod(
                                "getRawHeight");
                        int raw_h = (Integer) r_h.invoke(display);
                        if (h > 0 && raw_h > 0)
                        {
                            statusBarHeight = Math.abs((int) (h - raw_h));
                        }
                    }
                }
            }
            catch (NoSuchMethodException e)
            {
                e.printStackTrace();
            }
            catch (IllegalArgumentException e)
            {
                e.printStackTrace();
            }
            catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }
            catch (InvocationTargetException e)
            {
                e.printStackTrace();
            }
            if (statusBarHeight == 0)
            {
                // 给力的获取状态栏高度的方法
                Class<?> c = null;
                Object obj = null;
                Field field = null;
                int x = 0, sbar = 0;
                try
                {
                    c = Class.forName("com.android.internal.R$dimen");
                    obj = c.newInstance();
                    field = c.getField("status_bar_height");
                    x = Integer.parseInt(field.get(obj).toString());
                    sbar = mContext.getResources().getDimensionPixelSize(x);
                    System.out.println("sbar=" + sbar);
                    statusBarHeight = Math.abs(sbar);
                }
                catch (Exception e1)
                {
                    System.out.println("get status bar height fail");
                    e1.printStackTrace();
                }
            }
        }
        return statusBarHeight;
    }

    /**
     * 进行通配
     *
     * @param listdata
     */
    public void setViewLayout(List<LayoutInformation> listdata)
    {
        for (int i = 0; i < listdata.size(); i++)
        {
            view = listdata.get(i).getView();
            viewStandardWidth = listdata.get(i).getViewWidth();
            viewStandardHeight = listdata.get(i).getViewHeight();
            viewStandardMarginLeft = listdata.get(i).getViewMarginLeft();
            viewStandardMarginTop = listdata.get(i).getViewMarginTop();
            viewStandardMarginRight = listdata.get(i).getViewMarginRight();
            viewStandardMarginBottom = listdata.get(i).getViewMarginBottom();
            setLayoutParams();
            viewCurrentMarginLeft = viewStandardMarginLeft * WIDTH_RATIO;
            viewCurrentMarginTop = viewStandardMarginTop * HEIGHT_RATIO;
            viewCurrentMarginRight = viewStandardMarginRight * WIDTH_RATIO;
            viewCurrentMarginBottom = viewStandardMarginBottom * WIDTH_RATIO;
            parentLayoutType = listdata.get(i).getParentLayoutType();
            setLayoutByParentLayoutType();
        }
    }

    /**
     * 判断布局属性的值，设置布局的属性
     */
    private void setLayoutParams()
    {
        // 如果基准的宽是wrap_content或者fill_parent则使用原值，否则进行计算得到通配后的值
        if (viewStandardWidth == LAYOUTPARAMS_WARP_CONTENT
                || viewStandardWidth == LAYOUTPARAMS_FILL_PARENT)
        {
            viewCurrentWidth = viewStandardWidth;
        }
        else
        {
            viewCurrentWidth = viewStandardWidth * WIDTH_RATIO;
        }
        // 如果基准的宽是wrap_content或者fill_parent则使用原值，否则进行计算得到通配后的值
        if (viewStandardHeight == LAYOUTPARAMS_WARP_CONTENT
                || viewStandardHeight == LAYOUTPARAMS_FILL_PARENT)
        {
            viewCurrentHeight = viewStandardHeight;
        }
        else
        {
            viewCurrentHeight = viewStandardHeight * HEIGHT_RATIO;
        }
    }

    /**
     * 通过判断此View父类的布局类型,给此View设置布局
     */
    private void setLayoutByParentLayoutType()
    {
        if (parentLayoutType == LAYOUT_TYPE_RELATiVELAYOUT)
        {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    (int) viewCurrentWidth, (int) viewCurrentHeight);
            params.setMargins((int) viewCurrentMarginLeft,
                    (int) viewCurrentMarginTop, (int) viewCurrentMarginRight,
                    (int) viewCurrentMarginBottom);
            view.setLayoutParams(params);
        }
        else if (parentLayoutType == LAYOUT_TYPE_LINEARLAYOUT)
        {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    (int) viewCurrentWidth, (int) viewCurrentHeight);
            params.setMargins((int) viewCurrentMarginLeft,
                    (int) viewCurrentMarginTop, (int) viewCurrentMarginRight,
                    (int) viewCurrentMarginBottom);
            view.setLayoutParams(params);
        }
    }

    /**
     * 设置字体大小
     *
     * @param standardSize
     *            原始大小
     * @return int
     */
    public int setTextSize(int standardSize)
    {
        int currentSize;
        currentSize = (int) (standardSize * WIDTH_RATIO * getDensityRatio());
        return currentSize;
    }

    /**
     * dipתpx
     *
     * @param context
     * @param dipValue
     * @return
     */
    public static int dip2px(Context context, float dipValue)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * pxתdip
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public class LayoutInformation
    {
        /**
         * View的对象
         */
        private View view;

        /**
         * View的宽度
         */
        private double viewWidth;

        /**
         * View的高度
         */
        private double viewHeight;

        /**
         * View距左边的距离，即marginLeft
         */
        private double viewMarginLeft;

        /**
         * View距顶部的距离,即MarginTop;
         */
        private double viewMarginTop;

        /**
         * View距右边的距离，即marginRight
         */
        private double viewMarginRight;

        /**
         * View距底部的距离,即MarginBottom;
         */
        private double viewMarginBottom;

        /**
         * 父类布局的类型为相对布局
         */
        public static final int R = -1;

        /**
         * 父类布局的类型为线性布局
         */
        public static final int L = -2;

        /**
         * 此View的父类布局的类型
         */
        private int parentLayoutType;

        /**
         * 存储View信息的JavaBean类
         *
         * @param view
         *            View的对象
         * @param viewWidth
         *            View的宽
         * @param viewHeight
         *            View的高
         * @param viewMarginLeft
         *            View距上部的距离
         * @param parentLayoutType
         *            父类布局的类型,LayoutInformation.R
         *            (表示相对布局)或者LayoutInformation.L(表示线性布局)
         */
        public LayoutInformation(View view, double viewWidth,
                                 double viewHeight, double viewMarginLeft, double viewMarginTop,
                                 int parentLayoutType)
        {
            this.view = view;
            this.viewWidth = viewWidth;
            this.viewHeight = viewHeight;
            this.viewMarginLeft = viewMarginLeft;
            this.viewMarginTop = viewMarginTop;
            this.parentLayoutType = parentLayoutType;
        }

        /**
         * 存储View信息的JavaBean类
         *
         * @param view
         *            View的对象
         * @param viewWidth
         *            View的宽
         * @param viewHeight
         *            View的高
         * @param viewMarginLeft
         *            View距左边的距离
         *            View距上部的距离
         * @param parentLayoutType
         *            父类布局的类型,LayoutInformation.R
         *            (表示相对布局)或者LayoutInformation.L(表示线性布局)
         */
        public LayoutInformation(View view, double viewWidth,
                                 double viewHeight, double viewMarginLeft, double viewMarginTop,
                                 double viewMarginRight, double viewMarginBottom,
                                 int parentLayoutType)
        {
            this.view = view;
            this.viewWidth = viewWidth;
            this.viewHeight = viewHeight;
            this.viewMarginLeft = viewMarginLeft;
            this.viewMarginTop = viewMarginTop;
            this.viewMarginRight = viewMarginRight;
            this.viewMarginBottom = viewMarginBottom;
            this.parentLayoutType = parentLayoutType;
        }

        /**
         * 获取View的对象
         *
         * @return View对象
         */
        public View getView()
        {
            return view;
        }

        /**
         * 设置View的对象
         */
        public void setView(View view)
        {
            this.view = view;
        }

        /**
         * 获取View的宽度
         *
         * @return View的宽度,double型
         */
        public double getViewWidth()
        {
            return viewWidth;
        }

        /**
         * 设置View的宽度，double型
         *
         * @param viewWidth
         */
        public void setViewWidth(double viewWidth)
        {
            this.viewWidth = viewWidth;
        }

        /**
         * 获取View的高度
         *
         * @return View的高度,double型
         */
        public double getViewHeight()
        {
            return viewHeight;
        }

        /**
         * 设置View的高度，double型
         *
         * @param viewHeight
         */
        public void setViewHeight(double viewHeight)
        {
            this.viewHeight = viewHeight;
        }

        /**
         * 获取View距离左边的距离
         *
         * @return View距离左边的距离，double型
         */
        public double getViewMarginLeft()
        {
            return viewMarginLeft;
        }

        /**
         * 设置View距离左边的距离,double型
         *
         * @param viewMarginLeft
         */
        public void setViewMarginLeft(double viewMarginLeft)
        {
            this.viewMarginLeft = viewMarginLeft;
        }

        /**
         * 获取View距离上部的距离
         *
         * @return View距离上部的距离，double型
         */
        public double getViewMarginTop()
        {
            return viewMarginTop;
        }

        /**
         * 设置View距离上部的距离，double型
         *

         */
        public void setViewMarginTop(double viewMarginTop)
        {
            this.viewMarginTop = viewMarginTop;
        }

        public double getViewMarginRight()
        {
            return viewMarginRight;
        }

        public void setViewMarginRight(double viewMarginRight)
        {
            this.viewMarginRight = viewMarginRight;
        }

        public double getViewMarginBottom()
        {
            return viewMarginBottom;
        }

        public void setViewMarginBottom(double viewMarginBottom)
        {
            this.viewMarginBottom = viewMarginBottom;
        }

        /**
         * 获取父类布局的类型
         *
         * @return parentLayoutType，int型
         */
        public int getParentLayoutType()
        {
            return parentLayoutType;
        }

        /**
         * 设置父类布局的类型
         *
         * @param parentLayoutType
         */
        public void setParentLayoutType(int parentLayoutType)
        {
            this.parentLayoutType = parentLayoutType;
        }
    }
}
