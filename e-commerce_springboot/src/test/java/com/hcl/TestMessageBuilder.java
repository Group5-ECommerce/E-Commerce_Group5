package com.hcl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestMessageBuilder {
	 @Test
	    public void testName() {

	        MessageBuilder obj = new MessageBuilder();
	        assertEquals("Hello Viki", obj.getMessage("Viki"));

	    }
}
