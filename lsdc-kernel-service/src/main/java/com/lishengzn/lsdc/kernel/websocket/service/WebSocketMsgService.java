package com.lishengzn.lsdc.kernel.websocket.service;


public interface WebSocketMsgService {
    void sendToAll(String json);
    void vehicleOnline() ;
    void vehicleOffline();
}
