package com.lishengzn.entity.read.content;

import com.lishengzn.util.SocketUtil;

import java.util.Arrays;

/**读取电池容量
 * @author Administrator
 *
 */
public class BatteryCapacityContent extends VariableContent {
	/**
	 * 电池总量
	 */
	private int batteryCapacity;
	/**
	 * 电池余量
	 */
	private int batteryResidues;

	/**
	 * 电池状态，是否损坏
	 */
	private int batteryState;
	
	public BatteryCapacityContent() {
	}

	
	public BatteryCapacityContent(int batteryCapacity, int batteryResidues, int batteryState) {
		super();
		this.batteryCapacity = batteryCapacity;
		this.batteryResidues = batteryResidues;
		this.batteryState = batteryState;
	}


	public int getBatteryCapacity() {
		return batteryCapacity;
	}


	public void setBatteryCapacity(int batteryCapacity) {
		this.batteryCapacity = batteryCapacity;
	}


	public int getBatteryResidues() {
		return batteryResidues;
	}


	public void setBatteryResidues(int batteryResidues) {
		this.batteryResidues = batteryResidues;
	}


	public int getBatteryState() {
		return batteryState;
	}


	public void setBatteryState(int batteryState) {
		this.batteryState = batteryState;
	}


	@Override
	public String toString() {
		return "BatteryCapacityContent [batteryCapacity=" + batteryCapacity + ", batteryResidues=" + batteryResidues
				+ ", batteryState=" + batteryState + "]";
	}


	@Override
	public byte[] toBytes() {
		byte[] batteryCapacity_byte = SocketUtil.intToBytes(batteryCapacity);
		byte[] batteryResidues_byte = SocketUtil.intToBytes(batteryResidues);
		byte[] batteryState_byte = SocketUtil.intToBytes(batteryState);

		byte[] subVar_bytes = new byte[batteryCapacity_byte.length + batteryResidues_byte.length+batteryState_byte.length];
		System.arraycopy(batteryCapacity_byte, 0, subVar_bytes, 0, batteryCapacity_byte.length);
		System.arraycopy(batteryResidues_byte, 0, subVar_bytes, 4, batteryResidues_byte.length);
		System.arraycopy(batteryState_byte, 0, subVar_bytes, 8, batteryState_byte.length);

		return subVar_bytes;
	}

	@Override
	public BatteryCapacityContent fromBytes(byte[] subVar_bytes) {
		if (subVar_bytes.length != 12) {
			throw new RuntimeException("“充电状态”变量内容字节数须为12");
		}
		this.setBatteryCapacity(SocketUtil.bytesToInt(Arrays.copyOfRange(subVar_bytes, 0, 4)));
		this.setBatteryResidues(SocketUtil.bytesToInt(Arrays.copyOfRange(subVar_bytes, 4, 8)));
		this.setBatteryState(SocketUtil.bytesToInt(Arrays.copyOfRange(subVar_bytes, 8, 12)));
		return this;
	}

	public enum State{
		/**
		 * 电池正常 0
		 */
		NORMAL(0,"电池正常"),
		
		/**
		 * 电池损坏1
		 */
		DAMAGED(1,"电池损坏"),
		INMAINTENANCE(2,"电池需要维护"),
		CHARGENOW(3,"电池需要立即充电"),
		CHARGEPRIORITY(4,"电池需要优先充电");
		private int value;
		private String description;
		private State(int value, String description) {
			this.value = value;
			this.description = description;
		}
		public int getValue() {
			return value;
		}
		public String getDescription() {
			return description;
		}
		
	}
}
