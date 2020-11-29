package org.covid19.tracker.model;

public class LocationStats {
	
private String country;
private String state;
private String county;
private int recentCases;
private int latestCases;

		public String getCountry() {
			return country;
		}
		public void setCountry(String country) {
			this.country = country;
		}

		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}

		public String getCounty() {
			return county;
		}
		public void setCounty(String county) {
			this.county = county;
		}
		
		public int getRecentCases() {
			return recentCases;
		}
		public void setRecentCases(int recentCases) {
			this.recentCases = recentCases;
		}
		
		public int getLatestCases() {
			return latestCases;
		}
		public void setLatestCases(int latestCases) {
			this.latestCases = latestCases;
		}
		
		@Override
		public String toString() {
			return "LocationStats [country=" + country + ", state=" + state + ", county=" + county + ", recentCases=" + recentCases + ", latestCases="
					+ latestCases + "]";
		}


}
