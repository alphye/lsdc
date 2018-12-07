package com.lishengzn.entity.read.content;

import com.lishengzn.util.SocketUtil;

import java.util.Arrays;

/**读取AGV充电状态
 * @author Administrator
 *
 */
public class ChargeStateContent extends VariableContent {
	/**
	 * 是否接触到了充电桩
	 */
	private int contactChargingPile;
	/**
	 * 充电继电器是否已经打开
	 */
	private int relayOn;

	public ChargeStateContent() {
	}

	public ChargeStateContent(int contactChargingPile, int relayOn) {
		super();
		this.contactChargingPile = contactChargingPile;
		this.relayOn = relayOn;
	}

	public int getContactChargingPile() {
		return contactChargingPile;
	}

	public void setContactChargingPile(int contactChargingPile) {
		this.contactChargingPile = contactChargingPile;
	}

	public int getRelayOn() {
		return relayOn;
	}

	public void setRelayOn(int relayOn) {
		this.relayOn = relayOn;
	}

	@Override
	public String toString() {
		return "ChargeStateContent [contactChargingPile=" + contactChargingPile + ", relayOn=" + relayOn + "]";
	}

	@Override
	public byte[] toBytes() {
		byte[] contactChargingPile_byte = SocketUtil.intToBytes(contactChargingPile);
		byte[] relayOn_byte = SocketUtil.intToBytes(relayOn);

		byte[] subVar_bytes = new byte[contactChargingPile_byte.length + relayOn_byte.length];
		System.arraycopy(contactChargingPile_byte, 0, subVar_bytes, 0, contactChargingPile_byte.length);
		System.arraycopy(relayOn_byte, 0, subVar_bytes, 4, relayOn_byte.length);

		return subVar_bytes;
	}

	@Override
	public ChargeStateContent fromBytes(byte[] subVar_bytes) {
		if (subVar_bytes.length != 8) {
			throw new RuntimeException("“充电状态”变量内容字节数须为8");
		}
		this.setContactChargingPile(SocketUtil.bytesToInt(Arrays.copyOfRange(subVar_bytes, 0, 4)));
		this.setRelayOn(SocketUtil.bytesToInt(Arrays.copyOfRange(subVar_bytes, 4, 8)));
		return this;
	}

}
