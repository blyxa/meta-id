package com.blyxa;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KeyGenTest
{
	private static Logger logger = LoggerFactory.getLogger(KeyGenTest.class);

	private KeyGen keyGen = new KeyGen();
	
	@Test
	public void test1()
	{
		// 2^41-1 = 2199023255551
		long max = 2199023255551L+keyGen.getCustomEpochStartMilli();
		long min = -2199023255552L+keyGen.getCustomEpochStartMilli();
		
		logger.info("min {} ", min);
		logger.info("max {} ", max);
		
		// test over max failure
		Assert.assertFalse(genkey(max)<genkey(max+1));

		// test min failure
		Assert.assertFalse(genkey(min-1)<genkey(min));
		
		// test working range
		Assert.assertTrue(genkey(min)<genkey(min-1));

		Assert.assertTrue(genkey(-1L)<genkey(0L));
		Assert.assertTrue(genkey(0L)<genkey(1L));

		Assert.assertTrue(genkey(max-1)<genkey(max));
	}
	
	public long genkey(long ts)
	{
		long key = keyGen.genKey(ts);
		System.out.println(ts+":"+key+":"+Long.toBinaryString(key)+":"+Long.toBinaryString(key).length()+"bits");
		return key;
	}
}
