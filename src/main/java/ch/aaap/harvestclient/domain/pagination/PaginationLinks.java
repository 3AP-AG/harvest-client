package ch.aaap.harvestclient.domain.pagination;

public class PaginationLinks {

    private String first;

    private String next;

    private String previous;

    private String last;

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    @Override
    public String toString() {
        return "PaginationLinks{" +
                "first='" + first + '\'' +
                ", next='" + next + '\'' +
                ", previous='" + previous + '\'' +
                ", last='" + last + '\'' +
                '}';
    }
}
