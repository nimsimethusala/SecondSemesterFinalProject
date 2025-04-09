/*
$(document).ready(function () {
    let token = localStorage.getItem("token");

    function loadMaterials() {
        $.ajax({
            url: "http://localhost:8080/api/v1/material/getAll",
            method: "GET",
            success: function (response) {
                let tableBody = $("#materialTable");
                tableBody.empty();
                $.each(response.data, function (index, material) {
                    tableBody.append(`
                        <tr>
                            <td>${material.name}</td>
                            <td>
                                <button class="deleteMaterial btn btn-sm btn-danger" data-id="${material.materialId}"><i class="fas fa-trash"></i></button>
                            </td>
                        </tr>
                    `);
                });
            }
        });
    }

    loadMaterials();

    $("#addMaterialForm").on("click", function (e) {
        e.preventDefault();
        let categoryData = {
            name: $("#materialName").val(),
        };

        $.ajax({
            url: "http://localhost:8080/api/v1/material/save",
            method: "POST",
            contentType: "application/json",
            headers: {"Authorization": "Bearer " +  localStorage.getItem("token")},
            data: JSON.stringify(categoryData),
            success: function () {
                loadMaterials();
            }
        });
    });

    $(document).on("click", ".deleteMaterial", function () {
        let materialId = $(this).data("id");

        if (confirm("Are you sure you want to delete this material?")) {
            $.ajax({
                url: `http://localhost:8080/api/v1/material/delete/${materialId}`,
                method: "POST",
                headers: {"Authorization": "Bearer " + token},
                success: function () {
                    loadMaterials();
                }
            });
        }
    });
});*/
