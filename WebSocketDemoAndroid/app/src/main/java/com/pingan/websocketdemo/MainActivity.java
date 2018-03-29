package com.pingan.websocketdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.java_websocket.WebSocket;

import rx.Subscriber;
import rx.functions.Action1;
import ua.naiksoftware.stomp.LifecycleEvent;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.client.StompClient;
import ua.naiksoftware.stomp.client.StompMessage;

public class MainActivity extends AppCompatActivity {

    private StompClient mStompClient;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        process();
    }

    private void process() {
        mTextView = (TextView) findViewById(R.id.tv_message_get);
    }

    @Override
    protected void onResume() {
        super.onResume();

//        WsManager.getInstance().init();
    }

    public void send(View v) {
        if (mStompClient == null) {
            return;
        }
        // 向/app/cheat发送Json数据
        mStompClient.send("/app/cheat","{\"userId\":\"lincoln\",\"message\":\"sss"+"\"}")
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {
                        toast("发送成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        toast("发送错误");
                    }

                    @Override
                    public void onNext(Void aVoid) {

                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        WsManager.getInstance().disconnect();
    }

    public void initWebSocket(View v) {
        mStompClient = Stomp.over(WebSocket.class, "http://172.18.19.97:8088/gs-guide-websocket/websocket");
        mStompClient.connect();
        Toast.makeText(MainActivity.this,"开始连接 172.18.19.97:8088",Toast.LENGTH_SHORT).show();
        mStompClient.lifecycle().subscribe(new Action1<LifecycleEvent>() {
            @Override
            public void call(LifecycleEvent lifecycleEvent) {
                switch (lifecycleEvent.getType()) {
                    case OPENED:
                        Log.d("sdh", "Stomp connection opened");
                        toast("连接已开启");
                        break;

                    case ERROR:
                        Log.e("sdh", "Stomp Error", lifecycleEvent.getException());
                        toast("连接出错");
                        break;
                    case CLOSED:
                        Log.d("sdh", "Stomp connection closed");
                        toast("连接关闭");
                        break;
                }
            }
        });
    }


    private void toast(final String message) {
        Log.e("MainActivity", "toast(MainActivity.java:65)" + Thread.currentThread() +"---message>>>"+ message);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this,message,Toast.LENGTH_SHORT).show();
            }
        });

        registerStompTopic();
    }

    private void registerStompTopic() {
        ///user/xiaoli/message
        mStompClient.topic("/app/sub").subscribe(new Action1<StompMessage>() {
            @Override
            public void call(StompMessage stompMessage) {
                Log.e("sdh", "call: " +stompMessage.getPayload() );
                showMessage(stompMessage);
            }
        });
    }

    /**
     * 测试展示代码
     * @param stompMessage
     */
    private void showMessage(final StompMessage stompMessage) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                TextView text = new TextView(MainActivity.this);
//                text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                mTextView.setText(System.currentTimeMillis() +" body is --->"+stompMessage.getPayload());

                Log.e("MainActivity", "run(MainActivity.java:101)---body>>>>" +stompMessage.getPayload());
//                message.addView(text);
            }
        });
    }


}
