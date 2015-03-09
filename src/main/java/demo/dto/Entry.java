package demo.dto;

/**
 * Created by huseyin.bacanak on 09.03.2015.
 */
public class Entry {
  private String name;
  private String value;

  public Entry(String name, String value) {
    this.name=name;
    this.value=value;
  }
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
