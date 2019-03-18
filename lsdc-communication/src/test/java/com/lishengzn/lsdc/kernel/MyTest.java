package com.lishengzn.lsdc.kernel;

import com.alibaba.druid.util.StringUtils;
import com.lishengzn.common.util.ByteUtil;
import com.lishengzn.common.util.SocketUtil;
import org.junit.Test;

import java.io.*;
import java.util.*;

public class MyTest {
	private static String hexString = "0123456789ABCDEF";
	public static String encode(String str) throws UnsupportedEncodingException {
		// 根据默认编码获取字节数组
		byte[] bytes = str.getBytes("GBK");
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		// 将字节数组中每个字节拆解成2位16进制整数
		for (int i = 0; i < bytes.length; i++) {
			sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
			sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
		}
		return sb.toString();
	}

	@Test
	public void test1()  {
		System.out.println(ByteUtil.bytesToHex("LSPH".getBytes()));
		System.out.println(ByteUtil.bytesToHex(ByteUtil.short2Bytes((short)1001)));
		System.out.println(ByteUtil.bytesToHex(ByteUtil.intToBytes_s(4)));
	}

	@Test
	public void test2(){

		short seriaNo=1;
		Scanner scanner = new Scanner(System.in);
		while(scanner.hasNextLine()){
			String next = scanner.nextLine();
			if(!StringUtils.isEmpty(next)){
				/*String nextArr[] = next.split("::");
				short packetType = Short.parseShort(nextArr[0]);
				String msg = nextArr[1].replace("NULL","");
				PacketModel packetModel = new PacketModel(seriaNo,msg.getBytes().length,packetType,msg);
				System.out.println(ByteUtil.bytesToHex(packetModel.toBytes()));*/

				switch (next){
					case "1000":

				}
			}
		}
	}

	public static void main(String[] args){
//		ClientOfVehicle client = new ClientOfVehicle("192.168.0.129",19204,null,null,ClientOfVehicle.ClientType.statusAPi);
		Scanner scanner = new Scanner(System.in);
		/*APIStatusMessageSenderService service = client.getMessageSenderService();
		while(scanner.hasNextLine()){
			try {
				String next = scanner.nextLine();
				if(!StringUtils.isEmpty(next)){
					switch (next){
						case "1000":
							service.queryVehicleInfo();
							break;
						case "1002":
							service.queryRuningInfo();
							break;
						case "1004":
							service.queryLocation();
							break;
						case "1005":
							service.querySpeed();
							break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}*/
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
	
	
}
