package demo.rest;

import org.activiti.rest.service.api.repository.ProcessDefinitionResponse;

import java.util.List;

public class ProcessList {
  private List<ProcessDefinitionResponse> data;
  private int total;
  private int start;
  private String sort;
  private String order;
  private int size;

  public List<ProcessDefinitionResponse> getData() {
    return data;
  }

  public void setData(List<ProcessDefinitionResponse> data) {
    this.data = data;
  }

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  public int getStart() {
    return start;
  }

  public void setStart(int start) {
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

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  @Override
  public String toString() {
    return "ProcessList{" +
        "data=" + data +
        ", total=" + total +
        ", start=" + start +
        ", sort='" + sort + '\'' +
        ", order='" + order + '\'' +
        ", size=" + size +
        '}';
  }
}
