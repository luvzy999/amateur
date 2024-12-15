package com.example.entity;


import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

/**
 * 支付宝订单信息实体类
 */
@Data
@Component
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID */
    private Integer id;
    /**
     * 商户订单号
     */
    private String out_trade_no;
    /**
     * 订单名称
     */
    private String subject;
    /**
     * 付款金额
     */
    private String total_amount;
    /**
     * 商品描述，可空
     */
    private String body;
    /**
     * 产品编号
     */
    private String product_code;

    private String user_id;

    private String qr_pay_mode;

    private Date create_date;

    private String trade_state;
    @Override
    public String toString() {
        return "Order{" +
                "out_trade_no='" + out_trade_no + '\'' +
                ", subject='" + subject + '\'' +
                ", total_amount='" + total_amount + '\'' +
                ", describe='" + body + '\'' +
                ", product_code='" + product_code + '\'' +
                '}';
    }
}
