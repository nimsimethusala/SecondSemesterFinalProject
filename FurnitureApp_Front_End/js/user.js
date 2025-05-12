$(document).ready(function () {
    let token = localStorage.getItem("token");

    function loadUsers() {
        $.ajax({
            url: "http://localhost:8080/api/v1/adminuser/all",
            method: "GET",
            headers: { "Authorization": "Bearer " + token },
            success: function (response) {
                console.log("response "+response.data[0].email )
                let tableBody = $("#userTable");
                tableBody.empty();
                $.each(response.data, function (index, user) {
                    tableBody.append(`
                        <tr>
                            <td>${user.name}</td>
                            <td>${user.email}</td>
                            <td>${user.role}</td>
                            <td>
                                <button class="editUser btn btn-sm btn-warning" data-bs-toggle="modal" data-bs-target="#userModal" id="updateUserBtn" data-id="${user.id}"><i class="fas fa-edit"></i></button>
                                <button class="deleteUser btn btn-sm btn-danger" data-id="${user.email}"><i class="fas fa-trash"></i></button>
                            </td>
                        </tr>
                    `);
                });
            },
            error: function () {
                alert("Error loading users.");
            }
        });
    }

    loadUsers();

    $("#addUserForm").on("click",function (event) {
        event.preventDefault();
        let role = $("#role").val()
        console.log(role)
        let userData = {
            name: $("#name").val(),
            email: $("#email").val(),
            password:$("#password").val(),
            role:$("#role").val()
        };

        $.ajax({
            url: "http://localhost:8080/api/v1/adminuser/register",
            method: "POST",
            contentType: "application/json",
            headers: { "Authorization": "Bearer " + token },
            data: JSON.stringify(userData),
            success: function () {
                loadUsers();
                $("#userForm")[0].reset();
            },
            error: function () {
                alert("Error saving user.");
            }
        });
    });

    $("#updateUserBtn").click(function () {
        let userId = $(this).data("id");
        let userData = {
            name: $("#name").val(),
            email: $("#email").val()
        };

        $.ajax({
            url: `http://localhost:8080/api/v1/adminuser/update/${userId}`,
            method: "PUT",
            contentType: "application/json",
            headers: { "Authorization": "Bearer " + token },
            data: JSON.stringify(userData),
            success: function () {
                loadUsers();
                $("#addUserForm")[0].reset();
                $("#updateUserBtn").hide();
                $("#addUserBtn").show();
            },
            error: function () {
                alert("Error updating user.");
            }
        });
    });

    $(document).on("click", ".deleteUser", function () {
        let userId = $(this).data("id");

        if (confirm("Are you sure you want to delete this user?")) {
            $.ajax({
                url: `http://localhost:8080/api/v1/adminuser/delete/${userId}`,
                method: "DELETE",
                headers: { "Authorization": "Bearer " + token },
                success: function () {
                    loadUsers();
                },
                error: function () {
                    alert("Error deleting user.");
                }
            });
        }
    });
});
