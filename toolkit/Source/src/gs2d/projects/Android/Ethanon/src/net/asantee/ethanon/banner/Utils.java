package net.asantee.ethanon.banner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;

import net.asantee.ethanon.EthanonApplication;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Utils {

	public static boolean isOnline(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}

	public static boolean isOnline() {
		return isOnline(EthanonApplication.getContext());
	}

	public static String executeGetRequest(String url) throws Exception {
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet http = new HttpGet(url);
		HttpResponse response = httpclient.execute(http);

		if (response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK)
			return EntityUtils.toString(response.getEntity());
		return null;
	}

	public static Bitmap downloadImage(String url) throws Exception {
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet http = new HttpGet(url);
		HttpResponse response = httpclient.execute(http);

		if (response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK) {
			return BitmapFactory.decodeStream(
					response.getEntity().getContent(), null, null);

		}
		return null;
	}

	public static void saveImage(String filename, Bitmap image)
			throws Exception {
		FileOutputStream outStream = EthanonApplication.getContext()
				.openFileOutput(filename, Context.MODE_PRIVATE);
		image.compress(Bitmap.CompressFormat.JPEG, 50, outStream);
		outStream.flush();
		outStream.close();
	}

	public static Drawable getImage(String filename) {
		try {
			FileInputStream fis = EthanonApplication.getContext()
					.openFileInput(filename);
			return Drawable.createFromStream(fis, filename);
		} catch (FileNotFoundException e) {
			return null;
		}
	}

	public static SharedPreferences getPreferences(Context context) {
		return context.getSharedPreferences(context.getPackageName(),
				Context.MODE_PRIVATE);
	}

	public static void saveString(String key, String value) {
		getPreferences(EthanonApplication.getContext()).edit().putString(key, value).commit();
	}

	public static String getString(String key,String defaultValue) {
		return getPreferences(EthanonApplication.getContext()).getString(key, defaultValue);
	}
}
