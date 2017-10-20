package com.xiaomaguanjia.keeper.web.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaoma.basicservice.contract.OrderCommentService;
import com.xiaoma.basicservice.vo.keeper.KeeperEvaluateVo;
import com.xiaomaguanjia.keeper.util.RequestUtils;
import com.xiaomaguanjia.keeper.web.form.CommentForm;
import com.xiaomaguanjia.keeper.web.vo.BaseJsonVo;

@Controller("CommentControllerV2.0")
@RequestMapping(value = "/comment/*")
public class CommentController extends BaseController{
	@Autowired
	private OrderCommentService orderCommentService;

	
	@RequestMapping(value = "history")
	public @ResponseBody
	BaseJsonVo getCommentHistory(@ModelAttribute CommentForm form) {
		 Long userId = RequestUtils.getUserId();
		 List<KeeperEvaluateVo> list=orderCommentService.getOrderCommentList(userId, form.getTime(), form.getStatus());
		if(list==null||list.size()==0){
			return BaseJsonVo.empty();
		}
		
		
		return BaseJsonVo.success(list);
		
		
	}
	
	@RequestMapping(value = "frist")
	public @ResponseBody BaseJsonVo getFristCommnentTime(){
//		Long userId = RequestUtils.getUserId();
//		Date date=orderCommentService.queryHouseKeeperFristCommentTime(userId);
//		if(date==null){
//			date=new Date(System.currentTimeMillis());
//				yao gai xuqiu 	 shabi
//		}
		
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.MONTH, -12); //查询上一个上一个
		Long value=calendar.getTimeInMillis();
		return BaseJsonVo.success(value);
	}
	
	
	@RequestMapping(value = "user/history")
	public @ResponseBody
	BaseJsonVo getUserCommentHistory(@ModelAttribute CommentForm form) {
		HashMap<String, Object> map=new HashMap<>();
		 List<KeeperEvaluateVo> list=orderCommentService.getUserComments(form.getUserId(), form.getStatus(), form.getPage());
		 int bad=orderCommentService.getUserComments(form.getUserId(), 0, 2);
		int good=orderCommentService.getUserComments(form.getUserId(), 3, 5);
		map.put("bad", bad);
		map.put("good", good);
		if(list!=null&&list.size()!=0){
			map.put("vo", list);
		}
		return BaseJsonVo.success(map);
		
	}
	
	


}
