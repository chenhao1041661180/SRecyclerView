package com.hzw.srecyclerview;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

/**
 * 功能：获取用户的全局SRV配置
 * Created by 何志伟 on 2017/7/17.
 */
class SRVConfig {

    private static final String SRV_CONFIG_VALUE = "SRecyclerViewModule";
    private volatile static SRVConfig instance;
    private static SRecyclerViewModule module;

    private SRVConfig() {
    }

    static SRVConfig getInstance(Context context) {
        if (instance == null) {
            synchronized (SRVConfig.class) {
                if (instance == null) {
                    instance = new SRVConfig();
                    initConfig(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    private static void initConfig(Context context) {
        String moduleName = null;
        try {
            ApplicationInfo appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo.metaData != null) {
                for (String key : appInfo.metaData.keySet()) {
                    if (SRV_CONFIG_VALUE.equals(appInfo.metaData.get(key))) {
                        moduleName = key;
                        break;
                    }
                }
            }
        } catch (Exception ignored) {
        }
        Class moduleClass;
        if (moduleName != null) {
            try {
                moduleClass = Class.forName(moduleName);
                Object cls = moduleClass.newInstance();
                if (cls instanceof SRecyclerViewModule) {
                    module = (SRecyclerViewModule) cls;
                }
            } catch (Exception ignored) {
            }
        }
    }

    SRecyclerViewModule getConfig() {
        return module;
    }

}
