package com.beeshop.beeshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.beeshop.beeshop.R;
import com.beeshop.beeshop.model.UserEntity;
import com.beeshop.beeshop.net.HttpLoader;
import com.beeshop.beeshop.net.JsonUtil;
import com.beeshop.beeshop.net.LoggerUtil;
import com.beeshop.beeshop.net.RSAUtil;
import com.beeshop.beeshop.net.ResponseEntity;
import com.beeshop.beeshop.net.SubscriberCallBack;
import com.beeshop.beeshop.utils.GsonUtil;
import com.beeshop.beeshop.utils.LogUtil;
import com.beeshop.beeshop.utils.PrefUtils;
import com.beeshop.beeshop.utils.ToastUtils;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/8/21 0021.
 */

public class No_wanActivity extends BaseActivity implements View.OnClickListener {
    private Button no_denglu;
    private Button no_zhuce;
    private TextView dasd;
    Handler handler= new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String aa = PrefUtils.getString(getBaseContext(), "fengdian", "2");

        if (aa.equals("1")) {
//            String  s="fJYvAzUmfYlAfW9yEXpopT0p3tsFM12WSSDa7b3KPTNFLIRQ4lmAva5W6RvVZfSN9ZGahZNHgrrvW47qEpLXUkwLmJdN0jerNS+FWwE3m2TjGvAnGE0zTY+p\\/rnm9c3Ztlzx3AQwKYdKa9cGt+E2yorJxBms5KNvf98gJUNlZFRSrzjtTpw5zpY3d\\/01Hp\\/0Pjms0axUhXn4VB5yAdmJbfQqB+VZ29rErVsfyPr02wag6QWGi028RdFgBHKqUFTkI+9pAkPg++1DJJqEQM1YjXK+Fms+uEkjTFRHybKODeFZhqYZlkvEXlJpYFYzdNe953+CvpJ8CuLVtI8Xyf\\/w3bkY7Zywhf50TRTmeggqKU6ABQG8b5dtd1LmmeEOkSotDcWFfzsSwzwOAI8rJ35lKCA9Jox7hm9TkYc3U6xoDFzUUVvH+D+CDAEWtDfqYKfBmcmWM+vVfM\\/oxLc3swD99mn1EK\\/ZlGPu2mtrTxeukRP0i\\/vrAhH9ycSxAynTIE5\\/mrrLBkdZ\\/9zT2MQbGghWDl9koS7fTYKVnOHof8OixgtGuMrCvsEjfyAjEvaTtKSU2TL\\/ujits346BJ6X3UJni8q2uQxiknHVcmLiOVFXGRvfpcFHwVhJasnUgMRP9jHDpmm\\/E9KsbVkvEqrN4AhLhmQAaFMmklqLvfEJI0JlmZipNWMpZY93r9k06zbzRzbtELzlvtmGTHtRg6+J9pyE1D0xbyeh0OP\\/rxjIfI2Pbaoa1AJjleRFzNV3Z5aNOjcp4WvMqrNjzwUAFJ7aNOwoyaLxobh5N7nGv5oy4oBZRcriHcMUk3QzQI\\/tKRyc0rsIgOQQaHjdPrltc64lwBQAsngJFvGBjgUi0eTAPQ9Z9bdTzSG97oAVfZ3ndqjaqeZj49Fe3HgnDuK2AXDqlDeaEIQ+8TUzVjxfUebxPy9S5RteE2q7RBinhpqiKi85wE++jfF3SVRHB4\\/63AX8TZLtV1rRKQpCZRIshaiG8A2DTzU7a+FTKescWxmrWPFOLamivPjCMX5zb1yPyJ\\/6uiLCd7S7PDHWODbu+cQr31FZJuMsL43XBton4+Vg74u2xikJ2aqTOAXpaDfHXAeGO0whGC\\/dTgTJUTE41UDfw6AWsQGWLg6J01Y+rlg5p8SUmyCNEE1Xm\\/QPsHPTcjRuRwL1J8jvnh\\/cpiRXy3lNBABh6ANaWklwkQsjm+HCNGqEbTinx6XuxAomNFiaVkVd6AGhNcSGyDIESS+wIj4mgzdLSlbmjnEg\\/WM0JObqbSMYdhfPrtlWOODsLutNGJ6D+I9+RSlhusCzZVLi70H3i5lKhq85Yiv+TuBVpeiiNE505B0PWMujYUT+fr5kxbmNFhl4EQ==";
//            String responseStr = RSAUtil.decryptByPublicKey(Base64.decode(s,Base64.DEFAULT));
//            dasd.setText(responseStr+"");

    //        LogUtil.i("_____________________--"+responseStr);
Intent bb =new Intent(No_wanActivity.this,MainActivity.class);
startActivity(bb);
        } else {

            setContentView(R.layout.activity_nowan);
            no_denglu = (Button) findViewById(R.id.no_denglu);
            no_zhuce = (Button) findViewById(R.id.no_zhuce);
            dasd=(TextView)findViewById(R.id.dad) ;
            no_denglu.setOnClickListener(this);
            no_zhuce.setOnClickListener(this);

//            HashMap<String, Object> params = new HashMap<>();
//            params.put("token","cef5c25587137c907c59b452a9afcb1d");
//            HttpLoader.getInstance().showcarshopping(params,mCompositeSubscription, new SubscriberCallBack<String>() {
//
//                @Override
//                protected void onSuccess(String response) {
//                    super.onSuccess(response);
//                     dasd.setText(response+"");
//                    Toast.makeText(No_wanActivity.this,"-----"+response,Toast.LENGTH_SHORT).show();;
//                }
//
//                @Override
//                protected void onFailure(ResponseEntity errorBean) {
//                    ToastUtils.showToast(errorBean.getMsg());
//                    //Toast.makeText(No_wanActivity.this,"asas-----"+errorBean.getCode(),Toast.LENGTH_SHORT).show();;
//                }
//            });

//
//            String  s="fJYvAzUmfYlAfW9yEXpopT0p3tsFM12WSSDa7b3KPTNFLIRQ4lmAva5W6RvVZfSN9ZGahZNHgrrvW47qEpLXUkwLmJdN0jerNS+FWwE3m2TjGvAnGE0zTY+p\\/rnm9c3Ztlzx3AQwKYdKa9cGt+E2yorJxBms5KNvf98gJUNlZFRSrzjtTpw5zpY3d\\/01Hp\\/0Pjms0axUhXn4VB5yAdmJbfQqB+VZ29rErVsfyPr02wag6QWGi028RdFgBHKqUFTkI+9pAkPg++1DJJqEQM1YjXK+Fms+uEkjTFRHybKODeFZhqYZlkvEXlJpYFYzdNe953+CvpJ8CuLVtI8Xyf\\/w3bkY7Zywhf50TRTmeggqKU6ABQG8b5dtd1LmmeEOkSotDcWFfzsSwzwOAI8rJ35lKCA9Jox7hm9TkYc3U6xoDFzUUVvH+D+CDAEWtDfqYKfBmcmWM+vVfM\\/oxLc3swD99mn1EK\\/ZlGPu2mtrTxeukRP0i\\/vrAhH9ycSxAynTIE5\\/mrrLBkdZ\\/9zT2MQbGghWDl9koS7fTYKVnOHof8OixgtGuMrCvsEjfyAjEvaTtKSU2TL\\/ujits346BJ6X3UJni8q2uQxiknHVcmLiOVFXGRvfpcFHwVhJasnUgMRP9jHDpmm\\/E9KsbVkvEqrN4AhLhmQAaFMmklqLvfEJI0JlmZipNWMpZY93r9k06zbzRzbtELzlvtmGTHtRg6+J9pyE1D0xbyeh0OP\\/rxjIfI2Pbaoa1AJjleRFzNV3Z5aNOjcp4WvMqrNjzwUAFJ7aNOwoyaLxobh5N7nGv5oy4oBZRcriHcMUk3QzQI\\/tKRyc0rsIgOQQaHjdPrltc64lwBQAsngJFvGBjgUi0eTAPQ9Z9bdTzSG97oAVfZ3ndqjaqeZj49Fe3HgnDuK2AXDqlDeaEIQ+8TUzVjxfUebxPy9S5RteE2q7RBinhpqiKi85wE++jfF3SVRHB4\\/63AX8TZLtV1rRKQpCZRIshaiG8A2DTzU7a+FTKescWxmrWPFOLamivPjCMX5zb1yPyJ\\/6uiLCd7S7PDHWODbu+cQr31FZJuMsL43XBton4+Vg74u2xikJ2aqTOAXpaDfHXAeGO0whGC\\/dTgTJUTE41UDfw6AWsQGWLg6J01Y+rlg5p8SUmyCNEE1Xm\\/QPsHPTcjRuRwL1J8jvnh\\/cpiRXy3lNBABh6ANaWklwkQsjm+HCNGqEbTinx6XuxAomNFiaVkVd6AGhNcSGyDIESS+wIj4mgzdLSlbmjnEg\\/WM0JObqbSMYdhfPrtlWOODsLutNGJ6D+I9+RSlhusCzZVLi70H3i5lKhq85Yiv+TuBVpeiiNE505B0PWMujYUT+fr5kxbmNFhl4EQ==";
//            String responseStr = RSAUtil.decryptByPublicKey(Base64.decode(s,Base64.DEFAULT));
//dasd.setText(responseStr+"");
//
//            LogUtil.i("_____________________--"+responseStr);
        }
        }



        @Override
        public void onClick (View v){

            switch (v.getId()) {
                case R.id.no_denglu:
                    Intent intent = new Intent(No_wanActivity.this, LoginActivity.class);
                    startActivity(intent);
                    break;
                case R.id.no_zhuce:
                    Intent intent1 = new Intent(No_wanActivity.this, RegisterActivity.class);
                    startActivity(intent1);
                    break;

            }
        }




}
