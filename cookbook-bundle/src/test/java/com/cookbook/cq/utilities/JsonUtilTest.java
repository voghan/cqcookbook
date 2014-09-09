package com.cookbook.cq.utilities;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class JsonUtilTest {
	private static final Logger LOG = LoggerFactory
			.getLogger(JsonUtilTest.class);

	@Test
	public void convert() {
		try {
			LOG.info("..testing stuff");
			
			Object obj = new Object();
		
			String output = JsonUtil.convert(obj);
			
			assertNotNull(output);
			
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			fail(e.getMessage());
		}
	}
}
