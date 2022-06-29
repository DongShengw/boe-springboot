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
public class Program implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "program_id", type = IdType.AUTO)
    private Integer programId;

    private String programImg;

    private String programName;

    private String resolvingPower;

    private String programTime;

    private String programSize;

    private Integer programState;

    private String programAuthor;

    private String programUpdate;


}
