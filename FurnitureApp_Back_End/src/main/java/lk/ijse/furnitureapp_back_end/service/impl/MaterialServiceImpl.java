/*
package lk.ijse.furnitureapp_back_end.service.impl;

import lk.ijse.furnitureapp_back_end.dto.MaterialDto;
import lk.ijse.furnitureapp_back_end.entity.Material;
import lk.ijse.furnitureapp_back_end.repo.MaterialRepository;
import lk.ijse.furnitureapp_back_end.service.MaterialService;
import lk.ijse.furnitureapp_back_end.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MaterialServiceImpl implements MaterialService {

    @Autowired
    private MaterialRepository materialRepository;

    @Override
    public int saveMaterial(MaterialDto material) {
        if (materialRepository.existsByName(material.getName())) {
            return VarList.Not_Acceptable;
        } else {
            Material newMaterial = new Material();
            newMaterial.setName(material.getName());

            materialRepository.save(newMaterial);
            return VarList.Created;
        }
    }

    @Override
    public List<MaterialDto> getAllMaterials() {
        List<Material> materialList = materialRepository.findAll();

        return materialList.stream().map(material ->
                new MaterialDto(material.getMaterialId(), material.getName())
        ).collect(Collectors.toList());
    }

    @Override
    public int deleteMaterial(UUID uuid) {
        Optional<Material> materialOptional = materialRepository.findById(uuid);

        if (materialOptional.isPresent()) {
            materialRepository.deleteById(uuid);
            return VarList.OK;
        }

        return VarList.Not_Found;
    }
}
*/
