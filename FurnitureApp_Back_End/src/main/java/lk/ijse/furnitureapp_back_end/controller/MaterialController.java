/*
package lk.ijse.furnitureapp_back_end.controller;

import jakarta.validation.Valid;
import lk.ijse.furnitureapp_back_end.dto.MaterialDto;
import lk.ijse.furnitureapp_back_end.dto.ResponseDTO;
import lk.ijse.furnitureapp_back_end.repo.MaterialRepository;
import lk.ijse.furnitureapp_back_end.service.MaterialService;
import lk.ijse.furnitureapp_back_end.util.VarList;
import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/material")
public class MaterialController {
    private final MaterialService materialService;

    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO> saveMaterial(@Valid @RequestBody MaterialDto materialDTO) {
        try {
            int res = materialService.saveMaterial(materialDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseDTO(VarList.Created, "Material Saved Successfully", res));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getAllMaterials() {
        try {
            List<MaterialDto> materials = materialService.getAllMaterials();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDTO(VarList.OK, "Materials Retrieved Successfully", materials));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }

    @PostMapping("/delete/{materialId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO> deleteMaterial(@PathVariable String materialId) {
        try {
            int res = materialService.deleteMaterial(UUID.fromString(materialId));
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDTO(VarList.OK, "Material Deleted Successfully", res));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }
}
*/
