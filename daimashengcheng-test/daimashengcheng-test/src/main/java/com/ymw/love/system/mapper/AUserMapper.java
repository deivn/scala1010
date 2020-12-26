package com.ymw.love.system.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ymw.love.system.entity.AUser;

/**
 * <p>
  * 后台用户 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2019-08-15
 */
public interface AUserMapper extends BaseMapper<AUser> {
	
	/**
	 * 清空 部门、职称
	 * @param type   2职称 3部门
	 * @param id  对应id
	 * @return
	 */
	@Update("<script> UPDATE `a_user` SET <if test='type==2'>`position_id`=NULL</if> "
			+ "<if test='type==3'>`sector_id`=NULL</if>  "
			+ "WHERE  <if test='type==2'>`position_id`=#{id}</if>   "
			+ " <if test='type==3'>`sector_id`=#{id}</if> </script>")
	int updateEmptySectorPosition(@Param("type") Integer type,@Param("id") Integer id);
	
	
	
	IPage<Map<String, Object>> findUserList(Page page,@Param("name")String name,@Param("hospitalId") String hospitalId);
	
	
	
	

}