package com.bretibad.tournoibretibad.utils;

import com.bretibad.tournoibretibad.service.RESTService;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

public abstract class RestResultReceiver extends ResultReceiver {

	public RestResultReceiver() {
		super(new Handler());
	}

	public RestResultReceiver(Handler handler) {
		super(handler);
	}

	@Override
	protected void onReceiveResult(int resultCode, Bundle resultData) {
		if (resultData != null && resultData.containsKey(RESTService.REST_RESULT)) {
			onRESTResult(resultCode, resultData.getString(RESTService.REST_RESULT));
		} else {
			onRESTResult(resultCode, null);
		}
	}

	public abstract void onRESTResult(int resultCode, String result);

}
