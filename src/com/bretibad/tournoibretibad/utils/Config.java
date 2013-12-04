package com.bretibad.tournoibretibad.utils;

import java.util.Properties;

import android.content.Context;

public class Config {

	public static Config instance = null;

	AssetsPropertyReader assetsPropertyReader;
	private Properties config;

	public Config(Context context) {
		assetsPropertyReader = new AssetsPropertyReader(context);
		config = assetsPropertyReader.getProperties("config.properties");
	}

	public String getProperty(String key) {
		return config.getProperty(key);
	}

	public static Config getInstance(Context context) {
		if (instance == null) {
			instance = new Config(context);
		}
		return instance;
	}

}
