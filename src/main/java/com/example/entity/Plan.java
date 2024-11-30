package com.example.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 排班信息表
*/
@Data
public class Plan implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID */
    private Integer id;
    private Integer doctorId;
    private Integer num;
    private String week;

    private String doctorName;
    private String departmentName;
}