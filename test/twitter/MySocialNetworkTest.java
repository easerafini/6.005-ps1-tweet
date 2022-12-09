package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class MySocialNetworkTest {
	/*
	 * test strategy gessFollowsGraph for same hashtag
	 */
	
	private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
	private static final Instant d3 = Instant.parse("2016-02-17T12:00:00Z");

	private static final Tweet tweetUnaMencion = new Tweet(1, "bbitdiddle",
			"@MikE rivest talk in 30 minutes #hype", d2);
	private static final Tweet tweetMayorUnaMencion = new Tweet(2, "tom",
			"@Dora rivest talk in 30 @Mike minutes #hype @victor", d3);

	@Test
	public final void testGuessFollowsGraph() {
		final List<Tweet> tweets = Arrays.asList(tweetUnaMencion,
				tweetMayorUnaMencion);

		final Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);
		assertEquals("Expected 2 user", 2, followsGraph.size());
		assertTrue("Expected true", followsGraph.containsKey("bbitdiddle"));
		assertTrue("Expected true", followsGraph.containsKey("tom"));
		assertTrue("Expected true", followsGraph.containsKey("Bbitdiddle"));
		assertTrue("Expected true", followsGraph.containsKey("toM"));
		
		Set<String> follows = followsGraph.get("tom");
		assertEquals("Expected 3 follow", 4, follows.size());
		assertTrue("Expected true", follows.contains("dora"));
		assertTrue("Expected true", follows.contains("mike"));
		assertTrue("Expected true", follows.contains("victor"));
		assertTrue("Expected follow bbitdiddle", follows.contains("bbitdiddle"));

		follows = followsGraph.get("bbitdiddle");
		assertEquals("Expected 2 follow", 2, follows.size());
		assertTrue("Expected true", follows.contains("mike"));
		assertTrue("Expected true", follows.contains("tom"));

	}

}
