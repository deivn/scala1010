package com.ymw.love.system.entity.advisory;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author zjc
 * @version 1.1
 * advisory type entity
 * @date 2019-08-08
 */
@Data
@TableName("u_advisory_type")
public class AdvisoryTypeEntity extends Model<AdvisoryTypeEntity> {

    @TableId(type = IdType.ID_WORKER_STR)
    private String id;

    private String typeName;

    private Integer typeStatus;

    private LocalDateTime addTime;

    @TableField(exist = false)
    private Integer pageSum = 1;

    @TableField(exist = false)
    private Integer pageSize = 10;
}
