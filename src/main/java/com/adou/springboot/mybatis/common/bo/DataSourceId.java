package com.adou.springboot.mybatis.common.bo;

import java.util.Calendar;

/**
 * 编号生成规则
 * 
 * @author zhoudoujun01
 *
 */
public class DataSourceId {
	/**
	 * 编号前缀
	 */
	private static String NO_PREFIX = "6";
	/**
	 * 时间六位
	 */
	private static int YMD_DIGITS = 6;
	/**
	 * 流水号位数
	 */
	private static int SEQID_DIGITS = 9;
	/**
	 * 数据源ID位数
	 */
	private static int DATASOURCE_DIGITS = 2;
	/**
	 * 数据源ID起始下标（12位）
	 */
	private static int DATASOURCE_START_IDX = NO_PREFIX.length() + YMD_DIGITS + SEQID_DIGITS;

	// /**
	// * 流水号算法实例对象
	// */
	// private static SentinelOrderNo SERIALNUMBER_INSTANCE = null;

	/**
	 * 新零售订单号流水号格式化字符串
	 */
	private static String SEQID_FORMAT = "%0" + SEQID_DIGITS + "d";

	/**
	 * 数据源个数
	 */
	private static int DATASOURCE_CNT = 4;

	/**
	 * 用户ID后四位
	 */
	private static int USERID_LAST_DIGITS = 4;

	/**
	 * 订单号格式化字符串：前缀+yyMMdd+%s%02d%04d,其内容为：订单号前缀+年月日+内部唯一流水号（固定9位）+数据源标识
	 */
	private static String ORDERNO_FORMAT = NO_PREFIX + "%ty%<tm%<td%s%0" + DATASOURCE_DIGITS + "d";

	/**
	 * 生成流水号的关键词键
	 */
	private static String ORDERNO_GENERAL_KEY = "ORDERNO_GENERAL";

	// protected DataSourceId(String proName) {
	// SERIALNUMBER_INSTANCE = SentinelOrderNo.getInstance(proName,
	// ORDERNO_GENERAL_KEY);
	// }

	/**
	 * 根据订单号取得数据源ID（后两位）
	 * 
	 * @param orderNo
	 *            订单号
	 * @return 数据源ID
	 */
	public static String getDataSourceID(String orderNo) {
		return orderNo.substring(DATASOURCE_START_IDX, DATASOURCE_START_IDX + DATASOURCE_DIGITS);
	}

	/**
	 * 产生编号
	 * 
	 * @return
	 */
	public static String buildID() {
		long seqId = nextId();
		String id = String.format(SEQID_FORMAT, seqId);
		// 超过所占位数，截取后几位
		if (id.length() > SEQID_DIGITS) {
			id = id.substring(id.length() - SEQID_DIGITS, id.length());
		}

		// seqId后四位与数据源个数取模，得到数据源编号
		int useridLast4 = (int) (seqId % ((int) Math.pow(10, USERID_LAST_DIGITS)));

		int ds = useridLast4 % DATASOURCE_CNT;
		// 时间
		Calendar cal = Calendar.getInstance();
		return String.format(ORDERNO_FORMAT, cal, id, ds);
	}

	public static long nextId() {
		return System.currentTimeMillis();
	}
}
