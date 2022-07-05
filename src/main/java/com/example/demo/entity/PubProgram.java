package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2022-07-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PubProgram implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "pub_program_id", type = IdType.AUTO)
    private Integer pubProgramId;

    private String pubProgramImg;


}
