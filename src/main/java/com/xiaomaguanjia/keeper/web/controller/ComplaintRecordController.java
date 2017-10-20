package com.xiaomaguanjia.keeper.web.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xiaoma.basicservice.contract.complaint.ComplaintService;
import com.xiaoma.basicservice.entity.complaint.ComplaintVo;
import com.xiaomaguanjia.keeper.util.RequestUtils;
import com.xiaomaguanjia.keeper.web.vo.BaseJsonVo;
@RestController
@RequestMapping(value = "/complaint/*")
public class ComplaintRecordController extends BaseController {

    @Autowired
    private ComplaintService complaintService;
    
    @RequestMapping(value = "record")
    public @ResponseBody BaseJsonVo getHouseKeeperRecord(@RequestParam String  time){
		Long userId = RequestUtils.getUserId();
		List<ComplaintVo> list=complaintService.getHouseKeeperAndMonth(userId, time);
		if(list==null||list.size()==0){
			return BaseJsonVo.empty();
		}
    	return BaseJsonVo.success(list);
    }
    
    @RequestMapping(value = "month")
    public @ResponseBody BaseJsonVo getHouseKeeperRecord(){
    	//SB yao gao xuq
 		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.MONTH, -12); //查询上一个上一个
		Long value=calendar.getTimeInMillis();
		return BaseJsonVo.success(value);
     }
    

    
}
