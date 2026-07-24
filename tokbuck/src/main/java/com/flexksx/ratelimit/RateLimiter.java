package com.flexksx.ratelimit;

public interface RateLimiter {
	boolean tryAcquire();
	boolean tryAcquire( long number);
}
