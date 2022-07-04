package com.example.demo.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author anonymous
 * @since 2022-07-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PubNotice implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "pub_notice_id", type = IdType.AUTO)
    private Integer pubNoticeId;

    private String pubNoticeContent;

    private String pubNoticeTextcolor;

    private Integer pubNoticeTextsize;

    private String pubNoticeBgcolor;

    private Integer pubNoticeBghigh;

    private String pubNoticeTexthigh;

    private String pubNoticePlayspeed;


}
