package com.lishengzn.entity.read.content;

import com.google.common.primitives.Bytes;
import com.lishengzn.util.SocketUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**提供皮带转动状态
 * @author Administrator
 *
 */
public class ContainerStateContent extends VariableContent {

	private long operationId;
	/**
	 * 皮带转动状态
	 */
	private int containerState;

	public ContainerStateContent() {
	}

	public ContainerStateContent(long operationId, int containerState) {
		super();
		this.operationId=operationId;
		this.containerState = containerState;
	}

	public long getOperationId() {
		return operationId;
	}

	public void setOperationId(long operationId) {
		this.operationId = operationId;
	}

	public int getContainerState() {
		return containerState;
	}

	public void setContainerState(int containerState) {
		this.containerState = containerState;
	}

	@Override
	public byte[] toBytes() {
		List<Byte> byteList=new ArrayList<Byte>();
		byteList.addAll(Bytes.asList(SocketUtil.longToBytes(operationId)));
		byteList.addAll(Bytes.asList(SocketUtil.intToBytes(containerState)));
		return Bytes.toArray(byteList);
	}

	@Override
	public ContainerStateContent fromBytes(byte[] subVar_bytes) {
		if (subVar_bytes.length != 12) {
			throw new RuntimeException("“读取货柜状态”变量内容字节数须为12");
		}
		this.setOperationId(SocketUtil.bytesToLong(Arrays.copyOfRange(subVar_bytes, 0, 8)));
		this.setContainerState(SocketUtil.bytesToInt(Arrays.copyOfRange(subVar_bytes, 8, 12)));
		return this;
	}

}
