/*
 * Copyright (C) 2020 Havoc-OS
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

package com.havoc.config.center.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.Settings;

import androidx.preference.Preference;

import com.android.internal.logging.nano.MetricsProto;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

import com.havoc.support.preferences.SystemSettingMasterSwitchPreference;

public class Battery extends SettingsPreferenceFragment implements 
        Preference.OnPreferenceChangeListener {

    public static final String TAG = "Battery";

    private static final String SMART_PIXELS_ENABLED = "smart_pixels_enable";
    private static final String SENSOR_BLOCK = "sensor_block";

    private SystemSettingMasterSwitchPreference mSmartPixelsEnabled;
    private SystemSettingMasterSwitchPreference mSensorBlockEnabled;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.config_center_battery);

        updateMasterPrefs();
    }

    private void updateMasterPrefs() {
        mSmartPixelsEnabled = (SystemSettingMasterSwitchPreference) findPreference(SMART_PIXELS_ENABLED);
        mSmartPixelsEnabled.setOnPreferenceChangeListener(this);
        int smartPixelsEnabled = Settings.System.getInt(getContentResolver(),
                SMART_PIXELS_ENABLED, 0);
        mSmartPixelsEnabled.setChecked(smartPixelsEnabled != 0);

        if (!getResources().getBoolean(com.android.internal.R.bool.config_enableSmartPixels)) {
            mSmartPixelsEnabled.setVisible(false);
        }

        mSensorBlockEnabled = (SystemSettingMasterSwitchPreference) findPreference(SENSOR_BLOCK);
        int sensorBlockEnabled = Settings.System.getIntForUser(getContentResolver(),
                Settings.System.SENSOR_BLOCK, 0, UserHandle.USER_CURRENT);
        mSensorBlockEnabled.setChecked(sensorBlockEnabled != 0);
        mSensorBlockEnabled.setOnPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object objValue) {
        if (preference == mSmartPixelsEnabled) {
            boolean value = (Boolean) objValue;
            Settings.System.putInt(getContentResolver(),
		            SMART_PIXELS_ENABLED, value ? 1 : 0);
            return true;
        } else if (preference == mSensorBlockEnabled) {
            boolean value = (Boolean) objValue;
            Settings.System.putIntForUser(getContentResolver(),
		            SENSOR_BLOCK, value ? 1 : 0, UserHandle.USER_CURRENT);
            return true;
        }
        return false; 
    }

    @Override
    public void onResume() {
        super.onResume();
        updateMasterPrefs();
    }

    @Override
    public void onPause() {
        super.onPause();
        updateMasterPrefs();
    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.HAVOC_SETTINGS;
    }
}
