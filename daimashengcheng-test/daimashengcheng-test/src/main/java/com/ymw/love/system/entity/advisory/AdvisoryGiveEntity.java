package com.ymw.love.system.entity.advisory;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author zjc
 * @version 1.1
 * @date 2019-08-02
 */
@Data
@TableName("u_advisory_give")
public class AdvisoryGiveEntity {

    @TableId(type = IdType.ID_WORKER_STR)
    private String id;

    private String userId;

    private String advisoryId;

    private LocalDateTime addTime;
}
