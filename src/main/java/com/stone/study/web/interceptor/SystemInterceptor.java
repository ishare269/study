package com.stone.study.web.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.stone.study.util.ConstantsUtil;

public class SystemInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		// 后台session控制
		String[] noFilters = new String[] { "user/loginpage" };
		String uri = request.getRequestURI();
		if (uri.indexOf("resources") != -1) {
			return true;
		}
		boolean beFilter = false;
		for (String s : noFilters) {
			if (uri.indexOf(s) != -1) {
				beFilter = false;
				break;
			}
		}
		if (beFilter) {
			Object obj = request.getSession().getAttribute(
					ConstantsUtil._LOGIN_USER);
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ request.getContextPath() + "/";
			if (null == obj) {
				if (request.getHeader("x-requested-with") != null
						&& request.getHeader("x-requested-with")
								.equalsIgnoreCase("xmlhttprequest")) {// ajax请求
					PrintWriter printWriter = response.getWriter();
					printWriter.print("{\"sessionState\":0}");
					printWriter.flush();
					printWriter.close();
					return false;
				} else { // 普通http请求
					PrintWriter out = response.getWriter();
					StringBuilder builder = new StringBuilder();
					builder.append("<script type=\"text/javascript\" charset=\"UTF-8\">");
					// builder.append("alert(\"页面过期，请重新登录\");");
					builder.append("window.top.location.href=\"");
					builder.append(basePath);
					builder.append("user/loginpage\";</script>");
					out.print(builder.toString());
					out.close();
					return false;
				}
			}
		}
		return true;
	}

}
