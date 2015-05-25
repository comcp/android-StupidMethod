package com.stupid.method.media;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

public class Photos {
	private String[] proj = { MediaStore.Images.Media.DATA };
	public final int TAKE_PICTURE = 0xFFFC;
	public final int CHOOSE_PICTURE = 0xFFA;
	public final int CUT_PICTURE = 0xFFAD;
	boolean cut = false;
	int callbackID = 0;
	private Activity activity;
	PictureInterface pmInterface;
	private String imageSavePath;

	public Photos(Activity activity, PictureInterface pmInterface) {
		this.activity = activity;
		this.pmInterface = pmInterface;
	}

	/**
	 * 启动照相机
	 * 
	 * @param imageSavePath
	 ***/
	public void openCamera(int callbackID, boolean isCut, String imageSavePath) {
		this.callbackID = callbackID;
		this.cut = isCut;
		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		if (imageSavePath != null) {
			Uri imageUri = Uri.fromFile(new File(imageSavePath));
			// 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
			openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		}

		activity.startActivityForResult(openCameraIntent, TAKE_PICTURE);

	}

	/** 选择照片 ***/
	public void selectPicure(int callbackID, boolean CUT) {
		this.callbackID = callbackID;
		this.cut = CUT;
		Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
		openAlbumIntent.setDataAndType(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
		activity.startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);

	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {

			case CHOOSE_PICTURE:
				// ContentResolver resolver = mContext.getContentResolver();
				Uri originalUri = data.getData();

				Cursor cursor = activity.managedQuery(originalUri, proj, null,
						null, null);
				int column_index = cursor
						.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
				cursor.moveToFirst();
				// 最后根据索引值获取图片路径
				imageSavePath = cursor.getString(column_index);

			case TAKE_PICTURE:
				// share_photo_show.

				if (cut) {
					startCutimg(imageSavePath);
				} else {

					pmInterface.onChooseComplete(callbackID, imageSavePath);
				}

				break;
			case CUT_PICTURE:
				// imageSavePath = data
				// .getStringExtra(ResultScreenshotActivity.FILE_BACK);
				// pmInterface.onChooseComplete(callbackID, imageSavePath);
				break;

			}
		}
	}

	public void startCutimg(String imagePath) {

		// Intent intent = new Intent(this.activity,
		// ResultScreenshotActivity.class);
		// intent.putExtra(ResultScreenshotActivity.FILE_SOURCE, imagePath);
		// intent.putExtra(ResultScreenshotActivity.FIXED_ASPECT_RATIO,
		// fixAspectRatio);
		//
		// if (catSavePath == null)
		// intent.putExtra(ResultScreenshotActivity.FILE_SAVE_PATH, imagePath);
		// else {
		// intent.putExtra(ResultScreenshotActivity.FILE_SAVE_PATH,
		// catSavePath);
		//
		// }
		// activity.startActivityForResult(intent, CUT_PICTURE);// 启动照片剪切

	}

	public interface PictureInterface {
		/***
		 * 选择完照片后回调,如果使用了切图的话,<br>
		 * 会先打开切图画面之后再回调,<br>
		 * 如果用户在切图画面选择了取消,则不回调
		 **/
		public void onChooseComplete(int id, String imagePath);
	}
}
