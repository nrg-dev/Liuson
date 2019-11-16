package com.nrg.liusen.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {

	public static void main(String[] args) {
		Date d=new Date();
		SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm:ss a");
		System.out.println("date--"+d);
		System.out.println("date--"+dateFormat.format(d));
	/*	System.out.println("date--"+ new Timestamp(sdf. d.getTime()));*/
	}

}
