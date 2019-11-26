/*
 * Copyright (C) 2018 Havoc-OS
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.havoc.config.center.fragments;

import android.content.ContentResolver;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.Settings;
import androidx.preference.*;

import com.android.internal.logging.nano.MetricsProto;
import com.android.internal.util.havoc.Utils;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

public class Screen extends SettingsPreferenceFragment implements
    Preference.OnPreferenceChangeListener {

    private static final String KEY_FORCE_FULLSCREEN = "display_cutout_force_fullscreen_settings";

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        addPreferencesFromResource(R.xml.config_center_screen);
        ContentResolver resolver = getActivity().getContentResolver();

        final Preference forceFullscreen = (Preference) getPreferenceScreen().findPreference(KEY_FORCE_FULLSCREEN);
        if (!Utils.hasNotch(getContext())) {
            getPreferenceScreen().removePreference(forceFullscreen);
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        return false;
    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.HAVOC_SETTINGS;
    }
}
