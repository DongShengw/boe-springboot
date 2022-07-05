package com.example.demo.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.ConcurrentHashMap;
//注册成组件
@Component
//定义websocket服务器端，它的功能主要是将目前的类定义成一个websocket服务器端。注解的值将被用于监听用户连接的终端访问URL地址
@ServerEndpoint("/websocket/{userId}")
//如果不想每次都写private  final Logger logger = LoggerFactory.getLogger(当前类名.class); 可以用注解@Slf4j;可以直接调用log.info
@Slf4j
public class WebSocket {

    //实例一个session，这个session是websocket的session
    private Session session;

    /**concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。*/
    private static ConcurrentHashMap<String, WebSocket> webSocketMap = new ConcurrentHashMap<>();

    /**接收userId*/
    private String userId="";

    private static int onlineCount = 0;

    //前端请求时一个websocket时
    @OnOpen
    public void onOpen(Session session,@PathParam("userId") String userId) {
        this.session = session;
        this.userId=userId;
        if(webSocketMap.containsKey(userId)){

            webSocketMap.remove(userId);
            webSocketMap.put(userId,this);
            //加入set中
        }else{

            webSocketMap.put(userId,this);
            //加入set中
            addOnlineCount();
            //在线数加1
        }
        log.info("用户连接:"+userId+",当前在线人数为:" + getOnlineCount());
        sendMessage("连接成功");

    }

    //前端关闭时一个websocket时
    @OnClose
    public void onClose() {
        if(webSocketMap.containsKey(userId)){

            webSocketMap.remove(userId);
            //从set中删除
            subOnlineCount();
        }
        log.info("用户退出:"+userId+",当前在线人数为:" + getOnlineCount());
    }

    @OnError
    public void onError(Session session, Throwable t) {

        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.error("WebSocket连接发生异常，message:"+t.getMessage());
    }

    StringBuilder stringBuilder;
    //前端向后端发送消息
    @OnMessage
    public void onMessage(String message) {
        log.info("用户消息:"+userId+",报文:"+message);
        //可以群发消息
        //消息保存到数据库、redis
        if(!message.isEmpty()){
            try {
                //解析发送的报文
                JSONObject jsonObject = JSON.parseObject(message);
                //追加发送人(防止串改)
                jsonObject.put("fromUserId",this.userId);
                String toUserId=jsonObject.getString("toUserId");
                //传送给对应toUserId用户的websocket
                if(!toUserId.isEmpty()&&webSocketMap.containsKey(toUserId)){
                    sendMessage(jsonObject.toJSONString());
                }else{

                    log.error("请求的userId:"+toUserId+"不在该服务器上");
                    //否则不在这个服务器上，发送到mysql或者redis
                }
            }catch (Exception e){

                e.printStackTrace();
            }
        }
    }
    //新增一个方法用于主动向客户端发送消息
    public void sendMessage(String message) {
        log.info("【websocket消息】广播消息, message={}", message);
        try {
            this.session.getBasicRemote().sendText(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /** * 发送自定义消息 * */
    public static void sendInfo(String message,@PathParam("userId") String userId) throws IOException {

        log.info("发送消息到:"+userId+"，报文:"+message);
        if(!userId.isEmpty()&&webSocketMap.containsKey(userId)){

            webSocketMap.get(userId).sendMessage(message);
        }else{

            log.error("用户"+userId+",不在线！");
        }
    }
    public synchronized int getOnlineCount() {

        return onlineCount;
    }
    public static synchronized void addOnlineCount() {

        WebSocket.onlineCount++;
    }
    public static synchronized void subOnlineCount() {
        WebSocket.onlineCount--;
    }
    public ConcurrentHashMap<String, WebSocket>  getWebSocketMap() {
        return webSocketMap;
    }
}

