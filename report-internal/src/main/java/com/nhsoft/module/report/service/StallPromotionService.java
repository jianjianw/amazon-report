package com.nhsoft.module.report.service;

import com.nhsoft.module.report.dto.StallPromotionCondition;
import com.nhsoft.module.report.model.StallPromotion;

import java.util.List;

public interface StallPromotionService {

    List<StallPromotion> find(StallPromotionCondition stallPromotionCondition);


}
