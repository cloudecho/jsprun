package cn.jsprun.utils;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.util.MessageResources;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import cn.jsprun.service.DataBaseService;
public class Jfreechart extends HttpServlet {
	private static final long serialVersionUID = -205545450335033053L;
	public void destroy() {
		super.destroy();
	}
	@SuppressWarnings("deprecation")
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		MessageResources mr = Common.getMessageResources(request);
		Locale locale = Common.getUserLocale(request);
		String arear = request.getParameter("arears");
		if(arear!=null && arear.equals("changjiang")){
			DefaultPieDataset data = getDataSet(arear,mr,locale);
			JFreeChart chart = ChartFactory.createPieChart3D(mr.getMessage(locale, "jfreechar_changjiang"), data, true, false, false);
			try {
				ChartUtilities.writeChartAsJPEG(response.getOutputStream(), 1.0f, chart, 400, 300, null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else if(arear!=null && arear.equals("huanghe")){
			DefaultPieDataset data = getDataSet(arear,mr,locale);
			JFreeChart chart = ChartFactory.createPieChart3D(mr.getMessage(locale, "jfreechar_huanghei"),data,true,false,false);
			try {
				ChartUtilities.writeChartAsJPEG(response.getOutputStream(), 1.0f, chart, 400, 300, null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			DefaultPieDataset data = getDataSet(arear,mr,locale);
			JFreeChart chart = ChartFactory.createPieChart3D(mr.getMessage(locale, "jfreechar_member"),data,true,false,false);
			try {
				ChartUtilities.writeChartAsJPEG(response.getOutputStream(), 1.0f, chart, 400, 300, null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	public void init() throws ServletException {
	}
	private DefaultPieDataset getDataSet(String res,MessageResources mr,Locale locale) {
		java.text.DecimalFormat df = new java.text.DecimalFormat("0.00");
		DefaultPieDataset dataset = new DefaultPieDataset();
		List<Map<String,String>> memberlist = ((DataBaseService)BeanFactory.getBean("dataBaseService")).executeQuery("select regip from jrun_members");
		IPSeeker seeker  = IPSeeker.getInstance();
		if(memberlist!=null && memberlist.size()>0){
			if(res.equals("changjiang")){
				String north[]= {"辽宁","吉林","河南","河北","陕西","山西","黑龙江","甘肃","内蒙古","北京市","天津市","山东"};
				int northint = 0;
				int southint = 0;
				for(Map<String,String> members:memberlist){
					String address = seeker.getAddress(members.get("regip"),mr,locale);
					boolean flag = false;
					for(int j=0;j<north.length;j++){
						if(address.indexOf(north[j])!=-1){
							northint++;
							flag = true;
						}
					}
					if(!flag){
						southint++;
					}
				}
				double percentnorth = (double)northint/(double)(northint+southint)*100;
				double percentsouth = (double)southint/(double)(northint+southint)*100;
				dataset.setValue(mr.getMessage(locale, "jfreechar_changjiang_north")+df.format(percentnorth)+"%", northint);
				dataset.setValue(mr.getMessage(locale, "jfreechar_changjiang_south")+df.format(percentsouth)+"%", southint);
			}else if(res.equals("huanghe")){
				dataset.setValue(mr.getMessage(locale, "jfreechar_huanghei_nodata"),0);
			}else{
				String north[]= {"电信","网通","铁通","手机","联通","移动","其它"};
				int telecom = 0;
				int reticle = 0;
				int tianton = 0;
				int souji = 0;
				int lianton = 0;
				int yidong = 0;
				int other = 0;
				for(Map<String,String> members:memberlist){
					String address = seeker.getAddress(members.get("regip"),mr,locale);
					if(address.indexOf(north[0])!=-1){
						telecom++;
					}else if(address.indexOf(north[1])!=-1){
						reticle++;
					}else if(address.indexOf(north[2])!=-1){
						tianton++;
					}else if(address.indexOf(north[3])!=-1){
						souji++;
					}else if(address.indexOf(north[4])!=-1){
						lianton++;
					}else if(address.indexOf(north[5])!=-1){
						yidong++;
					}else{
						other++;
					}
				}
				double percenttele = (double)telecom/(double)(telecom+reticle+other+tianton+souji+lianton+yidong)*100;
				double percentreticle = (double)reticle/(double)(telecom+reticle+other+tianton+souji+lianton+yidong)*100;
				double percentother = (double)other/(double)(telecom+reticle+other+tianton+souji+lianton+yidong)*100;
				double percenttianton = (double)tianton/(double)(telecom+reticle+other+tianton+souji+lianton+yidong)*100;
				double percentsouji = (double)souji/(double)(telecom+reticle+other+tianton+souji+lianton+yidong)*100;
				double percentlianton = (double)lianton/(double)(telecom+reticle+other+tianton+souji+lianton+yidong)*100;
				double percentyidong = (double)yidong/(double)(telecom+reticle+other+tianton+souji+lianton+yidong)*100;
				dataset.setValue(mr.getMessage(locale, "jfreechar_telecom")+df.format(percenttele)+"%", telecom);
				dataset.setValue(mr.getMessage(locale, "jfreechar_reticle")+df.format(percentreticle)+"%", reticle);
				dataset.setValue(mr.getMessage(locale, "jfreechar_iron")+df.format(percenttianton)+"%", tianton);
				dataset.setValue(mr.getMessage(locale, "jfreechar_combined")+df.format(percentsouji)+"%", souji);
				dataset.setValue(mr.getMessage(locale, "jfreechar_couplet")+df.format(percentlianton)+"%", lianton);
				dataset.setValue(mr.getMessage(locale, "jfreechar_move")+df.format(percentyidong)+"%", yidong);
				dataset.setValue(mr.getMessage(locale, "jfreechar_other")+df.format(percentother)+"%", other);
			}
		}
		memberlist = null;
		return dataset;
	}
}
