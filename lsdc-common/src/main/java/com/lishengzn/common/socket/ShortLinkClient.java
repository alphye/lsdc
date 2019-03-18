package com.lishengzn.common.socket;

import com.lishengzn.common.exception.SimpleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;
import java.util.function.Consumer;

public class ShortLinkClient {
    private static final Logger LOG = LoggerFactory.getLogger(ShortLinkClient.class);
    private Socket socket;
    private String ip;
    private int port;
    private ClientOfVehicle.ClientType clientType;

    public ShortLinkClient(String ip, int port, ClientOfVehicle.ClientType clientType) {
        this.ip = ip;
        this.port = port;
        this.clientType = clientType;
    }
    public void runShortLinkClient(Consumer<Socket> sender, Consumer<Socket> receiver){
        try {
            this.socket = new Socket(ip,port);
            sender.accept(socket);
            receiver.accept(socket);
        } catch (IOException e) {
            throw new SimpleException("无法连接到车辆，请确认车辆已开启！");
        }finally {
            close();
        }
    }
    private void close(){
        if(socket != null){
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        System.gc();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        LOG.info("====shortLink client finalize:{},{}",ip,clientType);
    }
}
