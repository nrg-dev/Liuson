package com.nrg.liusen.util;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class OrderNumber {
	
	public static final int interval=1000;
	public static final String suffixID="@nrg.com";
	public static NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);

	
	public static String getSalesOrderNo(int count)
	{
		/*int randomNo=count;
		String refId="SON"+randomNo;*/
		Calendar now= Calendar.getInstance();
		   int year=now.get(Calendar.YEAR);
		    System.out.println("Current Year is : " +year );
		   String month=new SimpleDateFormat("MMM").format(now.getTime());
		    System.out.println("Month"+month);
		    String s1=String.format("%05d",count);

			String refId="SO"+s1+"/"+month+"/"+year;
		
		return refId;
	}
	
	public static String getPurRefNum(int count)
	{
		int randomNo=count+1;
		String refId=null;
		if(randomNo<=9)
		{
			refId="PO000"+randomNo;
		}
		else if(randomNo>9 && randomNo<=99)
		{
			refId="PO00"+randomNo;
		}
		else if(randomNo>99 && randomNo<=999)
		{
			refId="PO0"+randomNo;
		}
		else
		{
			refId="PO"+randomNo;
		}
		System.out.println("-->> PurRefNum "+refId);
		return refId;
	}
	
}
