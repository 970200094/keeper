package com.xiaomaguanjia.keeper.web.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaoma.basicservice.contract.OrderCommentService;
import com.xiaoma.basicservice.contract.college.KeeperChannelService;
import com.xiaoma.basicservice.contract.college.KeeperCourseService;
import com.xiaoma.basicservice.entity.college.KeeperChannelEntity;
import com.xiaoma.basicservice.entity.college.KeeperCourseEntity;
import com.xiaoma.basicservice.form.ApiBaseForm;
import com.xiaoma.basicservice.vo.keeper.KeeperEvaluateVo;
import com.xiaomaguanjia.keeper.util.RequestUtils;
import com.xiaomaguanjia.keeper.web.form.CommentForm;
import com.xiaomaguanjia.keeper.web.vo.BaseJsonVo;

@Controller("keeperCollegeController")
@RequestMapping("/college/*")
public class KeeperCollegeController extends BaseController{
	@Autowired
	private OrderCommentService orderCommentService;
	@Autowired
	private KeeperCourseService courseService;
	@Autowired
	private KeeperChannelService channelService;
	
	private static final Logger log = LoggerFactory
			.getLogger(KeeperCollegeController.class);
	/**
	 * 获取频道信息
	 * @return
	 */
	@RequestMapping("getChannel")
	public @ResponseBody BaseJsonVo getChannel(){
		List<KeeperChannelEntity> channels = channelService.getChannel();
		if(channels.isEmpty()){
			return BaseJsonVo.empty();
		}
		return BaseJsonVo.success(channels);
	}
	@RequestMapping("getExam")
	public @ResponseBody BaseJsonVo getExam(@ModelAttribute ApiBaseForm form){
		List<KeeperChannelEntity> channels = channelService.getExam(form);
		if(channels.isEmpty()){
			return BaseJsonVo.empty();
		}
		return BaseJsonVo.success(channels);
	}
	@RequestMapping("getChannelInfo")
	public @ResponseBody BaseJsonVo getChannelInfo(int id){
		List<KeeperCourseEntity> channelInfos = courseService.getChannelInfo(id);
		if(channelInfos.isEmpty()){
			return BaseJsonVo.empty();
		}
		return BaseJsonVo.success(channelInfos);
	}
	@RequestMapping("getQuestionAnswer")
	public @ResponseBody BaseJsonVo getQuestionAnswer(@ModelAttribute ApiBaseForm form){
		List<KeeperCourseEntity> channelInfos = courseService.getQuestionAnswer(form);
		if(channelInfos.isEmpty()){
			return BaseJsonVo.empty();
		}
		return BaseJsonVo.success(channelInfos);
	}
	@RequestMapping("addReadCount")
	public @ResponseBody BaseJsonVo addReadCount( int id){
		int addReadCount = courseService.addReadCount(id);
		if(addReadCount==0){ 
			return BaseJsonVo.empty();
		}
		return BaseJsonVo.success();
	}
	
}
