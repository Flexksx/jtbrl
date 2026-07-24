package com.flexksx.ratelimit;

import com.flexksx.ratelimit.impl.synchro.SynchronizedRateLimiter;

public class RateLimiterFactory {
	public static SynchronizedRateLimiter synchronizedRateLimiter(
		long reloadPeriodNanoseconds, long tokensRefilledPerPeriod, long tokenCapacity
	) {
		return new SynchronizedRateLimiter(
			reloadPeriodNanoseconds, tokensRefilledPerPeriod, tokenCapacity
		);
	}
}
