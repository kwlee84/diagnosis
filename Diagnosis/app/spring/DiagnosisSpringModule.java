package spring;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.guice.module.SpringModule;


public class DiagnosisSpringModule extends SpringModule{
	//
	public DiagnosisSpringModule() {
		super(new AnnotationConfigApplicationContext(AppConfig.class));
	}
}
