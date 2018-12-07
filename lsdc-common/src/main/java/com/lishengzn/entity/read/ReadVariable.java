package com.lishengzn.entity.read;

import com.google.common.primitives.Bytes;
import com.lishengzn.entity.DataArea;
import com.lishengzn.util.SocketUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReadVariable<E extends ReadItem> implements DataArea{

	private int itemNum;
	private List<E> items;
	private Class<E> clazz;
	
	public ReadVariable(Class<E> clazz){
		this.clazz=clazz;
	}
	public ReadVariable(int itemNum, List<E> items,Class<E> clazz) {
		super();
		this.itemNum = itemNum;
		this.items = items;
		this.clazz=clazz;
	}

	public int getItemNum() {
		return itemNum;
	}

	public void setItemNum(int itemNum) {
		this.itemNum = itemNum;
	}

	public List<E> getItems() {
		return items;
	}

	public void setItems(List<E> items) {
		this.items = items;
	}

	@Override
	public byte[] toBytes() {
		List<Byte> byteList=new ArrayList<Byte>();
		byteList.addAll(Bytes.asList(SocketUtil.intToBytes(itemNum)));
		items.forEach((item)->{
			if(item !=null){
				byteList.addAll(Bytes.asList(item.toBytes()));
			}
		});
		return Bytes.toArray(byteList);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ReadVariable<E> fromBytes(byte[] bytes) {
		if(bytes.length<20){
			throw new RuntimeException("读取变量的数据长度不得小于20！");
		}
		this.setItemNum(SocketUtil.bytesToInt(Arrays.copyOf(bytes, 4)));
		// 从4开始，后边内容都是items
		int itemStart_index=4;
		int item_len=16;// 一个item长度默认为16
		List<E> list = new ArrayList<E>();

		for(int i=0;i<itemNum;i++){
			// 对于每个item，第12-15位，存储的是变量的长度，因为变量的长度是不固定的，所以它的值会影响整个item的长度。
			int length_byte_start=itemStart_index+12;
			int var_length=SocketUtil.bytesToInt(Arrays.copyOfRange(bytes, length_byte_start, length_byte_start+4));// 取得变量长度
			E readItem=null;
			try {
				readItem = clazz.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				throw new RuntimeException(e);
			}
			// 取出一个item,如果此时解析的是读取变量的包（ReadItem_Request），则item长度就是16，如果是读取变量的应答包（ReadItem_Response），item的长度需在16的基本上加上变量内容的长度，
			byte[] item_bytes=Arrays.copyOfRange(bytes, itemStart_index, (itemStart_index+=item_len+(readItem instanceof ReadItem_Request?0:var_length)));
			list.add((E) readItem.fromBytes(item_bytes));
		}
		this.setItems(list);
		return this;
	}

}
