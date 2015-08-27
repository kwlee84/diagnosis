package util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jxls.common.Context;
import org.jxls.reader.ReaderBuilder;
import org.jxls.reader.XLSReadStatus;
import org.jxls.reader.XLSReader;
import org.jxls.util.JxlsHelper;

import exception.DiagnosisException;
import models.Employee;

public class ExcelUtil {
	//
	public static List<Employee> convertToEmployees(File file) {
		//
		List<Employee> employees = new ArrayList<Employee>();
		String xml = FileUtil.rootPath + File.separatorChar + "conf" + File.separatorChar + 
				"jxls" + File.separatorChar + "config"+ File.separatorChar+ "employeeconfig.xml";
		try {
			InputStream inputXML = new BufferedInputStream(new FileInputStream(xml));
			XLSReader mainReader = ReaderBuilder.buildFromXML(inputXML);
			InputStream inputXLS = new BufferedInputStream(new FileInputStream(file));
			Map<String, Object> beans = new HashMap<String, Object>();
			beans.put("employees", employees);
			XLSReadStatus readStatus = mainReader.read(inputXLS, beans);
			
			if (!readStatus.isStatusOK()) {
				throw new DiagnosisException("There is an invalid excel file.");
		    }
			
		} catch (Exception e) {
			throw new DiagnosisException("There is an invalid excel file.");
		}
		return employees;
	}

	public static File employeesToExcel(List<Employee> employees) {
		//
		String input = FileUtil.rootPath + File.separatorChar + "conf" + File.separatorChar + 
				"jxls" + File.separatorChar + "template" + File.separatorChar + "template_management.xlsx";
		String output = FileUtil.rootPath + File.separatorChar + "conf" + File.separatorChar + 
				"jxls" + File.separatorChar + "target" + File.separatorChar + "result_management.xlsx";
		
	    try(InputStream is = new BufferedInputStream(new FileInputStream(input))) {
	        try (OutputStream os = new FileOutputStream(output)) {
	            Context context = new Context();
	            context.putVar("employees", employees);
	            JxlsHelper.getInstance().processTemplate(is, os, context);
	            
	            return new File(output);
	        }
	    } catch(Exception e) {
	    	throw new DiagnosisException("There is an invalid template file.");
	    }
	}
	
}
