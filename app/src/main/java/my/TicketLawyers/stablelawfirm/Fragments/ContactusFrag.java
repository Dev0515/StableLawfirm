package my.TicketLawyers.stablelawfirm.Fragments;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import my.TicketLawyers.stablelawfirm.Activity.DoneScreen;

import my.TicketLawyers.stablelawfirm.R;
import my.TicketLawyers.stablelawfirm.model.FilePath;
import my.TicketLawyers.stablelawfirm.model.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class ContactusFrag extends Fragment {
    Button next;
    ImageView imgg;
    EditText editfirstname, editlastname, editemail, editphoneno, editdesc;
    String Firstname, Lastname, email, Phone, Desc,ConvertImage,path,success2;
    static int x=0,y=0,value=0;

    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    public static final int MY_PERMISSIONS_REQUEST_WRITE_CALENDAR = 1231,MY_PERMISSIONS_REQUEST_WRITE_CALENDAR1=100;

    //private String HTTP_URL = "http://bytecodetechnologies.co.in/StableLawfirm/InsertImage.php";
    private String HTTP_URL   ="http://www.stabilelawfirm.com/MobileApi/InsertImage.php";
    private String userChoosenTask,FinalJSonObject;

    Bitmap FixBitmap;
    int photo=0;
    Uri imageUri = null;
    ProgressDialog pd;

    private AwesomeValidation awesomeValidation;

    public ContactusFrag() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_contactus, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Upload your Traffic ticket");
        checkPermission1();
        checkPermission();
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        next = (Button) view.findViewById(R.id.next);
        editdesc = (EditText) view.findViewById(R.id.editdesc);
        editfirstname = (EditText) view.findViewById(R.id.editfrstname);
        editlastname = (EditText) view.findViewById(R.id.editlastname);
        editemail = (EditText) view.findViewById(R.id.editemail);
        editphoneno = (EditText) view.findViewById(R.id.editphoneno);
        imgg = (ImageView) view.findViewById(R.id.header_cover_image);
        editfirstname.setMovementMethod(null);
        editlastname.setMovementMethod(null);
        imgg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                x=2;
                selectImage();

            }
        });


        awesomeValidation.addValidation(getActivity(), R.id.editfrstname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(getActivity(), R.id.editlastname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror1);
        awesomeValidation.addValidation(getActivity(), R.id.editemail, Patterns.EMAIL_ADDRESS, R.string.emailerror);
       // awesomeValidation.addValidation(getActivity(), R.id.editphoneno, "^[2-9]{2}[0-9]{8}$", R.string.mobileerror);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(!isNullOrEmpty(path))
                {
                    Firstname = editfirstname.getText().toString();
                    Lastname = editlastname.getText().toString();
                    email = editemail.getText().toString();
                    Phone = editphoneno.getText().toString();
                    Desc = editdesc.getText().toString();
                    pd = new ProgressDialog(getActivity(), R.style.MyTheme);
                    pd.setCancelable(false);
                    pd.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
                    pd.show();


                    if (awesomeValidation.validate()) {
                        if(Desc.isEmpty())
                        {
                            editdesc.setError("enter Description");
                            editdesc.requestFocus();
                            pd.dismiss();

                        }
                       else if(Phone.isEmpty())
                        {
                            editphoneno.setError("enter Phone no");
                            editphoneno.requestFocus();
                            pd.dismiss();

                        }

                        else
                        {

                            Thread t=new Thread(new Runnable() {
                                @Override
                                public void run() {

                                    File f = new File(path);
                                    String contenttype="image/png";
                                    OkHttpClient client=new OkHttpClient();
                                    RequestBody fil_body= RequestBody.create(MediaType.parse(contenttype),f);
                                    RequestBody reqbody=new MultipartBody.Builder()
                                            .setType(MultipartBody.FORM)

                                            .addFormDataPart("file_type",contenttype)
                                            .addFormDataPart("file",path.substring(path.lastIndexOf("/")+1),fil_body)
                                            .addFormDataPart("fname",Firstname)
                                            .addFormDataPart("lname",Lastname)
                                            .addFormDataPart("email",email)
                                            .addFormDataPart("phoneno",Phone)
                                            .addFormDataPart("description",Desc)
                                            .build();


                                    okhttp3.Request request=new okhttp3.Request.Builder().url("https://www.stabilelawfirm.com/MobileApi/InsertImage.php").post(reqbody).build();
                                    try {
                                        okhttp3.Response response=client.newCall(request).execute();
                                        FinalJSonObject=response.body().string().toString();
                                        Log.e("pto", "run: "+FinalJSonObject );
                                        //Toast.makeText(getContext(), ""+FinalJSonObject, Toast.LENGTH_SHORT).show();
                                        if(!response.isSuccessful())
                                        {

                                            pd.dismiss();
                                            throw new IOException("ERROR:"+response);

                                        }
                                        else{
                                            try {

                                                JSONArray jsonArrays = new JSONArray(FinalJSonObject);
                                                for (int i = 0; i < jsonArrays.length(); i++) {
                                                    JSONObject json_data = jsonArrays.getJSONObject(i);
                                                    success2=json_data.getString("message");
                                                    Log.e("ss", "run: "+success2 );

                                                    pd.dismiss();
                                                    if(success2.equalsIgnoreCase("New record created successfully"))
                                                    {
                                                        Intent aa=new Intent(getContext(), DoneScreen.class);
                                                         getContext().startActivity(aa);
                                                    }
                                                    else
                                                    {
                                                        Toast.makeText(getContext(), ""+success2, Toast.LENGTH_SHORT).show();
                                                    }
//
                                                }

                                            } catch (JSONException e) {

                                                e.printStackTrace();
                                                pd.dismiss();
                                            } }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            t.start(); } }
                    else{
                        pd.dismiss();
                    }
                }
                else{
                    Toast.makeText(getContext(), "Please upload Your Ticket", Toast.LENGTH_SHORT).show();
                } }
        });


    }
    //    x0PRA{1WHJ#.
    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result= Utility.checkPermission(getActivity());
                if (items[item].equals("Take Photo")) {
                    userChoosenTask="Take Photo";
                    if(result)
                        cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask="Choose from Library";
                    if(result)
                        galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                } }
        });
        builder.show();
    }
    public void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            if (requestCode == SELECT_FILE) {
                try {
                    final Uri filePath = data.getData();
                    FixBitmap= MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                    imgg.setImageBitmap(FixBitmap);
                    photo=1;
                     path = FilePath.getPath(getContext(), filePath);
                    Log.e("5", "onActivityResult: "+path );

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            else if (requestCode == REQUEST_CAMERA)
                FixBitmap = (Bitmap) data.getExtras().get("data");
                imgg.setImageBitmap(FixBitmap);
                photo=2;
                Uri abc= getImageUri(getContext(),FixBitmap);
                path = FilePath.getPath(getContext(), abc);

        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        Log.e("3", "getImageUri: "+path );
        return Uri.parse(path);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public boolean checkPermission()
    {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if(currentAPIVersion>=android.os.Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("Write external permission is necessary to write event!!!");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity)getContext(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_CALENDAR);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions((Activity)getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_CALENDAR);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public boolean checkPermission1()
    {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if(currentAPIVersion>=android.os.Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) getActivity(), Manifest.permission.CAMERA)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("Camera permission is necessary to camera event!!!");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity)getContext(), new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_WRITE_CALENDAR1);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions((Activity)getActivity(), new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_WRITE_CALENDAR1);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_CALENDAR: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    cameraIntent();
                } else {
                    //code for deny
                }
                break;
            }
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if (userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                }

            case MY_PERMISSIONS_REQUEST_WRITE_CALENDAR1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    cameraIntent();
                } else {
                    //code for deny
                }
                break;
            }
        }
    }
    public static boolean isNullOrEmpty(String str) {
        if(str != null && !str.isEmpty())
            return false;
        return true;
    }

}

