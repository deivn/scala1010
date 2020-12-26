package com.ymw.love.system.service;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ymw.love.system.dto.BasicDTO;
import com.ymw.love.system.mapper.sys.SysLogMapper;
import com.ymw.love.system.util.DateUtil;

/**
 * @author 作者 ：suhua
 * @date 创建时间：2019年8月26日 下午2:53:34 类说明：
 */
@Service
public class SysLogService extends BaseService {

	
	
	public IPage<Map<String, Object>> findList(BasicDTO arg) {
		
		Page page =new Page(arg.getPageNum(), arg.getPageSize());
		Map<String, String> map=null;
		// 1今天 、2昨天、3最近3天、4最近7天、5最近1个月
		if (arg.getTimeType() != null) {
			switch (arg.getTimeType()) {
			case 1:
				map=DateUtil.getTimeScope(new Date(), 0, null);
				break;

			case 2:
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, -1);// 加减天数
				map=DateUtil.getTimeScope(c.getTime(), -1, null);
				break;

			case 3:
				map=DateUtil.getTimeScope(new Date(), -3, null);
				break;

			case 4:
				map=DateUtil.getTimeScope(new Date(), -7, null);
				break;

			case 5:
				map=DateUtil.getTimeScope(new Date(), null, -1);
				break;

			default:
				break;
			}
		}

		if(map!=null) {
			arg.setStartTime(map.get("startTime"));
			arg.setEndTime(map.get("closeTime"));
		}
		
		return mf.getSysLogMapper().findList(page, arg);
	}

}
