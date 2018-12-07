package com.lishengzn.entity.write;

import com.google.common.primitives.Bytes;
import com.lishengzn.entity.read.content.VariableContent;
import com.lishengzn.util.SocketUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**写入包裹大小
 * @author Administrator
 *
 */
public class PackageSize extends VariableContent {
	/**
	 * 包裹长度
	 */
	private double packageLength;
	/**
	 * 包裹宽度
	 */
	private double packageWidth;

	public PackageSize() {
	}


	public PackageSize(double packageLength, double packageWidth) {
		super();
		this.packageLength = packageLength;
		this.packageWidth = packageWidth;
	}



	public double getPackageLength() {
		return packageLength;
	}


	public void setPackageLength(double packageLength) {
		this.packageLength = packageLength;
	}


	public double getPackageWidth() {
		return packageWidth;
	}


	public void setPackageWidth(double packageWidth) {
		this.packageWidth = packageWidth;
	}


	@Override
	public byte[] toBytes() {
		List<Byte> byteList=new ArrayList<Byte>();
		byteList.addAll(Bytes.asList(SocketUtil.doubleToBytes(packageLength)));
		byteList.addAll(Bytes.asList(SocketUtil.doubleToBytes(packageWidth)));
		return Bytes.toArray(byteList);
	}

	@Override
	public PackageSize fromBytes(byte[] data_bytes) {
		if (data_bytes.length != 16) {
			throw new RuntimeException("“写入包裹大小”变量内容字节数须为16");
		}
		this.setPackageLength(SocketUtil.bytesToDouble(Arrays.copyOfRange(data_bytes, 0, 8)));
		this.setPackageWidth(SocketUtil.bytesToDouble(Arrays.copyOfRange(data_bytes, 8, 16)));
		return this;
	}

}
