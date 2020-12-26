package com.ymw.love.system.service.uuser;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ymw.love.system.config.excep.MissRequiredParamException;
import com.ymw.love.system.dto.BaseEntity;
import com.ymw.love.system.dto.BasicDTO;
import com.ymw.love.system.entity.SysDict;
import com.ymw.love.system.entity.UUser;
import com.ymw.love.system.immobile.HintTitle;
import com.ymw.love.system.mapper.UUserMapper;
import com.ymw.love.system.service.BaseService;
import com.ymw.love.system.util.DateUtil;
import com.ymw.love.system.util.StringUtils;
import com.ymw.love.system.util.VerifyUtil;

/**
 * @author 作者 ：suhua
 * @date 创建时间：2019年8月7日 下午3:57:07 类说明：
 */
@Service
public class UUserService extends BaseService{

	

	/**
	 * 用户中心显示数据
	 * 
	 * @return
	 */
	public UUser findUUserCount(String userId) {
		return mf.getUUserMapper().findUserCount(userId);
	}

	/**
	 * 用户个人中详情
	 * 
	 * @return
	 */
	public Map<String, Object> findUserDetails(String userId) {
		return  mf.getUUserMapper().findUserDetails(userId);
	}

	/**
	 * 添加身份证验证
	 * 
	 * @param identity
	 * @param name
	 * @return
	 */
	public String updateIdCard(String userId, String idcard, String name) {

		if (StringUtils.isEmpty(idcard) || StringUtils.isEmpty(name)) {
			throw new MissRequiredParamException(HintTitle.System.parameter_is_null);
		}

		// 验证身份证位数、名字
		if (!VerifyUtil.IDCardVerify(idcard) || !VerifyUtil.zwVerify(name)) {
			throw new MissRequiredParamException(HintTitle.User.id_card_name_error);
		}

		UUser entity = new UUser();
		entity.setIdcard(idcard);
		entity.setName(name);
		entity.setIdcardTime(new Date());
		entity.setSex(VerifyUtil.getSex(idcard));
		entity.setBirthdate(VerifyUtil.getDirthdate(idcard));

		int in =  mf.getUUserMapper().update(entity, new UpdateWrapper<UUser>().eq("id", userId));

		if (in <= 0) {
			throw new MissRequiredParamException(HintTitle.System.failed);
		}

		return null;
	}

	/**
	 * 更新昵称或者头像
	 */
	public void updateUserDetails(String userId, String nickName, String imageUrl) {
		if (StringUtils.isEmpty(nickName) && StringUtils.isEmpty(imageUrl)) {
			throw new MissRequiredParamException(HintTitle.System.parameter_is_null);
		}
		UUser entity = new UUser();
		entity.setNickName(nickName);
		entity.setImageUrl(imageUrl);
		int in =  mf.getUUserMapper().update(entity, new UpdateWrapper<UUser>().eq("id", userId));
		if (in <= 0) {
			throw new MissRequiredParamException(HintTitle.System.failed);
		}

	}

	/**
	 * 更新收款方式
	 * 
	 * @param number
	 * @param imageUrl
	 */
	public void updateUserPayee(String userId, String number, String imageUrl) {
		if (StringUtils.isEmpty(number) && StringUtils.isEmpty(imageUrl)) {
			throw new MissRequiredParamException(HintTitle.System.parameter_is_null);
		}

		int in =  mf.getUUserMapper().updateUserPayee(userId, number, imageUrl);
		if (in <= 0) {
			throw new MissRequiredParamException(HintTitle.System.failed);
		}
	}

	/**
	 * 获取推荐人数
	 * 
	 * @param userId
	 * @return
	 */
	public IPage<UUser> fidnUserfriend(String userId, Integer pageNum, Integer pageSize,String hidePhone) {
		if (StringUtils.isEmpty(userId)) {
			throw new MissRequiredParamException(HintTitle.System.parameter_is_null);
		}
		Page<SysDict> page = new Page<>(pageNum, pageSize);
		return  mf.getUUserMapper().fidnUserfriend(page, userId,hidePhone);
	}

	/**
	 * 后台 ：用户列表
	 * 
	 * @param arg
	 * @return
	 */
	public IPage<UUser> findUserList(BasicDTO arg) {
		Page<SysDict> page = new Page<>(arg.getPageNum(), arg.getPageSize());
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
				map=DateUtil.getTimeScope(c.getTime(), 0, null);
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
		
		return  mf.getUUserMapper().findUserList(page, arg);
	}

	/**
	 * 修改用户状态
	 * 
	 * @param id
	 * @param state
	 * @return
	 */
	public String updateUserState(String id, Integer state, String cause) {
		if (StringUtils.isEmpty(id) || StringUtils.isEmpty(state)) {
			throw new MissRequiredParamException(HintTitle.System.parameter_is_null);
		}

		if (state != 1 && state != 2) {
			throw new MissRequiredParamException(HintTitle.System.error_type);
		}

		UUser entity = new UUser();
		if (state == 2) {
			entity.setCause(cause);
		} else {
			entity.setCause(" ");
		}

		entity.setState(state);
		int is =  mf.getUUserMapper().update(entity, new UpdateWrapper<UUser>().eq("id", id));
		if (is <= 0) {
			throw new MissRequiredParamException(HintTitle.System.failed);
		}

		return null;
	}

}
