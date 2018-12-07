package com.lishengzn.entity.map;

import com.lishengzn.entity.Coordinate;

public class Edge extends MapObj{

	private Integer startNodeId;
	private Integer endNodeId;
	private Coordinate startNodeCoor;
	private Coordinate endNodeCoor;
	public Edge(int id, Integer startNodeId, Integer endNodeId) {
		super(id);
		this.startNodeId = startNodeId;
		this.endNodeId = endNodeId;
	}
	
	
	public Integer getStartNodeId() {
		return startNodeId;
	}
	public void setStartNodeId(Integer startNodeId) {
		this.startNodeId = startNodeId;
	}
	public Integer getEndNodeId() {
		return endNodeId;
	}
	public void setEndNodeId(Integer endNodeId) {
		this.endNodeId = endNodeId;
	}


	public Coordinate getStartNodeCoor() {
		return startNodeCoor;
	}


	public void setStartNodeCoor(Coordinate startNodeCoor) {
		this.startNodeCoor = startNodeCoor;
	}


	public Coordinate getEndNodeCoor() {
		return endNodeCoor;
	}


	public void setEndNodeCoor(Coordinate endNodeCoor) {
		this.endNodeCoor = endNodeCoor;
	}



	

}
