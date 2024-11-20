package net.haebup.utils.PaginationUtil;

public class Pagination {
    private int pageNo;        // 현재 페이지 번호
    private int pageSize;      // 한 페이지에 표시할 게시글 수
    private int totalCount;    // 전체 게시글 수
    private int totalPages;    // 전체 페이지 수
    private int blockSize;     // 한 번에 표시할 페이지 번호 수 (블록 크기)
    
    private int blockStartPage;  // 현재 블록의 시작 페이지
    private int blockEndPage;    // 현재 블록의 끝 페이지

    public Pagination(int pageNo, int pageSize, int totalCount, int blockSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.blockSize = blockSize;
        
        this.totalPages = (int) Math.ceil((double) totalCount / pageSize);
        this.blockStartPage = ((pageNo - 1) / blockSize) * blockSize + 1;
        this.blockEndPage = Math.min(blockStartPage + blockSize - 1, totalPages);
    }

    // << (맨앞 페이지 이동)
    public boolean hasFirstPage() {
        return pageNo > 1;
    }

    // < (이전 블록 이동)
    public boolean hasPreviousBlock() {
        return blockStartPage > 1;
    }

    // > (다음 블록 이동)
    public boolean hasNextBlock() {
        return blockEndPage < totalPages;
    }

    // >> (마지막 페이지 이동)
    public boolean hasLastPage() {
        return pageNo < totalPages;
    }

    public int getPageNo() {
        return pageNo;
    }

    public int getBlockStartPage() {
        return blockStartPage;
    }

    public int getBlockEndPage() {
        return blockEndPage;
    }
    
    public int getTotalPages() {
        return totalPages;
    }
    //확인
    @Override
    public String toString() {
        return "Pagination [pageNo=" + pageNo 
                + ", pageSize=" + pageSize 
                + ", totalCount=" + totalCount 
                + ", totalPages=" + totalPages 
                + ", blockSize=" + blockSize 
                + ", blockStartPage=" + blockStartPage 
                + ", blockEndPage=" + blockEndPage + "]";
    }
}
