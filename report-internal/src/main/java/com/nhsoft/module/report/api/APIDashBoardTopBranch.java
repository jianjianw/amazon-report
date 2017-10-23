package com.nhsoft.module.report.api;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.nhsoft.module.report.api.dto.DashBoardTopBranchDTO;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController()
@RequestMapping("/topBranch")
public class APIDashBoardTopBranch {
	
	@RequestMapping(method = RequestMethod.GET, value = "/json")
	public @ResponseBody List<DashBoardTopBranchDTO> listBranchJson(@RequestHeader("LoggedInUserTenantId") String LoggedInUserTenantId,
		@RequestHeader("LoggedInUserFullName") String LoggedInUserFullName) {
		
		if(LoggedInUserTenantId.equals("4020")) {
			DashBoardTopBranchDTO dto = null;
			List<DashBoardTopBranchDTO> list = new ArrayList<DashBoardTopBranchDTO>();
			for(int i = 10; i > 0; i--) {
				dto = new DashBoardTopBranchDTO();
				dto.setBranchNum(i);
				dto.setBranchName("测试api门店 "+i);
				dto.setRevenue(new BigDecimal(10000*i));
				list.add(dto);
			}
			return list;
		} else {
			return new ArrayList<DashBoardTopBranchDTO>();
		}
		
	}
	
	
}
