package demo.dto;

public class JsonResponse {
  private RequestStatus status = RequestStatus.FAILURE;
  private String message;
  private Object result = null;

  public RequestStatus getStatus() {
    return status;
  }

  public void setStatus(RequestStatus status) {
    this.status = status;
  }

  public Object getResult() {
    return result;
  }

  public void setResult(Object result) {
    this.result = result;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public JsonResponse(){}

  public JsonResponse(Object result) {
    this.status = RequestStatus.SUCCESS;
    this.result = result;
  }

  /**
   * JsonResponse const.
   * @param status RequestStatus of response: success, failure
   * @param message informational message
   * @param result result object
   */
  public JsonResponse(RequestStatus status, String message, Object result) {
    this.status = status;
    this.message = message;
    this.result = result;
  }
}