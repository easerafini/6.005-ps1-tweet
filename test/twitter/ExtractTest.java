/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class ExtractTest {

	/*
	 * TODO: your testing strategies for these methods should go here. See the
	 * ic03-testing exercise for examples of what a testing strategy comment looks
	 * like. Make sure you have partitions.
	 */

	/*
	 * Estrategia de test para geTimespan() tweets.size() == 0 , 1 , >1 Tweets
	 * timestamp: todos distintos, algunos iguales, todos iguales
	 * 
	 * Estrategia de test getMentionedUser() tweets.size = 1, >1 Mencion: 0, 1, >1
	 * Case-insensitive: 0, >0 usuario invalido: 0, >0
	 * 
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

	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}

	// getTimespan()

	// covers
	// tweets.size() = 1
	@Test
	public void testGetTimespanOneTweet() {
		Timespan timespan = Extract.getTimespan(Arrays.asList(tweetCeroMencionCeroUsuarioInvalido));
		assertEquals("expected start", d1, timespan.getStart());
		assertEquals("expected end", d1, timespan.getEnd());

	}

	// covers
	// tweets.size > 1
	// Tweets timestamp: todos distintos
	@Test
	public void testGetTimespanMasUnTweetTodosDistintos() {
		Timespan timespan = Extract.getTimespan(Arrays.asList(tweetCeroMencionCeroUsuarioInvalido,
				tweetCeroMencionUnUsuarioInvalido, tweetMayorUnaMencionCeroUsuarioInvalido));
		assertEquals("expected start", d1, timespan.getStart());
		assertEquals("expected end", d3, timespan.getEnd());

	}

	@Test
	public void testGetTimespanTwoTweets() {
		Timespan timespan = Extract
				.getTimespan(Arrays.asList(tweetCeroMencionCeroUsuarioInvalido, tweetUnaMencionCeroUsuarioInvalido));

		assertEquals("expected start", d1, timespan.getStart());
		assertEquals("expected end", d2, timespan.getEnd());
	}

	// covers
	// tweets.size > 1
	// Tweets timestamp: todos iguales
	@Test
	public void testGetTimespanMasUnTweetTodosIguales() {
		Timespan timespan = Extract.getTimespan(Arrays.asList(tweetCeroMencionCeroUsuarioInvalido,
				tweetCeroMencionCeroUsuarioInvalido, tweetCeroMencionCeroUsuarioInvalido));
		assertEquals("expected start", d1, timespan.getStart());
		assertEquals("expected end", d1, timespan.getEnd());

	}

	// covers
	// tweets.size() > 1
	// Tweets timestamp: algunos iguales
	@Test
	public void testGetTimespanMasUnTweetAlgunosIguales() {
		Timespan timespan = Extract.getTimespan(Arrays.asList(tweetCeroMencionCeroUsuarioInvalido,
				tweetUnaMencionCeroUsuarioInvalido, tweetUnaMencionCeroUsuarioInvalido));
		assertEquals("expected start", d1, timespan.getStart());
		assertEquals("expected end", d2, timespan.getEnd());

	}

	// getMentionedUsers()

	/*
	 * covers tweets.size: 1, >1 Mencion: 0, 1 , >1 Usuario invalido: 0, 1, >1
	 * Case-insensitive: 0, 1, >1
	 */

	/*
	 * covers tweets.size: 1, >1 Mention: 0 usuario invalido: 0
	 */
	@Test
	public void testGetMentionedUsersCeroMencionCeroUsuarioInvalido() {
		// tweet.size = 1
		Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweetCeroMencionCeroUsuarioInvalido));
		assertTrue("expected empty set", mentionedUsers.isEmpty());

		// tweet.size > 1
		mentionedUsers = Extract.getMentionedUsers(
				Arrays.asList(tweetCeroMencionCeroUsuarioInvalido, tweetCeroMencionCeroUsuarioInvalido));
		assertTrue("expected empty set", mentionedUsers.isEmpty());

	}

	/*
	 * covers tweets.size: 1, >1 Mention: 1 usuario invalido: 0 Case-insensitive: 0,
	 * >0
	 */
	@Test
	public void testGetMentionedUsersUnaMencionCeroUsuarioInvalido() {
		// tweets.size = 1
		// Case-insensitive = 0
		Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweetUnaMencionCeroUsuarioInvalido));
		assertEquals("expected 1 mencion", 1, mentionedUsers.size());
		// tweets.size > 1
		// Case-insensitive >0
		mentionedUsers = Extract.getMentionedUsers(
				Arrays.asList(tweetUnaMencionCeroUsuarioInvalido, tweetUnaMencionCeroUsuarioInvalido));
		assertEquals("expected 1 mencion", 1, mentionedUsers.size());
	}

	/*
	 * covers tweets.size: 1, >1 Mention: >1 usuario invalido: 0 Case-insensitive:
	 * 0, 0>
	 */
	@Test
	public void testGetMentionedUsersMayorUnaMencionCeroUsuarioInvalido() {
		// tweets.size = 1
		Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweetMayorUnaMencionCeroUsuarioInvalido));
		assertTrue("expected mas de 1 mencion", mentionedUsers.size() > 1);
		// tweets.size > 1
		// Case-insensitive > 0
		mentionedUsers = Extract.getMentionedUsers(
				Arrays.asList(tweetMayorUnaMencionCeroUsuarioInvalido, tweetMayorUnaMencionCeroUsuarioInvalido));
		assertTrue("expected mas de 1 mencion", mentionedUsers.size() > 1);
	}

	/*
	 * covers tweets.size: 1, >1 Mention: 0 usuario invalido: 0
	 */
	@Test
	public void testGetMentionedUsersCeroMencionUnUsuarioInvalido() {
		// tweet.size = 1
		Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweetCeroMencionUnUsuarioInvalido));
		assertTrue("expected empty set", mentionedUsers.isEmpty());

		// tweet.size > 1
		mentionedUsers = Extract
				.getMentionedUsers(Arrays.asList(tweetCeroMencionUnUsuarioInvalido, tweetCeroMencionUnUsuarioInvalido));
		assertTrue("expected empty set", mentionedUsers.isEmpty());

	}

	/*
	 * covers tweets.size: 1, >1 Mention: 1 usuario invalido: 1 Case-insensitive: 0,
	 * >0
	 */
	@Test
	public void testGetMentionedUsersUnaMencionUnUsuarioInvalido() {
		// tweets.size = 1
		// Case-insensitive = 0
		Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweetUnaMencionUnUsuarioInvalido));
		assertEquals("expected 1 mencion", 1, mentionedUsers.size());
		// tweets.size > 1
		// Case-insensitive >0
		mentionedUsers = Extract.getMentionedUsers(
				Arrays.asList(tweetUnaMencionUnUsuarioInvalido, tweetUnaMencionUnUsuarioInvalido));
		assertEquals("expected 1 mencion", 1, mentionedUsers.size());
	}

	/*
	 * covers tweets.size: 1, >1 Mention: >1 usuario invalido: 1 Case-insensitive:
	 * 0, 0>
	 */
	@Test
	public void testGetMentionedUsersMayorUnaMencionUnUsuarioInvalido() {
		// tweets.size = 1
		Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweetMayorUnaMencionUnUsuarioInvalido));
		assertTrue("expected mas de 1 mencion", mentionedUsers.size() > 1);
		// tweets.size > 11
		// Case-insensitive > 0
		mentionedUsers = Extract.getMentionedUsers(
				Arrays.asList(tweetMayorUnaMencionUnUsuarioInvalido, tweetMayorUnaMencionUnUsuarioInvalido));
		assertTrue("expected mas de 1 mencion", mentionedUsers.size() > 1);
	}

	/*
	 * covers tweets.size: 1, >1 Mention: 0 usuario invalido: >1
	 */
	@Test
	public void testGetMentionedUsersCeroMencionMayorUnUsuarioInvalido() {
		// tweet.size = 1
		Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweetCeroMencionMayorUnUsuarioInvalido));
		assertTrue("expected empty set", mentionedUsers.isEmpty());

		// tweet.size > 1
		mentionedUsers = Extract.getMentionedUsers(
				Arrays.asList(tweetCeroMencionMayorUnUsuarioInvalido, tweetCeroMencionMayorUnUsuarioInvalido));
		assertTrue("expected empty set", mentionedUsers.isEmpty());

	}

	/*
	 * covers tweets.size: 1, >1 Mention: 1 usuario invalido: 0 Case-insensitive: 0,
	 * >0
	 */
	@Test
	public void testGetMentionedUsersUnMencionMayorMayorUnUsuarioInvalido() {
		// tweets.size = 1
		// Case-insensitive = 0
		Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweetUnaMencionMayorUnUsuarioInvalido));
		assertEquals("expected 1 mencion", 1, mentionedUsers.size());
		// tweets.size > 1
		// Case-insensitive >0
		mentionedUsers = Extract.getMentionedUsers(
				Arrays.asList(tweetUnaMencionMayorUnUsuarioInvalido, tweetUnaMencionMayorUnUsuarioInvalido));
		assertEquals("expected 1 mencion", 1, mentionedUsers.size());
	}

	/*
	 * covers tweets.size: 1, >1 Mention: >1 usuario invalido: 0 Case-insensitive:
	 * 0, 0>
	 */
	@Test
	public void testGetMentionedUsersMayorUnaMencionMayorUnUsuarioInvalido() {
		// tweets.size = 1
		Set<String> mentionedUsers = Extract
				.getMentionedUsers(Arrays.asList(tweetMayorUnaMencionMayorUnUsuarioInvalido));
		assertTrue("expected mas de 1 mencion", mentionedUsers.size() > 1);
		// tweets.size > 1
		// Case-insensitive > 0
		mentionedUsers = Extract.getMentionedUsers(
				Arrays.asList(tweetMayorUnaMencionMayorUnUsuarioInvalido, tweetMayorUnaMencionMayorUnUsuarioInvalido));
		assertTrue("expected mas de 1 mencion", mentionedUsers.size() > 1);
	}

	/*
	 * covers tweets.size: 1 Mention: 1 usuario invalido: >0 Case-insensitive: 0>
	 */
	@Test
	public void testGetMentionedUsersCaseInsensitive() {
		Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweetCaseInsensitive));
		assertEquals("expected 1 mencion", 1, mentionedUsers.size());
		assertTrue("expected true", mentionedUsers.contains("mike"));
		assertTrue("expected true", mentionedUsers.contains("MIKE"));
		assertTrue("expected true", mentionedUsers.contains("mikE"));
		assertTrue("expected true", mentionedUsers.contains("Mike"));
	}


	/*
	 * covers tweets.size: >1 Mention: >1 usuario invalido: >0 Case-insensitive: 0>
	 */
	@Test
	public void testGetMentionedUsersCaseInsensitiveMayorUnTweet() {
		Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweetCaseInsensitive,tweetCaseInsensitiveMayorUnTweet));
		assertEquals("expected 2 menciones", 2, mentionedUsers.size());
		assertTrue("expected true", mentionedUsers.contains("mike"));
		assertTrue("expected true", mentionedUsers.contains("victor"));
		assertTrue("expected true", mentionedUsers.contains("mIke"));
		assertTrue("expected true", mentionedUsers.contains("VICTOR"));
		assertFalse("expected false, victor fue agregado", mentionedUsers.add("VICTOR"));
		assertFalse("expected false, mike fue agragado", mentionedUsers.add("mike"));
	}
	
	/*
	 * Test que lista de tweets no cambia
	 */
    @Test
    public void testGetMentionedUsersNoCambiaListaTweets() {
        
        final List<Tweet> listaOriginal = Arrays.asList(tweetCaseInsensitive,tweetCaseInsensitiveMayorUnTweet);
        final List<Tweet> copiaListaOriginal = Arrays.asList(tweetCaseInsensitive,tweetCaseInsensitiveMayorUnTweet);
        assertTrue("se espera que ambas listas sean iguales",listaOriginal.equals(copiaListaOriginal));
        Set<String> mentionedUsers = Extract.getMentionedUsers(listaOriginal);
        assertEquals("expected 2 menciones", 2, mentionedUsers.size());
        assertTrue("expected true", mentionedUsers.contains("mike"));
        assertTrue("expected true", mentionedUsers.contains("victor"));
        assertTrue("expected true", mentionedUsers.contains("mIke"));
        assertTrue("expected true", mentionedUsers.contains("VICTOR"));
        assertFalse("expected false, victor fue agregado", mentionedUsers.add("VICTOR"));
        assertFalse("expected false, mike fue agragado", mentionedUsers.add("mike"));
        assertTrue("se espera que ambas listas sean iguales",listaOriginal.equals(copiaListaOriginal));
        
    }
	
	/*
	 * Warning: all the tests you write here must be runnable against any Extract
	 * class that follows the spec. It will be run against several staff
	 * implementations of Extract, which will be done by overwriting (temporarily)
	 * your version of Extract with the staff's version. DO NOT strengthen the spec
	 * of Extract or its methods.
	 * 
	 * In particular, your test cases must not call helper methods of your own that
	 * you have put in Extract, because that means you're testing a stronger spec
	 * than Extract says. If you need such helper methods, define them in a
	 * different class. If you only need them in this test class, then keep them in
	 * this test class.
	 */

}
