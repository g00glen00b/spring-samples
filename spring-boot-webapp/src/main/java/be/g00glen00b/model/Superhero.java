package be.g00glen00b.model;

public class Superhero {
  private String firstName;
  private String lastName;
  private String name;
  private boolean good;
  
  public Superhero(String firstName, String lastName, String name, boolean good) {
    super();
    this.firstName = firstName;
    this.lastName = lastName;
    this.name = name;
    this.good = good;
  }
  
  public String getFirstName() {
    return firstName;
  }
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }
  
  public String getLastName() {
    return lastName;
  }
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
  
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  
  public boolean isGood() {
    return good;
  }
  public void setGood(boolean good) {
    this.good = good;
  }
}
