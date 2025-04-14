$(document).ready(function () {
    let token = localStorage.getItem("token");

    function loadCategories() {
        $.ajax({
            url: "http://localhost:8080/api/v1/categories/getAll",
            method: "GET",
            headers: { "Authorization": "Bearer " + token },
            success: function (response) {
                let tableBody = $("#categoryTable");
                tableBody.empty();
                $.each(response.data, function (index, category) {
                    tableBody.append(`
                        <tr>
                            <td>${category.categoryId}</td>
                            <td>${category.name}</td>
                            <td>${category.status}</td>
                            <td>
                                <button class="btn btn-sm btn-warning" data-bs-toggle="modal" data-bs-target="#categoryModal" id="updateCategoryBtn" data-id="${category.id}"><i class="fas fa-edit"></i></button>
                                <button class="deleteCategory btn btn-sm btn-danger" data-id="${category.categoryId}"><i class="fas fa-trash"></i></button>
                            </td>
                        </tr>
                    `);
                });
            }
        });
    }

    function loadCategoryButtons() {
        $.ajax({
            url: "http://localhost:8080/api/v1/categories/names",
            method: "GET",
            headers: { "Authorization": "Bearer " + token },
            success: function (response) {
                const categoryNames = response.content;
                const categoryNav = $("#categoryNav");
                categoryNav.empty();

                if (categoryNames && categoryNames.length > 0) {
                    $.each(categoryNames, function (index, name) {
                        const button = $("<button></button>")
                            .addClass("category-button")
                            .attr("data-category", name.toLowerCase().replace(/\s+/g, '-'))
                            .text(name);
                        if (index === 0) {
                            button.addClass("active");
                        }
                        categoryNav.append(button);
                    });
                }
            },
            error: function (xhr, status, error) {
                console.error("Failed to load category names:", error);
            }
        });
    }

    function loadProductsByCategory() {
        const params = new URLSearchParams(window.location.search);
        const categoryName = params.get("name");

        if (!categoryName) {
            $("#category-title").text("Invalid Category");
            return;
        }

        $("#category-title").text(`Category: ${categoryName}`);

        $.ajax({
            url: `http://localhost:8080/api/v1/products/category/${encodeURIComponent(categoryName)}`,
            method: "GET",
            headers: { "Authorization": "Bearer " + token },
            success: function (response) {
                const container = $("#category-products");
                container.empty();

                response.data.forEach(product => {
                    container.append(`
<!--                        <div class="col-md-4 mb-4">-->
<!--                            <div class="card h-100 shadow-sm product-card">-->
<!--                                <div class="card-img-wrapper">-->
<!--                                    <img src="http://localhost:8080/images/${product.imageUrl}" class="card-img-top" alt="${product.name}">-->
<!--                                </div>-->
<!--                                <div class="card-body d-flex flex-column">-->
<!--                                    <h5 class="card-title">${product.name}</h5>-->
<!--                                    <p class="card-text text-truncate-3">${product.description}</p>-->
<!--                                    <p class="card-text fw-bold">Rs. ${product.price}</p>-->
<!--                                    // <a href="/product/${product.id}" class="btn btn-outline-primary mt-auto">View Details</a>-->
<!--                                </div>-->
<!--                            </div>-->
<!--                        </div>-->
                        <div class="col-md-4 mb-3">
                            <div class="card h-100 shadow-sm">
                                <img src="http://localhost:8080/images/${product.imageUrl}" class="card-img-top" alt="${product.name}">
                                <div class="card-body">
                                    <h4 class="card-title">${product.name}</h4>
                                    <p class="card-text">${product.description}</p>
                                    <p class="card-text"><strong>Rs. ${product.price}</strong></p>
                                    <button class="btn btn-outline-primary mt-2 btn-add-to-cart"
                                        data-id="${product.productId}"
                                        data-name="${product.name}"
                                        data-price="${product.price}"
                                        data-image="${product.imageUrl}">
                                        Add
                                    </button>
                                </div>
                            </div>
                       </div>
                    `);
                });
            },
            error: function () {
                $("#category-products").html("<p>Failed to load products.</p>");
            }
        });
    }

    loadCategories();
    loadCategoryButtons();
    loadProductsByCategory();

    $("#addCategoryForm").on("click", function (e) {
        e.preventDefault();
        let categoryData = {
            name: $("#categoryName").val(),
            status: $("#categoryStatus").val()
        };

        $.ajax({
            url: "http://localhost:8080/api/v1/categories/save",
            method: "POST",
            contentType: "application/json",
            headers: {"Authorization": "Bearer " + token},
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
            headers: {"Authorization": "Bearer " + token},
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
                url: `http://localhost:8080/api/v1/categories/delete/${categoryId}`,
                method: "POST",
                headers: {"Authorization": "Bearer " + token, "Access-Control-Allow-Origin": "*"},
                success: function () {
                    loadCategories();
                }
            });
        }
    });
});
