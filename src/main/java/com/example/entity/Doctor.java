package com.example.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 医生
*/
@Data
public class Doctor extends Account implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID */
    private Integer id;
    /** 用户名 */
    private String username;
    /** 密码 */
    private String password;
    /** 姓名 */
    private String name;
    /** 电话 */
    private String phone;
    /** 邮箱 */
    private String email;
    /** 头像 */
    private String avatar;
    /** 角色标识 */
    private String role;
    private String description;
    private Double price;
    private String time;
    private String position;
    private Integer departmentId;

    private String departmentName;
    private String week;
    private Integer num;
    private Date create_date;
}