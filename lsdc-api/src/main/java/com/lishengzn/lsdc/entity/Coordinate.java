package com.lishengzn.lsdc.entity;

import java.io.Serializable;
import java.util.Objects;

public class Coordinate implements Serializable,Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private double x;
	private double y;
	
	
	public Coordinate(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Coordinate that = (Coordinate) o;
		return Double.compare(that.x, x) == 0 &&
				Double.compare(that.y, y) == 0;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	public Coordinate clone() {
		Coordinate coor1 = new Coordinate(this.x,this.y);
		return coor1;
       
    }

	@Override
	public String toString() {
		return "Coordinate{" +
				"x=" + x +
				", y=" + y +
				'}';
	}
}
