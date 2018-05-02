package com.nhsoft.module.report.rpc;


import com.nhsoft.module.report.dto.ConsumePointDTO;
import com.nhsoft.module.report.queryBuilder.CardReportQuery;

import java.util.Date;
import java.util.List;

public interface ConsumePointRpc {



	public List<ConsumePointDTO> findByCardReportQuery(String systemBookCode, CardReportQuery cardReportQuery, int offset, int limit);

	public Object[] sumByCardReportQuery(String systemBookCode, CardReportQuery cardReportQuery);


}
