package val.com.velparked.utils;

/**
 * Created by Saunik on 4/24/2015.
 *
 */

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;

import val.com.velparked.R;

public class ComplexPreferences {
    private static ComplexPreferences complexPreferences;
    private final SharedPreferences preferences;
//    private final SharedPreferences.Editor editor;
    private static final Gson GSON = new Gson();

    private ComplexPreferences(Context context, String namePreferences) {
        if (namePreferences == null || namePreferences.equals("")) {
            namePreferences = context.getString(R.string.app_name);
        }
        preferences = context.getSharedPreferences(namePreferences, Context.MODE_PRIVATE);
    }

    public static ComplexPreferences getComplexPreferences(Context context,
                                                           String namePreferences) {
        if (complexPreferences == null) {
            complexPreferences = new ComplexPreferences(context,
                    namePreferences);
        }
        return complexPreferences;
    }

    public void putObject(String key, Object object) {
        if (object == null) {
            throw new IllegalArgumentException("Object is null");
        }
        if (key == null || key.equals("")) {
            throw new IllegalArgumentException("Key is empty or null");
        }
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, GSON.toJson(object));
        editor.apply();
    }

    void clearAll() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }

    public void removeObject(String key) {
        if (key == null || key.equals("")) {
            throw new IllegalArgumentException("Key is empty or null");
        }
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, null);
        editor.apply();
    }

    public void commit() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.apply();
    }

    public <T> T getObject(String key, Class<T> a) {
        String gson = preferences.getString(key, null);
        if (gson == null) {
            return null;
        } else {
            try {
                return GSON.fromJson(gson, a);
            } catch (Exception e) {
                throw new IllegalArgumentException("Object stored with key "
                        + key + " is instance of other class");
            }
        }
    }
}