package com.example.demo.entity;

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
 * @since 2022-06-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Device implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer deviceId;

    private String deviceName;

    private String deviceMechanism;

    private String deviceGroup;

    private String macAddress;

    private String resolvingPower;

    private String deviceState;

    private String deviceSchedule;

    private String deviceSystem;

}
