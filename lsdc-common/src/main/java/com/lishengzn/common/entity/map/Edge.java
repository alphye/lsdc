package com.lishengzn.common.entity.map;

import com.lishengzn.lsdc.entity.Coordinate;

public class Edge extends MapObj{

	private String startNodeId;
	private String endNodeId;
	private Coordinate startPos;
	private Coordinate endPos;
	public Edge(String id, String startNodeId, String endNodeId) {
		super(id);
		this.startNodeId = startNodeId;
		this.endNodeId = endNodeId;
	}
	
	
	public String getStartNodeId() {
		return startNodeId;
	}
	public void setStartNodeId(String startNodeId) {
		this.startNodeId = startNodeId;
	}
	public String getEndNodeId() {
		return endNodeId;
	}
	public void setEndNodeId(String endNodeId) {
		this.endNodeId = endNodeId;
	}

	public Coordinate getStartPos() {
		return startPos;
	}

	public void setStartPos(Coordinate startPos) {
		this.startPos = startPos;
	}

	public Coordinate getEndPos() {
		return endPos;
	}

	public void setEndPos(Coordinate endPos) {
		this.endPos = endPos;
	}

	public Edge clone(){
		Edge newEdge =new Edge(getId(),startNodeId,endNodeId);
		if(startPos!=null){
			newEdge.startPos=startPos.clone();
		}
		if(endPos!=null){
			newEdge.endPos=endPos.clone();
		}
		return newEdge;
	}

	

}
