package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author anonymous
 * @since 2022-06-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ScheduleExamine implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "examine_id", type = IdType.AUTO)
    private Integer examineId;

    private String examineImg;

    private String examineName;

    private Integer devicesNumber;

    private String examineAuthor;

    private LocalDateTime examineUpdate;


}
