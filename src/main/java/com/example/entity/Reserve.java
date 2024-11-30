package com.example.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 预约挂号表
*/
@Data
public class Reserve implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID */
    private Integer id;
    private Integer doctorId;
    private Integer userId;
    private String time;
    private String status;

    private String doctorName;
    private String userName;
}