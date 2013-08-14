package net.asantee.ethanon.banner;

import net.asantee.ethanon.EthanonApplication;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class Banner {
	private static final String BANNER_URL = "http://pastebin.com/raw.php?i=EEquHjsj";
	private static final String TAG = "ETHANON - BANNER";
	private static final boolean DEBUG = true;
	
	String id;
	String imgTabletUrl;
	String imgPhoneUrl;
	String link;

	public String getImgTabletFilename() {
		return id + "_tablet";
	}

	public String getImgPhoneFilename() {
		return id + "_phone";
	}

	public Drawable getImgTabletDrawable() {
		return Utils.getImage(getImgTabletFilename());
	}

	public Drawable getImgPhoneDrawable() {
		return Utils.getImage(getImgPhoneFilename());
	}

	public boolean wasViewed() {
		return viewedBanners().contains("#"+ id + "#");
	}

	public void setViewed() {
		Utils.saveString("viewed_banners", viewedBanners() +"#"+ id + "#");
	}

	public void show() {
		Intent intent = new Intent(EthanonApplication.getContext(),
				BannerActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra("banner", toString());
		EthanonApplication.getContext().startActivity(intent);
	}

	@Override
	public String toString() {
		return String
				.format("{\"id\":\"%s\",\"image-tablet\":\"%s\",\"image-celphone\":\"%s\",\"link\":\"%s\"}",
						id, imgTabletUrl, imgPhoneUrl, link);
	}

	public Banner(String banner) throws Exception {
		JSONObject jsonObject = new JSONObject(banner);
		id = jsonObject.getString("id");
		imgTabletUrl = jsonObject.getString("image-tablet");
		imgPhoneUrl = jsonObject.getString("image-celphone");
		link = jsonObject.getString("link");
	}

	public static boolean hasBannerAvailable() {
		Banner lastBanner = getLastBanner();
		return lastBanner != null && !lastBanner.wasViewed();
	}

	public static void requestBannerAsync() {
		new Thread() {
			public void run() {
				requestBanner();
			};
		}.start();
	}

	public static void requestBanner() {
		if (Utils.isOnline()) {
			try {
				String response = Utils.executeGetRequest(BANNER_URL);
				Banner banner = new Banner(response);
				if (banner != null) {
					Utils.saveImage(banner.getImgTabletFilename(),
							Utils.downloadImage(banner.imgTabletUrl));
					Utils.saveImage(banner.getImgPhoneFilename(),
							Utils.downloadImage(banner.imgPhoneUrl));
				}
				Utils.saveString("last_banner", banner.toString());
			} catch (Exception e) {
				if (DEBUG)
					Log.e(TAG, "Error retrieving banner", e);
			}
		}
	}

	public static Banner getLastBanner() {
		try {
			return new Banner(Utils.getString("last_banner", null));
		} catch (Exception e) {
			if (DEBUG && e instanceof JSONException)
				Log.e(TAG, "Error last banner", e);
		}
		return null;
	}

	private static String viewedBanners() {
		return Utils.getString("viewed_banners", "");
	}

}
