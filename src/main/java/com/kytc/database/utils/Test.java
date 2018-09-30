package com.kytc.database.utils;

public class Test {
	public static void main(String[] args) {
		String listSql = "select DATE_FORMAT(gmt_create, '%Y-%m-%d') as 'xTitle','注册' as category,'日期' as xList,'注册数据' as title,count(1) as value,'注册（人数）' as yTitle,'注册人数数据统计' as subTitle from tb_user where 1=1 GROUP BY DATE_FORMAT(gmt_create, '%Y-%m-%d') ORDER BY gmt_create desc limit 30";
		System.out.println(listSql);
		System.out.println(listSql.length());
		System.out.println(listSql.lastIndexOf(" limit "));
		System.out.println(listSql.length()-listSql.lastIndexOf(" limit ")>15);
	}
}
