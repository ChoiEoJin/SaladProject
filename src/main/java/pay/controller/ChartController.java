package pay.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import pay.model.PayDao;

@Controller
public class ChartController {
	
	@Autowired
	PayDao paydao;
	
	@RequestMapping("chart.pay")
	public void printChart(HttpServletRequest request,HttpServletResponse response) {

		response.setContentType("text/html; charset=UTF-8");
		String date1str=request.getParameter("date1");
		String date2str=request.getParameter("date2");
		date1str=date1str.replaceAll("-", "/");
		date2str=date2str.replaceAll("-", "/");
		
		Date date1=new Date(date1str);
		Date date2=new Date(date2str);
		
		long date1long=date1.getTime();
		long date2long=date2.getTime();
		long subdate=date2long-date1long;
		long subday=subdate/(24 * 60 * 60 * 1000);
		SimpleDateFormat sdf=new SimpleDateFormat("yy/MM/dd");
		System.out.println(subday);
		
		ArrayList<String> list=new ArrayList<String>();
		
		for(long i=0;i<=subday;i++) {
			long a=date1long+i*(24 * 60 * 60 * 1000);
			Date date3=new Date(a);
			String date3str=sdf.format(date3);
			list.add(date3str);
		}

		ArrayList<String> list2=paydao.chart(list);
		
		
		try {
			PrintWriter out=response.getWriter();
			out.println("<script>");
			out.println("function drawChart3() {");
			out.println("var chart_options = {");
			out.println("title : '일별 매출',");
			out.println("width : 1024,");
			out.println("height : 400,");
			out.println("bar : {groupWidth : '60%'},");
			out.println("legend : {position : 'right'}};");
			out.println("var data = google.visualization.arrayToDataTable([");
			out.println("['날짜', '가격',{role:'annotation'}],");
			
			for(int i=0;i<list2.size();i++) {
				StringTokenizer stk=new StringTokenizer(list2.get(i), ",");
				while(stk.hasMoreTokens()) {
					String day=stk.nextToken();
					String sales=stk.nextToken();
					out.print("['"+day+"',"+sales+",'"+sales+"원'],");
				}
			}
			
			
			out.println("]);");
			out.println("var chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));");
			out.println("chart.draw(data, chart_options);}");
			out.println("drawChart3()");
			out.println("</script>");
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping("salesPersonAnalytics.pay")
	public void goPersonAnalytics(HttpServletResponse response,HttpServletRequest request) {
		
		ModelAndView mav=new ModelAndView();
		int totalCount2=paydao.getTotalCount2();
		double avgAge=paydao.getavgAge(totalCount2);
		double sungbi=paydao.getSungbi(totalCount2);
		ArrayList<String> addressList=paydao.getAddress(totalCount2);
		ArrayList<String> paymetList=paydao.getPayMet(totalCount2);

		response.setContentType("text/html; charset=UTF-8");
		try {
			PrintWriter out=response.getWriter();
			out.println("<script type=\"text/javascript\">");
			out.println("function drawChart() {");
			out.println("var data = google.visualization.arrayToDataTable([");
			out.println("['결제수단', '수치'],");
			for(int i=0;i<paymetList.size();i++) {
				StringTokenizer stk=new StringTokenizer(paymetList.get(i),",");
				String name=stk.nextToken();
				String num=stk.nextToken();
				out.println("['"+name+"',"+num+"],");
			}
			out.println("]);");
			out.println(" var options = {");
			out.println("title: '결제수단 분포',");
			out.println("pieSliceText: 'label',");
			out.println("pieHole: 0.3,");
			out.println("};");
			out.println("var chart = new google.visualization.PieChart(document.getElementById('div1'));");
			out.println("chart.draw(data, options);");
			out.println("}");
			
			out.println("function drawChart2() {");
			out.println("var data = google.visualization.arrayToDataTable([");
			out.println("['지역', '수치'],");
			for(int i=0;i<addressList.size();i++) {
				StringTokenizer stk=new StringTokenizer(addressList.get(i),",");
				String name=stk.nextToken();
				String num=stk.nextToken();
				out.println("['"+name+"',"+num+"],");
			}
			out.println("]);");
			out.println(" var options = {");
			out.println("title: '결제고객 지역 분포',");
			out.println("pieSliceText: 'label',");
			out.println("pieHole: 0.3,");
			out.println("};");
			out.println("var chart = new google.visualization.PieChart(document.getElementById('div2'));");
			out.println("chart.draw(data, options);");
			out.println("}");
			
			out.println("function sungbiprint() {");
			out.println("var mansungbi="+sungbi+"*1*100");
			out.println("var womansungbi=100-mansungbi;");
			out.println("$('#mansungbi').html(mansungbi+'%');");
			out.println("$('#womansungbi').html(womansungbi+'%');");
			out.println("}");
			
			out.println("function avgAgeprint() {");
			out.println("var avgAge="+avgAge+";");
			out.println("$('#div4').html(avgAge+'세');");
			out.println("}");
			

			out.println("</script>");
			out.flush();


		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
