package com.yavay.test;

import java.io.IOException;
import java.util.Enumeration;


import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.exception.KettleXMLException;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;

/**
 * Servlet implementation class getKettle
 */
//@WebServlet("/service")
public class getKettle extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	 
    public getKettle() {
        super();
     
        
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		   try {
				KettleEnvironment.init(false);
			} catch (KettleException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	 

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
        TransMeta metaData;
		try {
			
			String dir = request.getSession().getServletContext().getInitParameter("ktrDir");
			
          //  String dir = request.getServletContext().getInitParameter("ktrDir");
            if (dir == null ) {
            	
            	System.out.println("Directory is null");
            }
            String sName = request.getParameter("sName");
			metaData = new TransMeta(dir +sName + ".ktr");
			 Trans trans = new Trans( metaData );
			 trans.setServletPrintWriter(response.getWriter());
			 
				Enumeration  <String> pNames = request.getParameterNames();
				for (;pNames.hasMoreElements(); ) {
					String str = pNames.nextElement();
					if (str != null) {
					 String [] values =	request.getParameterValues(str);
					 trans.setParameterValue(str, values[0]);
					}
					
				}
			 
		     trans.execute( null );
		        trans.waitUntilFinished();
		        if ( trans.getErrors() > 0 ) {
		            System.out.print( "Error Executing transformation" );
		        }


		} catch (KettleXMLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KettleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
        
   	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
