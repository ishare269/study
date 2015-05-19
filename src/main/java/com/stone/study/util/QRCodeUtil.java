package com.stone.study.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;

import jp.sourceforge.qrcode.QRCodeDecoder;
import jp.sourceforge.qrcode.data.QRCodeImage;

import com.swetake.util.Qrcode;

/**
 * 
 * <p>
 * Class Name: QRCodeUtil
 * </p>
 * <p>
 * Description: 二维码生成工具
 * </p>
 * <p>
 * Sample: 该类的典型使用方法和用例
 * </p>
 * <p>
 * Author: 石涛
 * </p>
 * <p>
 * Date: 2014-8-28
 * </p>
 * <p>
 * Modified History: 修改记录，格式(Name) (Version) (Date) (Reason & Contents)
 * </p>
 */
public class QRCodeUtil {
	/**
	 * 
	 * 描述: 生成二维码图片
	 * 
	 * @param content
	 *            二维码图片内容
	 * @param iconPath
	 *            二维码图片中间的图标
	 * @param imgPath
	 *            二维码图片输出地址
	 * @return int 1:生成成功 0：生成失败 -1：异常
	 * @author 石涛 date 2014-8-28
	 *         -------------------------------------------------- 修改人 修改日期 修改描述
	 *         石涛 2014-8-28 创建
	 *         --------------------------------------------------
	 *         140 55
	 * @Version Ver1.0
	 */
	public static int createQRCode(String content, String iconPath,
			String imgPath) {
		try {
			Qrcode qrcode = new Qrcode();
			// 设置二维码排错率，可选L(7%)、M(15%)、Q(25%)、H(30%)，排错率越高可存储的信息越少，但对二维码清晰度的要求越小  
			qrcode.setQrcodeErrorCorrect('M');
			//编码方式
			qrcode.setQrcodeEncodeMode('B');
			// 设置设置二维码尺寸，取值范围1-40，值越大尺寸越大，可存储的信息越大  
			qrcode.setQrcodeVersion(12);
			// 获得内容的字节数组，设置编码格式  
			byte[] contentBytes = content.getBytes("GBK");
			BufferedImage buffImg = new BufferedImage(345, 345,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D gs = buffImg.createGraphics();
			gs.setBackground(Color.WHITE);
			gs.clearRect(0, 0, 345, 345);
			gs.setColor(Color.BLACK);// 设置图片颜色
			int pixoff = 10;// 设置二维码边距偏移量 不设置可能导致解析出错 
			if (contentBytes.length > 0 && contentBytes.length < 160) {//二维码块状大小 值越大块越大
				boolean[][] codeOut = qrcode.calQrcode(contentBytes);
				for (int i = 0; i < codeOut.length; i++) {
					for (int j = 0; j < codeOut.length; j++) {
						if (codeOut[j][i]) {
							//前面两个为偏移量自增算法  后面两个为二维码块状大小（必须一致）
							gs.fillRect(j * 5 + pixoff, i * 5 + pixoff, 5, 5);
						}
					}
				}
			} else {
				System.err.println("QRCode content bytes length = "
						+ contentBytes.length + " not in [ 0,120 ]. ");
				return 0;
			}
			//是否存在二维码logo
			if (iconPath != null && !iconPath.equals("")) {
				int width = 0;
				int height = 0;
				if (iconPath.startsWith("http://")) {//获取远程图片绘制logo
					URLConnection u = new URL(iconPath).openConnection();
					InputStream in = u.getInputStream();
					if (null != in) {
						BufferedImage bufferedImage = ImageIO.read(in);
						width = bufferedImage.getWidth();
						height = bufferedImage.getHeight();
						gs.drawImage(bufferedImage, (345 - width) / 2,
								(345 - height) / 2, null);
					}
				} else {//获取本地图片绘制logo
					BufferedImage bufferedImage = ImageIO.read(new File(
							iconPath));
					width = bufferedImage.getWidth();
					height = bufferedImage.getHeight();
					gs.drawImage(bufferedImage, (345 - width) / 2,
							(345 - height) / 2, null);
				}
			}
			gs.dispose();
			buffImg.flush();

			// 生成二维码图片
			File imgFile = new File(imgPath);
			ImageIO.write(buffImg, "png", imgFile);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return 1;
	}

	/**
	 * 
	 * 描述: 解析二维码
	 * @param qrcodeImgPath 二维码图片路劲
	 * @return 二维码解析后的数据
	 * @author     石涛
	 * date        2014-8-28
	 * --------------------------------------------------
	 * 修改人    	      修改日期       修改描述
	 * 石涛                    2014-8-28       创建
	 * --------------------------------------------------
	 * @Version  Ver1.0
	 */
	public static String decoderQRCode(String qrcodeImgPath) {
		String decoderData = null;
		try {
			QRCodeDecoder decoder = new QRCodeDecoder();
			File imageFile = new File(qrcodeImgPath);
			final BufferedImage image = ImageIO.read(imageFile);
			decoderData = new String(decoder.decode(new QRCodeImage() {

				public int getWidth() {
					return image.getWidth();
				}

				public int getPixel(int x, int y) {
					return image.getRGB(x, y);
				}

				public int getHeight() {
					return image.getHeight();
				}
			}), "GBK");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return decoderData;
	}

	public static void main(String[] args) {
//		createQRCode("http://weixin.qq.com/r/MdeJkWjEXzUgrSzy94Ij",
//				"C:/Users/Administrator/Desktop/images/2.jpg",
//				"C:/Users/Administrator/Desktop/images/qrcode.png");
		System.out.println(decoderQRCode("C:\\Users\\Administrator\\Desktop\\mmqrcode1430965138055.png"));
		//http://127.0.0.1:8888/orderfood/client/foodspage?qrcodeid=ca5dc3dbb62546c383e23c0d09ea0312
	}
}