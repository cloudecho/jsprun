package cn.jsprun.utils;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
public class ImageUtil {
	private static String[] imageFormatArray = new String[] { ".jpg",".gif", ".png", ".bmp",".jpeg"};
	public static String createWaterMarkWithImage(String srcImgPath,String outputImage, String waterMarkImgPath,String zone,float transparency,float imageQuality,int watermarkMinWidth, int watermarkMinHeight) throws IOException {
		imageQuality /= 100;
		String srcType = verifyImageFormat(srcImgPath);
		if (srcType == null||verifyImageFormat(waterMarkImgPath)==null) {
			return "img_notype";
		}
		if((srcType.equals("gif")||srcType.equals("jpg"))&&isCattoon(srcImgPath)){
			return "img_isflash";
		}
		FileInputStream srcFis =new FileInputStream(srcImgPath);
		Image src_image = ImageIO.read(srcFis); 
		srcFis.close();
		int width = src_image.getWidth(null);
		int height = src_image.getHeight(null);
		if(width<watermarkMinWidth||height<watermarkMinHeight){
			return "img_mack_nofinish\t"+width + "\t"+ height;
		}		
		BufferedImage bufferedImage = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
		Graphics graphics=bufferedImage.getGraphics();
		graphics.drawImage(src_image, 0, 0, null);
		src_image.flush();
		src_image=null;
		FileInputStream waterMarkFis =new FileInputStream(waterMarkImgPath);
		Image watermark_image = ImageIO.read(waterMarkFis); 
		waterMarkFis.close();
		int waterMarkImgWidth = watermark_image.getWidth(null);
		int waterMarkImgHeight = watermark_image.getHeight(null);
		if(width<waterMarkImgWidth||height<waterMarkImgHeight){
			return "img_mackimg_less";
		}
		int zoneArray[] = getWriteZoneForImage(zone, width, height, waterMarkImgWidth,waterMarkImgHeight);
		graphics.drawImage(watermark_image, zoneArray[0], zoneArray[1],waterMarkImgWidth, waterMarkImgHeight, null, null);
		graphics.dispose();
		watermark_image.flush();
		watermark_image=null;
		try {
			FileOutputStream out = new FileOutputStream(outputImage);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bufferedImage);
			param.setQuality(imageQuality, true);
			encoder.encode(bufferedImage, param);
			out.close();
			bufferedImage.flush();
			bufferedImage = null;
			param=null;
			encoder=null;
		} catch (Exception e) {
			return e.getMessage();
		}
		return null;
	}
	public static String createWaterMarkWithCharacter(String srcImagePath, String outputImagePath,int watermarkminwidth,
			String watermarktext_text,String watermarktext_fontpath,int watermarktext_size,
			int watermarktext_angleInt,String watermarktext_color,int watermarktext_shadowx,
			int watermarktext_shadowy,String watermarktext_shadowcolor,String watermarktext_translatex,
			String watermarktext_translatey,String watermarktext_skewx,String watermarktext_skewy,
			float imageQuality,String watermarkstatus,float watermarktrans) 
			throws IOException {
		watermarktrans /= 100;
		imageQuality /= 100;
		float watermarktext_angle = (float)watermarktext_angleInt/100;
		String srcType = verifyImageFormat(srcImagePath);
		if (srcType == null) {
			return "img_notype";
		}
		if((srcType.equals("gif")||srcType.equals("jpg"))&&isCattoon(srcImagePath)){
			return "img_isflash";
		}
		FileInputStream srcFis =new FileInputStream(srcImagePath);
		Image src_image = ImageIO.read(srcFis); 
		srcFis.close();
		int width = src_image.getWidth(null);
		int height = src_image.getHeight(null);
		if(width<watermarkminwidth){
			return "img_mack_nofinish\t"+ width + "\t"+ height;
		}
		BufferedImage bimage = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics2D = bimage.createGraphics();
		graphics2D.drawImage(src_image, 0, 0, null);
		src_image.flush();
		src_image=null;
		Font font=null;
		FileInputStream fis=new FileInputStream(watermarktext_fontpath);
		try {
			font = Font.createFont(Font.TRUETYPE_FONT,fis);
		} catch (FontFormatException e1) {
			return "img_mack_nofont";
		}finally{
			fis.close();
		}
		font = font.deriveFont((float)watermarktext_size);		
		int textWidth = graphics2D.getFontMetrics(font).stringWidth(watermarktext_text);
		int textHeigth = graphics2D.getFontMetrics(font).getHeight();
		int zoneArray[] = getWriteZoneForCharacter(watermarkstatus, width, height, textWidth, textHeigth);
		graphics2D.rotate(watermarktext_angle);
		graphics2D.setFont(font);
		graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,watermarktrans));
		if(watermarktext_shadowcolor!=null&&!watermarktext_shadowcolor.trim().equals("")){
			try{
				graphics2D.setColor(Color.decode(watermarktext_shadowcolor));
			}catch(NumberFormatException exception){
				return "img_mack_shadow";
			}
			graphics2D.drawString(watermarktext_text, zoneArray[0]+watermarktext_shadowx, zoneArray[1]+watermarktext_shadowy);
		}
		try{
			graphics2D.setColor(Color.decode(watermarktext_color));
		}catch(NumberFormatException exception){
			return "img_mack_color";
		}
		graphics2D.drawString(watermarktext_text, zoneArray[0], zoneArray[1]); 
		graphics2D.dispose();
		try {
			FileOutputStream out = new FileOutputStream(outputImagePath);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bimage);
			param.setQuality(imageQuality, true);
			encoder.encode(bimage, param);
			bimage.flush();
			out.close();
			graphics2D=null;
			bimage=null;
			param=null;
			encoder=null;			
		} catch (Exception e) {
			return e.getMessage();
		}
		return null;
	}
	private static String verifyImageFormat(String fileName) {
		String result = null;		
		String fileType = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();		
		for (String imageType:imageFormatArray) {
			if (fileType.equals(imageType)) {
				result = fileType.substring(1);
				break;
			}
		}
		return result;
	}	
	private static int[] getWriteZoneForImage(String zone, int width, int height,int waterMarkImgWidth, int waterMarkImgHeight) {
		int distance = 5;
		int[] zoneArray = new int[2];
		if ("1".equals(zone)) {
			zoneArray[0] = distance;
			zoneArray[1] = distance;
		}
		if ("2".equals(zone)) {
			zoneArray[0] = (width - waterMarkImgWidth) / 2;
			zoneArray[1] = distance;
		}
		if ("3".equals(zone)) {
			zoneArray[0] = width - distance - waterMarkImgWidth;
			zoneArray[1] = distance;
		}
		if ("4".equals(zone)) {
			zoneArray[0] = distance;
			zoneArray[1] = (height - waterMarkImgHeight) / 2;
		}
		if ("5".equals(zone)) {
			zoneArray[0] = (width - waterMarkImgWidth) / 2;
			zoneArray[1] = (height - waterMarkImgHeight) / 2;
		}
		if ("6".equals(zone)) {
			zoneArray[0] = width - distance - waterMarkImgWidth;
			zoneArray[1] = (height - waterMarkImgHeight) / 2;
		}
		if ("7".equals(zone)) {
			zoneArray[0] = distance;
			zoneArray[1] = height - distance - waterMarkImgHeight;
		}
		if ("8".equals(zone)) {
			zoneArray[0] = (width - waterMarkImgWidth) / 2;
			zoneArray[1] = height - distance - waterMarkImgHeight;
		}
		if ("9".equals(zone)) {
			zoneArray[0] = width - distance - waterMarkImgWidth;
			zoneArray[1] = height - distance - waterMarkImgHeight;
		}
		if ("isRandom".equals(zone)) {
			zoneArray[0] = (int) ((Math.random() * 1000) + 1)% (width - waterMarkImgWidth);
			zoneArray[1] = (int) ((Math.random() * 1000) + 1)% (height - waterMarkImgHeight);
		}
		return zoneArray;
	}
	private static int[] getWriteZoneForCharacter(String zone, int width, int height,int textWidth, int textHeigth) {
		int distance = 15;
		int[] zoneArray = new int[2];
		if ("1".equals(zone)) {
			zoneArray[0] = distance;
			zoneArray[1] = textHeigth;
		}
		if ("2".equals(zone)) {
			zoneArray[0] = (width - textWidth) / 2;
			zoneArray[1] = textHeigth;
		}
		if ("3".equals(zone)) {
			zoneArray[0] = width  - textWidth- distance;
			zoneArray[1] = textHeigth;
		}
		if ("4".equals(zone)) {
			zoneArray[0] = distance;
			zoneArray[1] = height /2+textHeigth/4;
		}
		if ("5".equals(zone)) {
			zoneArray[0] = (width - textWidth) / 2;
			zoneArray[1] = height /2+textHeigth/4;
		}
		if ("6".equals(zone)) {
			zoneArray[0] = width  - textWidth- distance;
			zoneArray[1] = height /2+textHeigth/4;
		}
		if ("7".equals(zone)) {
			zoneArray[0] = distance;
			zoneArray[1] = height  - distance;
		}
		if ("8".equals(zone)) {
			zoneArray[0] = (width - textWidth) / 2;
			zoneArray[1] = height  - distance;
		}
		if ("9".equals(zone)) {
			zoneArray[0] = width  - textWidth- distance;
			zoneArray[1] = height  - distance;
		}
		if ("isRandom".equals(zone)) {
			zoneArray[0] = (int) ((Math.random() * 1000) + 1)% (width - textWidth);
			zoneArray[1] = (int) ((Math.random() * 1000) + 1)% (height - textHeigth);
		}
		return zoneArray;
	}
	public static String createZoomImage(String sourceFilePath,String targetFileName,int width,int height) throws IOException{
		String fileType = verifyImageFormat(sourceFilePath);
		if(fileType==null){
			return "img_notype";
		}
		if((fileType.equals("gif")||fileType.equals("jpg"))&&isCattoon(sourceFilePath)){
			return "img_isflash";
		}
		FileInputStream fis =new FileInputStream(sourceFilePath); 
		Image src_image = ImageIO.read(fis); 
		fis.close();
		double oldHeight=src_image.getHeight(null);
		double oldWidth=src_image.getWidth(null);
		if (oldHeight > height ||oldWidth > width) {
			double ratio = 0;
			if (oldHeight > oldWidth) {
				ratio = (double)height /oldHeight;
			}
			else{
				ratio =(double)width /oldWidth;
			}
			if(ratio>1){
				ratio=1;
			}
			width=(int)(oldWidth*ratio);
			height=(int)(oldHeight*ratio);
			BufferedImage bufferedImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
			Graphics graphics = bufferedImage.createGraphics();
			graphics.drawImage(src_image, 0,0, width,height,null);
			graphics.dispose();			
			FileOutputStream fos = new FileOutputStream(targetFileName);
			JPEGImageEncoder jpg_encoder = JPEGCodec.createJPEGEncoder(fos);
			jpg_encoder.encode(bufferedImage); 
			src_image.flush();
			src_image = null;
			fos.close();
			graphics=null;
			fos=null;
			bufferedImage.flush();
			bufferedImage=null;
			jpg_encoder=null;
			return null;
		}else{
			return "img_thumb_nofinish\t"+ width + "\t"+ height;
		}
	}
	private static boolean isCattoon(String srcImgPath) throws IOException{
		FileInputStream srcFis =new FileInputStream(srcImgPath);
		int log = 0;
		int currentByte = 0;
		while((currentByte = srcFis.read())!=-1){
			if(log == 0 && currentByte == 0x21
					||log == 1 && currentByte == 0xff
					||log == 2 && currentByte == 0x0b
					||log == 3 && currentByte == 'N'
					||log == 4 && currentByte == 'E'
					||log == 5 && currentByte == 'T'
					||log == 6 && currentByte == 'S'
					||log == 7 && currentByte == 'C'
					||log == 8 && currentByte == 'A'
					||log == 9 && currentByte == 'P'
					||log == 10 && currentByte == 'E'
					||log == 11 && currentByte == '2'
					||log == 12 && currentByte == '.'
					||log == 13 && currentByte == '0'){
				log++;
				continue;
			}
			if(log==14){
				break;
			}
			log = 0;
		}
		srcFis.close();
		return log == 14;
	}
}
