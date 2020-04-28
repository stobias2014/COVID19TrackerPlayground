package com.tobias.saul.COVID19Tracker;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tobias.saul.COVID19Tracker.service.LocationStatService;

@SpringBootTest
class Covid19TrackerApplicationTests {
	
	@Autowired
	LocationStatService lss;

	@Test
	void contextLoads() {
	}
	
	@Test
	public void servicePrintsData() throws IOException {
		lss.fetchCoronaData();
	}

}
