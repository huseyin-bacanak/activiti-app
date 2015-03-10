package demo.dto;

/**
 * Created by huseyin.bacanak on 09.03.2015.
 */
public class Variable {
  private String name;
  private Object value;
  private String type;
  private String scope;

  public Variable() {

  }
  public Variable(String name, Object value) {
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

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getScope() {
    return scope;
  }

  public void setScope(String scope) {
    this.scope = scope;
  }

  @Override
  public String toString() {
    return "Entry{" +
        "name='" + name + '\'' +
        ", value=" + value +
        '}';
  }
}
