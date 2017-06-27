package com.example.imm.citi.technicalClasses;

import android.graphics.Bitmap;

import com.example.imm.citi.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

/**
 * Created by imm on 6/23/2017.
 */

    public class MyDisplayImageOptions {
        private DisplayImageOptions displayImageOptions;
        private static MyDisplayImageOptions mInstance;

        private MyDisplayImageOptions() {
            displayImageOptions = getDisplayImageOptions();
        }

        public static synchronized MyDisplayImageOptions getInstance() {
            if(mInstance == null) {
                mInstance = new MyDisplayImageOptions();
            }
            return mInstance;
        }

        public DisplayImageOptions getDisplayImageOptions() {
            if(displayImageOptions == null) {
                displayImageOptions = new DisplayImageOptions.Builder()
                        .showImageForEmptyUri(R.drawable.placeholder)
                        .showImageOnFail(R.drawable.placeholder)
                        .showImageOnLoading(R.drawable.placeholder)
                        .cacheInMemory(true)
                        .cacheOnDisk(true)
                        .imageScaleType(ImageScaleType.EXACTLY)
                        .considerExifParams(true)
                        .bitmapConfig(Bitmap.Config.RGB_565)
                        .resetViewBeforeLoading(true)
                        .build();
            }
            return displayImageOptions;
        }
    }
