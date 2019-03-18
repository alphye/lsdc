package com.lishengzn.common.entity;

import com.lishengzn.common.entity.response.RequestSyncObj;

/**
 *  此类包含所有向小车发送请求时的锁对象，每个所对象只应用于一个方法
 *  所有的锁对象，都只提供getterr，防止中在使用过程中有篡改
 * */
public class RequestSyncObjCollection {
    /** 查询小车阻塞状态时，所要用的锁对象 */
    private RequestSyncObj queryBlockStatusSyncObj=new RequestSyncObj();

    public RequestSyncObj getQueryBlockStatusSyncObj() {
        return queryBlockStatusSyncObj;
    }
}
