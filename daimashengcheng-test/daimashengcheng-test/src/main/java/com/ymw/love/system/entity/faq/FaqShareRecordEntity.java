package com.ymw.love.system.entity.faq;

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
@TableName("u_faq_share_record")
public class FaqShareRecordEntity {

    @TableId(type = IdType.ID_WORKER_STR)
    private String id;

    private String faqId;

    private String userId;

    private LocalDateTime addTime;


}
