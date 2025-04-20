$(document).ready(function () {
    let token = localStorage.getItem("token");

    // Function to load products
    function loadProducts() {
        $.ajax({
            url: "http://localhost:8080/api/v1/products/getAll",
            method: "GET",
            headers: {"Authorization": "Bearer " + token},
            success: function (response) {
                let tableBody = $("#productTable");
                tableBody.empty();
                $.each(response.data, function (index, product) {
                    tableBody.append(`
                        <tr>
                            <td>${product.productId}</td>
                            <td>${product.name}</td>
                            <td>${product.categoryName}</td>
                            <td>${product.price}</td>
                            <td>${product.quantity}</td>
                            <td>
                                <button class="btn btn-sm btn-warning" data-bs-toggle="modal" data-bs-target="#productModal" id="updateProductBtn" data-id="${product.productId}"><i class="fas fa-edit"></i></button>
                                <button class="deleteProduct btn btn-sm btn-danger" data-id="${product.productId}"><i class="fas fa-trash"></i></button>
                            </td>
                        </tr>
                    `);
                });
            }
        });
    }

    $("#categoryTableSet").on("click", "#updateCategoryBtn", function () {
        var catagaryId = $(this).closest('tr').find('td').first().text();
        console.log(catagaryId);
        $("#editCategoryId").val(catagaryId);
    });

    function loadCategories() {
        $.ajax({
            url: "http://localhost:8080/api/v1/categories/getAll", // Replace with your actual endpoint
            method: "GET",
            success: function (response) {
                const dropdown = $("#categoryDropdown");
                dropdown.empty(); // Clear previous options
                dropdown.append(`<option value="" disabled selected>Select a category</option>`);
                $.each(response.data, function (index, category) {
                    dropdown.append(`<option value="${category.name}">${category.name}</option>`);
                });
            },
            error: function () {
                console.error("Failed to load categories.");
            }
        });
    }

    loadCategories();

    function loadLatestProducts() {
        $.ajax({
            url: "http://localhost:8080/api/v1/products/latest-per-category",
            method: "GET",
            headers: { "Authorization": "Bearer " + token },
            success: function (response) {
                console.log(response);
                const contentContainer = $("#categoryContent");
                const navContainer = $("#categoryNav");
                contentContainer.empty();
                navContainer.empty();

                let isFirst = true;

                $.each(response, function (categoryName, products) {
                    let sectionId = categoryName.toLowerCase().replace(/\s+/g, '-');

                    // Add category nav button
                    navContainer.append(`
                    <button class="category-button ${isFirst ? 'active' : ''}" data-category="${sectionId}">
                        ${categoryName}
                    </button>
                `);

                    // Create section for category products
                    let section = $(`
                    <div class="category-content ${isFirst ? 'active' : ''}" id="${sectionId}">
                        <div class="products row"></div>
                    </div>
                `);

                    // Add latest products to section
                    products.forEach(product => {
                        section.find(".products").append(`
                            <div class="col-md-4 mb-3">
                                <div class="card h-100 shadow-sm">
                                    <img src="http://localhost:8080/images/${product.imageUrl}" class="card-img-top" alt="${product.name}">
                                    <div class="card-body">
                                        <h4 class="card-title">${product.name}</h4>
                                        <p class="card-text">${product.description}</p>
                                        <p class="card-text"><strong>Rs. ${product.price}</strong></p>
<!--                                        <button class="btn btn-outline-primary mt-2 btn-add-to-cart"-->
<!--                                            // data-id="${product.productId}"-->
<!--                                            // data-name="${product.name}"-->
<!--                                            // data-price="${product.price}"-->
<!--                                            // data-image="${product.imageUrl}">-->
<!--                                            Add-->
<!--                                        </button>-->
                                    </div>
                                </div>
                            </div>
                        `);
                    });

                    // Add "See More" button (navigate to category.html)
                    section.append(`
                    <div class="text-end mt-3">
                        <button class="btn btn-primary see-more-btn" data-category-name="${categoryName}">See More</button>
                    </div>
                `);

                    contentContainer.append(section);
                    isFirst = false;
                });

                // Category nav click handler
                $(".category-button").on("click", function () {
                    const selectedCategory = $(this).data("category");
                    $(".category-button").removeClass("active");
                    $(this).addClass("active");
                    $(".category-content").removeClass("active");
                    $(`#${selectedCategory}`).addClass("active");
                });

                // Redirect to category.html with category name on "See More"
                $(document).on("click", ".see-more-btn", function () {
                    const categoryName = $(this).data("category-name");
                    const encodedName = encodeURIComponent(categoryName);
                    window.location.href = `category.html?name=${encodedName}`;
                });
            },
            error: function () {
                console.error("Failed to load latest products.");
            }
        });
    }

    // Load products on page load
    loadProducts();
    loadLatestProducts();

    // Add new product
    $("#saveProductBtn").click(function (e) {
        e.preventDefault();

        let formData = new FormData();
        formData.append("name", $("#productName").val());
        formData.append("categoryName", $("#categoryDropdown").val());
        formData.append("price", $("#productPrice").val());
        formData.append("quantity", $("#productQty").val());
        formData.append("description", $("#productDescription").val());

        let imageFile = $("#productImage")[0].files[0];
        if (imageFile) {
            formData.append("image", imageFile);
        }

        $.ajax({
            url: "http://localhost:8080/api/v1/products/save",
            method: "POST",
            contentType: false,
            processData: false,
            headers: {"Authorization": "Bearer " + token},
            data: formData,
            success: function () {
                loadProducts();
                $("#productModal").modal("hide");
                $("#productForm")[0].reset();
            }
        });
    });

    $("#updateCategoryForm").click(function () {
        let catId = $("#editCategoryId").val()
        console.log(catId)
        let categoryData = {
            name: $("#categoryUpdateName").val(),
            status: $("#categoryUpdateStatus").val()
        };

        $.ajax({
            url: `http://localhost:8080/api/v1/categories/update/${catId}`,
            method: "PUT",
            contentType: "application/json",
            headers: {"Authorization": "Bearer " + token},
            data: JSON.stringify(categoryData),
            success: function () {
                loadCategories();
                // $("#updateCategoryForm")[0].reset();
            }
        });
    });

    // Delete product
    $(document).on("click", ".deleteProduct", function () {
        let productId = $(this).data("id");

        if (confirm("Are you sure you want to delete this product?")) {
            $.ajax({
                url: `http://localhost:8080/api/v1/products/delete/${productId}`,
                method: "POST",
                headers: {"Authorization": "Bearer " + token},
                success: function () {
                    alert("Product deleted successfully!");
                    loadProducts();
                },
                error: function (xhr) {
                    alert("Error deleting product: " + xhr.responseJSON.message);
                }
            });
        }
    });
});