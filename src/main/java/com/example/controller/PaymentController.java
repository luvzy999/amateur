package com.example.controller;


import com.alipay.api.AlipayApiException;
import com.example.common.Result;
import com.example.common.config.pay.AliPayConfig;
import com.example.entity.AliPayBean;
import com.example.service.AliPayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 */
@RestController
@RequestMapping("/pay")
public class PaymentController {
    //开启日志
    private static final Logger log = LoggerFactory.getLogger(PaymentController.class);

    @Resource
    AliPayService aliPayService;

    /**
     * 生成用户支付页面
     * @param aliPayBean 订单
     * @return
     * @throws AlipayApiException
     * @throws IOException
     */
    @GetMapping(value = "/alipay",produces = "text/html;charset=UTF-8")
    public void AliPay(AliPayBean aliPayBean, HttpServletResponse response) throws AlipayApiException, IOException {
        response.getWriter().write(aliPayService.pay(aliPayBean));
        response.getWriter().flush();
        response.getWriter().close();
    }

    /**
     * 付款码
     * @return
     * @throws AlipayApiException
     * @throws IOException
     */
    @GetMapping("/qrcode")
    public Result AliPayQR(AliPayBean aliPayBean) throws AlipayApiException {
        String form = aliPayService.pay(aliPayBean);//生成form
       return Result.success(form);
    }

    /**
     * 异步回调
     * @param request
     * @throws AlipayApiException
     */
    @PostMapping("/notify")
    public String callback(HttpServletRequest request){
        return aliPayService.parseAliPayNotify(request);
    }

    @PostMapping("")
    public void asyncDataBase(){
    }

}



