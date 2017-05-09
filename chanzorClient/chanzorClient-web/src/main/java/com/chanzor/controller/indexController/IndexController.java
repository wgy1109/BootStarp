package com.chanzor.controller.indexController;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.chanzor.controller.base.BaseController;
import com.chanzor.entity.SpInfo;
import com.chanzor.util.Const;

/**
 * 首页controller 参数
 * 
 * @author Administrator
 *
 */
@Controller
public class IndexController extends BaseController {

	/**
	 * @return
	 */
	@RequestMapping("")
	public String main() {

		// return "index/proxyIndex";
		return "index/index";
	}

	@RequestMapping("main.html")
	public String toMain() {
		return "main/main";
	}

	@RequestMapping("index")
	public ModelAndView index(HttpSession session) {
		Map<String, Object> userInfo = (Map<String, Object>) session.getAttribute(Const.SESSION_USER);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("main/main");
		if (userInfo.get("photo_img") != null) {
			mv.addObject("photo_img", properties.getNginx_url() + userInfo.get("photo_img"));
		}
		return mv;
	}

	@RequestMapping("demo")
	public String demo() {
		return "main/demo";
	}
}
