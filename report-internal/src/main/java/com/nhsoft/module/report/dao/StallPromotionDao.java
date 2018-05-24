package com.nhsoft.module.report.dao;

import com.nhsoft.module.report.dto.StallPromotionCondition;
import com.nhsoft.module.report.model.StallPromotion;

import java.util.List;

public interface StallPromotionDao {

    List<StallPromotion> find(StallPromotionCondition stallPromotionCondition);

}
