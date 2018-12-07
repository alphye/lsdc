package com.lishengzn.task;

import java.util.Map;
import java.util.Set;

import com.lishengzn.constant.TopicAddressConstants;
import com.lishengzn.service.WebSocketMsgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.lishengzn.dto.CommandDto;
import com.lishengzn.entity.order.TransportOrder;
import com.lishengzn.exception.SimpleException;
import com.lishengzn.objectpool.TransportOrderPool;
import com.lishengzn.socket.Client;
import com.lishengzn.util.CacheManager;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public class FullDispatchTask extends CyclicTask {
	private TransportOrderPool transportOrderPool;
	private WebSocketMsgService webSocketMsgService;
	private static final Logger LOG=LoggerFactory.getLogger(FullDispatchTask.class);
	public FullDispatchTask(long tSleep ,WebSocketMsgService webSocketMsgService) {
		super(tSleep);
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		transportOrderPool = wac.getBean(TransportOrderPool.class);
		this.webSocketMsgService = webSocketMsgService;
	}

	@Override
	protected void runActualTask() {
		try {
			Set<TransportOrder> transportOrders = transportOrderPool.getTransportOrders();
			transportOrders.stream().filter(transportOrder -> {
				return TransportOrder.STATE.DISPATCHABLE.equals(transportOrder.getState());
			}).sorted((transportOrder1, transportOrder2) -> {
				if (transportOrder1.getCreateTime() < transportOrder2.getCreateTime()) {
					return -1;
				}
				return 1;
			}).findFirst().ifPresent((order) -> dispatchTransportOrder(order));
		} catch (SimpleException e) {
    		CommandDto command = new CommandDto();
    		command.setCommandType(CommandDto.TYPE.error.name());
    		command.setMessage(e.getMessage());
			webSocketMsgService.sendToAll(TopicAddressConstants.error,JSONObject.toJSONString(command));
    		LOG.error("",e);
		}catch (Exception e) {
			LOG.error("",e);
		}
		
	}

	private void dispatchTransportOrder(TransportOrder transportOrder) {
		@SuppressWarnings("unchecked")
		Map<String, Client> clientMap = (Map<String, Client>) CacheManager.cache.get(CacheManager.clientPoolKey);
		clientMap.values().stream().filter(c -> {
			return c.canDispath();
		}).findFirst().ifPresent(c -> {
			c.dispatch(transportOrder);
		});
	}
}
