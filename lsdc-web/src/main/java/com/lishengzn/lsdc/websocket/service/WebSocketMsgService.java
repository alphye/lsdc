package com.lishengzn.lsdc.websocket.service;


public interface WebSocketMsgService {
    void sendToAll(String json);
    void vehicleOnline() ;
    void vehicleOffline();
}
