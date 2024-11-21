package com.edu.eksamenbackend.Mapper;

import com.edu.eksamenbackend.dto.StoreDTO;
import com.edu.eksamenbackend.entity.Store;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import com.edu.eksamenbackend.dto.StoreDTO;
import com.edu.eksamenbackend.entity.Store;

@Component
public class StoreMapper {

    public StoreDTO toDTO(Store store) {
        if (store == null) {
            return null;
        }

        StoreDTO dto = new StoreDTO();
        dto.setId(store.getId());
        dto.setName(store.getName());
        dto.setCity(store.getCity());
        dto.setStreet(store.getStreet());
        dto.setZip(store.getZip());
        return dto;
    }

    public Store toEntity(StoreDTO storeDTO) {
        if (storeDTO == null) {
            return null;
        }

        Store store = new Store();
        store.setId(storeDTO.getId());
        store.setName(storeDTO.getName());
        store.setCity(storeDTO.getCity());
        store.setStreet(storeDTO.getStreet());
        store.setZip(storeDTO.getZip());
        return store;
    }
}
