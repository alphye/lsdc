package com.lishengzn.entity.read.content;

import com.lishengzn.util.SocketUtil;

import java.util.Arrays;

/**读取本体是否开启安全防护或者有故障，以及本体的速度和里程
 * @author Administrator
 *
 */
public class VelocityContent extends VariableContent {
	/**
	 * 是否处于雷达防撞
	 */
	private int radarCollisionAvoidance;
	/**
	 * 是否出现故障
	 */
	private int malfunction;
	/**
	 * x方向速度
	 */
	private double vx;
	/**
	 * y方向速度
	 */
	private double vy;
	/**
	 * 角速度
	 */
	private double angularVelocity;
	/**
	 * 总里程数
	 */
	private long mileage;

	
	public VelocityContent() {
	}


	/**
	 * @param radarCollisionAvoidance
	 * @param malfunction
	 * @param vx
	 * @param vy
	 * @param angularVelocity
	 * @param mileage
	 */
	public VelocityContent(int radarCollisionAvoidance, int malfunction, double vx, double vy, double angularVelocity,
			long mileage) {
		super();
		this.radarCollisionAvoidance = radarCollisionAvoidance;
		this.malfunction = malfunction;
		this.vx = vx;
		this.vy = vy;
		this.angularVelocity = angularVelocity;
		this.mileage = mileage;
	}


	
	public int getRadarCollisionAvoidance() {
		return radarCollisionAvoidance;
	}


	public void setRadarCollisionAvoidance(int radarCollisionAvoidance) {
		this.radarCollisionAvoidance = radarCollisionAvoidance;
	}


	public int getMalfunction() {
		return malfunction;
	}


	public void setMalfunction(int malfunction) {
		this.malfunction = malfunction;
	}


	public double getVx() {
		return vx;
	}


	public void setVx(double vx) {
		this.vx = vx;
	}


	public double getVy() {
		return vy;
	}


	public void setVy(double vy) {
		this.vy = vy;
	}


	public double getAngularVelocity() {
		return angularVelocity;
	}


	public void setAngularVelocity(double angularVelocity) {
		this.angularVelocity = angularVelocity;
	}


	public long getMileage() {
		return mileage;
	}


	public void setMileage(long mileage) {
		this.mileage = mileage;
	}


	@Override
	public String toString() {
		return "VelocityContent [radarCollisionAvoidance=" + radarCollisionAvoidance + ", malfunction=" + malfunction
				+ ", vx=" + vx + ", vy=" + vy + ", angularVelocity=" + angularVelocity + ", mileage=" + mileage + "]";
	}


	@Override
	public byte[] toBytes() {
		byte[] radarCollisionAvoidance_byte = SocketUtil.intToBytes(radarCollisionAvoidance);
		byte[] malfunction_byte = SocketUtil.intToBytes(malfunction);
		byte[] vx_byte = SocketUtil.doubleToBytes(vx);
		byte[] vy_byte = SocketUtil.doubleToBytes(vy);
		byte[] angularVelocity_byte = SocketUtil.doubleToBytes(angularVelocity);
		byte[] mileage_byte = SocketUtil.longToBytes(mileage);
		
		byte[] subVar_bytes = new byte[radarCollisionAvoidance_byte.length+malfunction_byte.length+vx_byte.length+vy_byte.length+angularVelocity_byte.length+mileage_byte.length];
		System.arraycopy(radarCollisionAvoidance_byte, 0, subVar_bytes, 0, radarCollisionAvoidance_byte.length);
		System.arraycopy(malfunction_byte, 0, subVar_bytes, 4, malfunction_byte.length);
		System.arraycopy(vx_byte, 0, subVar_bytes, 8, vx_byte.length);
		System.arraycopy(vy_byte, 0, subVar_bytes, 16, vy_byte.length);
		System.arraycopy(angularVelocity_byte, 0, subVar_bytes, 24, angularVelocity_byte.length);
		System.arraycopy(mileage_byte, 0, subVar_bytes, 32, mileage_byte.length);

		return subVar_bytes;
	}

	@Override
	public VelocityContent fromBytes(byte[] subVar_bytes) {
		if(subVar_bytes.length!=40){
			throw new RuntimeException("“是否开启安全防护或者有故障，以及本体的速度和里程”变量内容字节数须为40");
		}
		this.setRadarCollisionAvoidance(SocketUtil.bytesToInt(Arrays.copyOfRange(subVar_bytes, 0, 4)));
		this.setMalfunction(SocketUtil.bytesToInt(Arrays.copyOfRange(subVar_bytes, 4, 8)));
		this.setVx(SocketUtil.bytesToDouble(Arrays.copyOfRange(subVar_bytes, 8, 16)));
		this.setVy(SocketUtil.bytesToDouble(Arrays.copyOfRange(subVar_bytes, 16, 24)));
		this.setAngularVelocity(SocketUtil.bytesToDouble(Arrays.copyOfRange(subVar_bytes, 24, 32)));
		this.setMileage(SocketUtil.bytesToLong(Arrays.copyOfRange(subVar_bytes, 32, subVar_bytes.length)));
		return this;
	}

}
