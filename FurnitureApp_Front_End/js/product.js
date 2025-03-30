// document.addEventListener("DOMContentLoaded", () => {
//     let selectedProductId = null; // Store the product ID for editing
//
//     // Load products from the server
//     function loadProducts() {
//         fetch("/api/products")
//             .then(response => response.json())
//             .then(data => {
//                 renderProducts(data);
//             })
//             .catch(error => console.error("Error fetching products:", error));
//     }
//
//     // Render Products in Table
//     function renderProducts(products) {
//         const productTableBody = document.querySelector("#productTableBody");
//         productTableBody.innerHTML = "";
//
//         products.forEach((product) => {
//             const row = document.createElement("tr");
//             row.innerHTML = `
//                 <td>${product.id}</td>
//                 <td>${product.name}</td>
//                 <td>${product.category}</td>
//                 <td>${product.price}</td>
//                 <td class="table-actions">
//                     <button class="btn btn-sm btn-warning" data-bs-toggle="modal" data-bs-target="#productUpdateModal" onclick="editProduct(${product.id})">
//                         <i class="fas fa-edit"></i>
//                     </button>
//                     <button class="btn btn-sm btn-danger" onclick="deleteProduct(${product.id})">
//                         <i class="fas fa-trash"></i>
//                     </button>
//                 </td>
//             `;
//             productTableBody.appendChild(row);
//         });
//     }
//
//     // Add Product
//     document.getElementById("saveProductBtn").addEventListener("click", () => {
//         const name = document.getElementById("newProductName").value.trim();
//         const category = document.getElementById("newProductCategory").value;
//         const price = document.getElementById("newProductPrice").value.trim();
//         const description = document.getElementById("newProductDescription").value.trim();
//
//         if (!name || !price) {
//             alert("Product name and price are required!");
//             return;
//         }
//
//         const newProduct = { name, category, price, description };
//
//         fetch("/api/products", {
//             method: "POST",
//             headers: { "Content-Type": "application/json" },
//             body: JSON.stringify(newProduct)
//         })
//             .then(response => response.json())
//             .then(() => {
//                 loadProducts();
//                 document.getElementById("addProductForm").reset();
//                 bootstrap.Modal.getInstance(document.getElementById("productAddModal")).hide();
//             })
//             .catch(error => console.error("Error adding product:", error));
//     });
//
//     // Edit Product (Populate Modal)
//     window.editProduct = (id) => {
//         fetch(`/api/products/${id}`)
//             .then(response => response.json())
//             .then(product => {
//                 selectedProductId = product.id;
//
//                 document.getElementById("productName").value = product.name;
//                 document.getElementById("productCategory").value = product.category;
//                 document.getElementById("productPrice").value = product.price;
//                 document.getElementById("productDescription").value = product.description;
//             })
//             .catch(error => console.error("Error fetching product:", error));
//     };
//
//     // Update Product
//     document.getElementById("updateProductBtn").addEventListener("click", () => {
//         if (!selectedProductId) return;
//
//         const name = document.getElementById("productName").value.trim();
//         const category = document.getElementById("productCategory").value;
//         const price = document.getElementById("productPrice").value.trim();
//         const description = document.getElementById("productDescription").value.trim();
//
//         if (!name || !price) {
//             alert("Product name and price are required!");
//             return;
//         }
//
//         const updatedProduct = { name, category, price, description };
//
//         fetch(`/api/products/${selectedProductId}`, {
//             method: "PUT",
//             headers: { "Content-Type": "application/json" },
//             body: JSON.stringify(updatedProduct)
//         })
//             .then(response => response.json())
//             .then(() => {
//                 loadProducts();
//                 bootstrap.Modal.getInstance(document.getElementById("productUpdateModal")).hide();
//             })
//             .catch(error => console.error("Error updating product:", error));
//     });
//
//     // Delete Product
//     window.deleteProduct = (id) => {
//         if (!confirm("Are you sure you want to delete this product?")) return;
//
//         fetch(`/api/products/${id}`, { method: "DELETE" })
//             .then(() => loadProducts())
//             .catch(error => console.error("Error deleting product:", error));
//     };
//
//     // Load products on page load
//     loadProducts();
// });
