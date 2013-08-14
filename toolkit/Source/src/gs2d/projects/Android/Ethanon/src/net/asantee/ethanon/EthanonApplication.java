package net.asantee.ethanon;

import net.asantee.ethanon.banner.Banner;
import android.app.Application;
import android.content.Context;

public class EthanonApplication extends Application {
	private static Context context;

	public static Context getContext() {
		return context;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		context = this;
		Banner.requestBannerAsync();
	}
}
