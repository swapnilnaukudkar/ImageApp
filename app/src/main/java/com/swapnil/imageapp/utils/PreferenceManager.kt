package com.swapnil.imageapp.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import kotlin.collections.ArrayList

/**
 * [PreferenceManager]: SharedPreferences manager class for all SharedPreferences related operations
 */
object PreferenceManager  {
    private var sharedPreferences: SharedPreferences? = null
    private var FAVORITE_IMAGE_DATA = "favorite_image_data";

    fun getInstance(context: Context): PreferenceManager {
        if (sharedPreferences == null) {
            sharedPreferences =
            context.getSharedPreferences(context.packageName, Activity.MODE_PRIVATE)}
        return this
    }

    private fun getFavoriteImageList(): ArrayList<String> {
        val gson = Gson()
        val jsonData = getString(FAVORITE_IMAGE_DATA, "");
        val type: Type = object : TypeToken<ArrayList<String?>?>() {}.type
        return if(jsonData == ""){
            ArrayList()
        } else {
            gson.fromJson(jsonData, type)
        }
    }

    fun isFavorite(date: String): Boolean {
        return getFavoriteImageList().contains(date)
    }

    fun setAsFavorite(date: String) {
        val favoriteList = getFavoriteImageList()
        favoriteList.add(date)
        val gson = Gson()
        val jsonString = gson.toJson(favoriteList)
       putString(FAVORITE_IMAGE_DATA, jsonString)
    }

    fun removeFromFavorite(date: String) {
        val favoriteList = getFavoriteImageList()
        favoriteList.remove(date)
        val gson = Gson()
        val jsonString = gson.toJson(favoriteList)
        putString(FAVORITE_IMAGE_DATA, jsonString)
    }

    fun getString(key: String?, defValue: String?): String? {
        return sharedPreferences?.getString(key, defValue)
    }

    fun putString(key: String?, value: String?) {
        val prefsEditor = sharedPreferences?.edit()
        prefsEditor?.putString(key, value)
        prefsEditor?.apply()
    }

    fun getInteger(key: String?, defValue: Int): Int {
        return sharedPreferences!!.getInt(key, defValue)
    }

    fun putInteger(key: String?, value: Int?) {
        val prefsEditor = sharedPreferences!!.edit()
        prefsEditor.putInt(key, value!!)
        prefsEditor.apply()
    }

    fun getBoolean(key: String?, defValue: Boolean): Boolean {
        return sharedPreferences!!.getBoolean(key, defValue)
    }

    fun putBoolean(key: String?, value: Boolean) {
        val prefsEditor = sharedPreferences!!.edit()
        prefsEditor.putBoolean(key, value)
        prefsEditor.apply()
    }

    fun getLong(key: String?, defValue: Long): Long {
        return sharedPreferences!!.getLong(key, defValue)
    }

    fun putLong(key: String?, value: Long) {
        val prefsEditor = sharedPreferences!!.edit()
        prefsEditor.putLong(key, value)
        prefsEditor.apply()
    }

    fun getFloat(key: String?, defValue: Float): Float {
        return sharedPreferences!!.getFloat(key, defValue)
    }

    fun putFloat(key: String?, value: Float) {
        val prefsEditor = sharedPreferences!!.edit()
        prefsEditor.putFloat(key, value)
        prefsEditor.apply()
    }

    //// Clear Preference ////
    fun clearPreference(context: Context?) {
        sharedPreferences!!.edit().clear().apply()
    }

    //// Remove ////
    fun removePreference(Key: String?) {
        sharedPreferences!!.edit().remove(Key).apply()
    }
}