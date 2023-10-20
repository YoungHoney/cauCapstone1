package cap.backend.back;

import cap.backend.back.preprocessing.p_oldevents.pre_oldevents;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication
public class BackApplication {

	public static void main(String[] args) {

		pre_oldevents p= new pre_oldevents();
		p.readCSV();

		//SpringApplication.run(BackApplication.class, args);
	}

}
