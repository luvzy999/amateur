package com.example.service;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayDataBillSellQueryRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayDataBillSellQueryResponse;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.example.common.config.pay.AliPayConfig;
import com.example.entity.AliPayBean;
import com.example.entity.Order;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;


@Service
public class AliPayService {
    private static final Logger log = LoggerFactory.getLogger(AliPayService.class);
    @Resource
    AliPayConfig aliPayConfig;

    @Resource
    OrderService orderService;

    /**
     * 支付网页
     * @param aliPayBean
     * @return
     * @throws AlipayApiException
     * @throws IOException
     */
    public String pay(AliPayBean aliPayBean) throws AlipayApiException{
        AlipayClient client = new DefaultAlipayClient(aliPayConfig.getGateway(),
                aliPayConfig.getAppId(),
                aliPayConfig.getAppPrivateKey(),
                aliPayConfig.getFormat(),
                aliPayConfig.getCharset(),
                aliPayConfig.getAlipayPublicKey(),
                aliPayConfig.getSignType());

        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setReturnUrl(aliPayConfig.getReturnUrl());
        request.setNotifyUrl(aliPayConfig.getNotifyUrl());//
        String content = JSON.toJSONString(aliPayBean);
        request.setBizContent(content);
        AlipayTradePagePayResponse response = client.pageExecute(request);
        return response.isSuccess()? response.getBody() : response.getCode() + response.getMsg();
    }

    /**
     * 处理阿里异步回调
     * @param request
     * @return
     */
    public String parseAliPayNotify(HttpServletRequest request) {
        Map paramMap = request.getParameterMap();
        Map<String,String> params = new HashMap<>();
        boolean signVerified;
        for(Iterator iter = paramMap.keySet().iterator();iter.hasNext();){
            String name = (String) iter.next();
            String[] values = (String[]) paramMap.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        params.remove("sign_type");
            try {
            signVerified = AlipaySignature.rsaCheckV2(params,
                    aliPayConfig.getAlipayPublicKey(), aliPayConfig.getCharset(),aliPayConfig.getSignType());
        }catch (AlipayApiException e){
            log.info("验签失败,{}",e.getErrMsg());
            return "failure";
        }

        if(!signVerified){
            log.info("验签失败:{}",signVerified);
            return "failure";
        }
        String trade_status = MapUtils.getString(params,"trade_status","");
        log.info("trade_status（交易状态）:{}",trade_status);

        if(trade_status.equals("TRADE_SUCCESS") ||
                trade_status.equals("TRADE_FINISHED")){
            //更新订单信息

            Order order = new Order();
            String out_trade_no = params.get("out_trade_no");
            log.info("out_trade_no===={}",out_trade_no);
            order.setOut_trade_no(out_trade_no);

            String subject = params.get("subject");
            log.info("subject===={}",subject);
            order.setSubject(subject);

            order.setBody(params.get("body"));
            order.setSubject(params.get("subject"));
            order.setTotal_amount(params.get("total_amount"));
            order.setProduct_code(params.get("product_code"));
            orderService.add(order);
            return "success";
        }
        return "success";
    }

    /**
     * 账单查询
     * @param startTime 起始时间
     * @param endTime 结束时间
     * @param alipay_aorder_no 订单号
     * @return
     * @throws AlipayApiException
     */
    public String queryBill(String startTime,String endTime,String alipay_aorder_no) throws AlipayApiException {
        AlipayClient client = new DefaultAlipayClient(aliPayConfig.getGateway(),
                aliPayConfig.getAppId(),
                aliPayConfig.getAppPrivateKey(),
                aliPayConfig.getFormat(),
                aliPayConfig.getCharset(),
                aliPayConfig.getAlipayPublicKey(),
                aliPayConfig.getSignType());
        AlipayDataBillSellQueryRequest request = new AlipayDataBillSellQueryRequest();
        request.setBizContent("{" +
                "  \"start_time\":"+startTime +
                "  ,\"end_time\":" +endTime+
                "  ,\"alipay_aorder_no\":" +alipay_aorder_no+ "}");
        AlipayDataBillSellQueryResponse response = client.pageExecute(request);
        return response.isSuccess()? response.getBody():"查询失败";
    }
}
