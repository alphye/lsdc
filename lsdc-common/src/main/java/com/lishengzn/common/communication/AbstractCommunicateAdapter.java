package com.lishengzn.common.communication;

import com.lishengzn.common.entity.Vehicle;
import com.lishengzn.common.util.Lifecycle;

import java.net.Socket;


public abstract class AbstractCommunicateAdapter implements Lifecycle {
    private Socket socket;
    protected Vehicle vehicle;
    private volatile boolean initialized;

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public boolean isInitialized() {
        return initialized;
    }

    @Override
    public void initialize() {
        if(!isInitialized()){
            this.initialized=true;
        }
    }

    @Override
    public void terminate() {
        if(isInitialized()){
            this.initialized=false;
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }


}
