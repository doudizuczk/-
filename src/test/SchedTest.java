package test;

import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
@Lazy(false)  
public class SchedTest {

	@Scheduled(fixedRate = 5000)
	public void parseMongodbDataToJson() {
		System.out.println("************");
	}
}
