package com.bretibad.tournoibretibad.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import com.bretibad.tournoibretibad.model.Joueur;
import com.bretibad.tournoibretibad.utils.AssetsPropertyReader;
import com.bretibad.tournoibretibad.utils.Config;

public class RencontreService {

	public static RencontreService instance = null;
	private String baseUrl = "";

	DefaultHttpClient client = new DefaultHttpClient();
	private Context context;
	private Config config;

	public RencontreService(Context context) {
		config = Config.getInstance(context);
		baseUrl = config.getProperty("baseUrl");
		this.context = context;
	}

	public Intent getRencontreIntent(ResultReceiver receiver) {
		Intent intent = new Intent(context, RESTService.class);
		intent.setData(Uri.parse(baseUrl + config.getProperty("rencontre")));

		intent.putExtra(RESTService.EXTRA_RESULT_RECEIVER, receiver);
		return intent;
	}

	public static RencontreService getInstance(Context context) {
		if (instance == null) {
			instance = new RencontreService(context);
		}
		return instance;
	}
}
