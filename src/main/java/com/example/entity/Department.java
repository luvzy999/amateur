package com.example.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 科室信息表
*/
@Data
public class Department implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID */
    private Integer id;
    /** 科室名称 */
    private String name;
    /** 科室描述 */
    private String description;
}