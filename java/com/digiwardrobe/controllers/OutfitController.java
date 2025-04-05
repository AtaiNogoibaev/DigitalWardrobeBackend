package com.digiwardrobe.controllers;

import java.util.List;
import java.util.UUID;

import com.digiwardrobe.model.OutfitInputDto;
import com.digiwardrobe.model.OutfitWithItemsDTO;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.digiwardrobe.configurations.utils.AuthUtils;
import com.digiwardrobe.data_access.entity.OutfitEntity;
import com.digiwardrobe.services.OutfitService;

@Controller
public class OutfitController {

    private final OutfitService outfitService;

    public OutfitController(final OutfitService outfitService) {
        this.outfitService = outfitService;
    }

    @QueryMapping
    public List<OutfitEntity> userOutfits() {
        return outfitService.getUserOutfits(getUserId());
    }

    @QueryMapping
    public OutfitWithItemsDTO outfitWithItems(@Argument final UUID outfitId) {
        return outfitService.getUserOutfitById(getUserId(), outfitId);
    }

    @MutationMapping
    public OutfitWithItemsDTO createOutfit(@Argument final OutfitInputDto outfit) {
        return outfitService.addUserOutfit(getUserId(), outfit);
    }

    @MutationMapping
    public boolean deleteOutfit(@Argument final UUID outfitId) {
        outfitService.deleteOutfit(getUserId(), outfitId);
        return true;
    }

    @MutationMapping
    public OutfitWithItemsDTO updateOutfit(@Argument final UUID outfitId,
                                           @Argument final OutfitInputDto outfit) {
        return outfitService.updateUserOutfit(getUserId(), outfitId, outfit);
    }

    private UUID getUserId() {
        return AuthUtils.getCurrentUserId();
    }

}
