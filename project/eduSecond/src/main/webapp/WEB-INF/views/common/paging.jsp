
<!-- 페이징 영역 -->
<nav aria-label="Page navigation example">
    <ul class="pagination justify-content-center">
        <!-- 이전 페이지 블록 버튼 -->
        <c:if test="${paging.prevBlock}">
            <li class="page-item">
                <a class="page-link" href="?pageNo=${paging.startBlockPage - 1}&searchCategory=${param.searchCategory}&searchValue=${param.searchValue}" aria-label="Previous">
                    <span aria-hidden="true">&laquo;</span>
                </a>
            </li>
        </c:if>

        <!-- 현재 페이지 블록 버튼들 -->
        <c:forEach begin="${paging.startBlockPage}" end="${paging.endBlockPage}" var="i">
            <li class="page-item ${i == param.pageNo ? 'active' : ''}">
                <a class="page-link" href="?pageNo=${i}&searchCategory=${param.searchCategory}&searchValue=${param.searchValue}">${i}</a>
            </li>
        </c:forEach>

        <!-- 다음 페이지 블록 버튼 -->
        <c:if test="${paging.nextBlock}">
            <li class="page-item">
                <a class="page-link" href="?pageNo=${paging.endBlockPage + 1}&searchCategory=${param.searchCategory}&searchValue=${param.searchValue}" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
        </c:if>
    </ul>
</nav>
