package com.nhsoft.module.report.service.impl;

import com.nhsoft.module.report.dao.StallPromotionDao;
import com.nhsoft.module.report.dto.StallPromotionCondition;
import com.nhsoft.module.report.model.StallPromotion;
import com.nhsoft.module.report.service.StallPromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StallPromotionServiceImpl implements StallPromotionService {

    @Autowired
    private StallPromotionDao stallPromotionDao;

    @Override
    public List<StallPromotion> find(StallPromotionCondition stallPromotionCondition) {
        List<StallPromotion> stallPromotions = stallPromotionDao.find(stallPromotionCondition);
        if (stallPromotionCondition.isQueryDetail()) {
            stallPromotions.forEach(d -> d.getDetails().size());
        }
        return stallPromotions;
    }


}
