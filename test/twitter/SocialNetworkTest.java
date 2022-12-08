/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class SocialNetworkTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     */
    /*
     * test strategy for guessFollowsGraph;
     * tweets.size: 0, 1, >1;
     * tweets.size >1: same autor, diferent autor;
     * follow: 0, 1, >1;
     */
	
	private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
	private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
	private static final Instant d3 = Instant.parse("2016-02-17T12:00:00Z");
	private static final Instant d4 = Instant.parse("2016-02-17T13:00:00Z");
	private static final Instant d5 = Instant.parse("2016-02-17T14:00:00Z");

	private static final Tweet tweetCeroMencionCeroUsuarioInvalido = new Tweet(1, "alyssa",
			"is it reasonable to talk about rivest so much?", d1);
	private static final Tweet tweetUnaMencionCeroUsuarioInvalido = new Tweet(2, "bbitdiddle",
			"@MikE rivest talk in 30 minutes #hype", d2);
	private static final Tweet tweetMayorUnaMencionCeroUsuarioInvalido = new Tweet(3, "tom",
			"@Dora rivest talk in 30 @Mike minutes #hype @victor", d3);
	private static final Tweet tweetCeroMencionUnUsuarioInvalido = new Tweet(1, "alyssa",
			"is it reasonable to talk @mike! about rivest so much?", d1);
	private static final Tweet tweetUnaMencionUnUsuarioInvalido = new Tweet(2, "bbitdiddle",
			"@MikE rivest talk mike@gmail.com in 30 minutes #hype", d2);
	private static final Tweet tweetMayorUnaMencionUnUsuarioInvalido = new Tweet(3, "tom",
			"@Dora rivest talk in 30 @Mike minutes @victor? #hype @victor", d3);
	private static final Tweet tweetCeroMencionMayorUnUsuarioInvalido = new Tweet(1, "alyssa",
			"is it @mike! mail@bob.com reasonable to talk about rivest so much?", d1);
	private static final Tweet tweetUnaMencionMayorUnUsuarioInvalido = new Tweet(2, "bbitdiddle",
			"@MikE rivest @mike! mail@bob.com talk in 30 minutes #hype", d2);
	private static final Tweet tweetMayorUnaMencionMayorUnUsuarioInvalido = new Tweet(3, "tom",
			"@Dora rivest talk in 30 @Mike minutes @mike! mail@bob.com #hype @victor", d3);
	private static final Tweet tweetCaseInsensitive = new Tweet(4, "Mike",
			"@Mike @mike rivest talk in 30 minutes #hype @miKe", d4);
	private static final Tweet tweetCaseInsensitiveMayorUnTweet = new Tweet(4, "Mike",
			"@Mike @mike @Victor @VictoR rivest talk @mike! mail@bob.com in 30 minutes #hype @miKe", d5);

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    /*
     * guessFollowsGraph
     */
    /*
     * tweets.size = 0
     * follow: 0
     */
    @Test
    public void testGuessFollowsGraphEmpty() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(new ArrayList<>());
        assertTrue("expected empty graph", followsGraph.isEmpty());
    }

    
    @Test
    public void testInfluencersEmpty() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        assertTrue("expected empty list", influencers.isEmpty());
    }

    /*
     * Warning: all the tests you write here must be runnable against any
     * SocialNetwork class that follows the spec. It will be run against several
     * staff implementations of SocialNetwork, which will be done by overwriting
     * (temporarily) your version of SocialNetwork with the staff's version.
     * DO NOT strengthen the spec of SocialNetwork or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in SocialNetwork, because that means you're testing a
     * stronger spec than SocialNetwork says. If you need such helper methods,
     * define them in a different class. If you only need them in this test
     * class, then keep them in this test class.
     */

}
