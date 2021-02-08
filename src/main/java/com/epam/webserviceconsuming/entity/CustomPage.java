package com.epam.webserviceconsuming.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomPage<T> {
    private static final long serialVersionUID = 1L;
    private int number;
    private int size;
    private int totalPages;
    private int numberOfElements;
    private long totalElements;
    private boolean previousPage;
    private boolean firstPage;
    private boolean nextPage;
    private boolean lastPage;
    private List<T> content;
    private SpringDataWebProperties.Sort sort;

    public CustomPage() {
    }

    public CustomPage(int number, int size, int totalPages, int numberOfElements, long totalElements, boolean previousPage, boolean firstPage, boolean nextPage, boolean lastPage, List<T> content, SpringDataWebProperties.Sort sort) {
        this.number = number;
        this.size = size;
        this.totalPages = totalPages;
        this.numberOfElements = numberOfElements;
        this.totalElements = totalElements;
        this.previousPage = previousPage;
        this.firstPage = firstPage;
        this.nextPage = nextPage;
        this.lastPage = lastPage;
        this.content = content;
        this.sort = sort;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public boolean isPreviousPage() {
        return previousPage;
    }

    public void setPreviousPage(boolean previousPage) {
        this.previousPage = previousPage;
    }

    public boolean isFirstPage() {
        return firstPage;
    }

    public void setFirstPage(boolean firstPage) {
        this.firstPage = firstPage;
    }

    public boolean isNextPage() {
        return nextPage;
    }

    public void setNextPage(boolean nextPage) {
        this.nextPage = nextPage;
    }

    public boolean isLastPage() {
        return lastPage;
    }

    public void setLastPage(boolean lastPage) {
        this.lastPage = lastPage;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public SpringDataWebProperties.Sort getSort() {
        return sort;
    }

    public void setSort(SpringDataWebProperties.Sort sort) {
        this.sort = sort;
    }
}
