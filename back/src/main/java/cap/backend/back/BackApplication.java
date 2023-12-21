package cap.backend.back;

import cap.backend.back.preprocessing.PreProcessInit;
import cap.backend.back.preprocessing.p_oldevents.pre_oldevents;

import org.json.simple.parser.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@SpringBootApplication
public class BackApplication {

	public static void main(String[] args) throws Exception {




		ApplicationContext context=SpringApplication.run(BackApplication.class, args);

		PreProcessInit init=new PreProcessInit(context); //한번만 실행하고 db에 올라간거 확인하면 주석처리









	}

}
