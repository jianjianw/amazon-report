package com.nhsoft.module.report.rpc.impl;

import com.nhsoft.module.azure.model.PosItemLat;
import com.nhsoft.module.report.rpc.PosItemRpc;
import com.nhsoft.module.report.service.PosItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class PosItemRpcImpl implements PosItemRpc {

    @Autowired
    private PosItemService posItemService;
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
}
