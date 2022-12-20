package com.digital4.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.digital4.schema.Inventory;
import com.digital4.sql.mapper.InventoryMapper;
import com.digital4.sql.vo.InventoryVO;



@Component
public class InventoryService {
	
	@Autowired
	InventoryMapper inventoryMapper;
	
	public Inventory inventorySearch(String productName) throws Exception {

		try {
			InventoryVO inVO = inventoryMapper.getInventory(productName);
			Inventory inventory = new Inventory();
			
			if (inVO != null) {
				inventory.setProductId(inVO.getProductId());
				inventory.setQuantity(inVO.getQuantity());
			}
			
			return inventory;
		}catch(Exception e) {
			throw e;
		}
	}
	
	public Inventory inventorySearchById(String productId) throws Exception {

		try {
			InventoryVO inVO = inventoryMapper.getInventoryById(Long.parseLong(productId));
			Inventory inventory = new Inventory();
			
			if (inVO != null) {
				inventory.setProductId(inVO.getProductId());
				inventory.setQuantity(inVO.getQuantity());
			}
			
			return inventory;
		}catch(Exception e) {
			throw e;
		}
	}
	
	public boolean updateQuantity(Inventory inventory) throws Exception {
		try{
			if (inventorySearchById("" + inventory.getProductId()).getProductId() == 0) { //상품이 등록될때 인벤토리도 생성됨. 인벤토리가 없다는 것은 상품도 없는 것으로 간주
				throw new Exception("상품의 인벤토리가 등록되지 않은 상태입니다.");
			}
			
			InventoryVO inVO = new InventoryVO(inventory.getProductId(), inventory.getQuantity());
			int updateResult = inventoryMapper.updateQuantity(inVO);
			if(updateResult == 1)
				return true;
			else
				return false;
		} catch(Exception e) {
			throw e;
		}
	}
	
	//구매시 수량 차감
	public boolean subtractQuantity(Inventory inventory, long purchaseQuantity) throws Exception {
		try{
			inventory = inventorySearchById("" + inventory.getProductId());
			if (inventory.getProductId() == 0) { //상품이 등록될때 인벤토리도 생성됨. 인벤토리가 없다는 것은 상품도 없는 것으로 간주
				throw new Exception("상품의 인벤토리가 등록되지 않은 상태입니다.");
			}
			
			if(inventory.getQuantity() - purchaseQuantity < 0) {
				throw new Exception("물량이 없습니다.");
			}
			
			InventoryVO inVO = new InventoryVO(inventory.getProductId(), inventory.getQuantity() - purchaseQuantity);
			int updateResult = inventoryMapper.updateQuantity(inVO);
			if(updateResult == 1)
				return true;
			else
				return false;
		} catch(Exception e) {
			throw e;
		}
	}

	//재고 등록
	public boolean insertInventory(long productId) throws Exception {
		try{
			if (inventorySearchById("" + productId).getProductId() != 0) { //상품이 등록될때 인벤토리도 생성됨. 인벤토리가 없다는 것은 상품도 없는 것으로 간주
				throw new Exception("상품의 인벤토리가 존재합니다.");
			}
			
			int insertResult = inventoryMapper.insertInventory(productId);
			if(insertResult == 1)
				return true;
			else
				return false;
		} catch(Exception e) {
			throw e;
		}
	}
	
}
