/**
 * AJAX检查SESSION是否失效
 * 
 * @param data
 *            AJAX返回数据
 * @param path
 *            跳转地址
 * @returns {Boolean}
 */
function isSessionOnline(data, path) {
	if (data.sessionState == 0) {
		alert("您已掉线，请重新登录");
		window.top.location.href = path;
		return false;
	}
	return true;
}

/**
 * 过滤敏感字符为指定字符
 * 
 * @param str
 *            替换前字符串
 * @returns {String} 替换后字符串
 */
function filterSpecialCharacter(str) {
	// str = '<script>';
	var specialChar = new Array(/</gi, />/gi);// 特殊字符
	var replaceChar = new Array("&lt;", "&gt;");// 对应替换字符
	for ( var i = 0; i < specialChar.length; i++) {
		str = str.replace(specialChar[i], replaceChar[i]);
	}
	return str;
}

/**
 * 日期格式字符串转日期
 * 
 * @param str
 *            日期格式字符串
 * @returns {Date}
 */
function strConvertDate(str) {
	// str = '2012-08-12 23:13:15';
	str = str.replace(/-/g, "/");
	return new Date(str);
}

/**
 * 各浏览器判读
 */
function getExplorer() {
	var explorer = window.navigator.userAgent;
	// ie
	if (explorer.indexOf("MSIE") >= 0) {
		alert("ie");
	}
	// firefox
	else if (explorer.indexOf("Firefox") >= 0) {
		alert("Firefox");
	}
	// Chrome
	else if (explorer.indexOf("Chrome") >= 0) {
		alert("Chrome");
	}
	// Opera
	else if (explorer.indexOf("Opera") >= 0) {
		alert("Opera");
	}
	// Safari
	else if (explorer.indexOf("Safari") >= 0) {
		alert("Safari");
	} else {
		alert("unrecognized");
	}
}

/** ----------------------浏览器操作--------------------------- */
/*
 * //禁止浏览器缓存： response.cachecontrol="no-cache" Response.AddHeader
 * "pragma","no-cache"
 * 
 * //浏览器返回并刷新 window.history.go(-1); window.opener.location.reload(true);
 * location.href = document.referrer;
 */

/** ----------------------/浏览器操作--------------------------- */

document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
	WeixinJSBridge.call('hideToolbar');// 隐藏底部刷新和后退工具
	WeixinJSBridge.call('hideOptionMenu');// 隐藏右上角分享工具
});

document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
	// 发送给好友
	WeixinJSBridge.on('menu:share:appmessage', function(argv) {
		WeixinJSBridge.invoke('sendAppMessage', {
			"appid" : "123",
			"img_url" : "http://bcs.duapp.com/api100/image/logo/lover.jpg",
			"img_width" : "160",
			"img_height" : "160",
			"link" : "http://api100.duapp.com/card/",
			"desc" : "山无陵，天地合，乃敢与君绝。",
			"title" : "爱情贺卡"
		}, function(res) {
			_report('send_msg', res.err_msg);
		})
	});

	// 分享到朋友圈
	WeixinJSBridge.on('menu:share:timeline', function(argv) {
		WeixinJSBridge.invoke('shareTimeline', {
			"img_url" : "http://bcs.duapp.com/api100/image/logo/newyear.jpg",
			"img_width" : "160",
			"img_height" : "160",
			"link" : "http://api100.duapp.com/card/",
			"desc" : "Best wishes for a wonderful new year.",
			"title" : "新年贺卡"
		}, function(res) {
			_report('timeline', res.err_msg);
		});
	});

	// 发送到微博
	WeixinJSBridge.on('menu:share:weibo', function() {
		WeixinJSBridge.invoke('shareWeibo', config, function(res) {
			// _report('shareWeibo', res.err_msg);
		});
	});
}, false);







/* 验证手机号 */
function validateTel(tel){
// var regTel = /^(1[1-9])[0-9]{9}$/i;
	 var regTel = /^1\d{10}$/;
	 if(regTel.test(tel)){
	    return true;
   }else{
	    return false;
	 }
}

/* 验证邮箱 */
function validateMail(mail){
	 var regMail = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
	 if(mail.match(regMail)){
	    return true;
   }else{
	    return false;
	 }
}

/* 密码验证 6-16个字符和数字组合 */
function validatePwdIntensity(pwd){
	  var reg = /^(?=.*\d.*)(?=.*[a-zA-Z].*).{6,16}$/;
	  return reg.test(pwd);
}
