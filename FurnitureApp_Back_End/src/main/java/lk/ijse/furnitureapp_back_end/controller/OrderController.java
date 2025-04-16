package lk.ijse.furnitureapp_back_end.controller;

import lk.ijse.furnitureapp_back_end.dto.OrderDto;
import lk.ijse.furnitureapp_back_end.dto.OrderRequestDto;
import lk.ijse.furnitureapp_back_end.dto.ResponseDTO;
import lk.ijse.furnitureapp_back_end.service.OrderService;
import lk.ijse.furnitureapp_back_end.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/place")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<ResponseDTO> placeOrder(@RequestBody OrderRequestDto orderRequestDto) {
        System.out.println(orderRequestDto.toString());
        try {
            OrderDto placedOrder = orderService.placeOrder(orderRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseDTO(VarList.Created, "Order placed successfully", placedOrder));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, "Failed to place order", null));
        }
    }

    @GetMapping("/{orderId}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<ResponseDTO> getOrder(@PathVariable UUID orderId) {
        try {
            OrderDto order = orderService.getOrderById(orderId);
            return ResponseEntity.ok(new ResponseDTO(VarList.Created, "Order fetched successfully", order));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, "Failed to fetch order", null));
        }
    }
}