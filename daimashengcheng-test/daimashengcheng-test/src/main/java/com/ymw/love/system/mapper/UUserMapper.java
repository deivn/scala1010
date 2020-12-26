package com.ymw.love.system.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ymw.love.system.dto.BasicDTO;
import com.ymw.love.system.entity.UUser;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author
 * @since 2019-08-02
 */
public interface UUserMapper extends BaseMapper<UUser> {
    /**
     * 插入扩展表
     */
    @Insert("INSERT INTO `u_user_extend` (`uid`) VALUES (#{uid})")
    public int insertUserExtend(@Param("uid") String uid);


    @Update("UPDATE u_user_extend SET money=money+#{money} WHERE uid=#{uid}")
    Integer updUserMoney(@Param("money") Integer money, @Param("uid") String uid);


    @Update("UPDATE u_user_extend SET money=money-#{money} WHERE uid=#{uid}")
    Integer updSubtractionMoney(@Param("money") Double money, @Param("uid") String uid);

    @Update("UPDATE u_user_extend SET total_money=total_money+#{money} WHERE uid=#{uid}")
    Integer updUserTotalMoney(@Param("money") Integer money, @Param("uid") String uid);

    /**
     * 插入关系表
     *
     * @param uid
     * @param uidOne 上一级uid
     * @return
     */
    @Insert("INSERT INTO `u_user_relation` (`u_id`, `u_id_one`) VALUES (#{uid}, #{uidOne})")
    public int insertUserRelation(@Param("uid") String uid, @Param("uidOne") String uidOne);

    /**
     * 插入收款方式
     *
     * @param uid
     * @param id
     * @return
     */
    @Insert("INSERT INTO `u_user_payee` (`id`, `uid`) VALUES (#{id}, #{uid})")
    public int insertUserPayee(@Param("uid") String uid, @Param("id") String id);

    /**
     * 更新收款方式
     *
     * @param uid
     * @param number   账号
     * @param imageUrl 图片
     * @return
     */
    @Update("UPDATE `u_user_payee` SET `number`=#{number}, `image_url`=#{imageUrl} WHERE `uid`=#{uid}")
    public int updateUserPayee(@Param("uid") String uid, @Param("number") String number, @Param("imageUrl") String imageUrl);


    /**
     * 用户中心显示数据
     *
     * @param id
     * @return
     */
    public UUser findUserCount(@Param("id") String id);


    /**
     * 用户个人中详情
     *
     * @param id
     * @return
     */
    public Map<String, Object> findUserDetails(@Param("id") String id);

    /**
     * 获取登录人推荐人列表
     *
     * @param id
     * @param hidePhone 是否隐藏手机号码， 隐藏设为null
     * @return
     */
    public IPage<UUser> fidnUserfriend(Page page, @Param("id") String id, @Param("hidePhone") String hidePhone);

    /**
     * 条件查询
     *
     * @param page
     * @param arg
     * @return
     */
    public IPage<UUser> findUserList(Page page, @Param("arg") BasicDTO arg);


}