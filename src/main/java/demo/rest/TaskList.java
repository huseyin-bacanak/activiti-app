package demo.rest;

import org.activiti.rest.service.api.runtime.task.TaskResponse;

import java.util.ArrayList;
import java.util.List;

public class TaskList {

  private List<TaskResponse> data = new ArrayList<TaskResponse>();
  private Integer total;
  private Integer start;
  private String sort;
  private String order;
  private Integer size;

  public List<TaskResponse> getData() {
    return data;
  }

  public void setData(List<TaskResponse> data) {
    this.data = data;
  }

  public Integer getTotal() {
    return total;
  }

  public void setTotal(Integer total) {
    this.total = total;
  }

  public Integer getStart() {
    return start;
  }


  public void setStart(Integer start) {
    this.start = start;
  }


  public String getSort() {
    return sort;
  }


  public void setSort(String sort) {
    this.sort = sort;
  }


  public String getOrder() {
    return order;
  }


  public void setOrder(String order) {
    this.order = order;
  }


  public Integer getSize() {
    return size;
  }

  public void setSize(Integer size) {
    this.size = size;
  }

  @Override
  public String toString() {
    return "TaskList{" +
        "data=" + data +
        ", total=" + total +
        ", start=" + start +
        ", sort='" + sort + '\'' +
        ", order='" + order + '\'' +
        ", size=" + size +
        '}';
  }
}