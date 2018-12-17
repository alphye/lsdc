package com.lishengzn.socket;

import com.lishengzn.entity.Coordinate;
import com.lishengzn.entity.Vehicle;
import com.lishengzn.enums.NaviStateEnum;
import com.lishengzn.util.SocketUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static final Logger LOG = LoggerFactory.getLogger(Server.class);
    private ServerSocket serverSocket = null;
    public static volatile Vehicle vehicle01 = new Vehicle(new Coordinate(0, 0));

    public static Server getInstance() {
        return ServerInstance.serverInstance;
    }

    private ExecutorService threadPool = Executors.newCachedThreadPool();

    private static class ServerInstance {
        final static Server serverInstance = getServer();

        private static Server getServer() {
            try {
                return new Server(2000);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * @param port
     * @throws IOException
     */
    private Server(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        LOG.info("在端口" + port + "开启服务");
    }

    public Socket getSocket() throws IOException {
        return serverSocket.accept();
    }

    public void doSocket() throws Exception {

        while (true) {
            Socket socket = getSocket();
            LOG.info("客户端连接：" + socket.getInetAddress().getHostAddress());
            ClientHandler clientHandler = new ClientHandler(socket);
            threadPool.execute(clientHandler);
            initializeVehicle();
        }
    }

    private void initializeVehicle() {
        vehicle01.setNaviState(NaviStateEnum.IDLE.getValue());
        vehicle01.setOperationState(NaviStateEnum.IDLE.getValue());
        vehicle01.setBatteryResidues(100);
    }

    public static void main(String[] args) throws Exception {
        Server socketServer = Server.getInstance();
        socketServer.doSocket();
    }
}

class ClientHandler implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(ClientHandler.class);
    private volatile boolean terminate;
    private Socket socket;
    long lastHeartBeatTime;
    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(8);

    public ClientHandler(Socket socket) {
        super();
        terminate = false;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            handleSocket();
        } catch (IOException e) {
            close();
            e.printStackTrace();
        }
    }

    public void handleSocket() throws IOException {
        lastHeartBeatTime = System.currentTimeMillis();// 启动监听之前先把lastHeartBeatTime初始化
        new Thread(() -> listen2Server(socket)).start();
        Scanner scanner = new Scanner(System.in);
        while (!terminate) {
            if (scanner.hasNextLine()) {
                String str = scanner.nextLine() + "\r\n";
                LOG.info("发送简单指令：{}", str);
                socket.getOutputStream().write(str.getBytes());
            }
        }
        LOG.info("==========handleSocket end");

    }



    private void listen2Server(Socket socket) {
        InputStream is = null;
        String result = "";
        try {
            is = socket.getInputStream();
            while (!terminate) {
                if (((result = SocketUtil.readSimpleProtocol(is)) != null) && result.trim().length() > 0) {
                    if (",0500,0601,0600,".indexOf("," + result.toUpperCase() + ",") < 0) {
                        LOG.info("收到简单指令：{}", result);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            close();
        }
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.terminate = true;
        fixedThreadPool.shutdown();
    }

    public boolean isTerminate() {
        return terminate;
    }

    public void setTerminate(boolean terminate) {
        this.terminate = terminate;
    }

}