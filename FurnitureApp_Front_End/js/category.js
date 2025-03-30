$(document).ready(function () {
    let token = localStorage.getItem("token");

    function loadCategories() {
        $.ajax({
            url: "http://localhost:8080/api/v1/categories/getAll",
            method: "GET",
            // headers: { "Authorization": "Bearer " + token },
            success: function (response) {
                let tableBody = $("#categoryTable");
                tableBody.empty();
                $.each(response.data, function (index, category) {
                    console.log(category)
                    console.log("aaaaaaaaaaaaaaa"+category.status)
                    console.log("xxxxxxxx"+category.name)
                    console.log("ggggggggg"+category.id)
                    tableBody.append(`
                        <tr>
                            <td>${category.categoryId}</td>
                            <td>${category.name}</td>
                            <td>${category.status}</td>
                            <td>
                                <button class="btn btn-sm btn-warning" data-bs-toggle="modal" data-bs-target="#categoryModal" id="updateCategoryBtn" data-id="${category.id}"><i class="fas fa-edit"></i></button>
                                <button class="deleteCategory btn btn-sm btn-danger" data-id="${category.id}"><i class="fas fa-trash"></i></button>
                            </td>
                        </tr>
                    `);
                });
            }
        });
    }

    loadCategories();

    $("#addCategoryForm").on("click",function (e) {
        e.preventDefault();
        let categoryData = {
            name: $("#categoryName").val(),
            status: $("#categoryStatus").val()
        };

        $.ajax({
            url: "http://localhost:8080/api/v1/categories/save",
            method: "POST",
            contentType: "application/json",
            headers: { "Authorization": "Bearer " + token },
            data: JSON.stringify(categoryData),
            success: function () {
                loadCategories();
            }
        });
    });

    $("#updateCategoryBtn").click(function () {
        let categoryId = $(this).data("id");
        let categoryData = {
            name: $("#categoryName").val(),
            status: $("#categoryStatus").val()
        };

        $.ajax({
            url: `http://localhost:8080/api/v1/categories/update/${categoryId}`,
            method: "PUT",
            contentType: "application/json",
            headers: { "Authorization": "Bearer " + token },
            data: JSON.stringify(categoryData),
            success: function () {
                loadCategories();
                $("#addCategoryForm")[0].reset();
                $("#updateCategoryBtn").hide();
                $("#addCategoryBtn").show();
            }
        });
    });

    $(document).on("click", ".deleteCategory", function () {
        let categoryId = $(this).data("id");

        if (confirm("Are you sure you want to delete this category?")) {
            $.ajax({
                url: `http://localhost:8080/api/v1/categories/categories/${categoryId}`,
                method: "DELETE",
                headers: { "Authorization": "Bearer " + token },
                success: function () {
                    loadCategories();
                }
            });
        }
    });
});
