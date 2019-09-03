package com.exercise;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.exercise.model.LevelType;
import com.exercise.model.Log;
import com.exercise.model.Store;
import com.exercise.repository.ILogRepository;
import com.exercise.service.JobLoggerImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoggerApplicationTests {
	
	@Autowired
	JobLoggerImpl job;
	@Autowired
	ILogRepository logRepo;

	@Test
	public void saveTest() {
		assertEquals(true,job.Log("prueba mensaje", LevelType.WARNING, Store.database));
	}
	
	@Test
	public void saveFileTest() {
		assertEquals(true,job.Log("prueba archivo", LevelType.SEVERE_AND_WARNING, Store.file));
	}
	
	@Test
	public void displayInConsoleTest() {
		assertEquals(true,job.Log("prueba consola", LevelType.INFO, Store.console));
	}
	
	@Test
	public void saveLogControl() {
		String messageToSave = "prueba1";
		String messageExpected = "prueba1";
		if(job.Log(messageToSave, LevelType.SEVERE, Store.database)) {
			Log logFind = logRepo.findByMessage(messageToSave);
		    assertThat(logFind.getMessage()).isEqualTo(messageExpected);
		}else {
			assertTrue(false);
		}
	}

}
