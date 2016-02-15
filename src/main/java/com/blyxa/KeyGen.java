package com.blyxa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 64 bit unique time ordered id generation
 * 
 * (sign bit)  not used
 * 41 bits for timestamp 2^41-1 = 2199023255551
 * 10 bits for shard id 2^10-1  = 1023
 * 12 bits for sequence 2^12-1  = 4095
 *
 *
 * examples:
 * 
 * CUSTOM_EPOCH_START = 1420070400000 (2015-01-01 00:00:00 GMT)
 * CURTIME = 1420070432000 (2015-01-01 00:00:32 GMT)
 * 
 * (CURTIME - CUSTOM_EPOCH_START)(shard 2)(sequence 13) = 0
 * 1420070432000-1420070400000 = 32000 => 111110100000000
 * 2 => 10
 * 13 => 1101
 * 000000000000000000000000000111110100000000  0000000010  000000001101
 * 
 */
public class KeyGen
{
	/**
	 * (GMT): 01 Jan 2015 00:00:00 GMT
	 */
	private long customEpochStartMilli = 1420070400000L;
	
	private long shardId = 0;

	private Long seq = 0L;

	public Long getShardId() {
		return shardId;
	}

	public void setShardId(Long shardId) {
		this.shardId = shardId;
	}
	
	public long getCustomEpochStartMilli()
	{
		return customEpochStartMilli;
	}

	public long genKey(long curTime)
	{
		synchronized(seq)
		{
			long customEpoch = curTime - customEpochStartMilli;
			long big = customEpoch << 22;
			big |= shardId << 10;
			big |= seq%1024;
			seq++;

			return big;
		}
	}

	public long genKey()
	{
		return genKey(System.currentTimeMillis());
	}
	
//	public long updateTimestamp(long key, long timestamp)
//	{
//		return key |= (timestamp - customEpochStart) << 22;
//	}
	
}