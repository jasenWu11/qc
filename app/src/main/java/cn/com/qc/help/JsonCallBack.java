package cn.com.qc.help;

/**
 * Created by lenovo on 2017/10/9.
 */


import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.request.base.Request;
import com.tandong.sa.loopj.ResponseHandlerInterface;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import cn.com.qc.bean.LoginBean;
import okhttp3.Headers;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static android.content.Context.MODE_PRIVATE;

public abstract class JsonCallBack<T> extends AbsCallback<T> {

    private Type type;
    private Class<T> clazz;

    public JsonCallBack() {
    }

    public JsonCallBack(Type type) {
        this.type = type;
    }

    public JsonCallBack(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public void onStart(Request<T, ? extends Request> request) {
        super.onStart(request);
        // 主要用于在所有请求之前添加公共的请求头或请求参数
        // 例如登录授权的 token
        // 使用的设备信息
        // 可以随意添加,也可以什么都不传
        // 还可以在这里对所有的参数进行加密，均在这里实现
    /*    request.headers("header1", "HeaderValue1")//
                .params("params1", "ParamsValue1")//
                .params("token", "3215sdf13ad1f65asd4f3ads1f");*/
    }

    /**
     * 该方法是子线程处理，不能做ui相关的工作
     * 主要作用是解析网络返回的 response 对象,生产onSuccess回调中需要的数据对象
     * 这里的解析工作不同的业务逻辑基本都不一样,所以需要自己实现,以下给出的时模板代码,实际使用根据需要修改
     */
    @Override
    public T convertResponse(Response response) throws Throwable {

        // 重要的事情说三遍，不同的业务，这里的代码逻辑都不一样，如果你不修改，那么基本不可用
        // 重要的事情说三遍，不同的业务，这里的代码逻辑都不一样，如果你不修改，那么基本不可用
        // 重要的事情说三遍，不同的业务，这里的代码逻辑都不一样，如果你不修改，那么基本不可用

        //详细自定义的原理和文档，看这里： https://github.com/jeasonlzy/okhttp-OkGo/wiki/JsonCallback

        ResponseBody body = response.body();
        Headers headers=response.headers();
        List<String> cookies=headers.values("Set-Cookie");
        System.out.println("获取的cookie是"+cookies);
        if(body == null) return null;

        T data = null;
        Gson gson = new Gson();
        JsonReader jsonReader = new JsonReader(body.charStream());
        if(type != null){
            data = Convert.fromJson(jsonReader,type);
        }else if(clazz != null){
            data = Convert.fromJson(jsonReader,clazz);
        }else{
            Type getType = getClass().getGenericSuperclass();
            Type type = ((ParameterizedType)getType).getActualTypeArguments()[0];
            data = Convert.fromJson(jsonReader,type);
        }
        return data;
    }
}
