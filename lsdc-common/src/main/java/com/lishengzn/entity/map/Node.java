package com.lishengzn.entity.map;

import com.lishengzn.entity.Coordinate;

public class Node extends MapObj{

	private Coordinate position;

	public Node(int id, Coordinate position) {
		super(id);
		this.position = position;
	}

	public Coordinate getPosition() {
		return position;
	}

	public void setPosition(Coordinate position) {
		this.position = position;
	}
	

}
