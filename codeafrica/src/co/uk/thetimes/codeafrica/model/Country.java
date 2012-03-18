package co.uk.thetimes.codeafrica.model;

/**
 * @author anuragkapur
 *
 */
public class Country {
	
	private String name;
	private int isAfrican;
	private double area; // in sq kms
	private double cellularSubs; // per 100
	private double internetUsers; // per 100
	private double population;
	private double alcoholConsumption;
	private double facebookPenetration;
	private double gdpGrowth;
	private double happyPlanet;
	private double broadbandSpeed;
	private double cocaColaConsumtion;
	private double urbanPopulation;
	private double womenInParliament;
	private double corruptionIndex;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int isAfrican() {
		return isAfrican;
	}
	public void setAfrican(int isAfrican) {
		this.isAfrican = isAfrican;
	}
	public double getArea() {
		return area;
	}
	public void setArea(double area) {
		this.area = area;
	}
	public double getCellularSubs() {
		return cellularSubs;
	}
	public void setCellularSubs(double cellularSubs) {
		this.cellularSubs = cellularSubs;
	}
	public double getInternetUsers() {
		return internetUsers;
	}
	public void setInternetUsers(double internetUsers) {
		this.internetUsers = internetUsers;
	}
	public double getPopulation() {
		return population;
	}
	public void setPopulation(double population) {
		this.population = population;
	}
	public double getAlcoholConsumption() {
		return alcoholConsumption;
	}
	public void setAlcoholConsumption(double alcoholConsumption) {
		this.alcoholConsumption = alcoholConsumption;
	}
	public double getFacebookPenetration() {
		return facebookPenetration;
	}
	public void setFacebookPenetration(double facebookPenetration) {
		this.facebookPenetration = facebookPenetration;
	}
	public double getGdpGrowth() {
		return gdpGrowth;
	}
	public void setGdpGrowth(double gdpGrowth) {
		this.gdpGrowth = gdpGrowth;
	}
	public double getHappyPlanet() {
		return happyPlanet;
	}
	public void setHappyPlanet(double happyPlanet) {
		this.happyPlanet = happyPlanet;
	}
	public double getBroadbandSpeed() {
		return broadbandSpeed;
	}
	public void setBroadbandSpeed(double broadbandSpeed) {
		this.broadbandSpeed = broadbandSpeed;
	}
	public double getCocaColaConsumtion() {
		return cocaColaConsumtion;
	}
	public void setCocaColaConsumtion(double cocaColaConsumtion) {
		this.cocaColaConsumtion = cocaColaConsumtion;
	}
	public double getUrbanPopulation() {
		return urbanPopulation;
	}
	public void setUrbanPopulation(double urbanPopulation) {
		this.urbanPopulation = urbanPopulation;
	}
	public double getWomenInParliament() {
		return womenInParliament;
	}
	public void setWomenInParliament(double womenInParliament) {
		this.womenInParliament = womenInParliament;
	}
	public double getCorruptionIndex() {
		return corruptionIndex;
	}
	public void setCorruptionIndex(double corruptionIndex) {
		this.corruptionIndex = corruptionIndex;
	}
}
