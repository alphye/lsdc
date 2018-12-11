package com.lishengzn.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lishengzn.entity.read.ReadItem_Request;
import com.lishengzn.entity.read.ReadVariable;
import com.lishengzn.packet.PacketModel;
import com.lishengzn.packet.PacketSerialNo;
import com.lishengzn.socket.Client;
import com.lishengzn.util.LSConstants;

public class RetrievalAGVInfoTask extends CyclicTask{
	private static final Logger LOG=LoggerFactory.getLogger(RetrievalAGVInfoTask.class);
	private Client client;
	public RetrievalAGVInfoTask(long tSleep, Client client) {
		super(tSleep);
		this.client = client;
	}


	@Override
	protected void runActualTask() {
		try {
			if(client.isTerminate()){
				this.terminate();
				return;
			}
			String command="0000";
			/*// 读取平台状态
			command="030E";
			client.sendSimProToServer(command);
			Thread.sleep(20);*/

			// 读取当前位置
			command="050E";
			client.sendSimProToServer(command);
			Thread.sleep(20);

			// 读取X速度
			command="0601";
			client.sendSimProToServer(command);
			Thread.sleep(20);

			// 读取Y速度
			command="0600";
			client.sendSimProToServer(command);
			Thread.sleep(20);

			// 读取目标位置
			/*command="090E";
			client.sendSimProToServer(command);
			Thread.sleep(80);*/

			// 读取二维码
			/*command="310E";
			client.sendSimProToServer(command);
			Thread.sleep(80);*/

		} catch (Exception e) {
			LOG.error("",e);
		}

		
	}

}