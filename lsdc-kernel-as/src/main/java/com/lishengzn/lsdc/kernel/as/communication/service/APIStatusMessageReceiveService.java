package com.lishengzn.lsdc.kernel.as.communication.service;


import com.lishengzn.common.communication.service.MessageReceiveService;

public interface APIStatusMessageReceiveService extends MessageReceiveService {
    void handlReceivedMsg();
}
