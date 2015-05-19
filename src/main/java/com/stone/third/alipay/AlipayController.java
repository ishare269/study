package com.stone.third.alipay;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.stone.third.alipay.config.AlipayConfig;
import com.stone.third.alipay.util.AlipayNotify;

/**
 * 
 * <p>Class Name: AlipayController</p>
 * <p>Description: 支付宝支付</p>
 * <p>Sample: 该类的典型使用方法和用例</p>
 * <p>Author: 石涛</p>
 * <p>Date: 2014-8-28</p>
 * <p>Modified History: 修改记录，格式(Name)  (Version)  (Date) (Reason & Contents)</p>
 */
@Controller
@RequestMapping("/alipay")
public class AlipayController {
	/**
	 * 
	 * 描述: 发起购买页面
	 * @return
	 * @author     石涛
	 * date        2014-8-28
	 * --------------------------------------------------
	 * 修改人    	      修改日期       修改描述
	 * 石涛                    2014-8-28       创建
	 * --------------------------------------------------
	 * @Version  Ver1.0
	 */
	@RequestMapping(value="/goodspage",produces={"text/html;charset=utf-8"})
	public ModelAndView goodsPage(){
		return new ModelAndView("alipay/merchant");
	}
	
	/**
	 * 
	 * 描述: 跳转支付宝支付页面
	 * @return
	 * @author     石涛
	 * date        2014-8-28
	 * --------------------------------------------------
	 * 修改人    	      修改日期       修改描述
	 * 石涛                    2014-8-28       创建
	 * --------------------------------------------------
	 * @Version  Ver1.0
	 */
	@RequestMapping(value="/alipaypage",produces={"text/html;charset=utf-8"})
	public ModelAndView alipayPage(@RequestParam(value="money",defaultValue="0.01") double money){
		ModelAndView mv = new ModelAndView();
		mv.addObject("WIDseller_email","ivoxcn@163.com");
		mv.addObject("WIDout_trade_no",UUID.randomUUID().toString());
		mv.addObject("WIDsubject","test");
		mv.addObject("WIDtotal_fee",money);
		mv.addObject("WIDbody","test api");
		mv.addObject("WIDshow_url","www.tuixiang.com/reg.jsp");
		mv.setViewName("alipay/alipayapi");
		return mv;
	}
	
	/**
	 * 
	 * 描述: 服务器异步通知页面路径
	 * @return
	 * @author     石涛
	 * date        2014-8-28
	 * --------------------------------------------------
	 * 修改人    	      修改日期       修改描述
	 * 石涛                    2014-8-28       创建
	 * --------------------------------------------------
	 * @Version  Ver1.0
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/notify",produces={"text/html;charset=utf-8"})
	public ModelAndView notifyPage(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv = new ModelAndView();
		// 获取支付宝POST过来反馈信息
		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter
				.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
		}
		if(AlipayNotify.verify(params,AlipayConfig.key,AlipayConfig.partner)){
			mv.addObject("result","1");
		}else{
			mv.addObject("result",0);
		}
		mv.setViewName("alipay/notify_url");
		return mv;
	}
	
	/**
	 * 
	 * 描述: 同步通知页面路径
	 * @return
	 * @author     石涛
	 * date        2014-8-28
	 * --------------------------------------------------
	 * 修改人    	      修改日期       修改描述
	 * 石涛                    2014-8-28       创建
	 * --------------------------------------------------
	 * @Version  Ver1.0
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/return",produces={"text/html;charset=utf-8"})
	public ModelAndView returnPage(HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		try {
			// 获取支付宝POST过来反馈信息
			Map<String, String> formParams = new HashMap<String, String>();
			Map requestParams = request.getParameterMap();
			for (Iterator iter = requestParams.keySet().iterator(); iter
					.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
				// valueStr = new String(valueStr.getBytes("ISO-8859-1"),
				// "gbk");
				formParams.put(name, valueStr);
			}

			// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
			// 商户订单号

			String formId = new String(request.getParameter("out_trade_no").getBytes(
					"utf-8"), "UTF-8");

			// 支付宝交易号
			String trade_no = new String(request.getParameter("trade_no").getBytes("utf-8"), "UTF-8");

			// 交易状态
			String formState = new String(request.getParameter("trade_status")
					.getBytes("utf-8"), "UTF-8");
			
			//交易金额
			String returnTotalFee =new String(request.getParameter("total_fee")
					.getBytes("utf-8"), "UTF-8"); 
			mv.addObject("formParams", formParams);
			mv.addObject("formId", formId);
			mv.addObject("trade_no", trade_no);
			mv.addObject("formState", formState);
			mv.addObject("returnTotalFee", returnTotalFee);
			mv.setViewName("alipay/return_url");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return mv;
	}
}
