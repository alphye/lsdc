package com.lishengzn.lsdc.kernel.navi.communication.service;


import com.lishengzn.common.communication.service.MessageReceiveService;

public interface NaviMessageReceiveService extends MessageReceiveService {
    void handlReceivedMsg();
}
