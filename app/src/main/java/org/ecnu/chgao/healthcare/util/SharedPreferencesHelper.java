package org.ecnu.chgao.healthcare.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by chgao on 17-6-1.
 */

public class SharedPreferencesHelper {
    private static final Map<String, SharedPreferences> collections = new HashMap<>();

    public static SharedPreferences getSharedPreferences(Context context, String name) {
        if (collections.containsKey(name)) {
            return collections.get(name);
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(name, MODE_PRIVATE);
        collections.put(name, sharedPreferences);
        return sharedPreferences;
    }
}
