package demo.dto;

/**
 * Created by huseyin.bacanak on 09.03.2015.
 */
public class Entry {
  private String name;
  private Object value;

  public Entry() {

  }
  public Entry(String name, Object value) {
    this.name=name;
    this.value=value;
  }
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Object getValue() {
    return value;
  }

  public void setValue(Object value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "Entry{" +
        "name='" + name + '\'' +
        ", value=" + value +
        '}';
  }
}
