package myy803.socialbookstore.formsdata;

import java.util.ArrayList;

public class RecommendationsDto {
	private ArrayList<String> recommendationStrategies;
	private String selectedStrategy;
	
	public RecommendationsDto() {
		super();
		recommendationStrategies = new ArrayList<String>();
		recommendationStrategies.add("Favourite Categories"); 
		recommendationStrategies.add("Favourite Authors");
		recommendationStrategies.add("Trending");
		recommendationStrategies.add("Both");
	}

	public ArrayList<String> getRecommendationStrategies() {
		return recommendationStrategies;
	}

	public void setRecommendationStrategies(ArrayList<String> recommendationStrategies) {
		this.recommendationStrategies = recommendationStrategies;
	}

	public String getSelectedStrategy() {
		return selectedStrategy;
	}

	public void setSelectedStrategy(String selectedStrategy) {
		this.selectedStrategy = selectedStrategy;
	}

	

}
