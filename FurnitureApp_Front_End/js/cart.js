// cart.js

let cart = [];
let userId = null;

function getCurrentUser(callback) {
    $.ajax({
        url: "http://localhost:8080/api/v1/users/me", // Adjust this to your actual endpoint
        method: "GET",
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("token") // If using JWT Auth
        },
        success: function (response) {
            userId = response.data.userId;
            callback(userId); // use userId in your order flow
        },
        error: function () {
            Swal.fire("Error", "Unable to fetch user info. Please login again.", "error");
        }
    });
}

// Add to Cart
$(document).on("click", ".btn-add-to-cart", function () {
    const id = $(this).data("id");
    const name = $(this).data("name");
    const price = $(this).data("price");
    const image = $(this).data("image");

    const existingItem = cart.find(item => item.id === id);
    if (existingItem) {
        existingItem.quantity += 1;
    } else {
        cart.push({ id, name, price, image, quantity: 1 });
    }

    updateCartCount();
    displayCartItems();
});

// Update Cart Count
function updateCartCount() {
    const totalQty = cart.reduce((acc, item) => acc + item.quantity, 0);
    $("#cart-count").text(totalQty);
}

// Display Cart Items
function displayCartItems() {
    const container = $("#cart-items");
    container.empty();

    if (cart.length === 0) {
        container.append(`<p class="text-muted">Your cart is empty.</p>`);
        return;
    }

    cart.forEach(item => {
        container.append(`
            <div class="col-12 mb-2">
                <div class="d-flex justify-content-between align-items-center border p-2 rounded">
                    <div class="d-flex align-items-center">
                        <img src="http://localhost:8080/images/${item.image}" width="50" class="me-3 rounded">
                        <div>
                            <h6 class="mb-0">${item.name}</h6>
                            <small>Rs. ${item.price} × ${item.quantity} = <strong>Rs. ${item.price * item.quantity}</strong></small>
                        </div>
                    </div>
                    <div>
                        <button class="btn btn-sm btn-outline-secondary me-2 btn-decrease-qty" data-id="${item.id}">-</button>
                        <button class="btn btn-sm btn-outline-secondary me-2 btn-increase-qty" data-id="${item.id}">+</button>
                        <button class="btn btn-sm btn-danger btn-remove-from-cart" data-id="${item.id}">Remove</button>
                    </div>
                </div>
            </div>
        `);
    });
}

// Increase Quantity
$(document).on("click", ".btn-increase-qty", function () {
    const id = $(this).data("id");
    const item = cart.find(item => item.id === id);
    if (item) {
        item.quantity++;
        updateCartCount();
        displayCartItems();
    }
});

// Decrease Quantity
$(document).on("click", ".btn-decrease-qty", function () {
    const id = $(this).data("id");
    const item = cart.find(item => item.id === id);
    if (item && item.quantity > 1) {
        item.quantity--;
    } else {
        cart = cart.filter(item => item.id !== id);
    }
    updateCartCount();
    displayCartItems();
});

// Remove from Cart
$(document).on("click", ".btn-remove-from-cart", function () {
    const id = $(this).data("id");
    cart = cart.filter(item => item.id !== id);
    updateCartCount();
    displayCartItems();
});

// Save Cart to Backend
/*$("#saveCart").click(function () {
    if (cart.length === 0) {
        Swal.fire("Oops!", "Your cart is empty!", "warning");
        return;
    }

    // Build order summary
    let summaryText = "";
    let total = 0;

    cart.forEach(item => {
        const subTotal = item.price * item.quantity;
        summaryText += `${item.name} x${item.quantity} = Rs. ${subTotal}\n`;
        total += subTotal;
    });

    summaryText += `\nTotal: Rs. ${total}`;

    Swal.fire({
        title: "Order Summary",
        text: summaryText,
        icon: "info",
        showCancelButton: true,
        confirmButtonText: "Proceed to Payment",
        cancelButtonText: "Cancel"
    }).then(result => {
        if (result.isConfirmed) {
            Swal.fire({
                title: "Simulate Payment",
                input: "text",
                inputLabel: "Enter Payment Reference ID",
                inputPlaceholder: "e.g. PH123XYZ",
                showCancelButton: true,
            }).then(paymentResult => {
                if (paymentResult.isConfirmed && paymentResult.value) {

                    // Step 1: Save Cart (Optional - if needed)
                    const cartPayload = {
                        items: cart.map(item => ({
                            productId: item.id,
                            quantity: item.quantity
                        }))
                    };

                    $.ajax({
                        url: "/api/v1/cart/save",
                        method: "POST",
                        contentType: "application/json",
                        data: JSON.stringify(cartPayload),
                        success: function () {
                            console.log("Cart saved.");

                            // Step 2: Place Order
                            const orderData = {
                                userId: 1, // Replace with dynamic user ID if possible
                                items: cart.map(item => ({
                                    productId: item.id,
                                    quantity: item.quantity,
                                    price: item.price
                                })),
                                totalAmount: total,
                                paymentRef: paymentResult.value
                            };

                            $.ajax({
                                url: "http://localhost:8080/api/orders/place",
                                method: "POST",
                                contentType: "application/json",
                                data: JSON.stringify(orderData),
                                success: function (response) {
                                    Swal.fire("Success", `Order placed! Order ID: ${response.orderId}`, "success");
                                    cart = [];
                                    updateCartCount();
                                    displayCartItems();
                                    $("#cartModal").modal("hide");
                                },
                                error: function () {
                                    Swal.fire("Error", "Failed to place order.", "error");
                                }
                            });
                        },
                        error: function () {
                            Swal.fire("Error", "Failed to save cart.", "error");
                        }
                    });

                } else {
                    Swal.fire("Cancelled", "Payment was not completed.", "info");
                }
            });
        }
    });
});*/

$("#saveCart").click(function () {
    if (cart.length === 0) {
        alert("Oops! Your cart is empty!");
        return;
    }

    let summaryText = "";
    let total = 0;

    cart.forEach(item => {
        const subTotal = item.price * item.quantity;
        summaryText += `${item.name} x${item.quantity} = Rs. ${subTotal}\n`;
        total += subTotal;
    });

    summaryText += `\nTotal: Rs. ${total}`;

    if (confirm("Confirm Order:\n\n" + summaryText)) {

        // 🔹 Fetch current user first
        getCurrentUser(function (userId) {
            console.log("User id : " + userId)
            // 🔹 Step 1: Save Cart
            const cartPayload = {
                userId:userId,
                items: cart.map(item => ({
                    productId: item.id,
                    quantity: item.quantity
                }))
            };

            $.ajax({
                url: "http://localhost:8080/api/v1/cart/save",
                method: "POST",
                contentType: "application/json",
                data: JSON.stringify(cartPayload),
                headers: {
                    "Authorization": "Bearer " + localStorage.getItem("token")
                },
                success: function () {
                    console.log("Cart saved.");

                    // 🔹 Step 2: Place Order
                    const orderPayload = {
                        userId: userId,
                        totalAmount: total,
                        items: cart.map(item => ({
                            productId: item.id,
                            quantity: item.quantity,
                            price: item.price
                        })),
                        paymentRef: "PAY-" + new Date().getTime()
                    };

                    $.ajax({
                        url: "http://localhost:8080/api/v1/orders/place",
                        method: "POST",
                        contentType: "application/json",
                        data: JSON.stringify(orderPayload),
                        headers: {
                            "Authorization": "Bearer " + localStorage.getItem("token")
                        },
                        success: function (response) {
                            alert(`Order placed!`);
                            cart = [];
                            updateCartCount();
                            displayCartItems();
                            $("#cartModal").modal("hide");
                        },
                        error: function () {
                            alert("Failed to place order.");
                        }
                    });

                },
                error: function () {
                    alert("Failed to save cart.");
                }
            });

        });
    }
});
