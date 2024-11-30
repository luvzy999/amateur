package com.example.entity;

import lombok.Data;

@Data
public class AliPayBean {

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

    /**
     * 二维码前置
     */
//    private String qr_pay_mode = "1";

    /**
     * 超时时间
     */
    private String time_out = "10m";



    @Override
    public String toString() {
        return "AliPayBean{" +
                "out_trade_no='" + out_trade_no + '\'' +
                ", subject='" + subject + '\'' +
                ", total_amount='" + total_amount + '\'' +
                ", body='" + body + '\'' +
                ", product_code='" + product_code + '\'' +
//                ", qr_pay_mode='" + qr_pay_mode + '\'' +
                '}';
    }
}
