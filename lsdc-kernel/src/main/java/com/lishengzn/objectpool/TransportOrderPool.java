package com.lishengzn.objectpool;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.lishengzn.dto.VehicleMoveDto;
import com.lishengzn.entity.order.TransportOrder;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


public class TransportOrderPool {
	private final Map<Long,TransportOrder>transports = new ConcurrentHashMap<Long,TransportOrder>(); 

	public void addTransportOrder(TransportOrder transportOrder){
		transports.put(transportOrder.getNaviTaskID(), transportOrder);
	}
	public TransportOrder getTransportOrderByNaviTaskID(Long naviTaskID){
		return transports.get(naviTaskID);
	}
	public Set<TransportOrder> getTransportOrders(){
		Set<TransportOrder> transportOrderSet = new HashSet<TransportOrder>();
		for(TransportOrder order :transports.values()){
			transportOrderSet.add(order);
		}
		return transportOrderSet;
	}
	
	/**创建订单--小车按指定格数移动
	 * @param vehicleMove
	 * @return
	 */
	public TransportOrder createTransportOrderWidthCellNum(VehicleMoveDto vehicleMove){
		TransportOrder tansportOrder = createTransportOrder(vehicleMove);
		tansportOrder.setTransportOrderType(TransportOrder.TYPE.moveWidthCells);
		return tansportOrder;
	}
	/***创建订单--小车发送到指定点
	 * @param vehicleMove
	 */
	public TransportOrder createTransportOrderWidthDestCoor(VehicleMoveDto vehicleMove) {
		TransportOrder tansportOrder = createTransportOrder(vehicleMove);
		tansportOrder.setTransportOrderType(TransportOrder.TYPE.sendToNode);
		return tansportOrder;
		
	}
	/**创建订单
	 * @param vehicleMove
	 * @return
	 */
	public TransportOrder createTransportOrder(VehicleMoveDto vehicleMove){
		long naviTaskID=Long.parseLong(new Double(Math.round(Math.random()*10000)).intValue()+""+System.currentTimeMillis());
		TransportOrder tansportOrder = new TransportOrder();
		tansportOrder.setNaviTaskID(naviTaskID);
		tansportOrder.setVehicleMove(vehicleMove);
		tansportOrder.setState(TransportOrder.STATE.DISPATCHABLE);
		addTransportOrder(tansportOrder);
		return tansportOrder;
	}
}
