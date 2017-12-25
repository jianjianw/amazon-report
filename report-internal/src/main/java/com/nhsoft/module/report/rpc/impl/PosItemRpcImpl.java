package com.nhsoft.module.report.rpc.impl;

import com.nhsoft.module.azure.model.PosItemLat;
import com.nhsoft.module.report.dto.PosItemDTO;
import com.nhsoft.module.report.param.PosItemTypeParam;
import com.nhsoft.module.report.rpc.PosItemRpc;
import com.nhsoft.module.report.service.BookResourceService;
import com.nhsoft.module.report.service.PosItemService;
import com.nhsoft.module.report.util.CopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class PosItemRpcImpl implements PosItemRpc {

    @Autowired
    private PosItemService posItemService;
    @Autowired
    private BookResourceService bookResourceService;
    @Override
    public List<PosItemLat> findItemLat(String systemBookCode) {
        List<Object[]> objects = posItemService.findItemLat(systemBookCode);
        List<PosItemLat> list = new ArrayList<>();
        if(objects.isEmpty()){
            return list;
        }
        for (int i = 0; i <objects.size() ; i++) {
            Object[] object = objects.get(i);
            PosItemLat PosItemLat  = new PosItemLat();
            PosItemLat.setSystemBookCode(systemBookCode);
            PosItemLat.setItemNum((Integer) object[0]);
            PosItemLat.setItem_category((String) object[1]);
            list.add(PosItemLat);
        }
        return list;
    }


    @Override
    public List<PosItemDTO> findAll(String systemBookCode) {

        List<PosItemTypeParam> posItemTypeParams = bookResourceService.findPosItemTypeParamsInCache(systemBookCode);

        List<PosItemDTO> posItemDTOS = CopyUtil.toList(posItemService.findAll(systemBookCode), PosItemDTO.class);
        for (int i = 0; i <posItemDTOS.size() ; i++) {
            PosItemDTO posItemDTO = posItemDTOS.get(i);
            PosItemTypeParam topCategory = PosItemTypeParam.getTopCategory(posItemTypeParams, posItemDTO.getItemCategoryCode());
            if(topCategory != null ){
                posItemDTO.setItemCategoryCode(topCategory.getPosItemTypeName());
            }

        }
        return posItemDTOS;
    }
}
