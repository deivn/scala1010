package com.ymw.love.system.entity.coupon;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * @author zjc
 * @date 2019年8月26日14:05:26
 */
@Data
@TableName("u_user_extend")
public class UserExtendEntity extends Model<UserExtendEntity> {

      @TableId(type = IdType.ID_WORKER_STR)
      private String uid;

      private Integer essayCount;

      private Integer score;

      private Double totalMoney;

      private Double money;

      private Integer faqsCount;
}
