package com.flexksx;

import java.util.concurrent.TimeUnit;

import com.flexksx.ratelimit.RateLimiter;
import com.flexksx.ratelimit.RateLimiterFactory;


public class Main {
	static void main() {
		long capacity = 3;
		long tokenRefilledPerPeriod = 1;
		long reloadPeriodNanoseconds = TimeUnit.MILLISECONDS.toNanos(100);

	}


}
