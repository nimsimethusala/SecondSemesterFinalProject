$(document).ready(function () {
    let token = localStorage.getItem("token");

    // Function to load products
    function loadProducts() {
        $.ajax({
            url: "http://localhost:8080/api/v1/products/getAll",
            method: "GET",
            headers: { "Authorization": "Bearer " + token },
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

    // Load products on page load
    loadProducts();

    // Add new product
    $("#saveProductBtn").click(function (e) {
        e.preventDefault();
        let productData = {
            name: $("#productName").val(),
            category: $("#productCategory").val(),
            price: $("#productPrice").val(),
            description: $("#productDescription").val(),
            image: $("#productImage")[0].files[0] // Image file
        };

        let formData = new FormData();
        for (const key in productData) {
            formData.append(key, productData[key]);
        }

        $.ajax({
            url: "http://localhost:8080/api/v1/products/save",
            method: "POST",
            contentType: false,
            processData: false,
            headers: { "Authorization": "Bearer " + token },
            data: formData,
            success: function () {
                loadProducts();
                $("#productModal").modal("hide");  // Close modal after saving
                $("#productForm")[0].reset();  // Reset form
            }
        });
    });

    // Update existing product
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
    });

    // Update the product via PUT
    $("#updateProductBtn").click(function () {
        let productId = $(this).data("id");

        let productData = {
            name: $("#productName").val(),
            category: $("#productCategory").val(),
            price: $("#productPrice").val(),
            description: $("#productDescription").val(),
            image: $("#productImage")[0].files[0]
        };

        let formData = new FormData();
        for (const key in productData) {
            formData.append(key, productData[key]);
        }

        $.ajax({
            url: `http://localhost:8080/api/v1/products/update/${productId}`,
            method: "PUT",
            contentType: false,
            processData: false,
            headers: { "Authorization": "Bearer " + token },
            data: formData,
            success: function () {
                loadProducts();
                $("#productModal").modal("hide");  // Close modal after saving
                $("#productForm")[0].reset();  // Reset form
                $("#saveProductBtn").show();  // Show Add button again
                $("#updateProductBtn").hide();  // Hide Update button
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
                headers: { "Authorization": "Bearer " + token },
                success: function () {
                    loadProducts();
                }
            });
        }
    });
});

fetch("http://localhost:8080/api/v1/categories/getAll")
    .then(response => response.json())
    .then(data => {
        let categoryDropdown = document.querySelector("#categoryDropdown"); // Update with correct ID
        categoryDropdown.innerHTML = ""; // Clear existing options

        if (Array.isArray(data.data)) {
            data.data.forEach(category => {
                let option = document.createElement("option");
                option.value = category.categoryId;
                option.textContent = category.name;
                categoryDropdown.appendChild(option);
            });
        } else {
            console.error("Expected an array but got:", data);
        }
    })
    .catch(error => console.error("Error fetching categories:", error));