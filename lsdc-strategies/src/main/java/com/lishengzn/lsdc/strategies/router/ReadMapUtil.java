package com.lishengzn.lsdc.strategies.router;

import com.lishengzn.common.entity.map.Edge;
import com.lishengzn.common.entity.map.Node;
import com.lishengzn.common.entity.response.map.AdvancedCurve;
import com.lishengzn.common.entity.response.map.AdvancedEdge;
import com.lishengzn.common.entity.response.map.AdvancedPoint;
import com.lishengzn.common.entity.response.map.Smap;
import com.lishengzn.lsdc.entity.Coordinate;
import com.lishengzn.lsdc.strategies.shortlink.DownloadVehicleMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@SuppressWarnings("unchecked")
public class ReadMapUtil {
	private static final Logger LOG = LoggerFactory.getLogger(ReadMapUtil.class);
	private static String configPath = ReadMapUtil.class.getClassLoader().getResource("config").getPath();
	private static final Map<Coordinate, String> nodesIDMap = new HashMap<>();

	private static final Map<String, Node> nodesMap = new HashMap<>();
	private static final Map<String, Edge> edgesMap = new HashMap<>();

	public volatile static boolean initialized=false;

	private static void readMapFromVehicle(){
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(new File(configPath,"config.properties"));
			ResourceBundle bundle=new PropertyResourceBundle(inputStream);
			String mainVehicleIp = bundle.getString("mainVehiclIp");
			Smap smap =DownloadVehicleMap.downloadMap(mainVehicleIp);
			generateNodesMap(smap);
			generateEdgesMap(smap);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public synchronized static void initialize() {
		if(!initialized){
			readMapFromVehicle();
			DefaultRouter.initializeRouter();
			initialized=true;
		}
	}

	/**
	 * 把点信息都存储到map里
	 *
	 * @param smap
	 */
	private static void generateEdgesMap(Smap smap) {
		int id=0;
		List<AdvancedEdge> advancedEdges =smap.getAdvancedLineList();
		List<AdvancedCurve> advancedCurves =smap.getAdvancedCurveList();
		if(advancedEdges!=null){
			for(AdvancedEdge advancedEdge:advancedEdges){
				Edge edge =advancedEdge.getLine();
				String startNodeId=getNodeId(edge.getStartPos());
				String endNodeId=getNodeId(edge.getEndPos());
				String edgeId="L-"+(id++)+"-"+startNodeId+"-"+endNodeId;
				Edge edge1=new Edge(edgeId,startNodeId,endNodeId);
				edge1.setStartPos(getNodeById(startNodeId).getPosition());
				edge1.setEndPos(getNodeById(endNodeId).getPosition());
				edgesMap.put(edgeId,edge1);
			}
		}
		if(advancedCurves!=null){
			id=0;
			for(AdvancedCurve advancedCurve:advancedCurves){
				AdvancedPoint startAdvancedPoint = advancedCurve.getStartPos();
				AdvancedPoint endAdvancedPoint = advancedCurve.getEndPos();
				String edgeId="C-"+(id++)+"-"+startAdvancedPoint.getInstanceName()+"-"+endAdvancedPoint.getInstanceName();
				Edge edge1=new Edge(edgeId,startAdvancedPoint.getInstanceName(),endAdvancedPoint.getInstanceName());
				edge1.setStartPos(getNodeById(startAdvancedPoint.getInstanceName()).getPosition());
				edge1.setEndPos(getNodeById(endAdvancedPoint.getInstanceName()).getPosition());
				edgesMap.put(edgeId,edge1);
			}
		}
	}

	/**
	 * 把地图上每个边的信息都存储到map里
	 * 
	 * @param smap
	 */
	private static void generateNodesMap(Smap smap) {
		List<AdvancedPoint> advancedPoints =smap.getAdvancedPointList();
		if(advancedPoints!=null){
			for(AdvancedPoint advancedPoint :advancedPoints){
				Node node = new Node(advancedPoint.getInstanceName(),advancedPoint.getPos());
				nodesMap.put(node.getId(), node);
				nodesIDMap.put(node.getPosition(), node.getId());
			}
		}
	}




	public static Set<String> getEdgeIds(){
		return edgesMap.keySet();
	}
	/**
	 * 根据边id，查找出边
	 * 
	 * @param id
	 */
	public static Edge getEdgeById(String id) {
		return edgesMap.get(id).clone();

	}

	/**
	 * 根据边id，查找出边的起点
	 * 
	 * @param id
	 */
	public static Node getStartNodeById(String id) {
		return getNodeById(edgesMap.get(id).getStartNodeId());

	}

	/**
	 * 根据边id，查找出边的终点
	 * 
	 * @param id
	 */
	public static Node getEndNodeById(String id) {
		return getNodeById(edgesMap.get(id).getEndNodeId());

	}

	public static String getNodeId(Coordinate coordinate){
		return nodesIDMap.get(coordinate);
	}
	public static Set<String> getNodeIds(){
		return nodesMap.keySet();
	}
	/**
	 * 根据点id，查找出点
	 * 
	 * @param id
	 */
	public static Node getNodeById(String id) {
		return nodesMap.get(id).clone();

	}


	private static boolean nodeEquals(Coordinate node1, Coordinate node2){
		double d =Math.abs(node1.getX()-node2.getX())+Math.abs(node1.getY()-node2.getY());
		return d<=10;
	}

}
