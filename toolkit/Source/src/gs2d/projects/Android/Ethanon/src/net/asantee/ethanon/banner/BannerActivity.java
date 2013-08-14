package net.asantee.ethanon.banner;

import net.asantee.ethanon.R;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class BannerActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		handleIntent(getIntent());
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		handleIntent(intent);
	}

	public void handleIntent(Intent intent) {
		setContentView(R.layout.banner);
		ImageView bannerImg = (ImageView) findViewById(R.id.banner);
		findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				finish();
			}
		});
		try {
			final Banner banner = new Banner(intent.getStringExtra("banner"));
			bannerImg.setOnClickListener(new View.OnClickListener() {

				public void onClick(View view) {
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri
							.parse(banner.link));
					startActivity(intent);
					finish();
				}
			});
			bannerImg.setImageDrawable(getResources().getBoolean(
					R.bool.isTablet) ? banner.getImgTabletDrawable() : banner
					.getImgPhoneDrawable());
			banner.setViewed();
		} catch (Exception e) {
			finish();
		}
	}

}
