$(document).ready(function () {
    let token = localStorage.getItem("token");

    function loadOrders() {
        $.ajax({
            url: "http://localhost:8080/api/v1/orders/getAllOrders",
            method: "GET",
            headers: { "Authorization": "Bearer " + token },
            success: function (response) {
                let tableBody = $("#orderTable");
                tableBody.empty();
                $.each(response.data, function (index, order) {
                    tableBody.append(`
                        <tr>
                            <td>${order.userName}</td>
                            <td>${order.orderDate}</td>
                            <td>${order.totalAmount}</td>
                        </tr>
                    `);
                });
            }
        });
    }

    loadOrders();
});