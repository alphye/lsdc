package com.lishengzn.objectpool;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.lishengzn.entity.operation.SendOperationTask;
import com.lishengzn.entity.order.OperationOrder;

public class OperationOrderPool {
	private final Map<Long,OperationOrder> operationOrders = new ConcurrentHashMap<Long,OperationOrder>(); 
	private static class OperationOrderPoolInstance{
		private static final OperationOrderPool instance = new OperationOrderPool();
	}
	
	private  OperationOrderPool() {
	}
	public static OperationOrderPool getInstance(){
		return OperationOrderPoolInstance.instance;
	}
	public void addOperationOrder(OperationOrder operationOrder){
		operationOrders.put(operationOrder.getOperationTaskID(), operationOrder);
	}
	public OperationOrder getOperationOrderByTaskID(Long naviTaskID){
		return operationOrders.get(naviTaskID);
	}
	public Set<OperationOrder> getOperationOrders(){
		Set<OperationOrder> operationOrderSet = new HashSet<OperationOrder>();
		for(OperationOrder order :operationOrders.values()){
			operationOrderSet.add(order);
		}
		return operationOrderSet;
	}
	
	/**创建订单
	 * @return
	 */
	public OperationOrder createOperationOrder(SendOperationTask sendOperationTask){
		long taskID=Long.parseLong(new Double(Math.round(Math.random()*10000)).intValue()+""+System.currentTimeMillis());
//		SendOperationTask sendOperationTask = new SendOperationTask();
		sendOperationTask.setTaskID(taskID);
		OperationOrder operationOrder = new OperationOrder();
		operationOrder.setSendOperationTask(sendOperationTask);
		operationOrder.setOperationTaskID(taskID);
		operationOrder.setState(OperationOrder.STATE.DISPATCHABLE);
		addOperationOrder(operationOrder);
		return operationOrder;
	}
	
	/**创建订单 --皮带停止转动
	 * @param whichBelt 停止转动哪跟皮带
	 * @return
	 */
	public OperationOrder createBeltRotationStop(int whichBelt){
		SendOperationTask sendOperationTask = new SendOperationTask();
		sendOperationTask.setOperationParamNum(2);
		List<Integer>operationParams = new ArrayList<Integer>();
		operationParams.add(whichBelt);
		sendOperationTask.setOperationParams(operationParams);
		OperationOrder operationOrder=createOperationOrder(sendOperationTask);
		return operationOrder;
	}
	
	


	/**创建订单 --皮带转动
	 * @param whichBelt 转动哪跟皮带
	 * @param rotationDirection 转动方向
	 * @return
	 */
	public OperationOrder createBeltRotationOn(int whichBelt,int rotationDirection){
		SendOperationTask sendOperationTask = new SendOperationTask();
		sendOperationTask.setOperationParamNum(1);
		List<Integer>operationParams = new ArrayList<Integer>();
		operationParams.add(whichBelt);
		operationParams.add(rotationDirection);
		sendOperationTask.setOperationParams(operationParams);
		OperationOrder operationOrder=createOperationOrder(sendOperationTask);
		return operationOrder;
	}
	
	/**创建订单 --皮带转动后停止
	 * @param whichBelt 转动哪跟皮带
	 * @param rotationDirection 转动方向
	 * @param time 转动时间
	 * @return
	 */
	public OperationOrder createBeltRotationOnAndStop(int whichBelt,int rotationDirection,int time){
		SendOperationTask sendOperationTask = new SendOperationTask();
		sendOperationTask.setOperationParamNum(3);
		List<Integer>operationParams = new ArrayList<Integer>();
		operationParams.add(whichBelt);
		operationParams.add(rotationDirection);
		operationParams.add(time);
		sendOperationTask.setOperationParams(operationParams);
		OperationOrder operationOrder=createOperationOrder(sendOperationTask);
		return operationOrder;
	}
}
