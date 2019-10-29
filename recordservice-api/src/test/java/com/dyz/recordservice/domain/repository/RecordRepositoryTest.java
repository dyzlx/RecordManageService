package com.dyz.recordservice.domain.repository;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.dyz.recordservice.domain.entity.Record;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecordRepositoryTest {

	@Autowired
	private RecordRepository recordRepository;

	@Test
	public void addTest() {
		Record record = Record.builder().createTime(new Date()).content("test content").title("Test").userId(1).build();
		recordRepository.save(record);
	}
}
