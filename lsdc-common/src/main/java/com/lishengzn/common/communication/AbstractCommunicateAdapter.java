package com.lishengzn.common.communication;

import com.lishengzn.common.entity.Vehicle;
import com.lishengzn.common.util.Lifecycle;

import java.net.Socket;


public abstract class AbstractCommunicateAdapter implements Lifecycle {
    private Socket socket;
    protected Vehicle vehicle;
    private boolean initialized;
    private volatile boolean terminate;

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public abstract void runTask();
    @Override
    public boolean isInitialized() {
        return initialized;
    }

    @Override
    public void initialize() {
        if(!isInitialized()){
            this.setInitialized(true);
            this.setTerminate(false);
        }
    }

    @Override
    public void terminate() {
        if(isInitialized()){
            this.setInitialized(false);
            this.setTerminate(true);
        }
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    public boolean isTerminate() {
        return terminate;
    }

    public void setTerminate(boolean terminate) {
        this.terminate = terminate;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }


}
