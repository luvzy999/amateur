package com.example.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 就诊记录表
*/

@Data
public class Record implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID */
    private Integer id;
    private Integer userId;
    private Integer doctorId;
    private String time;
    private String medicalRecord;
    private String inhospital;
    private String inhostpitalRecord;

    private String userName;
    private String doctorName;
}