package com.intuit.ipp.query;

import static com.intuit.ipp.query.GenerateQuery.$;
import static com.intuit.ipp.query.GenerateQuery.select;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Queries built on different threads at the same time must not share state (issue #223).
 */
public class GenerateQueryConcurrencyTest {

	private static final int THREADS = 8;
	private static final int ITERATIONS = 500;

	@Test
	public void testQuery_concurrentThreadsDoNotMixQueries() throws Exception {
		ExecutorService executor = Executors.newFixedThreadPool(THREADS);
		CyclicBarrier startTogether = new CyclicBarrier(THREADS);
		List<Future<String>> results = new ArrayList<Future<String>>();

		try {
			for (int t = 0; t < THREADS; t++) {
				final int threadNo = t;
				results.add(executor.submit(new Callable<String>() {
					@Override
					public String call() throws Exception {
						startTogether.await();
						for (int i = 0; i < ITERATIONS; i++) {
							String value = "thread" + threadNo + "-" + i;
							Data data = GenerateQuery.createQueryEntity(Data.class);
							String query = select($(data.getStringData())).where($(data.getStringData()).eq(value)).generate();
							String expected = "SELECT StringData FROM Data WHERE StringData = '" + value + "'";
							if (!expected.equals(query)) {
								return "expected <" + expected + "> but got <" + query + ">";
							}
						}
						return null;
					}
				}));
			}

			for (Future<String> result : results) {
				String failure = result.get(60, TimeUnit.SECONDS);
				Assert.assertNull(failure, failure);
			}
		} finally {
			executor.shutdownNow();
		}
	}
}
