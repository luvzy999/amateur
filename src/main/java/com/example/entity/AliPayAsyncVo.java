package com.example.entity;

import lombok.Data;

@Data
public class AliPayAsyncVo {
        private String gmt_create;
        private String charset;
        private String gmt_payment;
        private String notify_time;
        private String subject;
        private String sign;
        /** 支付者的id */
        private String buyer_id;
        /** 订单的信息 */
        private String body;
        /** 支付金额 */
        private String invoice_amount;
        private String version;
        /** 通知id */
        private String notify_id;
        private String fund_bill_list;
        /** 通知类型； trade_status_sync */
        private String notify_type;
        /** 订单号 */
        private String out_trade_no;
        /** 支付的总额 */
        private String total_amount;
        /** 交易状态  TRADE_SUCCESS */
        private String trade_status;
        /** 流水号 */
        private String trade_no;
        private String auth_app_id;
        /** 商家收到的款 */
        private String receipt_amount;
        private String point_amount;
        /** 应用id */
        private String app_id;
        /** 最终支付的金额 */
        private String buyer_pay_amount;
        /** 签名类型 */
        private String sign_type;
        /** 商家的id */
        private String seller_id;


        @Override
        public String toString() {
                return "AliPayAsyncVo{" +
                        "charset='" + charset + '\'' +
                        ", notify_time='" + notify_time + '\'' +
                        ", subject='" + subject + '\'' +
                        ", buyer_id='" + buyer_id + '\'' +
                        ", body='" + body + '\'' +
                        ", invoice_amount='" + invoice_amount + '\'' +
                        ", fund_bill_list='" + fund_bill_list + '\'' +
                        ", out_trade_no='" + out_trade_no + '\'' +
                        ", total_amount='" + total_amount + '\'' +
                        ", trade_status='" + trade_status + '\'' +
                        ", trade_no='" + trade_no + '\'' +
                        '}';
        }
}
