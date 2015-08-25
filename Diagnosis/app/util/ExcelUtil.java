package util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import exception.DiagnosisException;
import jxl.Sheet;
import jxl.Workbook;
import models.Diagnosis;
import models.Employee;

public class ExcelUtil {
	//
	public static Diagnosis convertToDiagnosis(Diagnosis diagnosis, File file) {
		//
		/*try {
			Workbook workbook = Workbook.getWorkbook(file);
			Sheet sheet = workbook.getSheet(0);
			
			//Java
			diagnosis.addQuestionnaire(getQuestionnaire(sheet, 8));
			//SQL
			diagnosis.addQuestionnaire(getQuestionnaire(sheet, 9));
			//RDBMS
			diagnosis.addQuestionnaire(getQuestionnaire(sheet, 10));
			//JDBC
			diagnosis.addQuestionnaire(getQuestionnaire(sheet, 11));
			//HTML/CSS
			diagnosis.addQuestionnaire(getQuestionnaire(sheet, 12));
			//JavaScript
			diagnosis.addQuestionnaire(getQuestionnaire(sheet, 13));
			//JSP/Servlet
			diagnosis.addQuestionnaire(getQuestionnaire(sheet, 14));
			//Spring DI
			diagnosis.addQuestionnaire(getQuestionnaire(sheet, 15));
			//Spring MVC
			diagnosis.addQuestionnaire(getQuestionnaire(sheet, 16));
			//jQuery
			diagnosis.addQuestionnaire(getQuestionnaire(sheet, 17));
			//Windows OS
			diagnosis.addQuestionnaire(getQuestionnaire(sheet, 18));
			//Linux OS
			diagnosis.addQuestionnaire(getQuestionnaire(sheet, 19));
			//MyBatis
			diagnosis.addQuestionnaire(getQuestionnaire(sheet, 20));
			//Java/Web 프로젝트
			diagnosis.addQuestionnaire(getQuestionnaire(sheet, 21));
			//SVN-형상관리
			diagnosis.addQuestionnaire(getQuestionnaire(sheet, 22));
			//eclipse-통합개발환경
			diagnosis.addQuestionnaire(getQuestionnaire(sheet, 23));
			//jUnit-단위테스트
			diagnosis.addQuestionnaire(getQuestionnaire(sheet, 24));

			
		} catch (Exception e) {
			throw new DiagnosisException("There is an invalid excel file.");
		}*/
		
		return diagnosis;
	}
	
	/*private static Questionnaire getQuestionnaire(Sheet sheet, int row) {
		Questionnaire questionnaire = new Questionnaire();
		questionnaire.setKnow(sheet.getCell(1,row).getContents().equals("O") ? true : false);
		return null;
	}*/

	public static List<Employee> convertToEmployees(File file) {
		//
		List<Employee> employees = new ArrayList<Employee>();
		try {
			Workbook workbook = Workbook.getWorkbook(file);
			Sheet sheet = workbook.getSheet(0);
			int rowSize = sheet.getRows();
	    	
	    	for(int i=1; i< rowSize ; i++) {
	    		Employee employee = new Employee();
	    		employee.setCompanyId(sheet.getCell(0,i).getContents());
	    		employee.setTeam(sheet.getCell(1,i).getContents());
	    		employee.setName((sheet.getCell(2,i).getContents()));
	    		employee.setEmail((sheet.getCell(3,i).getContents()));
	    		employees.add(employee);
	    	}
		} catch (Exception e) {
			throw new DiagnosisException("There is an invalid excel file.");
		}
		return employees;
	}
}
