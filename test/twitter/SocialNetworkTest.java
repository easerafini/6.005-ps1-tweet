/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class SocialNetworkTest {

	/*
	 * TODO: your testing strategies for these methods should go here. See the
	 * ic03-testing exercise for examples of what a testing strategy comment looks
	 * like. Make sure you have partitions.
	 */
	/*
	 * test strategy for guessFollowsGraph; tweets.size: 0, 1, >1; autor : single
	 * autor, multiple autor; follow: 0, 1, >1;
	 * 
	 * test strategy for influencers followersGraphs: empty, not empty
	 */

	private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
	private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
	private static final Instant d3 = Instant.parse("2016-02-17T12:00:00Z");

	private static final Tweet tweetCeroMencionCeroUsuarioInvalido = new Tweet(1, "alyssa",
			"is it reasonable to talk about rivest so much?", d1);
	private static final Tweet tweetUnaMencionCeroUsuarioInvalido = new Tweet(2, "bbitdiddle",
			"@MikE rivest talk in 30 minutes hype", d2);
	private static final Tweet tweetMayorUnaMencionCeroUsuarioInvalido = new Tweet(3, "tom",
			"@Dora rivest talk in 30 @Mike minutes hype @victor", d3);
	private static final Tweet tweetMayorUnaMencionUnUsuarioInvalido = new Tweet(3, "tom",
			"@Dora rivest talk in 30 @Mike minutes @victor? hype @victor", d3);
	private static final Tweet tweetUnaMencionMayorUnUsuarioInvalido = new Tweet(2, "bbitdiddle",
			"@MikE rivest @mike! mail@bob.com talk in 30 minutes hype", d2);
	private static final Tweet tweetMayorUnaMencionUnUsuarioInvalido1 = new Tweet(3, "alyssa",
			"@Dora rivest talk in 30 @Mike minutes @victor? hype", d3);

	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}

	/*
	 * guessFollowsGraph
	 */
	/*
	 * tweets.size = 0 follow: 0
	 */
	@Test
	public void testGuessFollowsGraphEmpty() {
		final Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(new ArrayList<>());
		assertTrue("expected empty graph", followsGraph.isEmpty());
	}

	/*
	 * tweets.size = 1 follow: 0
	 */
	@Test
	public void testGuessFollowsUnTweetGraphEmpty() {
		final Map<String, Set<String>> followsGraph = SocialNetwork
				.guessFollowsGraph(Arrays.asList(tweetCeroMencionCeroUsuarioInvalido));
		assertTrue("expected empty graph", followsGraph.isEmpty());
	}

	/*
	 * tweets.size = 1 autor: single follow: 1
	 */
	@Test
	public void testGuessFollowsUnTweetUnAutorUnSeguidor() {
		final Map<String, Set<String>> followsGraph = SocialNetwork
				.guessFollowsGraph(Arrays.asList(tweetUnaMencionCeroUsuarioInvalido));
		assertEquals("expected 1 user", 1, followsGraph.size());
		assertTrue("Expected true", followsGraph.containsKey("bbitdiddle"));
		final Set<String> follows = followsGraph.get("bbitdiddle");
		assertEquals("Expected 1 follow", 1, follows.size());
		assertTrue("Expected true", follows.contains("mike"));
	}

	/*
	 * tweets.size = 1 autor: single follow: >1
	 */
	@Test
	public void testGuessFollowsUnTweetUnAutorMultipleSeguidor() {
		final Map<String, Set<String>> followsGraph = SocialNetwork
				.guessFollowsGraph(Arrays.asList(tweetMayorUnaMencionCeroUsuarioInvalido));
		assertEquals("expected 1 user", 1, followsGraph.size());
		assertTrue("Expected true", followsGraph.containsKey("tom"));
		final Set<String> follows = followsGraph.get("tom");
		assertEquals("Expected 3 follow", 3, follows.size());
		assertTrue("Expected true", follows.contains("dora"));
		assertTrue("Expected true", follows.contains("mike"));
		assertTrue("Expected true", follows.contains("victor"));
	}

	/*
	 * tweets.size > 1; autor: single; follow: >1
	 */
	@Test
	public void testGuessFollowsMultipleTweetUnAutorMultipleSeguidor() {
		final Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(
				Arrays.asList(tweetMayorUnaMencionCeroUsuarioInvalido, tweetMayorUnaMencionUnUsuarioInvalido));
		assertEquals("Expected 1 user", 1, followsGraph.size());
		assertTrue("Expected true", followsGraph.containsKey("tom"));
		final Set<String> follows = followsGraph.get("tom");
		assertEquals("Expected 3 follow", 3, follows.size());
		assertTrue("Expected true", follows.contains("dora"));
		assertTrue("Expected true", follows.contains("mike"));
		assertTrue("Expected true", follows.contains("victor"));
	}

	/*
	 * tweets.size > 1; autor: multiple; follow: >1 Case-Insensitive list tweets no
	 * modified by this method
	 */
	@Test
	public void testGuessFollowsMultipleTweetMultipleAutorMultiplefollow() {
		final List<Tweet> tweets = Arrays.asList(tweetUnaMencionMayorUnUsuarioInvalido,
				tweetMayorUnaMencionUnUsuarioInvalido);
		final List<Tweet> copyTweets = Arrays.asList(tweetUnaMencionMayorUnUsuarioInvalido,
				tweetMayorUnaMencionUnUsuarioInvalido);

		final Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);
		assertEquals("Expected 2 user", 2, followsGraph.size());
		assertTrue("Expected true", followsGraph.containsKey("bbitdiddle"));
		assertTrue("Expected true", followsGraph.containsKey("tom"));
		assertTrue("Expected true", followsGraph.containsKey("Bbitdiddle"));
		assertTrue("Expected true", followsGraph.containsKey("toM"));

		Set<String> follows = followsGraph.get("tom");
		assertEquals("Expected 3 follow", 3, follows.size());
		assertTrue("Expected true", follows.contains("dora"));
		assertTrue("Expected true", follows.contains("mike"));
		assertTrue("Expected true", follows.contains("victor"));

		follows = followsGraph.get("bbitdiddle");
		assertEquals("Expected 1 follow", 1, follows.size());
		assertTrue("Expected true", follows.contains("mike"));

		assertEquals("Expected equals", tweets, copyTweets);
	}

	/*
	 * influencers
	 */
	/*
	 * followersGraphs is empty
	 */
	@Test
	public void testInfluencersEmpty() {
		final Map<String, Set<String>> followsGraph = new HashMap<>();
		List<String> influencers = SocialNetwork.influencers(followsGraph);

		assertTrue("expected empty list", influencers.isEmpty());
	}

	/*
	 * followersGraphs is not empty
	 */
	@Test
	public void testInfluencersFollowersGraphsNotEmpty() {
		final List<Tweet> tweets = Arrays.asList(tweetUnaMencionMayorUnUsuarioInvalido,
				tweetMayorUnaMencionUnUsuarioInvalido,tweetMayorUnaMencionUnUsuarioInvalido1);
		final Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);
		List<String> influencers = SocialNetwork.influencers(followsGraph);

		assertEquals("expected three users", 3, influencers.size());
		assertEquals("expected first tom", "tom", influencers.get(0));
		assertEquals("expected second alyssa", "alyssa", influencers.get(1));
		assertEquals("expected three bbitdiddle", "bbitdiddle", influencers.get(2));
	}

	/*
	 * Warning: all the tests you write here must be runnable against any
	 * SocialNetwork class that follows the spec. It will be run against several
	 * staff implementations of SocialNetwork, which will be done by overwriting
	 * (temporarily) your version of SocialNetwork with the staff's version. DO NOT
	 * strengthen the spec of SocialNetwork or its methods.
	 * 
	 * In particular, your test cases must not call helper methods of your own that
	 * you have put in SocialNetwork, because that means you're testing a stronger
	 * spec than SocialNetwork says. If you need such helper methods, define them in
	 * a different class. If you only need them in this test class, then keep them
	 * in this test class.
	 */

}
