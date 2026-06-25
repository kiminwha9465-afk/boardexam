package com.example.board0625.dto;

import lombok.Data;

@Data
public class PageDTO {
    private int currentPage = 1;
    private int pageSize    = 10;
    private int pageBarSize = 5;
    private int totalCount;
    private String keyword    = "";
    private String searchType = "title";

    // 계산 결과
    private int startPage;
    private int endPage;
    private boolean prev;
    private boolean next;
    private int offset;

    public void calculate() {
        if (currentPage < 1) currentPage = 1;

        int totalPages   = Math.max(1, (int) Math.ceil((double) totalCount / pageSize));
        if (currentPage > totalPages) currentPage = totalPages;

        int currentGroup = (int) Math.ceil((double) currentPage / pageBarSize);
        startPage = (currentGroup - 1) * pageBarSize + 1;
        endPage   = Math.min(startPage + pageBarSize - 1, totalPages);

        prev   = startPage > 1;
        next   = endPage < totalPages;
        offset = (currentPage - 1) * pageSize;
    }

    public int getTotalPages() {
        return Math.max(1, (int) Math.ceil((double) totalCount / pageSize));
    }
}
