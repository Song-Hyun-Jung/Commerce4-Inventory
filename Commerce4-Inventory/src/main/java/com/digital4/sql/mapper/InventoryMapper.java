package com.digital4.sql.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.digital4.sql.vo.InventoryVO;

@Mapper
public interface InventoryMapper {
	public int insertInventory(long productId);
	public InventoryVO getInventory(String productName);
	public InventoryVO getInventoryById(long productId);
	public int updateQuantity(InventoryVO inVO);
}
