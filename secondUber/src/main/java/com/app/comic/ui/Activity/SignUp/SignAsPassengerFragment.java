package com.app.comic.ui.Activity.SignUp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.app.comic.MainController;
import com.app.comic.R;
import com.app.comic.application.MainApplication;
import com.app.comic.base.BaseFragment;
import com.app.comic.ui.Activity.Home.HomeActivity;
import com.app.comic.ui.Activity.Login.LoginActivity;
import com.app.comic.ui.Model.Receive.SignPassengerReceive;
import com.app.comic.ui.Model.Request.SignPassengerRequest;
import com.app.comic.ui.Module.SignPassengerModule;
import com.app.comic.ui.Presenter.HomePresenter;
import com.app.comic.ui.Realm.RealmObjectController;
import com.app.comic.utils.DropDownItem;
import com.app.comic.utils.SharedPrefManager;
import com.app.comic.utils.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.pedant.SweetAlert.SweetAlertDialog;


public class SignAsPassengerFragment extends BaseFragment implements Validator.ValidationListener, HomePresenter.SignPassengerView {

    // Validator Attributes
    private SharedPrefManager pref;
    Activity act;

    @Inject
    HomePresenter presenter;

    @InjectView(R.id.btnSignUp)
    Button btnSignUp;

    @NotEmpty(sequence = 1, messageResId = R.string.student_id_empty)
    @InjectView(R.id.txtStudentID)
    EditText txtStudentID;

    @NotEmpty(sequence = 2, messageResId = R.string.email_empty)
    @InjectView(R.id.txtUsername)
    EditText txtUsername;

    @NotEmpty(sequence = 3, messageResId = R.string.password_empty)
    @InjectView(R.id.txtPassword)
    EditText txtPassword;

    @NotEmpty(sequence = 4, messageResId = R.string.phone_empty)
    @InjectView(R.id.txtPhoneNumber)
    EditText txtPhoneNumber;

    @InjectView(R.id.txtSmoker)
    TextView txtSmoker;

    @InjectView(R.id.radioSex)
    RadioGroup radioSex;

    @InjectView(R.id.prefRadioSex)
    RadioGroup prefRadioSex;

    @InjectView(R.id.passenger_addImage)
    ImageView passenger_addImage;

    RadioButton radioSexButton;
    RadioButton prefRadioSexButton;

    private Validator mValidator;
    private ArrayList<DropDownItem> purposeList = new ArrayList<DropDownItem>();
    View view;

    private AlertDialog dialog;

    private String imageBase64;
    public static final String URL_REGEX = "^((https?|ftp)://|(www|ftp)\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$";

    int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    String userChosenTask;
    private Boolean result;

    public static SignAsPassengerFragment newInstance() {

        SignAsPassengerFragment fragment = new SignAsPassengerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        act = getActivity();
        MainApplication.get(getActivity()).createScopedGraph(new SignPassengerModule(this)).inject(this);
        RealmObjectController.clearCachedResult(getActivity());

        // Validator
        mValidator = new Validator(this);
        mValidator.setValidationListener(this);
        mValidator.setValidationMode(Validator.Mode.BURST);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.share_ride_sign_as_passenger, container, false);
        ButterKnife.inject(this, view);

        purposeList = getSmoker(act);

        /*txtSmoker.setText(purposeList.get(0).getText());
        txtSmoker.setTag(purposeList.get(0).getCode());*/

        txtSmoker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtSmoker.setHint("Smoker");
                //Log.e(purposeList.get(0).getCode().toString(),purposeList.get(1).getCode().toString());
                popupSelection(purposeList, getActivity(), txtSmoker, true, view);
            }
        });


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hideKeyboard();
                mValidator.validate();

            }
        });

        passenger_addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImageInitialization();
                dialog.show();
            }
        });

        return view;
    }

    private void captureImageInitialization() {

        LayoutInflater li = LayoutInflater.from(act);
        final View myView = li.inflate(R.layout.ride_add_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(act);
        builder.setView(myView);

        final TextView btnGallery = (TextView) myView.findViewById(R.id.btn_select_gallery);
        TextView btnCamera = (TextView) myView.findViewById(R.id.btn_take_camera);
        TextView btnRemove = (TextView) myView.findViewById(R.id.btn_remove_img);
        TextView btnCancel = (TextView) myView.findViewById(R.id.btn_cancel);

        dialog = builder.create();

        btnRemove.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                passenger_addImage.setImageDrawable(ContextCompat.getDrawable(act, R.drawable.add));
                /*String noPicture = changeImage(imageView);*/
                /*uploadPhoto(noPicture, imageView);*/
                dialog.dismiss();
            }
        });

        btnGallery.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                userChosenTask = "Choose Photo";
                btnGallery.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            result = Utils.checkPermissionGallery(act);
                            if (result) {
                                galleryIntent();
                                dialog.dismiss();
                            } else {
                                Log.e("Gallery", "CLOSE");
                            }
                        } catch (Exception e) {

                        }
                    }
                });

            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                userChosenTask = "Take Photo";
                btnGallery.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            result = Utils.checkPermissionCamera(act);
                            if (result) {
                                cameraIntent();
                                dialog.dismiss();
                            } else {
                                Log.e("Camera", "CLOSE");
                            }
                        } catch (Exception e) {

                        }
                    }
                });

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog = builder.create();

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = 1070;
        dialog.getWindow().setAttributes(lp);

    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString());
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void galleryIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case Utils.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    galleryIntent();
                } else {
                    dialog.dismiss();
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                onSelectFromGalleryResult(data);

            } else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        } else {
            Log.e("STATUS UPLOAD", "Image not uploaded");
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        passenger_addImage.setImageBitmap(thumbnail);
        passenger_addImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        /*String imageString = changeImage(imageView);
        uploadPhoto(imageString, imageView);*/
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Uri selectedImageUri = null;

        try {
            selectedImageUri = data.getData();
        } catch (Exception e) {

        }

        if (selectedImageUri != null) {
            String[] projection = {MediaStore.MediaColumns.DATA};
            Cursor cursor = act.managedQuery(selectedImageUri, projection, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String selectedImagePath = cursor.getString(column_index);

            //String imageString = changeImage();
            //uploadPhoto(imageString);

            Pattern p = Pattern.compile(URL_REGEX);
            Matcher m = p.matcher(selectedImagePath);//replace with string to compare
            if (m.find()) {
                Log.e("Message", "String contains URL");

                Glide.with(act)
                        .load(selectedImagePath)
                        .asBitmap()
                        .into(new SimpleTarget<Bitmap>(100, 100) {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                                passenger_addImage.setImageBitmap(resource);
                                passenger_addImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    /*String imageString = changeImageGlide();*/

                    /*Bitmap bitmap = resource.getBitmap();*/
                                //
                                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                resource.compress(Bitmap.CompressFormat.PNG, 100, bos);
                                byte[] bb = bos.toByteArray();
                                imageBase64 = Base64.encodeToString(bb, 0);

                            }
                        });

            } else {
                Bitmap bm;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(selectedImagePath, options);
                final int REQUIRED_SIZE = 400;
                int scale = 1;
                while (options.outWidth / scale / 2 >= REQUIRED_SIZE && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                    scale *= 2;
                options.inSampleSize = scale;
                options.inJustDecodeBounds = false;
                bm = BitmapFactory.decodeFile(selectedImagePath, options);
                passenger_addImage.setImageBitmap(bm);
                passenger_addImage.setScaleType(ImageView.ScaleType.CENTER_CROP);

                imageBase64 = changeImage();
                //uploadPhoto(imageString, imageView);
            }
        } else {
            Utils.toastNotification(act, "Image file not supported.");
        }
    }

    public String changeImage() {
        BitmapDrawable drawable = (BitmapDrawable) passenger_addImage.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        byte[] bb = bos.toByteArray();
        String image = Base64.encodeToString(bb, 0);

        return image;
    }

    @Override
    public void onSignPassengerReceive(SignPassengerReceive obj) {

        dismissLoading();

        Boolean status = MainController.getRequestStatus(obj.getStatus(), obj.getMessage(), getActivity());
        if (status) {

            new SweetAlertDialog(act, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Success!")
                    .setContentText("Successfully registered!")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            getActivity().startActivity(intent);
                            getActivity().finish();
                            sDialog.dismiss();
                        }
                    })
                    .show();
        }

    }

    @Override
    public void onValidationSucceeded() {

        int selectedId = radioSex.getCheckedRadioButtonId();
        int prefSelectedId = prefRadioSex.getCheckedRadioButtonId();

        radioSexButton = (RadioButton) view.findViewById(selectedId);
        prefRadioSexButton = (RadioButton) view.findViewById(prefSelectedId);

        initiateLoading(getActivity());

        /* Validation Success - Start send data to server */
        SignPassengerRequest signPassengerRequest = new SignPassengerRequest();
        signPassengerRequest.setUsername(txtUsername.getText().toString());
        signPassengerRequest.setGender(radioSexButton.getText().toString());
        signPassengerRequest.setPassword(txtPassword.getText().toString());
        signPassengerRequest.setPhone(txtPhoneNumber.getText().toString());
        signPassengerRequest.setPrefGender(prefRadioSexButton.getText().toString());
        signPassengerRequest.setSmoker(txtSmoker.getTag().toString());
        signPassengerRequest.setStudentID(txtStudentID.getText().toString());
        if (imageBase64 == null) {
            imageBase64 = "";
        }
        signPassengerRequest.setUserImage(imageBase64);
        presenter.onRegisterRequest(signPassengerRequest);

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        /* Validation Failed - Toast Error */

        //boolean firstView = true;
        for (ValidationError error : errors) {
            View view = error.getView();
            setShake(view);
            view.setFocusable(true);
            view.requestFocus();

            /* Split Error Message. Display first sequence only */
            String message = error.getCollatedErrorMessage(getActivity());
            String splitErrorMsg[] = message.split("\\r?\\n");

            // Display error messages
            if (view instanceof EditText) {
                ((EditText) view).setError(splitErrorMsg[0]);
            }
        }
    }

    public void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }

}
