package myy803.socialbookstore.formsdata;

import java.util.ArrayList;

public class SearchDto {
	
	private String title;
	private String authors;
	private ArrayList<String> searchStrategies;
	private String selectedStrategy;
	
	public SearchDto() {
		super();
		searchStrategies = new ArrayList<String>();
    	searchStrategies.add("Exact"); 
    	searchStrategies.add("Approximate");
		searchStrategies.add("All");
	}

	
	public ArrayList<String> getSearchStrategies() {
		return searchStrategies;
	}


	public void setSearchStrategies(ArrayList<String> searchStrategies) {
		this.searchStrategies = searchStrategies;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthors() {
		return authors;
	}

	public void setAuthors(String authors) {
		this.authors = authors;
	}

	public String getSelectedStrategy() {
		return selectedStrategy;
	}

	public void setSelectedStrategy(String selectedStrategy) {
		this.selectedStrategy = selectedStrategy;
	}
}
