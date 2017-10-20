package com.xiaomaguanjia.keeper.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.xiaoma.p4jtools.Config;
import com.xiaomaguanjia.keeper.util.StringUtil;
import com.xiaomaguanjia.utils.oss.OSSProxy;

@Service("imageService")
public class ImageService {

	@Resource(name = "ossProxy")
	private OSSProxy ossProxy;

	public static final String keeper_ph_path = "http://img01.xiaomaguanjia.com/keeper/head/default.jpg";

	public String uploadOrderImage(MultipartFile ph_file) {
		return uploadImage(ph_file);
	}

	public String uploadComplaintImage(MultipartFile ph_file) {
		return uploadImage(ph_file);
	}

	public String uploadKeeperHeadImage(MultipartFile ph_file) {
		String filepath = uploadImage(ph_file);
		return filepath.equals("") ? keeper_ph_path : filepath;
	}

	public String uploadImage(MultipartFile ph_file) {
		String filepath = "";
		if (ph_file != null) {
			try {
				String fileServerPath = Config.value("keeper_photo_path");// 目录
				String fileUrl = Config.value("keeper_photo_url");//
				String suffix = ph_file.getOriginalFilename().substring(ph_file.getOriginalFilename().indexOf("."));
				String filename = StringUtil.getUUID() + suffix;
				boolean flag = ossProxy.createObject("xiaoma-img01",fileServerPath + filename, ph_file.getBytes());
				if (flag) {
					filepath = fileUrl + fileServerPath + filename;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return filepath;
	}
}
