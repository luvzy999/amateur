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
     * 产品编号
     */
    private String product_code;

    private String  qr_pay_mode="1";


    @Override
    public String toString() {
        return "AliPayBean{" +
                "out_trade_no='" + out_trade_no + '\'' +
                ", subject='" + subject + '\'' +
                ", total_amount='" + total_amount + '\'' +
                ", product_code='" + product_code + '\'' +
//                ", qr_pay_mode='" + qr_pay_mode + '\'' +
                '}';
    }
}
