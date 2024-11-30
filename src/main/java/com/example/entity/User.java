package com.example.entity;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

/**
 * 患者
*/
@Data
public class User extends Account implements Serializable {
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
    /** 账户余额 */
    private Double money;
    /** 创建时间 */
    private Date create_date;
}