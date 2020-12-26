package com.ymw.love.system.entity.coupon;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author zjc
 * @date 2019年8月26日14:05:26
 */
@Data
@TableName("u_coupons")
public class CouponsEntity extends Model<CouponsEntity> {

    @TableId(type = IdType.ID_WORKER_STR)
    private String id;

    private String userId;

    /**
     * 1.救助券 2.爱心券
     */
    private Integer couponsType;

    /**
     * 1.待使用、2.待提现、3.已使用
     */
    private Integer couponsStatus;

    private Integer money;

    private String salvationId;

    private LocalDateTime addTime;

    private String hxPerson;

    private LocalDateTime hxTime;

    private String hxBranch;

    @TableField(exist = false)
    private String phone;

    @TableField(exist = false)
    private String branchId;


    @TableField(exist = false)
    private String beginTime;

    @TableField(exist = false)
    private String endTime;

    @TableField(exist = false)
    private String str;

    @TableField(exist = false)
    private Integer day = 0;

    @TableField(exist = false)
    private Integer optionStatus=0;

    @TableField(exist = false)
    private Integer pageSum = 1;

    @TableField(exist = false)
    private Integer pageSize = 20;


}
