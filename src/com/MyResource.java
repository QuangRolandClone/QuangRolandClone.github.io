package com;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONObject;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.scope.FacebookPermissions;
import com.restfb.scope.ScopeBuilder;
import com.restfb.types.Comment;
import com.restfb.types.Comments;
import com.restfb.types.Post;
import com.restfb.types.User;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
	@Path("test")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!";
    }
	
	@Path("getPageFeed")
	@GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getPageFeed(@QueryParam("userToken") String userToken) {
		FacebookClient facebookUserClient = new DefaultFacebookClient(userToken, Version.LATEST);
		
		User user = facebookUserClient.fetchObject("me", User.class);
		boolean isAdmin;
		if (user.getId().compareTo("2746370918916670") == 0) {
			isAdmin = true;
		}
		else {
			isAdmin = false;
		}
		
		String canteenPageId = "111411690697844";
        String canteenPageName = "Quang Canteen";
		
		FacebookClient facebookPageClient = new DefaultFacebookClient("EAAJ16N6kaOIBAIoZBTEh9ZBbPusZBGQHN4guxVaGuZAslZAXQ5r0tQzz6UBbgZCdBGmXQDTvXZClMR6MmlnX05J2YBfRWLyFtvLU5OL8kpmWoVxdHvfuFLTLNpME55NVeP7mZCaTOSOskkLNW18ZAq7iSEbxDfMrUVpIfwOpLOqbF6wZDZD", Version.LATEST);
		
		Connection<Post> pageFeedConnection = facebookPageClient.fetchConnection("111411690697844/feed", Post.class,
				  Parameter.with("fields", "id,message,permalink_url,comments{id,message}"));
		
		JSONObject pageFeedJSON = new JSONObject();
		pageFeedJSON.put("isAdmin", isAdmin);
		for (List<Post> pageFeed : pageFeedConnection) {
			if (pageFeed != null) {
				JSONArray postArray = new JSONArray();
				
				for (Post post : pageFeed) {
					JSONObject postJSON = new JSONObject();
					postJSON.put("id", post.getId());
					postJSON.put("message", post.getMessage());
					postJSON.put("permalink_url", post.getPermalinkUrl());
					
					Comments commentList = post.getComments();
					if (commentList != null) {
						JSONArray commentArray = new JSONArray();
						
						for (Comment comment : commentList.getData()) {
							if (isAdmin || comment.getMessage().contains("; [" + user.getId() + ";" + user.getName() + ";]") || comment.getMessage().contains("; [" + canteenPageId + ";" + canteenPageName + ";]")) {
								JSONObject commentJSON = new JSONObject();
								commentJSON.put("id", comment.getId());
								commentJSON.put("message", comment.getMessage());
								
								Connection<Comment> replyCommentConnection = facebookPageClient.fetchConnection(comment.getId() + "/comments", Comment.class);
								for (List<Comment> replyCommentList : replyCommentConnection) {
									if (replyCommentList != null) {
										JSONArray replyArray = new JSONArray();
										
										for (Comment replyComment : replyCommentList) {
											JSONObject replyJSON = new JSONObject();
											replyJSON.put("message", replyComment.getMessage());
											replyArray.put(replyJSON);
										}
										
										commentJSON.put("reply", replyArray);
									}
								}
								
								commentArray.put(commentJSON);
							}
						}
						
						postJSON.put("comment", commentArray);
					}
					
					postArray.put(postJSON);
				}
				
				pageFeedJSON.put("data", postArray);
			}
		}
		
		return pageFeedJSON.toString();
    }
	
	@Path("postToPage")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String postToPage(@FormParam("message") String message, @FormParam("userToken") String userToken) {
		FacebookClient facebookUserClient = new DefaultFacebookClient(userToken, Version.LATEST);
		
		User user = facebookUserClient.fetchObject("me", User.class);

		if (user.getId().compareTo("2746370918916670") == 0) {
			FacebookClient facebookPageClient = new DefaultFacebookClient("EAAJ16N6kaOIBAIoZBTEh9ZBbPusZBGQHN4guxVaGuZAslZAXQ5r0tQzz6UBbgZCdBGmXQDTvXZClMR6MmlnX05J2YBfRWLyFtvLU5OL8kpmWoVxdHvfuFLTLNpME55NVeP7mZCaTOSOskkLNW18ZAq7iSEbxDfMrUVpIfwOpLOqbF6wZDZD", Version.LATEST);
			
			facebookPageClient.publish("111411690697844/feed", String.class, Parameter.with("message", message));
		}
		
        return "Done";
    }
	
	@Path("commentToPost")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String commentToPost(@FormParam("message") String message, @FormParam("postId") String postId, @FormParam("userToken") String userToken) {
		FacebookClient facebookUserClient = new DefaultFacebookClient(userToken, Version.LATEST);
		
		User user = facebookUserClient.fetchObject("me", User.class);
		
		FacebookClient facebookPageClient = new DefaultFacebookClient("EAAJ16N6kaOIBAIoZBTEh9ZBbPusZBGQHN4guxVaGuZAslZAXQ5r0tQzz6UBbgZCdBGmXQDTvXZClMR6MmlnX05J2YBfRWLyFtvLU5OL8kpmWoVxdHvfuFLTLNpME55NVeP7mZCaTOSOskkLNW18ZAq7iSEbxDfMrUVpIfwOpLOqbF6wZDZD", Version.LATEST);
		
		if (user.getId().compareTo("2746370918916670") == 0) {
			facebookPageClient.publish(postId + "/comments", String.class, Parameter.with("message", message + "; [111411690697844;Quang Canteen;]"));
		}
		else {
			facebookPageClient.publish(postId + "/comments", String.class, Parameter.with("message", message + "; [" + user.getId() + ";" + user.getName() + ";]"));
		}
		
        return "Done";
    }
	
	@Path("replyToComment")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String replyToComment(@FormParam("message") String message, @FormParam("commentId") String commentId, @FormParam("userToken") String userToken) {
		FacebookClient facebookUserClient = new DefaultFacebookClient(userToken, Version.LATEST);
		
		User user = facebookUserClient.fetchObject("me", User.class);
		
		FacebookClient facebookPageClient = new DefaultFacebookClient("EAAJ16N6kaOIBAIoZBTEh9ZBbPusZBGQHN4guxVaGuZAslZAXQ5r0tQzz6UBbgZCdBGmXQDTvXZClMR6MmlnX05J2YBfRWLyFtvLU5OL8kpmWoVxdHvfuFLTLNpME55NVeP7mZCaTOSOskkLNW18ZAq7iSEbxDfMrUVpIfwOpLOqbF6wZDZD", Version.LATEST);
		
		if (user.getId().compareTo("2746370918916670") == 0) {
			facebookPageClient.publish(commentId + "/comments", String.class, Parameter.with("message", message + "; [111411690697844;Quang Canteen;]"));
		}
		else {
			facebookPageClient.publish(commentId + "/comments", String.class, Parameter.with("message", message + "; [" + user.getId() + ";" + user.getName() + ";]"));
		}
		
        return "Done";
    }
}
