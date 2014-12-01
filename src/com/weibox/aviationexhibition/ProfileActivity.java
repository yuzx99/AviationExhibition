package com.weibox.aviationexhibition;

import java.io.FileNotFoundException;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class ProfileActivity extends Activity {
	private ImageButton ibtnUploadPhoto;
	private ImageView ivPhoto;
	private EditText etRealName;
	private EditText etUserName;
	private Button btnCancel;
	private Button btnOK;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.profile);
		initUI();
	}

	private void initUI() {
		ibtnUploadPhoto = (ImageButton) findViewById(R.id.ibtnUploadPhoto);
		ibtnUploadPhoto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				// ����Pictures����Type�趨Ϊimage
				intent.setType("image/*");
				// ʹ��Intent.ACTION_GET_CONTENT���Action
				intent.setAction(Intent.ACTION_GET_CONTENT);
				// ȡ����Ƭ�󷵻ر�����
				startActivityForResult(intent, 1);
			}
		});
		ivPhoto = (ImageView) findViewById(R.id.ivPhoto);
		etRealName = (EditText) findViewById(R.id.etRealName);
		etUserName = (EditText) findViewById(R.id.etUserName);
		btnCancel = (Button) findViewById(R.id.btnProfCancel);
		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

			}
		});
		btnOK = (Button) findViewById(R.id.btnProfOK);
		btnOK.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ProfileActivity.this,
						HomepageActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			Uri uri = data.getData();
			Log.e("uri", uri.toString());
			ContentResolver cr = this.getContentResolver();
			try {
				Bitmap bitmap = BitmapFactory.decodeStream(cr
						.openInputStream(uri));
				// ��Bitmap�趨��ImageView
				ivPhoto.setImageBitmap(bitmap);
			} catch (FileNotFoundException e) {
				Log.e("Exception", e.getMessage(), e);
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private static Bitmap getImageThumbnail(String imagePath, int width,
			int height) {
		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Bitmap.Config.ARGB_4444;
		options.inPurgeable = true;
		options.inInputShareable = true;
		options.inJustDecodeBounds = true;
		// ��ȡ���ͼƬ�Ŀ�͸ߣ�ע��˴���bitmapΪnull
		bitmap = BitmapFactory.decodeFile(imagePath, options);
		options.inJustDecodeBounds = false; // ��Ϊ false
		// �������ű�
		int h = options.outHeight;
		int w = options.outWidth;
		int beWidth = w / width;
		int beHeight = h / height;
		int be = 1;
		if (beWidth < beHeight) {
			be = beWidth;
		} else {
			be = beHeight;
		}
		if (be <= 0) {
			be = 1;
		}
		options.inSampleSize = be;
		// ���¶���ͼƬ����ȡ���ź��bitmap��ע�����Ҫ��options.inJustDecodeBounds ��Ϊ false
		bitmap = BitmapFactory.decodeFile(imagePath, options);
		// ����ThumbnailUtils����������ͼ������Ҫָ��Ҫ�����ĸ�Bitmap����
		bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
				ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		return bitmap;
	}
}
