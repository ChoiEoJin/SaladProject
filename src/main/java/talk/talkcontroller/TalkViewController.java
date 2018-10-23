package talk.talkcontroller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sun.mail.iap.Response;

import member.model.MemberBean;
import talk.model.TalkBean;
import talk.model.TalkDao;

@Controller
public class TalkViewController {
 
	@Autowired
	TalkDao dao;
	
	@RequestMapping(value="loginChkForTalk.talk")
	public void loginChkForTalk(HttpSession session,HttpServletResponse response,HttpServletRequest request) {
		response.setContentType("text/html; charset=UTF-8");
		System.out.println("세션확인: ");
		System.out.println(session.getAttribute("loginfo")!=null);
		
		try {
			PrintWriter out=response.getWriter();
			if(session.getAttribute("loginfo")==null) {
				out.println("<script>");
				out.println("alert('로그인 후 이용해주세요');");
				out.println("window.close();");
				out.println("</script>");
			}else {
				out.println("<script>");
				out.println("location.href='"+request.getContextPath()+"/view.talk';");
				out.println("</script>");
			}
			out.flush();			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping(value="view.talk",method=RequestMethod.GET)
	public ModelAndView talkview(HttpSession session) {
		
		ModelAndView mav=new ModelAndView();
		mav.setViewName("talkview");
		String userid=((MemberBean)session.getAttribute("loginfo")).getUserid();
		
		if(userid.equals("admin")) {
			
			List<TalkBean> newTalkList=dao.viewNewTalk();
			mav.addObject("newTalkList",newTalkList);
			mav.setViewName("admintalkview");
		}else{
			mav.setViewName("talkview");
			mav.addObject("roomhoner",userid);
			List<TalkBean> list=dao.selectListTalk(userid);
			mav.addObject("list",list);
		}
		
		return mav;
	}
	
	
	
	@RequestMapping(value="insertTalk.talk",method=RequestMethod.POST)
	public ModelAndView insertArticle(@RequestParam("talker") String talker,
			@RequestParam("talk") String talk,
			@RequestParam("roomhoner") String roomhoner,HttpSession session
			) {
		ModelAndView mav=new ModelAndView();
		TalkBean bean=new TalkBean();
		bean.setRoomhoner(roomhoner);
		bean.setTalk(talk);
		bean.setTalker(talker);
		dao.insertTalk(bean);
		List<TalkBean> newtalklist=dao.viewNewTalk();
		session.setAttribute("newChatCount", newtalklist.size());
		
		if(talker.equals("admin")) {
			mav.setViewName("redirect:adminview.talk?roomhoner="+roomhoner);
		}else {
			mav.setViewName("redirect:view.talk");
		}
		
		return mav;
		
	}
	
	@RequestMapping(value="adminview.talk",method=RequestMethod.GET)
	public ModelAndView adminViewChat(@RequestParam("roomhoner") String roomhoner) {
		ModelAndView mav=new ModelAndView();
		
		List<TalkBean> list=dao.selectListTalk(roomhoner);
		mav.addObject("roomhoner",roomhoner);
		mav.addObject("list",list);
		mav.setViewName("talkview");
		
		return mav;
	}
	
	
	@RequestMapping(value="newtalk2.talk",method=RequestMethod.GET)
	public ModelAndView newtalk2(@RequestParam("roomhoner") String roomhoner) {
	
	ModelAndView mav=new ModelAndView();
	mav.setViewName("talkview2");
	List<TalkBean> list=dao.selectListTalk(roomhoner);
	mav.addObject("roomhoner",roomhoner);
	mav.addObject("list",list);
	return mav;
	}
}
