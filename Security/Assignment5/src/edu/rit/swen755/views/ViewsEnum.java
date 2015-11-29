package edu.rit.swen755.views;

/**
 * Enumeration that map views (JSP files) to URL. It is just for the sake of
 * understandability and maintainability of the code
 * 
 * @author Joanna
 *
 */
public enum ViewsEnum {
	HOME("index"), LOGIN("login"), POST("post"), POST_ADD("post_add");
	private final String view;

	ViewsEnum(String view) {
		this.view = view;
	}

	public String getUrl() {
		return String.format("/Assignment4/%s", view.replace("_", "/"));
	}

	public String toString() {
		return String.format("/WEB-INF/%s.jsp", view);
	}

}
