package com.ymw.love.system.entity.sensitive;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Sensitive words
 * @author zjc
 * @date 2019年8月23日16:39:43
 */
@Data
@TableName("sys_sensitive")
public class SensitiveEntity extends Model<SensitiveEntity>{

    @TableId(type = IdType.ID_WORKER_STR)
    private String id;

    private String sensitiveText;

    private LocalDateTime addTime;

}
