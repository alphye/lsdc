package com.lishengzn.common.communication.service;


import java.io.IOException;

public interface WebSocketMsgService {
    void sendToAll(String address, String json);
    void vehicleOnline() throws IOException;
    void vehicleOffline();
}
