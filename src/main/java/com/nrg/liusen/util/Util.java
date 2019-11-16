package com.nrg.liusen.util;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public class Util {

	
	public static HttpSession getSession()
	{
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}
	
	
	public static HttpSession getRequest()
	{
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}
	
	public static String getUserName()
	{
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		return session.getAttribute("username").toString();
	}

	public static String getUserId()
	{
		HttpSession session = getSession();
		if(session != null)
		{
			return (String )session.getAttribute("userid");
		}
		else
		{
			return null;
		}
	}

	
	public static String getStudentRollNumber(String name)
	{
		System.out.println("Student Roll Number :::::::::::::::");

		
		String rollNumber=null;
		String[][] a = null;
		try
		{
			a = new String[100][4];
			a[0][1] = "A";a[22][1] = "B";a[43][1] = "C";a[0][3] = "D";
			a[1][1] = "E";a[23][1] = "F";a[44][1] = "G";a[0][3] = "H";
			a[2][1] = "I";a[24][1] = "J";a[45][1] = "K";a[0][3] = "L";
			a[3][1] = "M";a[25][1] = "N";a[46][1] = "O";a[0][3] = "P";
			a[4][1] = "Q";a[26][1] = "R";a[47][1] = "S";a[0][3] = "T";
			a[5][1] = "U";a[27][1] = "V";a[48][1] = "W";a[0][3] = "X";
			a[6][1] = "Y";a[28][1] = "Z";a[49][1] = "QB";a[0][3] = "WP";
			a[7][1] = "YB";a[29][1] = "TC";a[50][1] = "RA";a[0][3] = "EC";
			a[8][1] = "UM";a[30][1] = "IQ";a[51][1] = "OO";a[0][3] = "PB";
			a[9][1] = "HP";a[31][1] = "JX";a[52][1] = "KA";a[0][3] = "LA";
			a[10][1] = "GU";a[32][1] = "F";a[53][1] = "DL";a[0][3] = "S";
			a[11][1] = "CC";a[33][1] = "X";a[54][1] = "ZI";a[0][3] = "A";
			a[12][1] = "VV";a[34][1] = "BN";a[55][1] = "NU";a[0][3] = "M";
			a[13][1] = "KK";a[35][1] = "KJ";a[60][1] = "AY";a[0][3] = "QS";
			a[14][1] = "UU";a[36][1] = "VZ";a[0][2] = "AT";a[0][3] = "BD";
			a[15][1] = "XX";a[37][1] = "ZD";a[0][2] = "AR";a[0][3] = "BF";
			a[16][1] = "AA";a[38][1] = "BQ";a[0][2] = "AE";a[0][3] = "BG";
			a[17][1] = "CC";a[39][1] = "DD";a[0][2] = "AW";a[0][3] = "BH";
			a[18][1] = "EE";a[40][1] = "FF";a[0][2] = "AS";a[0][3] = "BJ";
			a[19][1] = "GG";a[41][1] = "HH";a[0][2] = "AD";a[0][3] = "BK";
			a[20][1] = "II";a[42][1] = "JJ";a[0][2] = "AV";a[0][3] = "BL";
			System.out.println("Alex 1");
			System.out.println("Alex 2"+a[22][1]);

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss-a-zzz");
			Calendar cal = Calendar.getInstance();
			String ymd = dateFormat.format(cal.getTime());
			String[] result = ymd.split("-");
			/*System.out.println("Year -->"+result[0]);
			System.out.println("Month -->"+result[1]);
			System.out.println("Date -->"+result[2]);
			System.out.println("Hours -->"+result[3]);
			System.out.println("Min -->"+result[4]);
			System.out.println("Seconds -->"+result[5]);
			System.out.println("Mode -->"+result[6]);
			System.out.println("Zone -->"+result[7]);*/
			
			
			
			rollNumber = result[0] + name.charAt(0) + a[Integer.valueOf(result[5])][1] + name.length() +a[Integer.valueOf(result[4])] [1];
			System.out.println("Student Roll Number -->"+rollNumber.toString());
			
		}
		catch(Exception e)
		{
			System.out.println("Exception -->"+e.getMessage());
		}
		finally
		{
			a = null;
		}
		return rollNumber;
	}
	
	public static String getDate()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		String ymd = dateFormat.format(cal.getTime());
		return ymd;
	}
	
	/*public static HttpSession getSession() {
		return (HttpSession)
				FacesContext.getCurrentInstance().getExternalContext().getSession(false);
      }*/
	
	/*public static HttpServletRequest getRequest()
	{
		return (HttpServletRequest) FacesContext.
				getCurrentInstance().
				getExternalContext().getRequest();
	}

	public static String getUserName()
      {
       HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        return  session.getAttribute("username").toString();
      }
       
      public static String getUserId()
      {
        HttpSession session = getSession();
	if ( session != null )
	return (String) session.getAttribute("userid");
        else
            return null;
}
*/
}
