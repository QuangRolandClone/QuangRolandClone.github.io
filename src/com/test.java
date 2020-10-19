package com;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.scope.FacebookPermissions;
import com.restfb.scope.ScopeBuilder;
import com.restfb.types.Comment;
import com.restfb.types.Comments;
import com.restfb.types.Post;
import com.restfb.types.User;

public class test {
	public static void main(String[] args) {
//		FacebookClient facebookUserClient = new DefaultFacebookClient("EAAJ16N6kaOIBALU813Kg6NL0OD1qZBI2066NQaUdfRf74nc3O9XZBSAG7AFDxE8TcO3cJTAFHy7ZCf5fnXLIRHw6nAnXZBbvdKXui3S7qHgG69PqyY2TcjGbhvbZCRGQ2ZAp4kZAmSZAz7yh87yZA5O2EIatzjLCYqVJSCfejqfYvZCMVPXf6lkgkaZAHXbcMsoQhlyq9JRTmuFupBDCQBgZCw8S4bDqeDu8PRBCyZBHOR40BxQZDZD", Version.LATEST);
//		
//		User user = facebookUserClient.fetchObject("me", User.class);
//		boolean isAdmin;
//		if (user.getId().compareTo("2746370918916670") == 0) {
//			isAdmin = true;
//		}
//		else {
//			isAdmin = false;
//		}
//		
//		String canteenPageId = "111411690697844";
//        String canteenPageName = "Quang Canteen";
//		
//		FacebookClient facebookPageClient = new DefaultFacebookClient("EAAJ16N6kaOIBAIoZBTEh9ZBbPusZBGQHN4guxVaGuZAslZAXQ5r0tQzz6UBbgZCdBGmXQDTvXZClMR6MmlnX05J2YBfRWLyFtvLU5OL8kpmWoVxdHvfuFLTLNpME55NVeP7mZCaTOSOskkLNW18ZAq7iSEbxDfMrUVpIfwOpLOqbF6wZDZD", Version.LATEST);
//		
//		Connection<Post> pageFeedConnection = facebookPageClient.fetchConnection("111411690697844/feed", Post.class,
//				  Parameter.with("fields", "id,message,permalink_url,comments{id,message}"));
//		
//		JSONObject pageFeedJSON = new JSONObject();
//		pageFeedJSON.put("isAdmin", isAdmin);
//		for (List<Post> pageFeed : pageFeedConnection) {
//			if (pageFeed != null) {
//				JSONArray postArray = new JSONArray();
//				
//				for (Post post : pageFeed) {
////					System.out.println("Post:");
////					System.out.println(post.getId());
////					System.out.println(post.getMessage());
////					System.out.println(post.getPermalinkUrl());
//					
//					JSONObject postJSON = new JSONObject();
//					postJSON.put("id", post.getId());
//					postJSON.put("message", post.getMessage());
//					postJSON.put("permalink_url", post.getPermalinkUrl());
//					
//					Comments commentList = post.getComments();
//					if (commentList != null) {
//						JSONArray commentArray = new JSONArray();
//						
//						for (Comment comment : commentList.getData()) {
//							if (isAdmin || comment.getMessage().contains("; [" + user.getId() + ";" + user.getName() + ";]") || comment.getMessage().contains("; [" + canteenPageId + ";" + canteenPageName + ";]")) {
////								System.out.println("Comment:");
////								System.out.println(comment.getId());
////								System.out.println(comment.getMessage());
//								
//								JSONObject commentJSON = new JSONObject();
//								commentJSON.put("message", comment.getMessage());
//								
//								Connection<Comment> replyCommentConnection = facebookPageClient.fetchConnection(comment.getId() + "/comments", Comment.class);
//								for (List<Comment> replyCommentList : replyCommentConnection) {
//									if (replyCommentList != null) {
//										JSONArray replyArray = new JSONArray();
//										
//										for (Comment replyComment : replyCommentList) {
////											System.out.println("Reply:");
////											System.out.println(replyComment.getMessage());
//											
//											JSONObject replyJSON = new JSONObject();
//											replyJSON.put("message", replyComment.getMessage());
//											replyArray.put(replyJSON);
//										}
//										
//										commentJSON.put("reply", replyArray);
//									}
//								}
//								
//								commentArray.put(commentJSON);
//							}
//						}
//						
//						postJSON.put("comment", commentArray);
//					}
//					
//					postArray.put(postJSON);
//				}
//				
//				pageFeedJSON.put("data", postArray);
//			}
//			else {
//				pageFeedJSON.put("data", "null");
//			}
//		}
//		
//		System.out.println(pageFeedJSON.toString());
		
		String code = "AQDbiPBhFs396Pb7-M2Iliw1vrnyXeYOqJFjYzj0ejmAAPXbdIRM5vGl1Q5pBBJCaFXIpm6Xr2IN-vi9vEqcoBZ0-hVrNv0socaCJ9ZlDSZ_GQ_WyoOewtUHxW8zMzF4WPLC-AmYXwI9Uhj6MjkcUoQgOwzYZfYGNZTDCmBa8YnbyhsUVBNfdU7AAxNeJEVHOFbFrI__ZEEq0fhPFXYf-xE7udjRsLgYO6KTqM1o10RrG8U1r84_Qg6ct5QXKdLdPHVY_OBev2Ulzkytbd4Dk1HXoP7J4LPtuhq4iODw1jz1PGt7ytEK_Dn7I2bYisMQG7BernosoN_Ocgea15r69Ii8TUdSpu5ipRG9hzB7CmBCxw#_=_";

		FacebookClient client = new DefaultFacebookClient(Version.LATEST);
		AccessToken token = client.obtainUserAccessToken("692592981600482", "c5413701dfeebde17f861549f9d785bd", "http://localhost:8080/webrest/", code);
		
		System.out.println(token.getAccessToken());
		
		FacebookClient facebookUserClient = new DefaultFacebookClient(token.getAccessToken(), Version.LATEST);

		User user = facebookUserClient.fetchObject("me", User.class);
		System.out.println(user.getId());
		System.out.println(user.getName());
		
		//facebookPageClient.publish("111411690697844_118840969954916/comments", String.class, Parameter.with("message", "Hello World"));
	}
}
