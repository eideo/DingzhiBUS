/*
 * BottomBar library for Android
 * Copyright (c) 2016 Iiro Krankka (http://github.com/roughike).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package chenfei.com.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;
import com.sina.cloudstorage.auth.AWSCredentials;
import com.sina.cloudstorage.auth.BasicAWSCredentials;
import com.sina.cloudstorage.services.scs.SCS;
import com.sina.cloudstorage.services.scs.SCSClient;
import com.sina.cloudstorage.services.scs.model.PutObjectResult;
import com.sina.cloudstorage.services.scs.model.S3Object;

import org.xutils.common.util.FileUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chenfei.com.UI.CircleImageView;
import chenfei.com.activity.LoginActivity;
import chenfei.com.activity.MainActivity;
import chenfei.com.activity.MydrtorderListActivity;
import chenfei.com.activity.QuestionCenterActivity;
import chenfei.com.activity.UpadatepassActivity;
import chenfei.com.activity.UserInfoActivity;
import chenfei.com.adapter.Myorderlistadapter;
import chenfei.com.base.ApiInterface;
import chenfei.com.base.BaseActivity;
import chenfei.com.base.BuildConfig;
import chenfei.com.base.R;
import chenfei.com.utils.SDCardUtils;
import chenfei.com.utils.SPUtils;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Iiro Krankka (http://github.com/roughike)
 */
public class YonghuFragment extends Fragment {
    public static String IMAGE_DIR = Environment.getExternalStorageDirectory() + File.separator + BuildConfig.APPLICATION_ID + File.separator + "ImageCache" + File.separator;
    public static final int REQUEST_CODE_CHOOSE_ALBUM = 10001;
    public static final int REQUEST_CODE_CAMEIA = 10002;
    public static final int REQUEST_CODE_ZOOM = 10003;
    String mPhotoPath;
    private String urlpath;
    @BindView(R.id.touxiang)
    CircleImageView touxiang;
    @BindView(R.id.login_user_name)
    TextView loginUserName;
    @BindView(R.id.name)
    LinearLayout name;
    @BindView(R.id.grzltv)
    TextView grzltv;
    @BindView(R.id.xgmmtv)
    TextView xgmmtv;
    @BindView(R.id.gerenll)
    LinearLayout gerenll;
    @BindView(R.id.xiaoxitongzhi)
    TextView xiaoxitongzhi;
    @BindView(R.id.mychepiao)
    TextView mychepiao;
    @BindView(R.id.mydingdan)
    TextView mydingdan;
    @BindView(R.id.myyouhuijuan)
    TextView myyouhuijuan;
    @BindView(R.id.myyuyue)
    TextView myyuyue;
    @BindView(R.id.yijianfankui)
    TextView yijianfankui;
    @BindView(R.id.loginout)
    Button loginout;
    private View YonghuView;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                Bitmap bitmap =getLoacalBitmap(Environment.getExternalStorageDirectory()+"/tempuserhead.jpg"); //从本地取图片(在cdcard中获取)  //
                if (bitmap!=null) {
                    touxiang.setImageBitmap(bitmap); //设置Bitmap
                }
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (YonghuView == null) {
            YonghuView = inflater.inflate(R.layout.fragment_yonghu, container,
                    false);
        }
        MainActivity.InitTitle("用户信息", true, false);
       new Thread(new Runnable() {
            @Override
            public void run() {
                getObject();
            }
        }).start();

        super.onCreateView(inflater, container, savedInstanceState);
        YonghuView.forceLayout();
        ViewGroup parent = (ViewGroup) YonghuView.getParent();
        if (parent != null) {
            parent.removeView(YonghuView);
        }
        ButterKnife.bind(this, YonghuView);
        return YonghuView;
    }

    @OnClick({R.id.grzltv,R.id.touxiang, R.id.xgmmtv, R.id.xiaoxitongzhi, R.id.mychepiao, R.id.mydingdan, R.id.myyouhuijuan, R.id.myyuyue, R.id.yijianfankui, R.id.loginout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.grzltv:
                Intent grzlintent = new Intent(getActivity(), UserInfoActivity.class);
                startActivity(grzlintent);
                break;
            case R.id.touxiang:
                showSheetDialog();
                break;
            case R.id.xgmmtv:
                startActivity(new Intent(getActivity(), UpadatepassActivity.class));
                break;
            case R.id.xiaoxitongzhi:
                break;
            case R.id.mychepiao:
                break;
            case R.id.mydingdan:
                startActivity(new Intent(getActivity(), MydrtorderListActivity.class));
                break;
            case R.id.myyouhuijuan:
                break;
            case R.id.myyuyue:
                break;
            case R.id.yijianfankui:
                startActivity(new Intent(getActivity(), QuestionCenterActivity.class));
                break;
            case R.id.loginout:
                Intent zhuxiaointent = new Intent();
                zhuxiaointent.setClass(getActivity(), LoginActivity.class);
                getActivity().finish();
                startActivity(zhuxiaointent);
                break;
        }
    }

    /**
     * 弹出dialog获取图片
     */
    private void showSheetDialog() {
        final ActionSheetDialog mActionSheetDialog = new ActionSheetDialog(getActivity(), new String[]{"拍照获取", "通过相册"}, null);
        mActionSheetDialog.title("选择图片获取方式");
        mActionSheetDialog.titleTextSize_SP(14.5f);
        mActionSheetDialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                mActionSheetDialog.dismiss();
                switch (position) {
                    case 0:
                        Intent mCametaIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        mPhotoPath = IMAGE_DIR + System.currentTimeMillis() + ".png";
                        Uri mPhotoUri = Uri.fromFile(new File(mPhotoPath));

                        File mDirFile = new File(mPhotoPath).getParentFile();
                        if (!mDirFile.exists()) {
                            mDirFile.mkdirs();
                        }
                        mCametaIntent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
                        mCametaIntent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoUri);
                        startActivityForResult(mCametaIntent, REQUEST_CODE_CAMEIA);
                        break;
                    case 1:
                        Intent mPhotoIntent = new Intent(Intent.ACTION_PICK);
                        mPhotoIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");//相片类型
                        startActivityForResult(mPhotoIntent, REQUEST_CODE_CHOOSE_ALBUM);

                }

            }
        });
        mActionSheetDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //从相机获取
        if (requestCode == REQUEST_CODE_CAMEIA) {
            File mFile = new File(mPhotoPath);
            int degree = readPictureDegree(mFile.getAbsolutePath());

            BitmapFactory.Options opts = new BitmapFactory.Options();//获取缩略图显示到屏幕上
            opts.inSampleSize = 2;
            Bitmap cbitmap = BitmapFactory.decodeFile(mFile.getAbsolutePath(), opts);
            Bitmap newbitmap = rotaingImageView(degree, cbitmap);
            urlpath = SDCardUtils.saveFile(getActivity(), "tempuserhead.jpg", newbitmap);
            File mFile1 = new File(urlpath);
            if (mFile1.exists()) {
                startPhotoZoom(Uri.fromFile(mFile1));
                //   ImageLoader.getInstance().displayImage("file:///" + mFile.getAbsolutePath(), touxiang);
            }
        }
        //从相册获取
        else if (requestCode == REQUEST_CODE_CHOOSE_ALBUM) {
            if (data != null && data.getData() != null) {

                Uri mUri = data.getData();
                startPhotoZoom(data.getData());
              /*  if (data.getScheme().contains("file")) {
                    ImageLoader.getInstance().displayImage(mUri.toString(), touxiang);
                } else {
                    Cursor mCursor = new CursorLoader(getActivity(), data.getData(), new String[]{MediaStore.Images.Media.DATA}, null, null, null).loadInBackground();
                    int mIndex = mCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    mCursor.moveToFirst();
                    ImageLoader.getInstance().displayImage("file:///" + mCursor.getString(mIndex), touxiang);
                    mCursor.close();
                }*/

            }
        } else if (requestCode == REQUEST_CODE_ZOOM) {
            if (data != null) {
                setPicToView(data);
            }
        }
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", true);
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, REQUEST_CODE_ZOOM);
    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param
     */
    private void setPicToView(Intent picdata) {
        Bundle extras = picdata.getExtras();
        if (extras != null) {
            // 取得SDCard图片路径做显示
            Bitmap photo = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(null, photo);
            urlpath = SDCardUtils.saveFile(getActivity(), "tempuserhead.jpg", photo);
            touxiang.setImageDrawable(drawable);
            File file = new File(urlpath);
            if (!file.exists()) {
                Toast.makeText(getActivity(), "文件不存在，请修改文件路径", Toast.LENGTH_SHORT).show();
                return;
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    putObject(urlpath);
                }
            }).start();


            //   List<File> fileList = new ArrayList<>();
            //    fileList.add(file);
         /*   OkHttpUtils.post(ApiInterface.SaveUsertx)     // 请求方式和请求url
                    .tag(this)
                    // 请求的 tag, 主要用于取消对应的请求
                    .params("photoname",(String)SPUtils.get(getActivity(),"telephone",""))
                    .execute(new StringCallback() {
                        @Override
                        public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {

                        }

                        @Override
                        public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                            super.onError(isFromCache, call, response, e);

                        }
                    });*/

        }
    }
    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /*
     * 旋转图片
     * @param angle
     * @param bitmap
     * @return Bitmap
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        //旋转图片 动作
        Matrix matrix = new Matrix();
        ;
        matrix.postRotate(angle);
        System.out.println("angle2=" + angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }

    /**
     * 上传文件
     */
    public void putObject(String filepath){
        String accessKey = "2lr5pkyVNtZYXv6W44TV";
        String secretKey = "661f0c7733e6aa5d88c273d634b15f6846a688b2";
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        SCS conn = new SCSClient(credentials);
        PutObjectResult putObjectResult = conn.putObject("dingzhibus",
                "touxiang/"+(String)SPUtils.get(getActivity(),"telephone","")+".jpg",
                new File(filepath));
        System.out.println(putObjectResult);
    }

    /**
     * 下载object
     *  //断点续传
     *  GetObjectRequest rangeObjectRequest = new GetObjectRequest("test11", "/test/file.txt");
     *  rangeObjectRequest.setRange(0, 10); // retrieve 1st 10 bytes.
     *  S3Object objectPortion = conn.getObject(rangeObjectRequest);
     *
     *  InputStream objectData = objectPortion.getObjectContent();
     *  // "Process the objectData stream.
     *  objectData.close();
     */
    public void getObject(){
        String accessKey = "2lr5pkyVNtZYXv6W44TV";
        String secretKey = "661f0c7733e6aa5d88c273d634b15f6846a688b2";
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        SCS conn = new SCSClient(credentials);
        //SDKGlobalConfiguration.setGlobalTimeOffset(-60*5);//自定义全局超时时间5分钟以后(可选项)
        S3Object s3Obj = conn.getObject("dingzhibus", "touxiang/"+(String)SPUtils.get(getActivity(),"telephone","")+".jpg");
        InputStream in = s3Obj.getObjectContent();
        byte[] buf = new byte[1024];
        OutputStream out = null;
        try {
            out = new FileOutputStream(new File(Environment.getExternalStorageDirectory()+"/tempuserhead.jpg"));
            int count;
            while( (count = in.read(buf)) != -1)
            {
                if( Thread.interrupted() )
                {
                    throw new InterruptedException();
                }
                out.write(buf, 0, count);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally{
            //SDKGlobalConfiguration.setGlobalTimeOffset(0);//还原超时时间
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                in.close();
                Message msg = new Message();
                msg.what = 1;
                handler.sendMessage(msg);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 加载本地图片
     * @param url
     * @return
     */
    public static Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);  ///把流转化为Bitmap图片

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
