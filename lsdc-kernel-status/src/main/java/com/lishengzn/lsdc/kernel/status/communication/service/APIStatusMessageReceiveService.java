package com.lishengzn.lsdc.kernel.status.communication.service;


import com.lishengzn.common.communication.service.MessageReceiveService;

public interface APIStatusMessageReceiveService extends MessageReceiveService {
    void handlReceivedMsg();
}
