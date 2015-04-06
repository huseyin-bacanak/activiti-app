package demo.dto;

public class JsonResponse {
  public static final String SUCCESS="SUCCESS";

  private String status = null;
  private Object result = null;

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Object getResult() {
    return result;
  }

  public void setResult(Object result) {
    this.result = result;
  }

}