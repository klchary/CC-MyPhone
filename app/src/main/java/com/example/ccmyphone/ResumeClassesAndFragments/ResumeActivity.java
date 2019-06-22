package com.example.ccmyphone.ResumeClassesAndFragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.ccmyphone.ResumeClassesAndFragments.ResumeFormats.DefaultResumeFormat;
import com.example.ccmyphone.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;
import static com.example.ccmyphone.ApplicationConstants.CC_MyPhone_RESUME;

public class ResumeActivity extends AppCompatActivity {

    String TAG = "ResumeActivity";
    private int IMAGE_REQUEST_CODE = 100;
    private Uri fileUriImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume);
        createDirectory();
        LoadResumePreviewFrag();

    }

    private void LoadPersonDetailsFrag() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        PersonalProFragment personalProFragment = new PersonalProFragment();
        fragmentTransaction.add(R.id.resumeMainLayout, personalProFragment).commit();
    }

    private void LoadResumePreviewFrag() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ResumePreviewFragment resumePreviewFragment = new ResumePreviewFragment();
        fragmentTransaction.add(R.id.resumeMainLayout, resumePreviewFragment).commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        if (requestCode == IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    if (data.getClipData() != null) {
                        int count = data.getClipData().getItemCount();
                        Log.d(TAG, "clipData count " + count);
                        if (count <= 0) {
                            Log.d(TAG, "clipData count " + 0);
                        } else {
                            DefaultResumeFormat generateResume = new DefaultResumeFormat();
                            String imageUri = fileUriImage.toString();
//                            generateResume.DefaultResume(this, fileUriImage);
                        }
                    } else if (data.getData() != null) {
                        DefaultResumeFormat generateResume = new DefaultResumeFormat();
                        fileUriImage = data.getData();
//                        generateResume.DefaultResume(this, fileUriImage);
                    }
                } else {
//                    ivProjectImages.setImageURI(fileUriImages);
                }
                Log.d(TAG, "Image Capture Successfully");
            } else if (resultCode == RESULT_CANCELED) {
                Log.d(TAG, "Image Capture Cancelled");
            } else {
                Log.d(TAG, "Image Capture Failed");
            }
        }
    }

    private void ImageChooserDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Choose Action");
        dialog.setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture(s)"), IMAGE_REQUEST_CODE);
            }
        });
        dialog.setNegativeButton("Camera", new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                fileUriImage = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUriImage);
                startActivityForResult(intent, IMAGE_REQUEST_CODE);
            }
        });
        dialog.setNeutralButton("Cancel", new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private File getOutputMediaFile(int type) {
        // External sdcard location
        File mediaStorageDir = new File(CC_MyPhone_RESUME);
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getAbsolutePath() + File.separator + "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getAbsolutePath() + File.separator + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

    public void createDirectory() {
        File dir = new File(CC_MyPhone_RESUME);
        if (!dir.exists()) {
            if (dir.mkdir())
                Log.e("CreateDirectory", "Main Directory Created : " + dir);
        }
    }
}
