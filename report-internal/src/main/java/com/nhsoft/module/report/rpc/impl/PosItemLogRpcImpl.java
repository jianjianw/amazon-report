package com.nhsoft.module.report.rpc.impl;

import com.nhsoft.module.report.dto.PosItemLogDTO;
import com.nhsoft.module.report.dto.PosItemLogSummaryDTO;
import com.nhsoft.module.report.model.PosItemLog;
import com.nhsoft.module.report.query.StoreQueryCondition;
import com.nhsoft.module.report.rpc.PosItemLogRpc;
import com.nhsoft.module.report.service.PosItemLogService;
import com.nhsoft.report.utils.CopyUtil;
import jdk.nashorn.internal.runtime.linker.LinkerCallSite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by yangqin on 2017/11/2.
 */
@Component
public class PosItemLogRpcImpl implements PosItemLogRpc {


    @Autowired
    private PosItemLogService posItemLogService;


    @Override
    public List<PosItemLogSummaryDTO> findBranchItemFlagSummary(StoreQueryCondition storeQueryCondition) {
        if(storeQueryCondition.getDateStart().after(storeQueryCondition.getDateEnd())){
            return Collections.emptyList();
        }
        List<Object[]> objects = posItemLogService.findBranchItemFlagSummary(storeQueryCondition);
        int size = objects.size();
        List<PosItemLogSummaryDTO> list = new ArrayList<PosItemLogSummaryDTO>(size);
        Object[] object;
        boolean querySaleMoney = storeQueryCondition.getQuerySaleMoney() == null?false:storeQueryCondition.getQuerySaleMoney();
         for (int i = 0; i < size; i++) {
            object = objects.get(i);

            PosItemLogSummaryDTO dto = new PosItemLogSummaryDTO();
            dto.setBranchNum((Integer) object[0]);
            dto.setItemNum((Integer) object[1]);
            dto.setInoutFlag((Boolean) object[2]);
            dto.setQty(object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3]);
            dto.setMoney(object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4]);
            dto.setAssistQty(object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5]);
            dto.setUseQty(object[6] == null ? BigDecimal.ZERO : (BigDecimal) object[6]);
            dto.setUseUnit(object[7] == null ? "" : (String) object[7]);
            dto.setAdjustMoney(object[8] == null ? BigDecimal.ZERO : (BigDecimal) object[8]);
            if(querySaleMoney) {
                dto.setSaleMoney(object[9] == null ? BigDecimal.ZERO : (BigDecimal) object[9]);

            }
            list.add(dto);
        }

        return list;
    }

    @Override
    public List<PosItemLogSummaryDTO> findItemBizTypeFlagSummary(StoreQueryCondition storeQueryCondition) {

        if(storeQueryCondition.getDateEnd() == null){
            Calendar calendar = Calendar.getInstance();
            storeQueryCondition.setDateEnd(calendar.getTime());
        }
        if(storeQueryCondition.getDateStart().after(storeQueryCondition.getDateEnd())){
            return Collections.emptyList();
        }
        List<Object[]> objects = posItemLogService.findItemBizTypeFlagSummary(storeQueryCondition);
        int size = objects.size();
        List<PosItemLogSummaryDTO> list = new ArrayList<PosItemLogSummaryDTO>(size);
        Object[] object = null;
        for (int i = 0; i < size; i++) {
            object = objects.get(i);
            PosItemLogSummaryDTO dto = new PosItemLogSummaryDTO();
            dto.setItemNum((Integer) object[0]);
            dto.setBizday((String) object[1]);
            dto.setSummary((String) object[2]);
            dto.setInoutFlag((Boolean) object[3]);
            dto.setQty(object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4]);
            dto.setMoney(object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5]);
            dto.setAssistQty(object[6] == null ? BigDecimal.ZERO : (BigDecimal) object[6]);
            list.add(dto);
        }

        return list;
    }

    @Override
    public List<PosItemLogSummaryDTO> findItemMatrixFlagSummary(StoreQueryCondition storeQueryCondition) {
        if(storeQueryCondition.getDateStart().after(storeQueryCondition.getDateEnd())){
            return Collections.emptyList();
        }
        List<Object[]> objects = posItemLogService.findSumByItemFlag(storeQueryCondition.getSystemBookCode(), storeQueryCondition.getBranchNums(),
                storeQueryCondition.getDateStart(), storeQueryCondition.getDateEnd(), storeQueryCondition.getPosItemLogSummary(), storeQueryCondition.getItemNums(),
                storeQueryCondition.getStorehouseNum(), storeQueryCondition.getMemos());
        int size = objects.size();
        List<PosItemLogSummaryDTO> list = new ArrayList<PosItemLogSummaryDTO>(size);
        Object[] object = null;
        for (int i = 0; i < size; i++) {
            object = objects.get(i);

            PosItemLogSummaryDTO dto = new PosItemLogSummaryDTO();
            dto.setItemNum((Integer) object[0]);
            dto.setItemMatrixNum((Integer) object[1]);
            dto.setInoutFlag((Boolean) object[2]);
            dto.setQty(object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3]);
            dto.setMoney(object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4]);
            dto.setAssistQty(object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5]);
            dto.setUseQty(object[6] == null ? BigDecimal.ZERO : (BigDecimal) object[6]);
            dto.setSaleMoney(object[7] == null ? BigDecimal.ZERO : (BigDecimal) object[7]);
            dto.setUseUnit(object[8] == null ? "" : (String) object[8]);
            list.add(dto);
        }

        return list;
    }

    @Override
    public List<PosItemLogSummaryDTO> findItemFlagSummary(StoreQueryCondition storeQueryCondition) {
        if(storeQueryCondition.getDateStart().after(storeQueryCondition.getDateEnd())){
            return Collections.emptyList();
        }
        List<Object[]> objects = posItemLogService.findItemFlagSummary(storeQueryCondition);
        int size = objects.size();
        List<PosItemLogSummaryDTO> list = new ArrayList<PosItemLogSummaryDTO>(size);
        Object[] object = null;
        for (int i = 0; i < size; i++) {
            object = objects.get(i);
            PosItemLogSummaryDTO dto = new PosItemLogSummaryDTO();
            dto.setItemNum((Integer) object[0]);
            dto.setInoutFlag((Boolean) object[1]);
            dto.setQty(object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2]);
            dto.setMoney(object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3]);
            dto.setAssistQty(object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4]);
            list.add(dto);
        }

        return list;
    }

    @Override
    public List<PosItemLogSummaryDTO> findBranchFlagSummary(StoreQueryCondition storeQueryCondition) {
        if(storeQueryCondition.getDateStart().after(storeQueryCondition.getDateEnd())){
            return Collections.emptyList();
        }
        List<Object[]> objects = posItemLogService.findBranchFlagSummary(storeQueryCondition);
        int size = objects.size();
        List<PosItemLogSummaryDTO> list = new ArrayList<PosItemLogSummaryDTO>(size);
        Object[] object;
        PosItemLogSummaryDTO dto;
        boolean querySaleMoney = storeQueryCondition.getQuerySaleMoney() == null?false:storeQueryCondition.getQuerySaleMoney();
        for (int i = 0; i < size; i++) {
            object = objects.get(i);

            dto = new PosItemLogSummaryDTO();
            dto.setBranchNum((Integer) object[0]);
            dto.setInoutFlag((Boolean) object[1]);
            dto.setQty(object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2]);
            dto.setMoney(object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3]);
            dto.setAssistQty(object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4]);
            if(querySaleMoney){
                dto.setSaleMoney(object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5]);
            }
            list.add(dto);
        }

        return list;
    }

    @Override
    public List<PosItemLogSummaryDTO> findItemBizFlagSummary(StoreQueryCondition storeQueryCondition) {
        if(storeQueryCondition.getDateStart().after(storeQueryCondition.getDateEnd())){
            return Collections.emptyList();
        }
        List<Object[]> objects = posItemLogService.findItemBizFlagSummary(storeQueryCondition);
        int size = objects.size();
        List<PosItemLogSummaryDTO> list = new ArrayList<PosItemLogSummaryDTO>(size);
        for (int i = 0; i < size; i++) {
            Object[] object = objects.get(i);
            PosItemLogSummaryDTO dto = new PosItemLogSummaryDTO();
            dto.setItemNum((Integer) object[0]);
            dto.setInoutFlag((Boolean) object[1]);
            dto.setBizday((String) object[2]);
            dto.setQty((BigDecimal) object[3]);
            list.add(dto);
        }
        return list;
    }


    //以下都是从amazonCenter中移过来的
    @Override
    public List<PosItemLogSummaryDTO> findItemOutAmountSummary(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, List<Integer> itemNums) {
        if(dateFrom.after(dateTo)){
            return Collections.emptyList();
        }
        List<Object[]> objects = posItemLogService.findItemOutAmountSummary(systemBookCode, branchNum, dateFrom, dateTo, itemNums);
        int size = objects.size();
        List<PosItemLogSummaryDTO> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            Object[] object = objects.get(i);
            PosItemLogSummaryDTO dto = new PosItemLogSummaryDTO();
            dto.setItemNum((Integer) object[0]);
            dto.setItemMatrixNum((Integer) object[1]);
            dto.setQty((BigDecimal) object[2]);
            list.add(dto);
        }
        return list;
    }

    @Override
    public List<PosItemLogSummaryDTO> findItemOutDateSummary(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, List<Integer> itemNums) {
        if(dateFrom.after(dateTo)){
            return Collections.emptyList();
        }
        List<Object[]> objects = posItemLogService.findItemOutDateSummary(systemBookCode, branchNum, dateFrom, dateTo, itemNums);
        int size = objects.size();
        List<PosItemLogSummaryDTO> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            Object[] object = objects.get(i);
            PosItemLogSummaryDTO dto = new PosItemLogSummaryDTO();
            dto.setItemNum((Integer) object[0]);
            dto.setDate((Date) object[1]);
            list.add(dto);
        }
        return list;
    }

    @Override
    public List<Integer> findNoticeItemSummary(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, String posItemLogType) {
        if(dateFrom.after(dateTo)){
            return Collections.emptyList();
        }
        return posItemLogService.findNoticeItemSummary(systemBookCode, branchNum, dateFrom, dateTo, posItemLogType);
    }

    @Override
    public List<PosItemLogSummaryDTO> findItemInOutQtyAndMoneySummary(StoreQueryCondition storeQueryCondition) {
        if(storeQueryCondition.getDateStart().after(storeQueryCondition.getDateEnd())){
            return Collections.emptyList();
        }
        List<Object[]> objects = posItemLogService.findItemInOutQtyAndMoneySummary(storeQueryCondition);
        int size = objects.size();
        List<PosItemLogSummaryDTO> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            Object[] object = objects.get(i);
            PosItemLogSummaryDTO dto = new PosItemLogSummaryDTO();
            dto.setItemNum((Integer) object[0]);
            dto.setInoutFlag((Boolean) object[1]);
            dto.setQty((BigDecimal) object[2]);
            dto.setMoney((BigDecimal) object[3]);
            dto.setAssistQty((BigDecimal) object[4]);
            dto.setAdjustMoney((BigDecimal) object[5]);
            list.add(dto);
        }
        return list;
    }

    @Override
    public List<PosItemLogSummaryDTO> sumByStoreQueryCondition(StoreQueryCondition storeQueryCondition) {
        if(storeQueryCondition.getDateStart().after(storeQueryCondition.getDateEnd())){
            return Collections.emptyList();
        }
        List<Object[]> objects = posItemLogService.sumByStoreQueryCondition(storeQueryCondition);
        int size = objects.size();
        List<PosItemLogSummaryDTO> list = new ArrayList<>(size);
        for (int i = 0; i <size ; i++) {
            Object[] object = objects.get(i);
            PosItemLogSummaryDTO dto = new PosItemLogSummaryDTO();
            dto.setInoutFlag((Boolean) object[0]);
            dto.setQty((BigDecimal) object[1]);
            dto.setMoney((BigDecimal) object[2]);
            dto.setAssistQty((BigDecimal) object[3]);
            dto.setItemCount((Integer) object[4]);
            dto.setAdjustMoney((BigDecimal) object[5]);
            list.add(dto);
        }
        return list;
    }

    @Override
    public List<PosItemLogDTO> findLastSummary(String systemBookCode, Integer branchNum, Integer storehouseNum, Date endDate) {
        return CopyUtil.toList(posItemLogService.findLastSummary(systemBookCode, branchNum, storehouseNum, endDate), PosItemLogDTO.class);
    }

    @Override
    public List<PosItemLogSummaryDTO> findItemSummaryInOutQtyAndMoney(StoreQueryCondition storeQueryCondition) {
        if(storeQueryCondition.getDateStart().after(storeQueryCondition.getDateEnd())){
            return Collections.emptyList();
        }
        List<Object[]> objects = posItemLogService.findItemSummaryInOutQtyAndMoney(storeQueryCondition);
        int size = objects.size();

        List<PosItemLogSummaryDTO> list = new ArrayList<>(size);
        for (int i = 0; i <size ; i++) {
            Object[] object = objects.get(i);
            PosItemLogSummaryDTO dto = new PosItemLogSummaryDTO();
            dto.setItemNum((Integer) object[0]);
            dto.setInoutFlag((boolean) object[1]);
            dto.setSummary((String) object[2]);
            dto.setMemo((String) object[3]);
            dto.setQty((BigDecimal) object[4]);
            dto.setMoney((BigDecimal) object[5]);
            dto.setAssistQty((BigDecimal) object[6]);
            dto.setAdjustMoney((BigDecimal) object[7]);
            list.add(dto);
        }
        return list;
    }

    @Override
    public List<PosItemLogSummaryDTO> findBranchSummaryInOutQtyAndMoney(StoreQueryCondition storeQueryCondition) {
        if(storeQueryCondition.getDateStart().after(storeQueryCondition.getDateEnd())){
            return Collections.emptyList();
        }
        List<Object[]> objects = posItemLogService.findBranchSummaryInOutQtyAndMoney(storeQueryCondition);
        int size = objects.size();
        List<PosItemLogSummaryDTO> list = new ArrayList<>(size);
        for (int i = 0; i <size ; i++) {
            Object[] object = objects.get(i);
            PosItemLogSummaryDTO dto = new PosItemLogSummaryDTO();
            dto.setBranchNum((Integer) object[0]);
            dto.setInoutFlag((Boolean) object[1]);
            dto.setSummary((String) object[2]);
            dto.setMemo((String) object[3]);
            dto.setQty((BigDecimal) object[4]);
            dto.setMoney((BigDecimal) object[5]);
            dto.setAssistQty((BigDecimal) object[6]);
            dto.setAdjustMoney((BigDecimal) object[7]);
            list.add(dto);
        }
        return list;
    }

    @Override
    public List<PosItemLogSummaryDTO> findBranchInOutSummary(StoreQueryCondition storeQueryCondition) {
        if(storeQueryCondition.getDateStart().after(storeQueryCondition.getDateEnd())){
            return Collections.emptyList();
        }
        List<Object[]> objects = posItemLogService.findBranchInOutSummary(storeQueryCondition);
        int size = objects.size();
        List<PosItemLogSummaryDTO> list = new ArrayList<>(size);
        for (int i = 0; i <size ; i++) {
            Object[] object = objects.get(i);
            PosItemLogSummaryDTO dto = new PosItemLogSummaryDTO();
            dto.setBranchNum((Integer) object[0]);
            dto.setInoutFlag((Boolean) object[1]);
            dto.setQty((BigDecimal) object[2]);
            dto.setMoney((BigDecimal) object[3]);
            dto.setAssistQty((BigDecimal) object[4]);
            dto.setAdjustMoney((BigDecimal) object[5]);
            list.add(dto);
        }
        return list;
    }

    @Override
    public List<PosItemLogSummaryDTO> findItemAmountBySummary(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, String summaries) {
        if(dateFrom.after(dateTo)){
            return Collections.emptyList();
        }

        List<Object[]> objects = posItemLogService.findItemAmountBySummary(systemBookCode, branchNum, dateFrom, dateTo, summaries);
        int size = objects.size();
        List<PosItemLogSummaryDTO> list = new ArrayList<>(size);
        for (int i = 0; i <size ; i++) {
            Object[] object = objects.get(i);
            PosItemLogSummaryDTO dto = new PosItemLogSummaryDTO();
            dto.setItemNum((Integer) object[0]);
            dto.setItemMatrixNum((Integer) object[1]);
            dto.setQty((BigDecimal) object[2]);
            list.add(dto);
        }
        return list;
    }

    @Override
    public List<PosItemLogSummaryDTO> findBranchAndItemFlagSummary(StoreQueryCondition storeQueryCondition) {
        if(storeQueryCondition.getDateStart().after(storeQueryCondition.getDateEnd())){
            return Collections.emptyList();
        }
        List<Object[]> objects = posItemLogService.findBranchAndItemFlagSummary(storeQueryCondition);
        int size = objects.size();
        List<PosItemLogSummaryDTO> list = new ArrayList<>(size);
        for (int i = 0; i <size ; i++) {
            Object[] object = objects.get(i);
            PosItemLogSummaryDTO dto = new PosItemLogSummaryDTO();
            dto.setBranchNum((Integer) object[0]);
            dto.setItemNum((Integer) object[0]);
            dto.setItemMatrixNum((Integer) object[0]);
            dto.setInoutFlag((Boolean) object[0]);
            dto.setQty((BigDecimal) object[0]);
            dto.setMoney((BigDecimal) object[0]);
            list.add(dto);
        }
        return list;
    }

    @Override
    public int countByBranch(String systemBookCode, Integer branchNum) {
        return posItemLogService.countByBranch(systemBookCode, branchNum);
    }

    @Override
    public Date getFirstDate(String systemBookCode, Integer branchNum) {
        return posItemLogService.getFirstDate(systemBookCode,branchNum);
    }

    @Override
    public List<PosItemLogSummaryDTO> findMoneyBranchFlagSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
        if(dateFrom.after(dateTo)){
            return Collections.emptyList();
        }
        List<Object[]> objects = posItemLogService.findMoneyBranchFlagSummary(systemBookCode, branchNums, dateFrom, dateTo);
        int size = objects.size();
        List<PosItemLogSummaryDTO> list = new ArrayList<>(size);
        for (int i = 0; i <size; i++) {
            Object[] object = objects.get(i);
            PosItemLogSummaryDTO dto = new PosItemLogSummaryDTO();
            dto.setBranchNum((Integer) object[0]);
            dto.setInoutFlag((Boolean) object[1]);
            dto.setMoney((BigDecimal) object[2]);
            dto.setSaleMoney((BigDecimal) object[3]);
            list.add(dto);
        }
        return list;
    }

    @Override
    public List<PosItemLogSummaryDTO> findMoneyBranchItemFlagSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
        if(dateFrom.after(dateTo)){
            return Collections.emptyList();
        }
        List<Object[]> objects = posItemLogService.findMoneyBranchItemFlagSummary(systemBookCode, branchNums, dateFrom, dateTo);
        int size = objects.size();
        List<PosItemLogSummaryDTO> list = new ArrayList<>(size);
        for (int i = 0; i <size ; i++) {
            Object[] object = objects.get(i);
            PosItemLogSummaryDTO dto = new PosItemLogSummaryDTO();
            dto.setBranchNum((Integer) object[0]);
            dto.setItemNum((Integer) object[1]);
            dto.setInoutFlag((Boolean) object[2]);
            dto.setMoney((BigDecimal) object[3]);
            dto.setQty((BigDecimal) object[4]);
            list.add(dto);
        }
        return list;
    }

    @Override
    public List<PosItemLogSummaryDTO> findMinPriceAndDateSummary(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, List<Integer> itemNums) {
        if(dateFrom.after(dateTo)){
            return Collections.emptyList();
        }
        List<Object[]> objects = posItemLogService.findMinPriceAndDateSummary(systemBookCode, branchNum, dateFrom, dateTo, itemNums);
        int size = objects.size();
        List<PosItemLogSummaryDTO> list = new ArrayList<>(size);
        for (int i = 0; i <size; i++) {
            Object[] object = objects.get(i);
            PosItemLogSummaryDTO dto = new PosItemLogSummaryDTO();
            dto.setItemNum((Integer) object[0]);
            dto.setDate((Date) object[1]);
            dto.setPrice((BigDecimal) object[2]);
            list.add(dto);
        }
        return list;
    }

    @Override
    public List<PosItemLogSummaryDTO> findMaxPriceAndDateSummary(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, List<Integer> itemNums) {

        if(dateFrom.after(dateTo)){
            return Collections.emptyList();
        }
        List<Object[]> objects = posItemLogService.findMaxPriceAndDateSummary(systemBookCode, branchNum, dateFrom, dateTo, itemNums);
        int size = objects.size();
        List<PosItemLogSummaryDTO> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            Object[] object = objects.get(i);
            PosItemLogSummaryDTO dto = new PosItemLogSummaryDTO();
            dto.setItemNum((Integer) object[0]);
            dto.setDate((Date) object[1]);
            dto.setPrice((BigDecimal) object[2]);
            list.add(dto);
        }
        return list;
    }

    @Override
    public List<PosItemLogSummaryDTO> findItemDetailSummary(StoreQueryCondition storeQueryCondition) {
        if(storeQueryCondition.getDateStart().after(storeQueryCondition.getDateEnd())){
            return Collections.emptyList();
        }
        List<Object[]> objects = posItemLogService.findItemDetailSummary(storeQueryCondition);
        int size = objects.size();
        List<PosItemLogSummaryDTO> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            Object[] object = objects.get(i);
            PosItemLogSummaryDTO dto = new PosItemLogSummaryDTO();
            dto.setItemNum((Integer) object[0]);
            dto.setSummary((String) object[1]);
            dto.setDate((Date) object[2]);
            dto.setOperatePrice((BigDecimal) object[3]);
            dto.setBillNo((Integer) object[4]);
            dto.setQty((BigDecimal) object[5]);
            dto.setInoutFlag((Boolean) object[6]);
            dto.setBalance((BigDecimal) object[7]);
            list.add(dto);
        }
        return list;
    }

    @Override
    public List<PosItemLogDTO> findByDate(String systemBookCode, Date dateFrom, Date dateTo) {
        if(dateFrom.after(dateTo)){
            return Collections.emptyList();
        }
        return CopyUtil.toList(posItemLogService.findByDate(systemBookCode, dateFrom, dateTo), PosItemLogDTO.class);
    }

    @Override
    public List<PosItemLogDTO> findUnUploadSummary(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, int offset, int limit) {
        if(dateFrom.after(dateTo)){
            return Collections.emptyList();
        }
        return CopyUtil.toList(posItemLogService.findUnUploadSummary(systemBookCode,branchNum,dateFrom,dateTo,offset,limit),PosItemLogDTO.class);
    }

    @Override
    public List<PosItemLogDTO> findRepeatAuditOrderSummary(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo) {
        if(dateFrom.after(dateTo)){
            return Collections.emptyList();
        }
        return CopyUtil.toList(posItemLogService.findRepeatAuditOrderSummary(systemBookCode,branchNum,dateFrom,dateTo),PosItemLogDTO.class);
    }

    @Override
    public List<PosItemLogSummaryDTO> findSummary(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo) {
        if(dateFrom.after(dateTo)){
            return Collections.emptyList();
        }
        List<Object[]> objects = posItemLogService.findSummary(systemBookCode, branchNum, dateFrom, dateTo);
        int size = objects.size();
        List<PosItemLogSummaryDTO> list = new ArrayList<>(size);
        for (int i = 0; i <size ; i++) {
            Object[] object = objects.get(i);
            PosItemLogSummaryDTO dto = new PosItemLogSummaryDTO();
            dto.setInStorehouseNum((Integer) object[0]);
            dto.setOutStorehouseNum((Integer) object[1]);
            dto.setSummary((String) object[2]);
            dto.setInoutFlag((Boolean) object[3]);
            dto.setItemMatrixNum((Integer) object[4]);
            dto.setQty((BigDecimal) object[5]);
            dto.setAssistQty((BigDecimal) object[6]);
            dto.setMoney((BigDecimal) object[7]);
            list.add(dto);
        }
        return list;
    }

    @Override
    public int countUnUpload(String systemBookCode, Date dateFrom, Date dateTo) {
        if(dateFrom.after(dateTo)){
            return 0;
        }
        return posItemLogService.countUnUpload(systemBookCode, dateFrom, dateTo);

    }

    @Override
    public List<PosItemLogSummaryDTO> findBranchItemFlagMemoSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom,
                                                                    Date dateTo, String summaries, List<Integer> itemNums, Integer storehouseNum) {
        if(dateFrom.after(dateTo)){
            return Collections.emptyList();
        }
        List<Object[]> objects = posItemLogService.findBranchItemFlagMemoSummary(systemBookCode, branchNums, dateFrom, dateTo, summaries, itemNums, storehouseNum);
        int size = objects.size();
        List<PosItemLogSummaryDTO> list = new ArrayList<>(size);
        for (int i = 0; i <size ; i++) {
            Object[] object = objects.get(i);
            PosItemLogSummaryDTO dto = new PosItemLogSummaryDTO();
            dto.setBranchNum((Integer) object[0]);
            dto.setItemNum((Integer) object[1]);
            dto.setInoutFlag((Boolean) object[2]);
            dto.setMemo((String) object[3]);
            dto.setQty((BigDecimal) object[4]);
            dto.setMoney((BigDecimal) object[5]);
            dto.setAssistQty((BigDecimal) object[6]);
            dto.setUseQty((BigDecimal) object[7]);
            dto.setSaleMoney((BigDecimal) object[8]);
            dto.setUseUnit((String) object[9]);
            dto.setAdjustMoney(object[10] == null?BigDecimal.ZERO:(BigDecimal)object[10]);
            list.add(dto);
        }
        return list;
    }

    @Override
    public List<PosItemLogSummaryDTO> findItemInOutSummary(StoreQueryCondition storeQueryCondition) {
        if(storeQueryCondition.getDateStart().after(storeQueryCondition.getDateEnd())){
            return Collections.emptyList();
        }
        List<Object[]> objects = posItemLogService.findItemInOutSummary(storeQueryCondition);
        int size = objects.size();
        List<PosItemLogSummaryDTO> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            Object[] object = objects.get(i);
            PosItemLogSummaryDTO dto = new PosItemLogSummaryDTO();
            dto.setItemNum((Integer) object[0]);
            dto.setInoutFlag((Boolean) object[1]);
            dto.setQty((BigDecimal) object[2]);
            dto.setMoney((BigDecimal) object[3]);
            dto.setAssistQty((BigDecimal) object[4]);
            dto.setAdjustMoney((BigDecimal) object[5]);
            list.add(dto);
        }
        return list;
    }

    @Override
    public List<PosItemLogDTO> findByBillNoSummary(String systemBookCode, String orderNo) {
        return CopyUtil.toList(posItemLogService.findByBillNoSummary(systemBookCode,orderNo),PosItemLogDTO.class);
    }

    @Override
    public int countByBillNo(String systemBookCode, String posItemLogBillNo) {
        return posItemLogService.countByBillNo(systemBookCode,posItemLogBillNo);
    }

    @Override
    public List<PosItemLogSummaryDTO> findDateItemFlagSummary(StoreQueryCondition storeQueryCondition) {
        if(storeQueryCondition.getDateStart().after(storeQueryCondition.getDateEnd())){
            return Collections.emptyList();
        }
        List<Object[]> objects = posItemLogService.findDateItemFlagSummary(storeQueryCondition);
        int size = objects.size();
        List<PosItemLogSummaryDTO> list = new ArrayList<>(size);
        for (int i = 0; i <size ; i++) {
            Object[] object = objects.get(i);
            PosItemLogSummaryDTO dto = new PosItemLogSummaryDTO();
            dto.setDateIndex((String) object[0]);
            dto.setItemNum((Integer) object[1]);
            dto.setItemMatrixNum((Integer) object[2]);
            dto.setInoutFlag((Boolean) object[3]);
            dto.setQty((BigDecimal) object[4]);
            dto.setAssistQty((BigDecimal) object[5]);
            dto.setUseQty((BigDecimal) object[6]);
            dto.setSaleMoney((BigDecimal) object[7]);
            dto.setUseUnit((String) object[8]);
            list.add(dto);
        }
        return list;
    }

    @Override
    public List<Integer> findItemNumSummary(String systemBookCode, Integer branchNum, Integer storehouseNum, Date dateFrom, Date dateTo) {
        if(dateFrom.after(dateTo)){
            return Collections.emptyList();
        }
        return posItemLogService.findItemNumSummary(systemBookCode, branchNum, storehouseNum, dateFrom, dateTo);
    }

    @Override
    public boolean checkExists(String posItemLogBillNo, Integer posItemLogBillDetailNum, Integer posItemLogSerialNumber) {
        return posItemLogService.checkExists(posItemLogBillNo,posItemLogBillDetailNum,posItemLogSerialNumber);
    }

    @Override
    public List<PosItemLogSummaryDTO> findBranchItemMatrixFlagSummary(StoreQueryCondition storeQueryCondition) {
        if(storeQueryCondition.getDateStart().after(storeQueryCondition.getDateEnd())){
            return Collections.emptyList();
        }
        List<Object[]> objects = posItemLogService.findBranchItemMatrixFlagSummary(storeQueryCondition);
        int size = objects.size();
        List<PosItemLogSummaryDTO> list = new ArrayList<>(size);
        for (int i = 0; i <size ; i++) {
            Object[] object = objects.get(i);
            PosItemLogSummaryDTO dto = new PosItemLogSummaryDTO();
            dto.setBranchNum((Integer) object[0]);
            dto.setItemNum((Integer) object[1]);
            dto.setItemMatrixNum((Integer) object[2]);
            dto.setInoutFlag((Boolean) object[3]);
            dto.setQty((BigDecimal) object[4]);
            dto.setMoney((BigDecimal) object[5]);
            dto.setAssistQty((BigDecimal) object[6]);
            dto.setUseQty((BigDecimal) object[7]);
            dto.setSaleMoney((BigDecimal) object[8]);
            dto.setUseUnit((String) object[9]);
            dto.setAdjustMoney(object[10] == null?BigDecimal.ZERO:(BigDecimal)object[10]);
            list.add(dto);

        }
        return list;
    }

    @Override
    public PosItemLogDTO read(String posItemLogBillNo, Integer posItemLogBillDetailNum) {
        return  CopyUtil.to(posItemLogService.read(posItemLogBillNo, posItemLogBillDetailNum), PosItemLogDTO.class);
    }

    @Override
    public List<PosItemLogSummaryDTO> findItemInOutQtyAndMoney(StoreQueryCondition storeQueryCondition) {
        if(storeQueryCondition.getDateStart().after(storeQueryCondition.getDateEnd())){
            return Collections.emptyList();
        }
        List<Object[]> objects = posItemLogService.findItemInOutQtyAndMoney(storeQueryCondition);
        int size = objects.size();
        List<PosItemLogSummaryDTO> list = new ArrayList<>(size);
        for (int i = 0; i <size ; i++) {
            Object[] object = objects.get(i);
            PosItemLogSummaryDTO dto = new PosItemLogSummaryDTO();
            dto.setItemNum((Integer) object[0]);
            dto.setQty((BigDecimal) object[1]);
            dto.setMoney((BigDecimal) object[2]);
            dto.setAssistQty((BigDecimal) object[3]);
            dto.setAdjustMoney((BigDecimal) object[4]);
            list.add(dto);
        }
        return list;
    }
}
