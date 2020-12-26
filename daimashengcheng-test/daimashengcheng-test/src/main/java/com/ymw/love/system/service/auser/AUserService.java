package com.ymw.love.system.service.auser;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ymw.love.system.common.Resource;
import com.ymw.love.system.common.SystemEnum;
import com.ymw.love.system.config.excep.MissRequiredParamException;
import com.ymw.love.system.config.intercept.UserLogInInfo;
import com.ymw.love.system.dto.BasicDTO;
import com.ymw.love.system.dto.UsetBasicDTO;
import com.ymw.love.system.entity.AUser;
import com.ymw.love.system.entity.authority.SysRole;
import com.ymw.love.system.immobile.HintTitle;
import com.ymw.love.system.mapper.AUserMapper;
import com.ymw.love.system.mapper.sys.SysRoleAuthorityMapper;
import com.ymw.love.system.mapper.sys.SysRoleMapper;
import com.ymw.love.system.redis.RedisService;
import com.ymw.love.system.service.BaseService;
import com.ymw.love.system.service.RoleAuthorityService;
import com.ymw.love.system.util.DESStupidUtil;
import com.ymw.love.system.util.DateUtil;
import com.ymw.love.system.util.SnowFlakeUtil;
import com.ymw.love.system.util.StringUtils;
import com.ymw.love.system.util.VerifyUtil;
import com.ymw.love.system.vo.AUserVO;

/**
 * @author 作者 ：suhua
 * @date 创建时间：2019年8月17日 下午5:13:15 类说明：
 */
@Service
public class AUserService extends BaseService {

	private AUserVO aUserVO;

	@Value("${secret.key}")
	private String secretKey;// 签名的 key 8位

	private Long tokenTime2 = 3600 * 24L;// 4小时

	public String aUerLogin(UsetBasicDTO au) {

		if (StringUtils.isEmpty(au.getName()) || StringUtils.isEmpty(au.getPassword())) {
			throw new MissRequiredParamException(HintTitle.System.parameter_is_null);
		}
		List<AUser> list = mf.getAUserMapper()
				.selectList(new QueryWrapper<AUser>().eq("user_name", au.getName()).or().eq("phone", au.getName()));
		if (list == null || list.size() <= 0) {
			throw new MissRequiredParamException(SystemEnum.FAIL_USER_PASSWORD_ERROR,
					HintTitle.User.account_password_error);
		}

		// 密码解密
		//String pa = VerifyUtil.getMD5(decrypt(au.getPassword()), 1);
		String pa = VerifyUtil.getMD5(au.getPassword(), 1);
		if (!list.get(0).getPassword().equals(pa)) {
			throw new MissRequiredParamException(SystemEnum.FAIL_USER_PASSWORD_ERROR,
					HintTitle.User.account_password_error);
		}

		if (list.get(0).getState() == 2) {// 2禁用
			throw new MissRequiredParamException(SystemEnum.FAIL_USER_JY, HintTitle.User.user_jy_error);
		}
		AUserVO aUserVO = new AUserVO();
		aUserVO.setId(list.get(0).getId());
		aUserVO.setHospitalId(list.get(0).getHospitalId());
		aUserVO.setName(list.get(0).getName());
		aUserVO.setUserName(list.get(0).getUserName());

		// 读取权限
		if (!list.get(0).getRoleCode().equals("0")) {
			SysRole sy = mf.getSysRoleMapper().selectOne(new QueryWrapper<SysRole>().eq("code", list.get(0).getRoleCode()));
			aUserVO.setDataTier(sy.getDataTier());
			// 设置权限
			Map<String, String> map = sf.getRoleAuthorityService().findUserAuthority(list.get(0).getRoleCode());
			aUserVO.setAuthoriUrl(map);
		}

		// 移除其他设备登录状态
		String token1 = getToken(list.get(0).getId(), list.get(0).getLoginTime());
		UserExit(token1);

		// 更新登录时间
		AUser auser = new AUser();
		auser.setLoginTime(new Date());
		mf.getAUserMapper().update(auser, new UpdateWrapper<AUser>().eq("id", list.get(0).getId()));

		// 设置token
		String token = getToken(list.get(0).getId(), auser.getLoginTime());
		sf.getRedisService().set(Resource.enter.admin_user + token, aUserVO, tokenTime2);
		return token;
	}

	/**
	 * 用户查询
	 * 
	 */
	public IPage<Map<String, Object>> findAUserList(BasicDTO arg,String hospitalId) {
		Page page = new Page<>(arg.getPageNum(), arg.getPageSize());
		IPage<Map<String, Object>> list = mf.getAUserMapper().findUserList(page, arg.getName(),hospitalId);
		return list;
	}
	
	/**
	 * 获取详情
	 * @param id
	 * @return
	 */
	public AUser findAUserId(String id) {
		if (StringUtils.isEmpty(id)){
			throw new MissRequiredParamException(HintTitle.System.parameter_is_null);
		}
		
		return 	mf.getAUserMapper().selectOne(new QueryWrapper<AUser>().eq("id", id));
	}
	

	/**
	 * 修改、新增用户信息
	 * 
	 * @param auser
	 * @return
	 */
	public String saveAUser(AUser auser) {
		if (StringUtils.isEmpty(auser.getName()) || StringUtils.isEmpty(auser.getUserName())
				|| StringUtils.isEmpty(auser.getPhone()) || StringUtils.isEmpty(auser.getPositionId())
				|| StringUtils.isEmpty(auser.getHospitalId()) || StringUtils.isEmpty(auser.getSectorId())
				|| StringUtils.isEmpty(auser.getRoleCode())) {
			throw new MissRequiredParamException(HintTitle.System.parameter_is_null);
		}

		if (StringUtils.isEmpty(auser.getId())) {// 新增
			if (StringUtils.isEmpty(auser.getPassword())) {
				throw new MissRequiredParamException(HintTitle.System.parameter_is_null);
			}
			auser.setPassword(VerifyUtil.getMD5(auser.getPassword(), 1));
			auser.setCreatesTime(new Date());
			mf.getAUserMapper().insert(auser);
			return null;
		}

		// 密码加密
		if (StringUtils.isNotEmpty(auser.getPassword())) {
			auser.setPassword(VerifyUtil.getMD5(auser.getPassword(), 1));
		}

		// 更新
		int i = mf.getAUserMapper().update(auser, new UpdateWrapper<AUser>().eq("id", auser.getId()));
		if (i <= 0) {
			throw new MissRequiredParamException(HintTitle.System.failed);
		}
		return null;
	}

	/**
	 * 
	 * @param auser
	 */
	public void updateAUser(AUser auser) {
		if (StringUtils.isEmpty(auser.getId())) {
			throw new MissRequiredParamException(HintTitle.System.parameter_is_null);
		}
		int i = mf.getAUserMapper().update(auser, new UpdateWrapper<AUser>().eq("id", auser.getId()));
		if (i <= 0) {
			throw new MissRequiredParamException(HintTitle.System.failed);
		}
	}

	/**
	 * 退出
	 */
	public void UserExit(String token) {

		if (StringUtils.isEmpty(token)) {
			token = sf.getUserLogInInfo().getToken();
		}
		sf.getRedisService().delete(Resource.enter.admin_user + token);
	}

	/**
	 * 获取登录用户详情
	 * @return
	 */
	 public AUserVO findUserDetails(String userId){
		 AUser au=  mf.getAUserMapper().selectOne(new QueryWrapper<AUser>().eq("id", userId));
		 if(au==null) {
			 return null;
		 }
		 
		 AUserVO auVo =new AUserVO();
		 auVo.setName(au.getName());
		 auVo.setUserName(au.getUserName());
		 //获取用户权限
		 auVo.setAuthori(sf.getRoleAuthorityService().findUserAuthoritys(au.getRoleCode()));
		 
		 return auVo;
	 }
	
	/**
	 * 修改用户密码
	 * @param userId
	 * @param newPassword
	 * @param oldPassword
	 */
	 public void updateAUserPassword(String userId,String newPassword,String oldPassword) {
		
		 if(StringUtils.isEmpty(oldPassword)|| StringUtils.isEmpty(newPassword)) {
			 throw new MissRequiredParamException(HintTitle.System.parameter_is_null);
		 }
		 
		 
		 AUser au=  mf.getAUserMapper().selectOne(new QueryWrapper<AUser>().eq("id", userId));
            if(au==null) {
            	throw new MissRequiredParamException(HintTitle.System.failed);
            }
            
            if(!VerifyUtil.getMD5(oldPassword, 1).equals(au.getPassword())) {
            	throw new MissRequiredParamException(HintTitle.User.password_error);
            }
        //修复密码
            AUser u =new AUser();
            u.setPassword(VerifyUtil.getMD5(newPassword, 1));
            mf.getAUserMapper().update(u, new UpdateWrapper<AUser>().eq("id", userId));
            
	 }
	
	
	
	/**
	 * 解密
	 * 
	 * @param arg
	 * @return
	 */
	private String decrypt(String arg) {
		try {
			return DESStupidUtil.decrypt(arg, secretKey);
		} catch (Exception e) {
			throw new MissRequiredParamException(HintTitle.System.parse_error);
		}
	}

	private String getToken(String id, Date date) {
		if (date == null) {
			return "1";
		}
		return VerifyUtil.getMD5(id + DateUtil.parseDateToStr(date, DateUtil.DATE_FORMAT_YYYYMMDDHHmm), 1);
	}
}
