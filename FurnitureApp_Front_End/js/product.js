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
                            <td>${product.category}</td>
                            <td>${product.price}</td>
                            <td ><img alt="${product.imageUrl}" class="product-image" src="http://localhost:8080/images/${product.imageUrl}"></td>
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

    function loadLatestProducts() {
        $.ajax({
            url: "http://localhost:8080/api/v1/products/latest-per-category",
            method: "GET",
            success: function (response) {
                console.log(response)
                const contentContainer = $("#categoryContent");
                const navContainer = $("#categoryNav");
                contentContainer.empty();
                navContainer.empty();

                let isFirst = true;

                $.each(response, function (categoryName, products) {
                    let sectionId = categoryName.toLowerCase().replace(/\s+/g, '-');

                    // Add category button
                    navContainer.append(`
                    <button class="category-button ${isFirst ? 'active' : ''}" data-category="${sectionId}">
                        ${categoryName}
                    </button>
                `);

                    // Create category content section
                    let section = $(`
                    <div class="category-content ${isFirst ? 'active' : ''}" id="${sectionId}">
<!--                        <h3 class="mb-3">${categoryName}</h3>-->
                        <div class="products row"></div>
                    </div>
                `);

                    products.forEach(product => {
                        section.find(".products").append(`
                            <div class="col-md-4 mb-3">
                                <div class="card h-100 shadow-sm">
                                    <img src="http://localhost:8080/images/${product.imageUrl}" class="card-img-top" alt="${product.name}">
                                    <div class="card-body">
                                        <h5 class="card-title">${product.name}</h5>
                                        <p class="card-text">${product.description}</p>
                                        <p class="card-text"><strong>Rs. ${product.price}</strong></p>
                                        <a href="/product/${product.id}" class="btn btn-outline-primary mt-2">View Details</a>
                                    </div>
                                </div>
                            </div>
                        `);
                    });

                    section.append(`
                        <div class="text-end mt-3">
                            <a href="/category/${sectionId}" class="btn btn-primary">See More</a>
                        </div>
                    `);

                    contentContainer.append(section);
                    isFirst = false;
                });

                // Handle category button clicks
                $(".category-button").on("click", function () {
                    const selectedCategory = $(this).data("category");

                    // Update button active state
                    $(".category-button").removeClass("active");
                    $(this).addClass("active");

                    // Show selected category section
                    $(".category-content").removeClass("active");
                    $(`#${selectedCategory}`).addClass("active");

                    /*// Navigate to the category-specific page
                    window.location.href = `/category/${selectedCategory}`;*/
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
                $("#productModal").modal("hide");  // Close modal after saving
                $("#productForm")[0].reset();  // Reset form
            }
        });
    });

    /*// Update existing product
    $(document).on("click", "#updateProductBtn", function () {
        let productId = $(this).data("id");

        // Load product details into the modal form for editing
        $.ajax({
            url: `http://localhost:8080/api/v1/products/getById/${productId}`,
            method: "GET",
            headers: { "Authorization": "Bearer " + token },
            success: function (response) {
                let product = response.data;
                $("#productName").val(product.name);
                $("#productCategory").val(product.category);
                $("#productPrice").val(product.price);
                $("#productDescription").val(product.description);
                // You can handle image preview here if needed

                $("#saveProductBtn").hide();  // Hide the Add button
                $("#updateProductBtn").show();  // Show the Update button
                $("#updateProductBtn").data("id", productId);  // Store product ID for update
            }
        });
    });*/

    $("#updateProductBtn").click(function () {
        let productId = $(this).data("id");

        let formData = new FormData();
        formData.append("name", $("#productName").val());
        formData.append("categoryName", $("#productCategory").val()); // Use categoryName
        formData.append("price", $("#productPrice").val());
        formData.append("description", $("#productDescription").val());

        let imageFile = $("#productImage")[0].files[0];
        if (imageFile) {
            formData.append("image", imageFile);
        }

        $.ajax({
            url: `http://localhost:8080/api/v1/products/update/${productId}`,
            method: "PUT",
            contentType: false,
            processData: false,
            headers: {"Authorization": "Bearer " + token},
            data: formData,
            success: function () {
                loadProducts();
                $("#productModal").modal("hide");
                $("#productForm")[0].reset();
                $("#saveProductBtn").show();
                $("#updateProductBtn").hide();
            }
        });
    });

    // Delete product
    $(document).on("click", ".deleteProduct", function () {
        let productId = $(this).data("id");

        if (confirm("Are you sure you want to delete this product?")) {
            $.ajax({
                url: `http://localhost:8080/api/v1/products/delete/${productId}`,
                method: "DELETE",
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