package com.lishengzn.common.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lishengzn.common.entity.map.Node;
import com.lishengzn.lsdc.entity.Coordinate;
import com.lishengzn.common.entity.map.Edge;
import com.lishengzn.common.exception.SimpleException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

@SuppressWarnings("unchecked")
public class ReadMapUtil {
	private static final Logger LOG = LoggerFactory.getLogger(ReadMapUtil.class);
	private static String configPath = ReadMapUtil.class.getClassLoader().getResource("config").getPath();
	private static final Map<Coordinate, Integer> nodesIDMap = new HashMap<Coordinate, Integer>();
//	private static final Map<String, Integer> edgesIDMap = new HashMap<String, Integer>();

	private static final Map<Integer, Node> nodesMap = new HashMap<Integer, Node>();
	private static final Map<Integer, Edge> edgesMap = new HashMap<Integer, Edge>();
	private static double cellLength;
	
	
	public static int MAPDIRECTION_LEFT=1;
	public static int MAPDIRECTION_RIGHT=2;
	public static int MAPDIRECTION_TOP=3;
	public static int MAPDIRECTION_BOTTOM=4;
	static {
		SAXReader saxreader = new SAXReader();
		BufferedInputStream inputStream =null;
		try {
			Document document = saxreader.read(new File(configPath + "/map.xml"));
			Element root = document.getRootElement();
			Element nodes = root.element("nodes");
			List<Element> nodesList = nodes.elements();
			nodesList.forEach(ReadMapUtil::generateNodesMap);

			Element edges = root.element("edges");
			List<Element> edgesList = edges.elements();
			edgesList.forEach(ReadMapUtil::generateEdgesMap);
			
			inputStream = new BufferedInputStream(new FileInputStream(new File(configPath,"config.properties")));
			ResourceBundle bundle=new PropertyResourceBundle(inputStream);
			cellLength = Double.valueOf(bundle.getString("trackCellLength"));
		} catch (DocumentException | IOException e) {
			LOG.error("读取map信息异常！", e);
		} finally{
			if(inputStream!=null){
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void initialize() {
	}

	/**
	 * 把点信息都存储到map里
	 * 
	 * @param e
	 */
	private static void generateEdgesMap(Element e) {
		if (e.getName().equals("edge")) {
			Integer id = Integer.valueOf(e.attribute("id").getValue());
			Coordinate startNodeCoor = nodesMap.get(Integer.valueOf(e.attribute("start_node_id").getValue()))
					.getPosition();
			Coordinate endNodeCoor = nodesMap.get(Integer.valueOf(e.attribute("end_node_id").getValue())).getPosition();
			Edge edge = new Edge(id, Integer.valueOf(e.attribute("start_node_id").getValue()),
					Integer.valueOf(e.attribute("end_node_id").getValue()));
			edge.setStartNodeCoor(startNodeCoor);
			edge.setEndNodeCoor(endNodeCoor);
			edgesMap.put(id, edge);
			/*edgesIDMap.put(startNodeCoor.getX() + "," + startNodeCoor.getY() + ","
					+ endNodeCoor.getX() + "," + endNodeCoor.getY(), id);*/

		}
	}

	/**
	 * 把地图上每个边的信息都存储到map里
	 * 
	 * @param e
	 */
	private static void generateNodesMap(Element e) {
		if (e.getName().equals("node")) {
			Integer id = Integer.valueOf(e.attribute("id").getValue());
			Coordinate coor = new Coordinate(Double.valueOf(e.attribute("x").getValue()),
					Double.valueOf(e.attribute("y").getValue()));
			Node node = new Node(id, coor);
			nodesMap.put(id, node);
			nodesIDMap.put(coor, id);
		}
	}

	/**
	 * 校验坐标是否在点上
	 * 
	 * @return
	 */
	public static boolean checkCoorAtNode(Coordinate coor) {
		Set<Map.Entry<Integer,Node>> entrySet=nodesMap.entrySet();
		for(Map.Entry<Integer,Node> entry:entrySet){
			if(nodeEquals(entry.getValue().getPosition(), coor)){
				return true;
			}
		}
		return false;
	}

	public static Node getNearestNode(Coordinate coor,int direction) {
		if(direction==MAPDIRECTION_LEFT){
			return getNearestNode_left(coor);
		}
		else if(direction==MAPDIRECTION_RIGHT){
			return getNearestNode_right(coor);
		}
		else if(direction==MAPDIRECTION_TOP){
			return getNearestNode_top(coor);
		}
		else if(direction==MAPDIRECTION_BOTTOM){
			return getNearestNode_bottom(coor);
		}
		return null;
		
	}
	/**
	 * 向左获取一个离当前坐标最近的Node
	 * 
	 * @param coor
	 * @return
	 */
	private static Node getNearestNode_left(Coordinate coor) {
		double x = coor.getX();
		x = x - x % cellLength;
		coor.setX(x);
		Integer nodeId =nodesIDMap.get(coor);
		if(nodeId==null){
			return null;
		}
		Node node = new Node(nodeId, coor);
		return  node;

	}

	/**
	 * 向右获取一个离当前坐标最近的Node
	 * 
	 * @param coor
	 * @return
	 */
	private static Node getNearestNode_right(Coordinate coor) {
		double x = coor.getX();
		x = x - x % cellLength + cellLength;
		coor.setX(x);
		Integer nodeId =nodesIDMap.get(coor);
		if(nodeId==null){
			return null;
		}
		Node node = new Node(nodeId, coor);
		return  node;

	}

	/**
	 * 向上获取一个离当前坐标最近的Node
	 * 
	 * @param coor
	 * @return
	 */
	private static Node getNearestNode_top(Coordinate coor) {
		double y = coor.getY();
		y = y - y % cellLength + cellLength;
		coor.setY(y);
		Integer nodeId =nodesIDMap.get(coor);
		if(nodeId==null){
			return null;
		}
		Node node = new Node(nodeId, coor);
		return  node;
	}

	/**
	 * 向下获取一个离当前坐标最近的Node
	 * 
	 * @param coor
	 * @return
	 */
	private static Node getNearestNode_bottom(Coordinate coor) {
		double y = coor.getY();
		y = y - y % cellLength;
		coor.setY(y);
		Integer nodeId =nodesIDMap.get(coor);
		if(nodeId==null){
			return null;
		}
		Node node = new Node(nodeId, coor);
		return  node;

	}

	public static Set<Integer> getEdgeIds(){
		return edgesMap.keySet();
	}
	/**
	 * 根据边id，查找出边
	 * 
	 * @param id
	 */
	public static Edge getEdgeById(Integer id) {
		return edgesMap.get(id).clone();

	}

	/**
	 * 根据边id，查找出边的起点
	 * 
	 * @param id
	 */
	public static Node getStartNodeById(Integer id) {
		return getNodeById(edgesMap.get(id).getStartNodeId());

	}

	/**
	 * 根据边id，查找出边的终点
	 * 
	 * @param id
	 */
	public static Node getEndNodeById(Integer id) {
		return getNodeById(edgesMap.get(id).getEndNodeId());

	}

	public static Set<Integer> getNodeIds(){
		return nodesMap.keySet();
	}
	/**
	 * 根据点id，查找出点
	 * 
	 * @param id
	 */
	public static Node getNodeById(Integer id) {
		return nodesMap.get(id).clone();

	}

	/**
	 * 根据两个坐标查询出边ID
	 * 
	 * @param node1
	 * @param node2
	 * @return
	 */
	public static Integer getEdgeID(Coordinate node1, Coordinate node2) {
		Coordinate startNodeCoor = null;
		Coordinate endNodeCoor = null;
		// 坐标值较小的一个作为start，值大的作为end
		if (Math.pow(node1.getX(), 2) + Math.pow(node1.getY(), 2) < Math.pow(node2.getX(), 2)
				+ Math.pow(node2.getY(), 2)) {
			startNodeCoor = node1.clone();
			endNodeCoor = node2.clone();
		} else {
			startNodeCoor = node2.clone();
			endNodeCoor = node1.clone();
		}
		/*Integer id = edgesIDMap.get(startNodeCoor.getX() + "," + startNodeCoor.getY() + ","
				+ endNodeCoor.getX() + "," + endNodeCoor.getY());*/
		Integer id=null;
		Set<Map.Entry<Integer,Edge>> entrySet=edgesMap.entrySet();
		for(Map.Entry<Integer,Edge> entry:entrySet){
			Node startNode =nodesMap.get(entry.getValue().getStartNodeId());
			Node endNode =nodesMap.get(entry.getValue().getEndNodeId());
			if(nodeEquals(startNode.getPosition(), startNodeCoor) && nodeEquals(endNode.getPosition(), endNodeCoor)){
				id=entry.getValue().getId();
				break;
			}
		}
		if (null == id) {
			LOG.error("要查找的边不在地图范围内，startNode:{}  endNodeCoor:{}", startNodeCoor.toString(), endNodeCoor.toString());
			throw new SimpleException("目标位置不在地图范围内！");
		}
		return id;
	}
	private static boolean nodeEquals(Coordinate node1, Coordinate node2){
		double d =Math.abs(node1.getX()-node2.getX())+Math.abs(node1.getY()-node2.getY());
		return d<=10;
	}
	public static void generateJSONMap() {
		JSONObject json = new JSONObject();
		json.put("nodes", nodesMap.values());
		json.put("edges", edgesMap.values());
		BufferedOutputStream bos = null;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(new File("map.json")));
			String jsonString = "var mapJSON="+JSONObject.toJSONString(json, SerializerFeature.DisableCircularReferenceDetect);
			bos.write(jsonString.getBytes());
			bos.flush();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
