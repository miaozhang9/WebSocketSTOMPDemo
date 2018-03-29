//package com.pingan.websocketdemo;
//
//import android.util.Log;
//
//import com.neovisionaries.ws.client.WebSocket;
//import com.neovisionaries.ws.client.WebSocketAdapter;
//import com.neovisionaries.ws.client.WebSocketException;
//import com.neovisionaries.ws.client.WebSocketFrame;
//
//import java.util.List;
//import java.util.Map;
//
//
///**
// * Created by sdh on 17/11/14
// */
//
///**
// * 继承默认的监听空实现WebSocketAdapter,重写我们需要的方法
// * onTextMessage 收到文字信息
// * onConnected 连接成功
// * onConnectError 连接失败
// * onDisconnected 连接关闭
// */
//public class WsListener extends WebSocketAdapter {
//    private WsStatus mStatus;
//
//    @Override
//    public void onTextMessage(WebSocket websocket, String text) throws Exception {
//        super.onTextMessage(websocket, text);
//        Log.e("sdh", text);
//    }
//
//
//    @Override
//    public void onConnected(WebSocket websocket, Map<String, List<String>> headers)
//            throws Exception {
//        super.onConnected(websocket, headers);
//        Log.e("sdh","连接成功");
//        setStatus(WsStatus.CONNECT_SUCCESS);
//    }
//
//
//    @Override
//    public void onConnectError(WebSocket websocket, WebSocketException exception)
//            throws Exception {
//        super.onConnectError(websocket, exception);
//        Log.e("sdh","连接错误");
//        setStatus(WsStatus.CONNECT_FAIL);
//    }
//
//
//    @Override
//    public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer)
//            throws Exception {
//        super.onDisconnected(websocket, serverCloseFrame, clientCloseFrame, closedByServer);
//        Log.e("sdh","断开连接");
//        setStatus(WsStatus.CONNECT_FAIL);
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
//}
