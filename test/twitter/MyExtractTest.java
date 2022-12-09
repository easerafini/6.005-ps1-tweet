package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import org.junit.Test;

public class MyExtractTest {
	/*
	 * test strategy for getMentionedHashtag() tweets.size = 0, 1, >1 Hashtag:
	 * empty, single, multiple
	 * 
	 */

	private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
	private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
	private static final Instant d3 = Instant.parse("2016-02-17T12:00:00Z");

	private static final Tweet tweetEmptyHashtag = new Tweet(1, "alyssa",
			"is it reasonable to talk about rivest so much?", d1);
	private static final Tweet tweetSingleHashtag = new Tweet(2, "bbitdiddle", "@MikE rivest talk in 30 minutes #hype",
			d2);
	private static final Tweet tweetMultipleHashtag = new Tweet(3, "tom",
			"@Dora rivest #mit talk in 30 @Mike #Topic minutes #hype @victor", d3);

	// getMentionedHashtag()
	/*
	 * covers tweets.size: 0 , 1 , >1 Hashtag: empty
	 * 
	 */
	@Test
	public void testGetMentionedHashtagEmptyHashtag() {
		// tweet.size = 0
		Set<String> mentionedHashtag = Extract.getMentionedHashtags(new ArrayList<>());
		assertTrue("expected empty set", mentionedHashtag.isEmpty());

		// tweet.size = 1
		mentionedHashtag = Extract.getMentionedHashtags(Arrays.asList(tweetEmptyHashtag));
		assertTrue("expected empty set", mentionedHashtag.isEmpty());

		// tweet.size > 1
		mentionedHashtag = Extract.getMentionedHashtags(Arrays.asList(tweetEmptyHashtag, tweetEmptyHashtag));
		assertTrue("expected empty set", mentionedHashtag.isEmpty());

	}

	/*
	 * covers tweets.size: 1 , >1 hashtag: single
	 * 
	 */
	@Test
	public void testGetMentionedHashtagSingleHashtag() {
		// tweet.size = 1
		Set<String> mentionedHashtag = Extract.getMentionedHashtags(Arrays.asList(tweetSingleHashtag));
		assertEquals("expected single hashtag", 1, mentionedHashtag.size());
		assertTrue("expcted hype hashtag", mentionedHashtag.contains("hype"));

		// tweet.size > 1
		mentionedHashtag = Extract.getMentionedHashtags(Arrays.asList(tweetSingleHashtag, tweetEmptyHashtag));
		assertEquals("expected single hashtag", 1, mentionedHashtag.size());
		assertTrue("expcted hype hashtag", mentionedHashtag.contains("hype"));

	}

	/*
	 * covers tweets.size: 1 , >1 hashtag: multiple
	 * 
	 */
	@Test
	public void testGetMentionedHashtagMultipleHashtag() {
		// tweets.size = 1
		Set<String> mentionedHashtag = Extract.getMentionedHashtags(Arrays.asList(tweetMultipleHashtag));
		assertTrue("expected multiple hashtag", mentionedHashtag.size() > 1);
		assertTrue("expcted hype hashtag", mentionedHashtag.contains("hype"));
		assertTrue("expcted mit hashtag", mentionedHashtag.contains("mit"));
		assertTrue("expcted Topic hashtag", mentionedHashtag.contains("Topic"));
		
		// tweets.size > 1
		mentionedHashtag = Extract.getMentionedHashtags(Arrays.asList(tweetSingleHashtag, tweetMultipleHashtag));
		assertTrue("expected multiple hashtag", mentionedHashtag.size() > 1);
		assertTrue("expcted hype hashtag", mentionedHashtag.contains("hype"));
		assertTrue("expcted mit hashtag", mentionedHashtag.contains("mit"));
		assertTrue("expcted Topic hashtag", mentionedHashtag.contains("Topic"));
	}

}
