package com.nhsoft.module.report.api;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.nhsoft.module.report.api.dto.DashBoardSalesTrendDTO;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController()
@RequestMapping("/salesTrend")
public class APIDashBoardSalesTrend {

	@RequestMapping(method = RequestMethod.GET, value = "/json")
	public @ResponseBody List<DashBoardSalesTrendDTO> listSalesTrendJson(@RequestHeader("LoggedInUserTenantId") String LoggedInUserTenantId,
		@RequestHeader("LoggedInUserFullName") String LoggedInUserFullName) {
		
		if(LoggedInUserTenantId.equals("4020")) {
			DashBoardSalesTrendDTO dto = null;
			List<DashBoardSalesTrendDTO> list = new ArrayList<DashBoardSalesTrendDTO>();
			for(int i = 1; i <= 12; i++) {
				dto = new DashBoardSalesTrendDTO();
				dto.setBranchNum2(i);
				dto.setBranchName2("测试api月份 "+i);
				dto.setRevenue2(new BigDecimal(10000*i));
				list.add(dto);
			}
			return list;
		} else {
			return new ArrayList<DashBoardSalesTrendDTO>();
		}
		
	}
	
}
