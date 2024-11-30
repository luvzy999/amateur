package com.example.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 公告信息表
*/
@Data
public class Registration implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID */
    private Integer id;
    private Integer userId;
    private String room;
    private Double price;
    private String status;
    private String medicine;
    private String hosStatus;
    private String userName;
}