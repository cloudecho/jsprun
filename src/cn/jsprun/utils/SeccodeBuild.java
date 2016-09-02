package cn.jsprun.utils;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
public class SeccodeBuild extends HttpServlet {
	private static final long serialVersionUID = -205545450335033053L;
	private String randStrs = "ABCDEFGHIGKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	public void destroy() {
		super.destroy(); 
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getImage(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	public void init() throws ServletException {
	}
	public void getImage(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		Map<String,String> settingMap = ForumInit.settings;
		String seccodedataString = settingMap.get("seccodedata");
		Map<String,Object> seccodedata = ((DataParse)BeanFactory.getBean("dataParse")).characterParse(seccodedataString, false);
		String widthString = String.valueOf(seccodedata.get("width"));
		String heightString = String.valueOf(seccodedata.get("height"));
		int width = Integer.parseInt(widthString);
		int height =  Integer.parseInt(heightString);
		int fontSize = width/4;
		int wordPlace = (height/2+fontSize/3);
		BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width, height);
		g.setFont(new Font("ËÎÌו", Font.PLAIN, fontSize));
		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < 155; i++) {
			int x = Common.rand(width);
			int y = Common.rand(height);
			int xl = Common.rand(width);
			int yl = Common.rand(height);
			g.drawLine(x, y , xl, yl);
		}
		int randLen=4;
		StringBuffer sRand = new StringBuffer(randLen);
		for (int i = 0; i < randLen; i++) {
			String rand = String.valueOf(randStrs.charAt(Common.rand(61)));
			sRand.append(rand);
			g.setColor(new Color(20 + Common.rand(110), 20 + Common.rand(110), 20 + Common.rand(110)));
			g.drawString(rand, fontSize * i + fontSize/4, wordPlace);
		}
		g.dispose();
		HttpSession session = request.getSession();
		session.setAttribute("rand", sRand.toString());
		ImageIO.write(image, "JPEG", response.getOutputStream());
		image=null;
	}
	private Color getRandColor(int fc, int bc) {
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + Common.rand(bc - fc);
		int g = fc + Common.rand(bc - fc);
		int b = fc + Common.rand(bc - fc);
		return new Color(r, g, b);
	}
}