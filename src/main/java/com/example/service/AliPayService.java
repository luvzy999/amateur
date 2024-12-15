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
     * @param order
     * @return
     * @throws AlipayApiException
     * @throws IOException
     */
    public String pay(Order order) throws AlipayApiException{
        log.info("======================================================================{}",order.getCreate_date());
        AlipayClient client = new DefaultAlipayClient(aliPayConfig.getGateway(),
                aliPayConfig.getAppId(),
                aliPayConfig.getAppPrivateKey(),
                aliPayConfig.getFormat(),
                aliPayConfig.getCharset(),
                aliPayConfig.getAlipayPublicKey(),
                aliPayConfig.getSignType());
        log.info("配置:{}",aliPayConfig.toString());
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();

        AliPayBean aliPayBean = new AliPayBean();
        aliPayBean.setQr_pay_mode(order.getQr_pay_mode());
        aliPayBean.setSubject(order.getSubject());
        aliPayBean.setProduct_code(order.getProduct_code());
        aliPayBean.setTotal_amount(order.getTotal_amount());
        aliPayBean.setOut_trade_no(order.getOut_trade_no());

        String content = JSON.toJSONString(aliPayBean);
        request.setBizContent(content);
        request.setReturnUrl(aliPayConfig.getReturnUrl());
        request.setNotifyUrl(aliPayConfig.getNotifyUrl());//

        log.info("bizContent:{}",content);
        AlipayTradePagePayResponse response = client.pageExecute(request);
        String result = response.isSuccess()? response.getBody() : response.getCode() + response.getMsg();
        log.info("请求结束：{}",result);
        //将支付信息写入数据库
        orderService.add(order);

        return result;
    }

    /**
     * 处理阿里异步回调
     * @param request
     * @return
     */
    public String parseAliPayNotify(HttpServletRequest request) {
        Map paramMap = request.getParameterMap();
        log.info("{}",paramMap.keySet());
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
            /**
             *
             * 验证支付信息
             */

            Order order = new Order();
            order.setOut_trade_no(paramMap.get("out_trade_no").toString());
            order.setTrade_state("1");//更新订单支付状态
            orderService.updateById(order);
            return "success";
        }
        return "success";
    }

    /**
     * 账单查询
     * @param startTime 起始时间 格式：2019-01-02 00:00:00
     * @param endTime 结束时间 格式:同上
     * @return
     * @throws AlipayApiException
     */
    public String queryBillByTime(String startTime,String endTime) throws AlipayApiException {
        AlipayClient client = new DefaultAlipayClient(aliPayConfig.getGateway(),
                aliPayConfig.getAppId(),
                aliPayConfig.getAppPrivateKey(),
                aliPayConfig.getFormat(),
                aliPayConfig.getCharset(),
                aliPayConfig.getAlipayPublicKey(),
                aliPayConfig.getSignType());
        AlipayDataBillSellQueryRequest request = new AlipayDataBillSellQueryRequest();
        request.setBizContent("{" +
                "  \"start_time\":\"2019-01-01 00:00:00\"," +
                "  \"end_time\":\"2019-01-02 00:00:00\"," +
                "  \"alipay_order_no\":\"20190101***\"," +
                "  \"merchant_order_no\":\"TX***\"," +
                "  \"store_no\":\"门店1\"," +
                "  \"page_no\":\"1\"," +
                "  \"page_size\":\"2000\"" +
                "}");
        AlipayDataBillSellQueryResponse response = client.pageExecute(request);
        return response.isSuccess()? response.getBody():"查询失败";
    }
    public Object queryBillByAlipayNum(String alipay_order_no) throws AlipayApiException {
        AlipayClient client = new DefaultAlipayClient(aliPayConfig.getGateway(),
                aliPayConfig.getAppId(),
                aliPayConfig.getAppPrivateKey(),
                aliPayConfig.getFormat(),
                aliPayConfig.getCharset(),
                aliPayConfig.getAlipayPublicKey(),
                aliPayConfig.getSignType());
        AlipayDataBillSellQueryRequest request = new AlipayDataBillSellQueryRequest();
        request.setBizContent("{" +
                "  \"alipay_order_no\":"+alipay_order_no+"}");
        AlipayDataBillSellQueryResponse response = client.pageExecute(request);
        return response.isSuccess()? response.getDetailList():"查询失败";
    }
}
