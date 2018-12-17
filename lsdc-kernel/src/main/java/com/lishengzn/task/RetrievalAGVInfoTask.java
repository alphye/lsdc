package com.lishengzn.task;

import com.lishengzn.socket.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
			command="0800";
			client.sendSimProToServer(command);
			Thread.sleep(30);

			// 读取X速度
			command="0601";
			client.sendSimProToServer(command);
			Thread.sleep(30);

			// 读取Y速度
			command="0600";
			client.sendSimProToServer(command);
//			Thread.sleep(30);

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