/*
 * Copyright (C) 2009 Teleca Poland Sp. z o.o. <android@teleca.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.guosun.lovego;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

import com.guosun.lovego.entity.LovegoInfo;


public class LovegoApplication extends Application {

    private final String TAG = "LovegoApplication";
    private final String PREFERENCES_NAME = "love_go_preferences";
    private static LovegoApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static LovegoApplication getInstance(){
        return instance;
    }

    /**
     * app PackageInfo
     *
     * @return
     */
    public LovegoInfo getLovegoInfo() {
        LovegoInfo lovegoInfo = new LovegoInfo();
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(getPackageName(), 0);
            lovegoInfo.versionCode = packageInfo.versionCode;
            lovegoInfo.versionName = packageInfo.versionName;
            lovegoInfo.appName =
                    (String) packageManager.getApplicationLabel(applicationInfo);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return lovegoInfo;
    }


    public String getSharedPreferences(String key) {
        try {
            SharedPreferences sp = this.getSharedPreferences(PREFERENCES_NAME, 0);
            return sp.getString(key, "");
        } catch (Exception e) {
            Log.e(this.toString(), e.toString());
        }
        return "";
    }

    public void removeSharedPreferences(String key) {
        try {
            SharedPreferences sp = this.getSharedPreferences(PREFERENCES_NAME, 0);
            SharedPreferences.Editor editor = sp.edit();
            editor.remove(key);
            editor.commit();
        } catch (Exception e) {
            Log.e(this.toString(), e.toString());
        }
    }

    public void setSharedPreferences(String key, String value) {
        try {
            SharedPreferences sp = this.getSharedPreferences(PREFERENCES_NAME, 0);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(key, value);
            editor.commit();
        } catch (Exception e) {
            Log.e(this.toString(), e.toString());
        }
    }
}
