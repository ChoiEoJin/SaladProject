package main.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import talk.model.TalkBean;
import talk.model.TalkDao;

@Controller
public class MainController {

	@Autowired
	TalkDao dao;
	
	@RequestMapping(value="main.main")
	public ModelAndView goMain(HttpSession session) {
		ModelAndView mav=new ModelAndView();
		mav.setViewName("main2");
		
		List<TalkBean> newtalklist=dao.viewNewTalk();
		session.setAttribute("newChatCount", newtalklist.size());
		
		return mav;
	}
	
	@RequestMapping(value="map.main")
	public String goMap() {
		return "map";
	}
}
