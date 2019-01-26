package com.lishengzn.entity;

import java.io.Serializable;

public class Coordinate implements Serializable,Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private double position_x;
	private double position_y;
	
	
	public Coordinate(double position_x, double position_y) {
		super();
		this.position_x = position_x;
		this.position_y = position_y;
	}
	public double getPosition_x() {
		return position_x;
	}
	public void setPosition_x(double position_x) {
		this.position_x = position_x;
	}
	public double getPosition_y() {
		return position_y;
	}
	public void setPosition_y(double position_y) {
		this.position_y = position_y;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(position_x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(position_y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coordinate other = (Coordinate) obj;
		if (Double.doubleToLongBits(position_x) != Double.doubleToLongBits(other.position_x))
			return false;
		if (Double.doubleToLongBits(position_y) != Double.doubleToLongBits(other.position_y))
			return false;
		return true;
	}
	public Coordinate clone() {
		Coordinate coor1 = new Coordinate(this.position_x,this.position_y);
		return coor1;
       
    }
	@Override
	public String toString() {
		return "Coordinate [position_x=" + position_x + ", position_y=" + position_y + "]";
	}

	
}
