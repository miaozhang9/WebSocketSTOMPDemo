//package com.pingan.websocketdemo;
//
//import android.text.TextUtils;
//import android.util.Log;
//
//import com.neovisionaries.ws.client.WebSocket;
//import com.neovisionaries.ws.client.WebSocketFactory;
//
//import java.io.IOException;
//
///**
// * Created by sdh on 17/11/14
// */
//
//public class WsManager {
//
//    private static WsManager mInstance;
//    private final String TAG = this.getClass().getSimpleName();
//
//    /**
//     * WebSocket config
//     */
//    private static final int FRAME_QUEUE_SIZE = 5;
//    private static final int CONNECT_TIMEOUT = 5000;
//    private static final String DEF_TEST_URL = "http://172.18.19.97:8088/gs-guide-websocket/websocket";//测试服默认地址
//    private static final String DEF_RELEASE_URL = "正式服地址";//正式服默认地址
//    private static final String DEF_URL = BuildConfig.DEBUG ? DEF_TEST_URL : DEF_RELEASE_URL;
//    private String url;
//
//    private WsStatus mStatus;
//    private WebSocket ws;
//    private WsListener mListener;
//
//    private WsManager() {
//    }
//
//    public static WsManager getInstance(){
//        if(mInstance == null){
//            synchronized (WsManager.class){
//                if(mInstance == null){
//                    mInstance = new WsManager();
//                }
//            }
//        }
//        return mInstance;
//    }
//
//    public void init(){
//        try {
//            /**
//             * configUrl其实是缓存在本地的连接地址
//             * 这个缓存本地连接地址是app启动的时候通过http请求去服务端获取的,
//             * 每次app启动的时候会拿当前时间与缓存时间比较,超过6小时就再次去服务端获取新的连接地址更新本地缓存
//             */
//            String configUrl = "";
//            url = TextUtils.isEmpty(configUrl) ? DEF_URL : configUrl;
//            ws = new WebSocketFactory().createSocket(url, CONNECT_TIMEOUT)
//                    .setFrameQueueSize(FRAME_QUEUE_SIZE)//设置帧队列最大值为5
//                    .setMissingCloseFrameAllowed(false)//设置不允许服务端关闭连接却未发送关闭帧
//                    .addListener(mListener = new WsListener())//添加回调监听
//                    .connectAsynchronously();//异步连接
//            setStatus(WsStatus.CONNECTING);
//            Log.e("sdh","第一次连接");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void setStatus(WsStatus status){
//        this.mStatus = status;
//    }
//
//    private WsStatus getStatus(){
//        return mStatus;
//    }
//
//
//    public void disconnect(){
//        if(ws != null)
//            ws.disconnect();
//    }
//}
