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
@TableName("u_withdraw_record")
public class WithdrawRecordEntity extends Model<WithdrawRecordEntity> {

    @TableId(type = IdType.ID_WORKER_STR)
    private String id;

    private String userId;

    private Double money;

    private Integer billingMethod;

    private Integer status;

    private LocalDateTime addTime;

    private LocalDateTime withdrawTime;

    private String account;


    @TableField(exist = false)
    private Integer pageSum = 1;

    @TableField(exist = false)
    private Integer pageSize = 20;
}
