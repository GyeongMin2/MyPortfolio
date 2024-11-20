// 숫자를 세 자리마다 콤마로 구분하는 함수
function formatNumber(num) {
  return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

// 총 결제금액을 계산하고 결제 후 남은 마일리지를 업데이트하는 함수
function calculateTotalPrice() {
  let total = 0;
  const checkboxes = document.querySelectorAll(".lectureCheckbox");

  checkboxes.forEach((checkbox) => {
    if (checkbox.checked) {
      const price = parseInt(checkbox.getAttribute("data-price"), 10);
      total += price;
    }
  });

  // 총 결제금액 표시
  document.getElementById("totalPayValue").innerText = formatNumber(total);

  // 결제 후 남은 마일리지 계산 및 표시
  const mileage = parseInt(document.getElementById("mileage").innerText.replace(/,/g, ""), 10);
  const afterMileage = mileage - total;
  document.getElementById("afterMileage").innerText = formatNumber(afterMileage < 0 ? 0 : afterMileage);
}

// 'selectAll' 체크박스를 눌렀을 때 모든 'lectureCheckbox'의 상태를 변경하는 함수
document.getElementById("selectAll").addEventListener("change", function () {
  const isChecked = this.checked;
  const checkboxes = document.querySelectorAll(".lectureCheckbox");

  checkboxes.forEach((checkbox) => {
    checkbox.checked = isChecked;
  });

  calculateTotalPrice();
});

// 개별 체크박스 상태가 변경될 때마다 총 금액을 업데이트
document.querySelectorAll(".lectureCheckbox").forEach((checkbox) => {
  checkbox.addEventListener("change", function () {
    if (!this.checked) {
      document.getElementById("selectAll").checked = false;
    }

    const allChecked = [...document.querySelectorAll(".lectureCheckbox")].every(
      (checkbox) => checkbox.checked
    );
    document.getElementById("selectAll").checked = allChecked;

    calculateTotalPrice();
  });
});

// 삭제 버튼에 이벤트 리스너 추가
document.querySelectorAll('.deleteBtn').forEach(button => {
    button.addEventListener('click', function() {
        if (!confirm('선택한 항목을 삭제하시겠습니까?')) return;

        const row = this.closest('tr');
        const paymentIdx = this.getAttribute('data-payment-idx');

        fetch('/payments/user/deleteCartItem.do', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: `paymentIdx=${paymentIdx}`
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                row.remove();
                calculateTotalPrice();
                alert(data.message);
            } else {
                alert(data.message);
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('삭제 중 오류가 발생했습니다.');
        });
    });
});

// 선택 삭제 기능 구현
function deleteSelected() {
  const checkboxes = document.querySelectorAll(".lectureCheckbox");

  checkboxes.forEach((checkbox) => {
    if (checkbox.checked) {
      const row = checkbox.closest("tr");
      row.remove();
    }
  });

  calculateTotalPrice();
}

// 페이지 로드 시 초기 총 금액 계산
window.onload = calculateTotalPrice;
