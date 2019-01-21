package com.lishengzn;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.Test;

import com.lishengzn.dto.VehicleDto;
import com.lishengzn.entity.Vehicle;
import com.lishengzn.entity.order.TransportOrder;
import com.lishengzn.entity.read.ReadItem_Request;
import com.lishengzn.entity.read.ReadItem_Response;
import com.lishengzn.entity.read.ReadVariable;
import com.lishengzn.packet.PacketModel;
import com.lishengzn.util.BeanConvertUtil;
import com.lishengzn.util.LSConstants;
import com.lishengzn.util.ReadMapUtil;
import com.lishengzn.util.SocketUtil;

public class MyTest {

	@Test
	public void test1(){
		System.out.println(Integer.toHexString(-2147483135));
	}

	@Test
	public void Test2() throws InstantiationException, IllegalAccessException{
		VehicleDto dto = new VehicleDto();
		Vehicle v = new Vehicle();
		v.setAngle(100.23);
		v.setVelocity(1000);
		v.setTransportOrder(new TransportOrder());
		BeanConvertUtil.beanConvert(v, dto);
		System.out.println(dto);
		VehicleDto dto2 = BeanConvertUtil.beanConvert(v, VehicleDto.class);
		System.out.println(dto2);
				
		
		Vehicle v2 = new Vehicle();
		v2.setAngle(300.23);
		v2.setVelocity(3000);
		v2.setTransportOrder(new TransportOrder());
		Vehicle v3 = new Vehicle();
		v3.setAngle(400.23);
		v3.setVelocity(4000);
		v3.setTransportOrder(new TransportOrder());
		List<Vehicle> vList = new ArrayList<Vehicle>();
		vList.add(v2);
		vList.add(v3);
		List<VehicleDto> dtoList = BeanConvertUtil.beanConvert(vList, VehicleDto.class);
		dtoList.forEach((d)->{
			System.out.println(d);
		});
	}
	@Test
	public void Test3() throws InstantiationException, IllegalAccessException{
		String str="01000000"
				+ "04010080500000000000000000000000000000000200000001000000010000000000000024000000000000000030a1400000000000c8994000000000000038403a0500008207000000000000030000000900000000000000080000000000000001000000";
		byte[] bs=SocketUtil.hexToBytes(str);
		PacketModel packetModel = SocketUtil.unPacketDataPacket(bs);
		System.out.println(packetModel);
		ReadVariable<ReadItem_Response> readVariable = new ReadVariable<ReadItem_Response>(ReadItem_Response.class).fromBytes(packetModel.getData_bytes());
		List<ReadItem_Response> items = readVariable.getItems();
		System.out.println(items.size()+"===="+readVariable.getItemNum());
		for(int i=0;i<items.size();i++){
			System.out.println(items.get(i));
		}
	}
	
	@Test
	public void Test4() throws InstantiationException, IllegalAccessException, UnsupportedEncodingException{
		PacketModel packetModel = new PacketModel();
		packetModel.setPacketType(SocketUtil.getResponsePacketType(LSConstants.PACKET_TYPE_HEART));
		packetModel.setData_bytes(new byte[0]);
		packetModel.setErrorCode(LSConstants.ERROR_CODE_SUCCESS);
		System.out.println(SocketUtil.bytesToHex(SocketUtil.packetMessage(packetModel)));
	}
	
	@Test
	public void test5() throws InstantiationException, IllegalAccessException{
//		System.out.println(Arrays.toString(SocketUtil.hexToBytes_8("10f")));
		System.out.println(SocketUtil.bytesToInt(SocketUtil.hexToBytes("84000000")));
		byte[] bs=SocketUtil.hexToBytes("010000000401000014000000000000000000000000000000010000000100000001000000e800000084000000");
		PacketModel packetModel = SocketUtil.unPacketDataPacket(bs);
		System.out.println(packetModel);
		ReadVariable<ReadItem_Request> readVariable = new ReadVariable<ReadItem_Request>(ReadItem_Request.class).fromBytes(packetModel.getData_bytes());
		List<ReadItem_Request> items = readVariable.getItems();
		System.out.println(items.size()+"===="+readVariable.getItemNum());
		for(int i=0;i<items.size();i++){
			System.out.println(items.get(i));
		}

	}
	@Test
	public void test6() throws InstantiationException, IllegalAccessException{
		byte[] bs=SocketUtil.hexToBytes("010000000401008098000000000000000000000000000000010000000100000001000000e8000000840000000100000001000000010000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000f83f00000000008035401f85eb51b81ef93f37144b0800000000000000000000f03f00000000f31c000000000000c0f9584000c83fc512184abf");
		PacketModel packetModel = SocketUtil.unPacketDataPacket(bs);
		System.out.println(packetModel);
//		ReadItem_Response[] vars =DataPacketUtil.unpackageReadItems(packetModel.getData_bytes(), ReadItem_Response.class);
		ReadVariable<ReadItem_Response> readVariable = new ReadVariable<ReadItem_Response>(ReadItem_Response.class).fromBytes(packetModel.getData_bytes());
		
		System.out.println(readVariable.getItemNum());
		System.out.println(readVariable.getItems().get(0));
		
	}
	
	@Test
	public void gXml() throws FileNotFoundException{
		File nodesFile = new File("nodes.txt");
		PrintWriter npw = new PrintWriter(new FileOutputStream(nodesFile));
		File edgssFile = new File("edges.txt");
		PrintWriter epw = new PrintWriter(new FileOutputStream(edgssFile));
		int minCoorXY=0;
		for(int j=0;j<20;j++){
			for(int i=0;i<20;i++){
				int id =Integer.valueOf(((j+1000)+""+(i+1000)));
				gNodes(id,minCoorXY+j*600.0,minCoorXY+i*600.0,npw);
				if(i>0){
					int ii =i-1;
					int jj =j;
					gEdges(id, ii, jj,epw);
					/*int startNodeId=Integer.valueOf(((jj+10000)+""+(ii+10000)));
					int edgeId=Integer.valueOf("800"+startNodeId);
//					String edgeStr="<edge id=\""+edgeId+"\" start_node_id=\""+startNodeId+"\" end_node_id=\""+id+"\" />";
					String edgeStr="<edge id=\""+edgeId+"\" start_node_id=\""+startNodeId+"\" end_node_id=\""+id+"\" distance=\"0.55\"> <move> <start_to_end_forward max_speed=\"0.5\"/>  <end_to_start_backward max_speed=\"0.5\"/> </move> </edge>";
					System.out.println(edgeStr);*/
				}
				
				if(j>0){
					int ii =i;
					int jj =j-1;
					gEdges(id, ii, jj,epw);
					/*int startNodeId=Integer.valueOf(((jj+10000)+""+(ii+10000)));
					int edgeId=Integer.valueOf("800"+startNodeId);
					String edgeStr="<edge id=\""+edgeId+"\" start_node_id=\""+startNodeId+"\" end_node_id=\""+id+"\" />";
					System.out.println(edgeStr);*/
				}
			}
		}
		npw.close();
		epw.close();
	}
	
	private void gNodes(int id,double x,double y,PrintWriter npw ){
		String nodesStr="<node id=\""+id+"\" x=\""+x+"\" y=\""+y+"\"/>";
		npw.println(nodesStr);
		npw.flush();
	}
	private void gEdges(int id,int ii,int jj,PrintWriter epw ){
		int startNodeId=Integer.valueOf(((jj+1000)+""+(ii+1000)));
		String prefix="4";
		if((jj+1000)!=Integer.valueOf((id+"").substring(0, 4))){
			// 如果startnode 与endnode的X坐标不相同
			prefix="8";
			
		}
		int edgeId=Integer.valueOf(prefix+startNodeId);
		String edgeStr="<edge id=\""+edgeId+"\" start_node_id=\""+startNodeId+"\" end_node_id=\""+id+"\" distance=\"0.55\"> <move> <start_to_end_forward max_speed=\"0.5\"/>  <end_to_start_backward max_speed=\"0.5\"/> </move> </edge>";
		epw.println(edgeStr);
		epw.flush();
	}
	
	
	@Test
	public void gMapJson() {
		ReadMapUtil.generateJSONMap();
	}
}
