/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * SocialNetwork provides methods that operate on a social network.
 * 
 * A social network is represented by a Map<String, Set<String>> where map[A] is
 * the set of people that person A follows on Twitter, and all people are
 * represented by their Twitter usernames. Users can't follow themselves. If A
 * doesn't follow anybody, then map[A] may be the empty set, or A may not even
 * exist as a key in the map; this is true even if A is followed by other people
 * in the network. Twitter usernames are not case sensitive, so "ernie" is the
 * same as "ERNie". A username should appear at most once as a key in the map or
 * in any given map[A] set.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class SocialNetwork {

	/**
	 * Guess who might follow whom, from evidence found in tweets.
	 * 
	 * @param tweets a list of tweets providing the evidence, not modified by this
	 *               method.
	 * @return a social network (as defined above) in which Ernie follows Bert if
	 *         and only if there is evidence for it in the given list of tweets. One
	 *         kind of evidence that Ernie follows Bert is if Ernie
	 * @-mentions Bert in a tweet. This must be implemented. Other kinds of evidence
	 *            may be used at the implementor's discretion. All the Twitter
	 *            usernames in the returned social network must be either authors
	 *            or @-mentions in the list of tweets.
	 */
	public static Map<String, Set<String>> guessFollowsGraph(List<Tweet> tweets) {
		final Map<String, Set<String>> result = new MyHashMap<>();
		final List<Tweet> tweetsInmutable = Collections.unmodifiableList(tweets);
		if (!tweetsInmutable.isEmpty()) {
			final Map<String,Set<String>> mapHashtags = new HashMap<>();
			for (Tweet tweet : tweetsInmutable) {
				Set<String> follows = Extract.getMentionedUsers(Arrays.asList(tweet));
				if (!follows.isEmpty()) {
					result.put(tweet.getAuthor(), follows);
				}
				
				//share same hashtag
				final Set<String> hashtagsOfTweet = Extract.getMentionedHashtags(Arrays.asList(tweet));
				for(String hashtag: hashtagsOfTweet) {
					if(!mapHashtags.containsKey(hashtag)){
						mapHashtags.put(hashtag, new MyHashSet<String>());
					}
					mapHashtags.get(hashtag).add(tweet.getAuthor());
				}
			}
			for(String keyHashtag: mapHashtags.keySet()) {
				Set<String> users = mapHashtags.get(keyHashtag);
				for(String userResult:users) {
					for(String userMapHashtag:users) {
						if(!userResult.equals(userMapHashtag)) {
							if(!result.containsKey(userResult)) {
								result.put(userResult, new MyHashSet<String>());
							}
							result.get(userResult).add(userMapHashtag);
						}
					}
				}
			}
			
		}
		assert tweets.equals(tweetsInmutable);
		return Collections.unmodifiableMap(result);
	}

	/**
	 * Find the people in a social network who have the greatest influence, in the
	 * sense that they have the most followers.
	 * 
	 * @param followsGraph a social network (as defined above)
	 * @return a list of all distinct Twitter usernames in followsGraph, in
	 *         descending order of follower count.
	 */
	public static List<String> influencers(Map<String, Set<String>> followsGraph) {
		final Set<Entry<String, Set<String>>> setEntry = followsGraph.entrySet();
		@SuppressWarnings("unchecked")
		Entry<String, Set<String>>[] setEntryToArray = setEntry.toArray(new Entry[setEntry.size()]);
		Arrays.sort(setEntryToArray, (a, b) -> b.getValue().size() - a.getValue().size());
		final List<String> result = Arrays.asList(setEntryToArray).stream().map((e) -> e.getKey()).toList();
		return Collections.unmodifiableList(result);
	}

}
