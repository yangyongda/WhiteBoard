package com.fjsd.yyd.whiteboard.util;

import android.os.Environment;

import java.io.File;

/**
 * SD卡状态
 */
public class SdCardStatus {
    private static String CACHE_FOLDER_NAME;
    private static String NONE_SD_CARD_PROMPT = "您的手机中sd卡不存在";

    public static void init(String cacheFolderName) {
        CACHE_FOLDER_NAME = cacheFolderName;
    }
    public static String getDefaulstCacheDirInSdCard() throws IllegalStateException {
        String sdCardPath = null;
        sdCardPath = getSDPath();
        if (null == sdCardPath) {
            throw new IllegalStateException(NONE_SD_CARD_PROMPT);
        }
        return sdCardPath + File.separator + CACHE_FOLDER_NAME;
    }

    /**
     * when not exist sd card,return null.
     *获取SD卡路径
     * @return
     */
    public static String getSDPath() {
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
                return null;
        }
    }
}
