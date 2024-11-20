<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
  <title>툴</title>
  <style>
    * {
      margin: 0;
      padding: 0;
      box-sizing: border-box;
    }

    body {
      font-family: Arial, sans-serif;
      line-height: 1.6;
      background-color: #f9f9f9;
      color: #333;
      min-height: 100vh;
      display: flex;
      flex-direction: column;
    }

    .privacy-policy {
      max-width: 1500px;
      margin: 50px auto;
      padding: 20px;
      background-color: #fff;
      box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
      border-radius: 8px;
    }

    .mainpage {
      display: flex;
      flex-grow: 1;
      min-height: calc(100vh); /* header와 footer를 제외한 높이 설정 */
    }

    .sidebar {
      background-color: #444444;
      padding: 15px;
      color: #fff;
      height: 100%;
    }

    .sidebar ul {
      list-style: none;
      padding: 0;
    }

    .sidebar ul li {
      margin-bottom: 10px;
    }

    .sidebar ul li a {
      color: #fff;
      text-decoration: none;
    }

    .sidebar ul li a:hover {
      text-decoration: underline;
    }

    .content {
      padding: 20px;
      flex-grow: 1;
    }

    .action-buttons {
      margin-top: 20px;
      text-align: right;
    }

    .footer {
      background-color: #f8f8f8;
      padding: 10px;
      text-align: center;
      border-top: 1px solid #ddd;
      width: 100%;
    }
  .chat-list {
      background-color: white;
      border-radius: 8px;
      box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  }

  .chat-item {
      cursor: pointer;
      transition: background-color 0.2s;
      padding: 15px 20px;
      border-bottom: 1px solid #eee;
  }

  .chat-item:last-child {
      border-bottom: none;
  }

  .chat-item:hover {
      background-color: #f8f9fa;
  }

  .product-image img {
      border: 1px solid #eee;
  }

  .chat-info h6 {
      font-weight: 600;
      font-size: 1rem;
      margin-bottom: 2px;
  }

  .chat-info small {
      font-size: 0.75rem;
      color: #888;
  }

  .message-preview {
      font-size: 0.9rem;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
      max-width: calc(100% - 40px);
      color: #666;
      margin-top: 4px;
  }

  .badge {
      font-size: 0.75rem;
      min-width: 20px;
      height: 20px;
      display: flex;
      align-items: center;
      justify-content: center;
      padding: 0 6px;
  }
</style>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</head>
<body>
<%@ include file="../main/header.jsp"%>

<div class="mainpage">
  <div class="sidebar col-2">
    <ul>
      <li><a href="/es/mypage/myInfo">내 정보</a></li>
      <li><a href="/es/mypage/wishList">찜 목록</a></li>
      <li><a href="/es/mypage/orderList">거래 내역</a></li>
      <li><a href="/es/mypage/productList">내 상품</a></li>
      <li><a href="/es/mypage/messageList">셀파톡</a></li>
    </ul>
  </div>

  <div class="content col-10">
    <h2 class="mb-4">셀파톡</h2>
    
    <div class="chat-list">
        <c:forEach items="${chatRooms}" var="room">
            <div class="chat-item d-flex align-items-center p-3 border-bottom position-relative" 
                 onclick="location.href='/message/chatting?roomId=${room.roomId}'">
                <div class="product-image me-3">
                    <img src="${room.productImage}" alt="상품 이미지" 
                         class="rounded" style="width: 60px; height: 60px; object-fit: cover;">
                </div>
                
                <div class="chat-info flex-grow-1">
                    <div class="d-flex justify-content-between align-items-center mb-1">
                        <div>
                            <h6 class="mb-0">${room.otherUserName}</h6>
                            <small class="text-muted">${room.productName}</small>
                        </div>
                        <small class="text-muted">
                            <fmt:formatDate value="${room.lastMessageDate}" pattern="MM.dd HH:mm"/>
                        </small>
                    </div>
                    
                    <div class="d-flex justify-content-between align-items-end">
                        <p class="mb-0 text-muted message-preview">
                            ${room.lastMessage}
                        </p>
                        <c:if test="${room.unreadCount > 0}">
                            <span class="badge bg-danger rounded-pill ms-2">${room.unreadCount}</span>
                        </c:if>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>

    <script>
        // 채팅방 클릭 이벤트
        document.querySelectorAll('.chat-item').forEach(item => {
            item.addEventListener('click', function() {
                // 채팅방 ID를 이용하여 채팅방으로 이동
                // location.href = '/es/message/chatting?roomId=' + roomId;
                console.log('채팅방 클릭');
            });
        });
    </script>
  </div>
</div>

<%@include file="../main/footer.jsp"%>
</body>
</html>