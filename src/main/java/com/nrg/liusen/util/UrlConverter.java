package com.nrg.liusen.util;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.NumberFormat;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("com.nrg.liusen.util.UrlConverter")
public class UrlConverter implements Converter {

   @Override
   public Object getAsObject(FacesContext facesContext, 
      UIComponent component, String value) {
	   String numberAsString = null ;
	   try
	   {
		   
	 
      StringBuilder url = new StringBuilder();
      
     System.out.println("---------------------------------------------");
       NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
       numberAsString = numberFormat.format(value);
      String numbeAsString = numberFormat.format(value);
       System.out.println(numberAsString);
      
      
System.out.println("------------------inside value---------------------aa");
     
	   }
	   catch(Exception e)
	   {
		   e.printStackTrace();
	   }
      
      return numberAsString;
   }

   @Override
   public String getAsString(FacesContext facesContext,
      UIComponent component, Object value) {
	   System.out.println("--------------------1----url---------------------"+value);
	   String numberAsString = null ;
	   try
	   {
		   
	 
      String url = value.toString();
      System.out.println("string--------->"+url);
      
 
       NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
       numberAsString = numberFormat.format(new BigDecimal(url));
      String numbeAsString = numberFormat.format(new BigDecimal(url));
       System.out.println(numberAsString);
      
      
System.out.println("------------------inside value---------------------aa");
     
	   }
	   catch(NumberFormatException ne)
	   {
		   ne.printStackTrace();
	   }
	   catch(Exception e)
	   {
		   e.printStackTrace();
	   }
         return numberAsString;
   }
}