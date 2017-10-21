package com.nhsoft.report.api;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nhsoft.report.api.dto.DashBoardTopItemDTO;

@RestController()
@RequestMapping("/topItem")
public class APIDashBoardTopItem {

	@RequestMapping(method = RequestMethod.GET, value = "/json")
	public @ResponseBody List<DashBoardTopItemDTO> listBranchJson(@RequestHeader("LoggedInUserTenantId") String LoggedInUserTenantId,
		@RequestHeader("LoggedInUserFullName") String LoggedInUserFullName) {
		
		if(LoggedInUserTenantId.equals("4020")) {
			DashBoardTopItemDTO dto = null;
			List<DashBoardTopItemDTO> list = new ArrayList<DashBoardTopItemDTO>();
			for(int i = 10; i > 0; i--) {
				dto = new DashBoardTopItemDTO();
				dto.setBranchNum3(i);
				dto.setBranchName3("测试api商品 "+i);
				dto.setRevenue3(new BigDecimal(10000*i));
				list.add(dto);
			}
			return list;
		} else {
			return new ArrayList<DashBoardTopItemDTO>();
		}
		
	}
	
}
