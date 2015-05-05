package demo.dto;

public class Pagination {
  private long fromIndex;
  private long toIndex;
  private long selectedIndex;

  public Pagination() {}

  public Pagination(long fromIndex, long toIndex, long selectedIndex) {
    this.fromIndex = fromIndex;
    this.toIndex = toIndex;
    this.selectedIndex = selectedIndex;
  }

  public long getFromIndex() {
    return fromIndex;
  }

  public void setFromIndex(long fromIndex) {
    this.fromIndex = fromIndex;
  }

  public long getToIndex() {
    return toIndex;
  }

  public void setToIndex(long toIndex) {
    this.toIndex = toIndex;
  }

  public long getSelectedIndex() {
    return selectedIndex;
  }

  public void setSelectedIndex(long selectedIndex) {
    this.selectedIndex = selectedIndex;
  }
}
