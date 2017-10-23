package com.nhsoft.module.report.api;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.nhsoft.module.report.api.dto.DashBoardOnlineOccupyDTO;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController()
@RequestMapping("/onlineOccupy")
public class APIDashBoardOnlineOccupy {

	@RequestMapping(method = RequestMethod.GET, value = "/json")
	public @ResponseBody List<DashBoardOnlineOccupyDTO> listSalesTrendJson(@RequestHeader("LoggedInUserTenantId") String LoggedInUserTenantId,
																		   @RequestHeader("LoggedInUserFullName") String LoggedInUserFullName) {
		
		if(LoggedInUserTenantId.equals("4020")) {
			DashBoardOnlineOccupyDTO dto = null;
			List<DashBoardOnlineOccupyDTO> list = new ArrayList<DashBoardOnlineOccupyDTO>();
			for(int i = 10; i > 0; i--) {
				dto = new DashBoardOnlineOccupyDTO();
				dto.setAreaNum(i);
				dto.setAreaName("测试api区域 "+i);
				dto.setOnlineRevenue(new BigDecimal(i*1000));
				dto.setOfflineRevenue(new BigDecimal((10-i)*1000));
				list.add(dto);
			}
			return list;
		} else {
			return new ArrayList<DashBoardOnlineOccupyDTO>();
		}
		
	}
}
