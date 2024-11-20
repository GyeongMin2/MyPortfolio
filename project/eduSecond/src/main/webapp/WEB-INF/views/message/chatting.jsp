<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
            <html>

            <head>
                <title>채팅</title>
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
                <link rel="stylesheet"
                    href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css">
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
                <style>
                    body {
                        margin: 0;
                        padding: 0;
                        height: 100vh;
                        width: 100vw;
                        overflow: hidden;
                    }

                    .chat-container {
                        width: 100%;
                        height: 100vh;
                        background-color: #b2c7d9;
                        display: flex;
                        flex-direction: column;
                    }

                    .chat-header {
                        background-color: #a1b1c1;
                        padding: 10px 20px;
                        border-bottom: 1px solid #ddd;
                    }

                    .chat-header .btn-close {
                        color: #000;
                        opacity: 0.7;
                    }

                    .chat-header .btn-close:hover {
                        opacity: 1;
                    }

                    .chat-header h3 {
                        margin: 0;
                        color: #000;
                    }

                    .product-info {
                        background-color: #ffffff;
                        padding: 15px 20px;
                        border-bottom: 1px solid #ddd;
                    }

                    .product-info .product-content {
                        display: flex;
                        align-items: center;
                        gap: 15px;
                    }

                    .product-info img {
                        width: 50px;
                        height: 50px;
                        object-fit: cover;
                        border-radius: 5px;
                    }

                    .product-info h4 {
                        margin: 0;
                        font-size: 1rem;
                        flex-grow: 1;
                    }

                    .product-info .btn-product {
                        background-color: #fef01b;
                        border: none;
                        color: #000;
                        padding: 5px 15px;
                        border-radius: 5px;
                        font-size: 0.9rem;
                    }

                    .product-info .btn-product:hover {
                        background-color: #e6d819;
                    }

                    .chat-messages {
                        flex: 1;
                        overflow-y: auto;
                        padding: 20px;
                    }

                    .message {
                        margin-bottom: 20px;
                        display: flex;
                        align-items: flex-start;
                        position: relative;
                    }

                    .message.sent {
                        justify-content: flex-end;
                    }

                    .message-content {
                        max-width: 60%;
                        padding: 10px 15px;
                        border-radius: 15px;
                        position: relative;
                    }

                    .received .message-content {
                        background-color: #ffffff;
                    }

                    .sent .message-content {
                        background-color: #fff430;
                    }

                    .message-info {
                        display: flex;
                        align-items: flex-end;
                        gap: 4px;
                        position: absolute;
                        bottom: 0;
                        font-size: 0.7rem;
                        color: #888;
                        white-space: nowrap;
                    }

                    .sent .message-info {
                        right: 100%;
                        margin-right: 5px;
                        display: flex;
                        flex-direction: row-reverse;
                        align-items: center;
                        gap: 4px;
                    }

                    .received .message-info {
                        left: 100%;
                        margin-left: 5px;
                    }

                    .read-status {
                        color: #6c757d;
                    }

                    .unread-status {
                        color: #fef01b;
                    }

                    .chat-input {
                        width: 100%;
                        padding: 20px;
                        background-color: #ffffff;
                        border-top: 1px solid #ddd;
                    }

                    .chat-input .input-group {
                        max-width: 100%;
                    }

                    .sender-name {
                        font-size: 0.9rem;
                        margin-bottom: 5px;
                        color: #000;
                    }

                    .btn-send {
                        background-color: #fef01b;
                        border: none;
                        color: #000;
                    }

                    .btn-send:hover {
                        background-color: #e6d819;
                    }

                    /* 메시지 그룹화 */
                    .message-group {
                        margin-bottom: 20px;
                    }

                    .message-group .sender-name {
                        margin-bottom: 8px;
                    }

                    .message-group .message {
                        margin-bottom: 5px;
                    }

                    .message-group .message:last-child {
                        margin-bottom: 0;
                    }

                    @media (min-width: 800px) {
                        .chat-container {
                            width: 800px;
                            margin: 0 auto;
                        }
                    }

                    .dropdown-menu {
                        min-width: 100px;
                        padding: 0.5rem 0;
                        margin: 0;
                        background-color: #fff;
                        border: 1px solid rgba(0, 0, 0, .15);
                        border-radius: 0.25rem;
                    }

                    .dropdown-item {
                        padding: 0.5rem 1rem;
                        color: #212529;
                        text-align: left;
                        background-color: transparent;
                        border: 0;
                    }

                    .dropdown-item:hover {
                        background-color: #f8f9fa;
                    }

                    .dropdown-item.text-danger:hover {
                        background-color: #fff5f5;
                    }

                    .chat-date {
                        text-align: center;
                        margin: 20px 0;
                        position: relative;
                    }

                    .chat-date span {
                        background-color: rgba(0, 0, 0, 0.1);
                        padding: 5px 15px;
                        border-radius: 15px;
                        font-size: 0.8rem;
                        color: #000;
                        display: inline-block;
                    }

                    .header-content {
                        display: flex;
                        justify-content: space-between;
                        align-items: center;
                    }

                    .header-left {
                        display: flex;
                        align-items: center;
                        gap: 10px;
                    }

                    .chat-system-message {
                        width: 100%;
                        padding: 15px;
                        background-color: rgba(0, 0, 0, 0.1);
                        text-align: center;
                        color: #6c757d;
                        font-size: 0.9rem;
                        border-top: 1px solid rgba(0, 0, 0, 0.1);
                    }
                </style>
            </head>

            <body>
                <div class="chat-container">
                    <!-- 채팅방 헤더 -->
                    <div class="chat-header">
                        <div class="header-content">
                            <div class="header-left">
                                <button type="button" class="btn" onclick="location.href='/message/list'">
                                    <i class="bi bi-arrow-left"></i>
                                </button>
                                <h3>${room.otherUserName}</h3>
                            </div>
                            <div class="dropdown">
                                <button class="btn" type="button" data-bs-toggle="dropdown">
                                    <i class="bi bi-three-dots-vertical"></i>
                                </button>
                                <ul class="dropdown-menu dropdown-menu-end">
                                    <li><button class="dropdown-item" type="button" onclick="leaveChatRoom()">채팅방
                                            나가기</button></li>
                                    <li><button class="dropdown-item" type="button">신고하기</button></li>
                                    <li>
                                        <hr class="dropdown-divider">
                                    </li>
                                    <li><button class="dropdown-item text-danger" type="button">차단하기</button></li>
                                </ul>
                            </div>
                        </div>
                    </div>

                    <!-- 상품 정보 영역 -->
                    <div class="product-info">
                        <div class="product-content">
                            <img src="${room.productImage}" alt="상품 이미지">
                            <div class="flex-grow-1">
                                <h4>${room.productName}</h4>
                            </div>
                            <button class="btn-product"
                                onclick="location.href='/product/view?productId=${room.productId}'">
                                상품 보기
                            </button>
                        </div>
                    </div>

                    <!-- 채팅 메시지 영역 -->
                    <div class="chat-messages" id="messageArea">
                        <c:forEach items="${messages}" var="msg">
                            <div class="message-group">
                                <c:if test="${msg.type == 'LEAVE'}">
                                    <div class="chat-date">
                                        <span>${msg.message}</span>
                                    </div>
                                </c:if>
                                <c:if test="${msg.type != 'LEAVE'}">
                                    <c:if test="${msg.senderId != sessionScope.memberInfo.userId}">
                                        <div class="sender-name">${msg.senderName}</div>
                                    </c:if>
                                    <div
                                        class="message ${msg.senderId == sessionScope.memberInfo.userId ? 'sent' : 'received'}">
                                        <div class="message-content">
                                            ${msg.message}
                                            <div class="message-info">
                                                <span class="message-time">
                                                    <fmt:formatDate value="${msg.regDate}" pattern="a h:mm" />
                                                </span>
                                                <c:if test="${msg.senderId == sessionScope.memberInfo.userId}">
                                                    <span
                                                        class="${msg.isRead == 'Y' ? 'read-status' : 'unread-status'}">
                                                        ${msg.isRead == 'Y' ? '읽음' : '1'}
                                                    </span>
                                                </c:if>
                                            </div>
                                        </div>
                                    </div>
                                </c:if>
                            </div>
                        </c:forEach>
                    </div>

                    <!-- 입력창 -->
                    <c:if test="${isActive}">
                    <div class="chat-input">
                        <div class="input-group">
                            <input type="text" class="form-control" id="messageInput" placeholder="메시지 입력..." />
                            <button class="btn btn-send" type="button" id="sendButton">
                                전송
                            </button>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${not isActive}">
                        <div class="chat-system-message">
                            상대방이 채팅방을 나갔습니다.
                        </div>
                    </c:if>
                </div>

                <script>
                    document.addEventListener('DOMContentLoaded', function () {
                        const messageArea = document.getElementById('messageArea');
                        messageArea.scrollTop = messageArea.scrollHeight;
                        // 드롭다운 메뉴 클릭 이벤트 처리
                        document.querySelectorAll('.dropdown-item').forEach(item => {
                            item.addEventListener('click', function () {
                                const action = this.textContent;
                                switch (action) {
                                    case '채팅방 나가기':
                                        leaveChatRoom();
                                        break;
                                    case '신고하기':
                                        //미구현
                                        console.log('신고하기');
                                        break;
                                    case '차단하기':
                                        if (confirm('이 사용자를 차단하시겠습니까?')) {
                                            //미구현
                                            console.log('차단하기');
                                        }
                                        break;
                                }
                            });
                        });
                    });
                    let ws = null;
                    const roomId = ${room.roomId};
                    const userId = '${sessionScope.memberInfo.userId}';
                    const userName = '${sessionScope.memberInfo.userName}';
                    const isActive = ${isActive};

                    let reconnectAttempts = 0;
                    const MAX_RECONNECT = 5;
                    if (isActive) {
                        function connect() {
                            ws = new WebSocket(`ws://\${window.location.host}/es/ws/chat?roomId=\${roomId}`);

                            ws.onopen = function () {
                                console.log('웹소켓 연결 성공');
                                reconnectAttempts = 0;
                            };

                            ws.onmessage = onMessage;

                            ws.onclose = function () {
                                console.log('웹소켓 연결 종료');
                                if (reconnectAttempts < MAX_RECONNECT) {
                                    reconnectAttempts++;
                                    setTimeout(connect, 2000 * reconnectAttempts);
                                }
                            };

                            ws.onerror = function (error) {
                            console.error('WebSocket 에러:', error);
                            };
                        }
                    }
                    function sendMessage() {
                        const messageInput = document.getElementById('messageInput');
                        const message = messageInput.value.trim();

                        if (!message) return;

                        const chatMessage = {
                            type: 'CHAT',
                            roomId: roomId,
                            senderId: userId,
                            senderName: userName,
                            message: message
                        };

                        messageInput.value = '';

                        // WebSocket으로 전송 시도
                        if (ws && ws.readyState === WebSocket.OPEN) {
                            ws.send(JSON.stringify(chatMessage));
                        } else {
                            // WebSocket 연결이 없으면 HTTP로 전송
                            fetch('${pageContext.request.contextPath}/message/send', {
                                method: 'POST',
                                headers: {
                                    'Content-Type': 'application/json',
                                },
                                body: JSON.stringify(chatMessage)
                            })
                                .then(() => {
                                    addMessage(chatMessage);  // HTTP 전송 성공 시에만 UI에 추가
                                })
                                .catch(error => {
                                    console.error('Error:', error);
                                    alert('메시지 전송에 패했습니다.');
                                });
                        }
                    }

                    function leaveChatRoom() {
                        if (confirm('채팅방을 나가시겠습니까?\n나가기 후에는 대화내용이 삭제되며 복구할 수 없습니다.')) {
                            window.location.href = '${pageContext.request.contextPath}/message/room/leave?roomId=' + roomId;
                        }

                        // if (confirm('채팅방을 나가시겠습니까?\n나가기 후에는 대화내용이 삭제되며 복구할 수 없습니다.')) {
                        //     // 현재 사용자가 판매자인지 구매자인지 확인
                        //     const isSeller = userId === '\${room.sellerId}';
                        //     const status = {
                        //         type: 'LEAVE',
                        //         roomId: roomId,
                        //         userId: userId,
                        //         status: 'N'
                        //     };

                        //     // WebSocket으로 나가기 메시지 전송
                        //     if (ws && ws.readyState === WebSocket.OPEN) {
                        //         ws.send(JSON.stringify(status));
                        //     }

                        //     // HTTP로 상태 업데이트
                        //     fetch('\${pageContext.request.contextPath}/message/room/leave', {
                        //         method: 'POST',
                        //         headers: {
                        //             'Content-Type': 'application/json',
                        //         },
                        //         body: JSON.stringify({
                        //             roomId: roomId,
                        //             userId: userId,
                        //             status: 'N'
                        //         })
                        //     })
                        //     .then(response => {
                        //         if (response.ok) {
                        //             if (ws) {
                        //                 ws.close();
                        //             }
                        //             window.location.href = '${pageContext.request.contextPath}/message/list';
                        //         }
                        //     })
                        //     .catch(error => {
                        //         console.error('Error:', error);
                        //         alert('채팅방 나가기에 실패했습니다.');
                        //     });
                        // }
                    }

                    // 이벤트 리스너 등록
                    document.getElementById('sendButton').addEventListener('click', sendMessage);
                    document.getElementById('messageInput').addEventListener('keypress', function (e) {
                        if (e.key === 'Enter') {
                            e.preventDefault();
                            sendMessage();
                        }
                    });

                    // 웹소켓 연결
                    connect();

                    function addMessage(message) {
                        const messageArea = document.getElementById('messageArea');
                        const messageGroup = document.createElement('div');
                        messageGroup.className = 'message-group';

                        const currentTime = new Date().toLocaleTimeString('ko-KR', {
                            hour: 'numeric',
                            minute: '2-digit',
                            hour12: true
                        });

                        if (message.type === 'LEAVE') {
                            messageGroup.innerHTML = `
                    <div class="chat-date">
                        <span>\${message.message}</span>
                    </div>
                `;
                        } else {
                            const isMyMessage = message.senderId === userId;
                            let html = '';

                            if (!isMyMessage) {
                                html += `<div class="sender-name">\${message.senderName}</div>`;
                            }

                            html += `
                    <div class="message \${isMyMessage ? 'sent' : 'received'}">
                        <div class="message-content">
                            \${message.message}
                            <div class="message-info">
                                <span class="message-time">\${currentTime}</span>
                                \${isMyMessage ? '<span class="unread-status">1</span>' : ''}
                            </div>
                        </div>
                    </div>
                `;
                            messageGroup.innerHTML = html;
                        }

                        messageArea.appendChild(messageGroup);
                        messageArea.scrollTop = messageArea.scrollHeight;
                    }

                    function onMessage(event) {
                        const message = JSON.parse(event.data);

                        if (message.type === 'READ') {
                            document.querySelectorAll('.unread-status').forEach(status => {
                                status.textContent = '읽음';
                                status.classList.remove('unread-status');
                                status.classList.add('read-status');
                            });
                        } else if (message.type === 'CHAT') {
                            addMessage(message);

                            // 상대방이 보낸 메시지일 경우에만 읽음 처리 전송
                            if (message.senderId !== userId) {
                                const readMessage = {
                                    type: 'READ',
                                    roomId: roomId,
                                    senderId: userId,
                                    targetId: message.senderId
                                };

                                if (ws && ws.readyState === WebSocket.OPEN) {
                                    ws.send(JSON.stringify(readMessage));
                                } else {
                                    fetch('${pageContext.request.contextPath}/message/send', {
                                        method: 'POST',
                                        headers: {
                                            'Content-Type': 'application/json',
                                        },
                                        body: JSON.stringify(readMessage)
                                    });
                                }
                            }
                        }
                    }
                </script>
            </body>

            </html>