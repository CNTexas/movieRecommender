package zjn.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import zjn.dao.RatingDao;
import zjn.model.Rating;
import zjn.model.User;

public class MovieRatingServlet extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String userIDSting = request.getParameter("userID");
		String movieIDString = request.getParameter("movieID");
		String scoreString = request.getParameter("score");
		String message = "";
		int userID = 0;
		int movieID = 0;
		int score = 0;
		
		Rating rate = new Rating();
		if(userIDSting != null && !"".equals(userIDSting)){
			userID = Integer.parseInt(userIDSting);	
		}else{
			throw new ServletException("������û�idΪ��");
		}
		
		if(movieIDString != null && !"".equals(movieIDString)){
			movieID = Integer.parseInt(movieIDString);	
		}else{
			throw new ServletException("����ĵ�ӰidΪ��");
		}
		
		if(scoreString != null && !"".equals(scoreString)){
			score = Integer.parseInt(scoreString);	
		}else{
			throw new ServletException("����ķ���Ϊ��");
		}
		
		if(userID >0 && movieID>0 && score>0f){
			rate.setMovie_id(movieID);
			rate.setUser_id(userID);
			rate.setRating(score);
		}else{
			message = "�������������";
			writeJSON(response,message);
		}
		
		if(rate != null){
			RatingDao.insertRating(rate);
			message = "��������ѱ��浽���ݿ�";
			writeJSON(response,message);
		}

	}
	
	private static void writeJSON(HttpServletResponse response, String message) throws IOException {
	    response.setContentType("text/plain");
	    response.setCharacterEncoding("UTF-8");
	    response.setHeader("Cache-Control", "no-cache");
	    PrintWriter writer = response.getWriter();
	   // writer.print(user.toJSON());
	    String str="{\"message\""+":\""+message+"\"}";
	    System.out.println(str);
	    writer.print(str);
	    
	    
	  }

}
