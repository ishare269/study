package com.stone.java._print;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.ArrayList;
import java.util.List;

/**
 * 热敏打印机
 * 
 * @author Stone
 * 
 */
public class PrintUtil implements Printable {
	/**
	 * 订单
	 */
	private Order order;

	/**
	 * 纸张宽度(单位：mm)
	 */
	// private final int paperWidth = 58;
	/**
	 * 纸张高度(单位：mm)
	 */
	// private final int paperHeight = 3276;

	/**
	 * 页边距
	 */
	private final int pageMargin = 5;

	/**
	 * 行高
	 */
	private final int lineHeight = 10;

	private PrintUtil() {

	}

	private static PrintUtil print;

	public static PrintUtil getInstance(Order order) {
		if (null == print) {
			print = new PrintUtil();
		}
		print.order = order;
		return print;
	}

	/**
	 * @param g
	 *            绘制页面的上下文
	 * @param pf
	 *            绘制的页面的大小和方向
	 * @param page
	 *            要绘制的页面从 0 开始的索引
	 * @return status 0:成功 1：失败
	 */
	public int print(Graphics g, PageFormat pf, int page)
			throws PrinterException {
		if (page > 0) {
			return NO_SUCH_PAGE;// NO_SUCH_PAGE=1 从 print 返回，表示 pageIndex
								// 太大以及请求的页面不存在
		}
		Graphics2D g2d = (Graphics2D) g;

		g2d.setFont(new Font("Default", Font.PLAIN, 10));
		// 标题
		g2d.drawString("邓丽君主题餐厅", pageMargin * 6, lineHeight);
		g2d.setFont(new Font("Default", Font.PLAIN, 8));
		g2d.drawString("----------------------------------------------",
				pageMargin, lineHeight * 2);
		g2d.drawString(
				"时间:" + order.getCreateTime() + "          #"
						+ order.getDeskNo(), pageMargin, lineHeight * 3);
		g2d.drawString("品名             数量       单价         金额", pageMargin,
				lineHeight * 4 + 5);
		// 菜品
		int position = 0;
		for (int i = 0; i < order.getOrderFoods().size(); i++) {
			OrderFood orderFood = order.getOrderFoods().get(i);
			position = lineHeight * 6 + 15 * i;
			position += 10 * i;
			// 菜名小于5个字符不换行,否者换行打印
			if (orderFood.getFood().getName().length() <= 5) {
				g2d.drawString(textAlign(orderFood.getFood().getName(),6) + "x"
						+ orderFood.getFoodCount() + "     "
						+ orderFood.getFood().getPrice() + "    "
						+ orderFood.getFood().getPrice(), pageMargin, position);
			} else {
				g2d.drawString(orderFood.getFood().getName(), pageMargin,
						position);
				position += 10;
				g2d.drawString("x" + orderFood.getFoodCount() + "     "
						+ orderFood.getFood().getPrice() + "     "
						+ orderFood.getFood().getPrice(), pageMargin * 11,
						position);
			}
		}
		g2d.drawString("----------------------------------------------",
				pageMargin, position + 15);
		g2d.drawString("合计数量:                    x" + order.getFoodTotal(),
				pageMargin, position + 25);
		g2d.drawString("合计金额:                    ￥" + order.getPriceTotal(),
				pageMargin, position + 35);
		g2d.drawString("----------------------------------------------",
				pageMargin, position + 50);

		return PAGE_EXISTS;// PAGE_EXISTS=0 从 print(Graphics, PageFormat, int)
							// 返回，表示请求的页面被呈现。
	}

	/**
	 * 执行打印
	 * 
	 * @return
	 */
	public boolean executePrint() {
		// 设置单张打印纸的高度
		int height = 175 + order.getOrderFoods().size() * 15 + 20;
		// 通俗理解就是书、文档
		Book book = new Book();
		// 打印格式
		PageFormat pf1 = new PageFormat();
		pf1.setOrientation(PageFormat.PORTRAIT);
		// 通过Paper设置页面的空白边距和可打印区域。必须与实际打印纸张大小相符。
		Paper p = new Paper();
		p.setSize(230, height);
		p.setImageableArea(pageMargin, -20, 230, height + 20);
		pf1.setPaper(p);
		// 把 PageFormat 和 Printable 添加到书中，组成一个页面
		book.append(PrintUtil.getInstance(order), pf1);
		// 获取打印服务对象
		PrinterJob job = PrinterJob.getPrinterJob();
		job.setJobName("热敏打印机打印小票");
		job.setPageable(book);
		try {
			job.print();
			return true;
		} catch (PrinterException e) {
			System.out.println("================打印出现异常");
		}
		return false;
	}
	
	/**
	 * 文字堆砌
	 * @param str	处理数据
	 * @param lenth	指定长度
	 * @return
	 */
	public String textAlign(String str,int lenth){
		String resultStr = str;
		if(str.length()<5){
			for(int i=0;i<lenth-str.length();i++){
				resultStr+=" ";
			}
		}
		return resultStr;
	}

	public static void main(String[] args) {
		Food food = new Food();
		food.setDiscountPrice(0.00);
		food.setName("舌尖上时段啊山大发送");
		food.setPrice(1000.00);
		food.setRemark("好吃得不得了");

		Food food1 = new Food();
		food1.setDiscountPrice(0.00);
		food1.setName("舌尖上时段");
		food1.setPrice(109.15);
		food1.setRemark("好吃得不得了");

		OrderFood orderFood1 = new OrderFood();
		orderFood1.setFood(food);
		orderFood1.setFoodCount(1);

		OrderFood orderFood2 = new OrderFood();
		orderFood2.setFood(food1);
		orderFood2.setFoodCount(1);

		List<OrderFood> orderFoods = new ArrayList<OrderFood>();
		orderFoods.add(orderFood1);
		orderFoods.add(orderFood2);
		orderFoods.add(orderFood1);
//		orderFoods.add(orderFood2);
//		orderFoods.add(orderFood1);

		Order order = new Order();
		order.setClientId("");
		order.setCreateTime("2015/01/19 18:00");
		order.setDeskNo(8);
		order.setFoodTotal(100);
		order.setId("sss1111111");
		order.setMerchantId("");
		order.setOrderFoods(orderFoods);
		order.setPeopleNo(100);
		order.setPriceTotal(100000.15);
		order.setRemark("打印系统将使用单调递增的页面索引来调用 Printable.print(..)，尽管上文已说明，Printable 应期望多次调用页面索引，并期望当客户端或用户通过打印对话指定页面范围时，可以跳过页面索引");
		order.setStatus("0");
		System.out.println(PrintUtil.getInstance(order).executePrint());
	}
}
