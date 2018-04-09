package com.nhsoft.module.report.rpc.impl;

import com.nhsoft.module.report.dto.InventoryDTO;
import com.nhsoft.module.report.dto.ItemInventoryDTO;
import com.nhsoft.module.report.rpc.InventoryRpc;
import com.nhsoft.module.report.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Component
public class InventoryRpcImpl implements InventoryRpc {

    @Autowired
    private InventoryService inventoryService;

    @Override
    public List<InventoryDTO> findCenterStore(String systemBookCode, Integer branchNum, List<Integer> itemNums) {
        List<Object[]> objects = inventoryService.findCenterStore(systemBookCode, branchNum, itemNums);
        int size = objects.size();
        List<InventoryDTO> list = new ArrayList<>(size);
        for (int i = 0; i <size ; i++) {
            Object[] object = objects.get(i);
            InventoryDTO inventoryDTO = new InventoryDTO();
            inventoryDTO.setItemNum((Integer) object[0]);
            inventoryDTO.setInventoryAmount((BigDecimal) object[1]);
            inventoryDTO.setInventoryMoney((BigDecimal) object[2]);
            inventoryDTO.setInventoryAssistAmount((BigDecimal) object[3]);
            list.add(inventoryDTO);
        }
        return list;
    }

    @Override
    public List<ItemInventoryDTO> findItemAmount(String systemBookCode, List<Integer> branchNums, List<Integer> itemNums, Integer storehouseNum) {

        List<Object[]> objects = inventoryService.findItemAmount(systemBookCode, branchNums, itemNums, storehouseNum);
        int size = objects.size();
        List<ItemInventoryDTO> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            Object[] object = objects.get(i);
            ItemInventoryDTO dto = new ItemInventoryDTO();
            dto.setItemNum((Integer) object[0]);
            dto.setAmount((BigDecimal) object[1]);
            dto.setMoney((BigDecimal) object[2]);
            list.add(dto);
        }
        return list;
    }

}
