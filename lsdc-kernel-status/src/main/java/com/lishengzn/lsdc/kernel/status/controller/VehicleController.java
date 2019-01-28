package com.lishengzn.lsdc.kernel.status.controller;

import com.lishengzn.common.service.VehicleService;
import com.lishengzn.lsdc.kernel.status.shortlink.DownloadVehicleMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @RequestMapping(value = "/allVehiclesOnline",method = RequestMethod.POST)
    public void allVehiclesOnline(){
        vehicleService.allVehiclesOnline();
    }
    @RequestMapping(value="/vehicleOnline",method = RequestMethod.POST)
    public void vehicleOnline(String ip,int port){
        vehicleService.vehicleOnline(ip);
    }
    @RequestMapping(value="/allVehiclesOffline",method = RequestMethod.POST)
    public void allVehiclesOffline(){
        vehicleService.allVehiclesOffline();
    }
    @RequestMapping(value="/vehicleOffline",method = RequestMethod.POST)
    public void vehicleOffline(String ip){
        vehicleService.vehicleOffline(ip);
    }

    @RequestMapping(value="/downloadMap",method = RequestMethod.GET)
    public void downloadMap(){
        DownloadVehicleMap.loadMap();
    }
    @RequestMapping(value="/GC",method = RequestMethod.GET)
    public void GC(){
        System.gc();
    }
}
