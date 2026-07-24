package com.flexksx.ratelimit.impl.synchro;

import com.flexksx.ratelimit.RateLimiter;

public class SynchronizedRateLimiter implements RateLimiter {
	private static final int MINIMUM_TOKEN_PER_ACQUIRE = 1;

	private final long reloadPeriodNanoseconds;
	private final long tokensRefilledPerPeriod;
	private final long tokenCapacity;

	private long lastRefillTimeNanoseconds;
	private long availableTokens;

	public SynchronizedRateLimiter(long tokenCapacity, long tokensRefilledPerPeriod, long reloadPeriodNanoseconds) {
		if (tokenCapacity < MINIMUM_TOKEN_PER_ACQUIRE) {
			throw new IllegalArgumentException("tokenCapacity must be greater than 0");
		}
		if (tokensRefilledPerPeriod < MINIMUM_TOKEN_PER_ACQUIRE) {
			throw new IllegalArgumentException("tokensRefilledPerPeriod must be greater than 0");
		}
		if (reloadPeriodNanoseconds < MINIMUM_TOKEN_PER_ACQUIRE) {
			throw new IllegalArgumentException("reloadPeriodNanoseconds must be greater than 0");
		}

		this.tokenCapacity = tokenCapacity;
		this.tokensRefilledPerPeriod = tokensRefilledPerPeriod;
		this.reloadPeriodNanoseconds = reloadPeriodNanoseconds;
		this.availableTokens = tokenCapacity;
		this.lastRefillTimeNanoseconds = System.nanoTime();
	}

	@Override
	public boolean tryAcquire() {
		return tryAcquire(1);
	}

	@Override
	public synchronized boolean tryAcquire(long number) {
		if (number < MINIMUM_TOKEN_PER_ACQUIRE) {
			throw new IllegalArgumentException("Cannot acquire less than " + MINIMUM_TOKEN_PER_ACQUIRE + " token.");
		}
		if (number > tokenCapacity) {
			return false;
		}

		refillIfAvailable();

		if (availableTokens < number) {
			return false;
		}

		availableTokens -= number;
		return true;
	}

	private void refillIfAvailable() {
		long now = System.nanoTime();
		long elapsedNanoseconds = Math.max(0, now - lastRefillTimeNanoseconds);

		long tokensToGenerate = (elapsedNanoseconds * tokensRefilledPerPeriod) / reloadPeriodNanoseconds;

		if (tokensToGenerate > 0) {
			this.availableTokens = Math.min(tokenCapacity, this.availableTokens + tokensToGenerate);

			long consumedNanoseconds = (tokensToGenerate * reloadPeriodNanoseconds) / tokensRefilledPerPeriod;
			this.lastRefillTimeNanoseconds += consumedNanoseconds;
		}
	}
}