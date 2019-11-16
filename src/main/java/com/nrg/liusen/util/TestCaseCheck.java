package com.nrg.liusen.util;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import com.nrg.liusen.managedBean.VendorMB;

public class TestCaseCheck {
	
	public static void  testing(){
	Result result = JUnitCore.runClasses(VendorMB.class);

	      for (Failure failure : result.getFailures()) {
	         System.out.println(failure.toString());
	      }
	      System.out.println(result.wasSuccessful());
	  
	}
	}


