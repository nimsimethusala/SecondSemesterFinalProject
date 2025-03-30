$(document).ready(function () {
    let token = localStorage.getItem("jwtToken");

    function loadUsers() {
        $.ajax({
            url: "http://localhost:8080/api/v1/users/all",
            method: "GET",
            // headers: { "Authorization": "Bearer " + token },
            success: function (response) {
                console.log("response "+response.data[0].email )
                let tableBody = $("#userTable tbody");
                tableBody.empty();
                $.each(response.data, function (index, user) {
                    tableBody.append(`
                        <tr>
                            <td>${user.id}</td>
                            <td>${user.name}</td>
                            <td>${user.email}</td>
                            <td>
                                <button class="editUser" data-id="${user.id}">Edit</button>
                                <button class="deleteUser" data-id="${user.id}">Delete</button>
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

    $("#addUserForm").submit(function (event) {
        event.preventDefault();
        let userData = {
            name: $("#name").val(),
            email: $("#email").val()
        };

        $.ajax({
            url: "http://localhost:8080/api/v1/users",
            method: "POST",
            contentType: "application/json",
            headers: { "Authorization": "Bearer " + token },
            data: JSON.stringify(userData),
            success: function () {
                loadUsers();
                $("#addUserForm")[0].reset();
            },
            error: function () {
                alert("Error saving user.");
            }
        });
    });

    $(document).on("click", ".editUser", function () {
        let userId = $(this).data("id");

        $.ajax({
            url: `http://localhost:8080/api/v1/users/${userId}`,
            method: "GET",
            headers: { "Authorization": "Bearer " + token },
            success: function (user) {
                $("#name").val(user.name);
                $("#email").val(user.email);
                $("#updateUserBtn").show().data("id", user.id);
                $("#addUserBtn").hide();
            },
            error: function () {
                alert("Error fetching user.");
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
            url: `http://localhost:8080/api/v1/users/${userId}`,
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
                url: `http://localhost:8080/api/v1/users/${userId}`,
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
