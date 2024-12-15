package com.example.controller;


import com.alipay.api.AlipayApiException;
import com.example.common.Result;
import com.example.common.config.pay.AliPayConfig;
import com.example.entity.AliPayBean;
import com.example.entity.Order;
import com.example.service.AliPayService;
import com.example.service.OrderService;
import io.swagger.annotations.Api;
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
@Api(tags = "支付宝充值操作接口")
@RestController
@RequestMapping("/pay")
public class PaymentController {
    //开启日志
    private static final Logger log = LoggerFactory.getLogger(PaymentController.class);

    @Resource
    AliPayService aliPayService;

    /**
     * 处理用户支付
     * @param order 订单信息
     * @return
     * @throws AlipayApiException
     * @throws IOException
     */
    @GetMapping(value = "/alipay")
    public void AliPay(Order order, HttpServletResponse response) throws AlipayApiException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(aliPayService.pay(order));
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
    public Result AliPayQR(Order order) throws AlipayApiException {
        String form = aliPayService.pay(order);//生成form
        return Result.success(form);
    }

    /**
     * 异步回调
     * @param request
     * @throws AlipayApiException
     */
    @PostMapping("/notify")
    public String callback(HttpServletRequest request) throws AlipayApiException {
        return aliPayService.parseAliPayNotify(request);
    }

}

