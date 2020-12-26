package com.ymw.love.system.entity.posters;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.ymw.love.system.util.SnowFlakeUtil;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 海报分享统计
 * </p>
 *
 * @author 
 * @since 2019-08-22
 */
@TableName("u_posters_share")
@Getter
@Setter
public class UPostersShare extends Model<UPostersShare> {

    private static final long serialVersionUID = 1L;

    public UPostersShare(){}

	public UPostersShare(String postersId, Integer appSign){
		SnowFlakeUtil snowFlakeUtil = new SnowFlakeUtil(0, 0);
		this.id = String.valueOf(snowFlakeUtil.nextId());
    	this.postersId = postersId;
    	this.appSign = appSign;
		this.shares = 1;
	}
	@TableField("id")
	private String id;
    /**
     * 海报ID外键
     */
	@TableField("posters_id")
	private String postersId;
    /**
     * 被分享的APP标识 1.微信 2.朋友圈 3.QQ
     */
	@TableField("app_sign")
	private Integer appSign;
    /**
     * 分享次数统计
     */
	@TableField("shares")
	private Integer shares;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
