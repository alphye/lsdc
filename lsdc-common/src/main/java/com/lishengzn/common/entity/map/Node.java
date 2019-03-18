package com.lishengzn.common.entity.map;

import com.lishengzn.lsdc.entity.Coordinate;

public class Node extends MapObj{

	private Coordinate position;

	public Node(String id, Coordinate position) {
		super(id);
		this.position = position;
	}

	public Coordinate getPosition() {
		return position;
	}

	public void setPosition(Coordinate position) {
		this.position = position;
	}

	public Node clone(){
		Node newNode = new Node(getId(),position.clone());
		return newNode;
	}

}
