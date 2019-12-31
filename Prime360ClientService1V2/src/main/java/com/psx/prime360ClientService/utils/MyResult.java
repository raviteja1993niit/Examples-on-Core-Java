package com.psx.prime360ClientService.utils;



import java.util.HashMap;

public class MyResult {
	String id;
	HashMap<String,String> scale;
	String type;
	String rule;
	String cscore;
	HashMap<String,String> Strengths;
	String rank;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public HashMap<String, String> getScale() {
		return scale;
	}
	public void setScale(HashMap<String, String> scale) {
		this.scale = scale;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRule() {
		return rule;
	}
	public void setRule(String rule) {
		this.rule = rule;
	}
	public String getCscore() {
		return cscore;
	}
	public void setCscore(String cscore) {
		this.cscore = cscore;
	}
	public HashMap<String, String> getStrengths() {
		return Strengths;
	}
	public void setStrengths(HashMap<String, String> strengths) {
		Strengths = strengths;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	
	
}
