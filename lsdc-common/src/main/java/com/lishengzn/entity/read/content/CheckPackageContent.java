package com.lishengzn.entity.read.content;

import com.lishengzn.util.SocketUtil;

import java.util.Arrays;

/**如果AGV本体能够检测包裹，需要提供读取AGV是否有包裹变量
 * @author Administrator
 *
 */
public class CheckPackageContent extends VariableContent {
	/**
	 * 是否有包裹
	 */
	private int hasPackage;

	public CheckPackageContent() {
	}

	public CheckPackageContent(int hasPackage) {
		super();
		this.hasPackage = hasPackage;
	}

	public int getHasPackage() {
		return hasPackage;
	}

	public void setHasPackage(int hasPackage) {
		this.hasPackage = hasPackage;
	}

	@Override
	public String toString() {
		return "CheckPackageContent [hasPackage=" + hasPackage + "]";
	}

	@Override
	public byte[] toBytes() {
		byte[] hasPackage_byte = SocketUtil.intToBytes(hasPackage);

		byte[] subVar_bytes = new byte[hasPackage_byte.length];
		System.arraycopy(hasPackage_byte, 0, subVar_bytes, 0, hasPackage_byte.length);

		return subVar_bytes;
	}

	@Override
	public CheckPackageContent fromBytes(byte[] subVar_bytes) {
		if (subVar_bytes.length != 4) {
			throw new RuntimeException("“检测包裹”变量内容字节数须为4");
		}
		this.setHasPackage(SocketUtil.bytesToInt(Arrays.copyOfRange(subVar_bytes, 0, 4)));
		return this;
	}

}
