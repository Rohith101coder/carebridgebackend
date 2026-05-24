package com.carebridge.backend.needsManagement.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.carebridge.backend.authManagement.entity.User;
import com.carebridge.backend.authManagement.repository.UserRepository;
// import com.carebridge.backend.authManagement.service.AuthService;
import com.carebridge.backend.common.enums.VerificationStatus;
import com.carebridge.backend.donorManagement.exception.UserNotFoundException;
import com.carebridge.backend.needsManagement.dto.NeedItemRequest;
import com.carebridge.backend.needsManagement.dto.NeedItemResponse;
import com.carebridge.backend.needsManagement.entity.DeletedItems;
import com.carebridge.backend.needsManagement.entity.NeedItem;
import com.carebridge.backend.needsManagement.exception.CommonException;
import com.carebridge.backend.needsManagement.exception.ItemAreadyExsist;
import com.carebridge.backend.needsManagement.exception.ItemNotFound;
import com.carebridge.backend.needsManagement.exception.OrpNotVerified;
import com.carebridge.backend.needsManagement.repository.DeletedItemRepo;
import com.carebridge.backend.needsManagement.repository.NeedItemRepo;
import com.carebridge.backend.needsManagement.util.NeedItemIdGenerator;
import com.carebridge.backend.orphanageManagement.entity.OrphanageProfile;
import com.carebridge.backend.orphanageManagement.exception.OrphanageProfileNotFoundException;
import com.carebridge.backend.orphanageManagement.repository.OrphanageProfileRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NeedItemService {
    
    private final NeedItemRepo needItemRepo;

    private final UserRepository userRepository;

    private final DeletedItemRepo deletedItemRepo;

    private final OrphanageProfileRepository orphanageProfileRepository;
    // private final NeedItemIdGenerator needItemIdGenerator;

    @Transactional
    public NeedItemResponse createNeedItem(NeedItemRequest request){

        


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("user not present"));
        OrphanageProfile profile = orphanageProfileRepository.findByUser(user).orElseThrow(()-> new OrphanageProfileNotFoundException("profile not found"));

        if(profile.getVerificationStatus()
        != VerificationStatus.VERIFIED){

            throw new OrpNotVerified(
            "Only verified orphanages can create need items"
            );
}


        String carebridgeId = profile.getCarebridgeId();
        String needItemId = NeedItemIdGenerator.generateNeedItemId(carebridgeId);

        boolean exists =
        needItemRepo
                .existsByNameIgnoreCaseAndCategoryAndOrphanageCareBridgeId(
                        request.getName(),
                        request.getCategory(),
                        carebridgeId
                );

            if(exists){

                    throw new ItemAreadyExsist(
                    "Need item already exists"
                    );
        }

        NeedItem item = new NeedItem();

        item.setName(request.getName());
        item.setDescription(request.getDescription());
        item.setCategory(request.getCategory());
        item.setQuantity(request.getQuantity());
        item.setFulfilledQuantity(0);
        item.setReservedQuantity(0);
        item.setPricePerQuantity(request.getPricePerQuantity());
        item.setPriority(request.getPriority());
        item.setProductLinks(request.getProductLinks());
        item.setOrphanageCareBridgeId(carebridgeId);
        item.setNeedItemId(needItemId);
        needItemRepo.save(item);
        return new NeedItemResponse("Item created Succussfully", needItemId);
    }


    public NeedItemResponse updateNeedItem(NeedItemRequest request, String itemId){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("user not present"));
        OrphanageProfile profile = orphanageProfileRepository.findByUser(user).orElseThrow(()-> new OrphanageProfileNotFoundException("profile not found"));


        NeedItem item = needItemRepo.findByNeedItemId(itemId).orElseThrow(() -> new ItemNotFound("No Such Item found"));

        if(!item.getOrphanageCareBridgeId()
        .equals(profile.getCarebridgeId())){

    throw new CommonException(
            "You are not authorized to update this item"
    );
}

        if(request.getName() != null && !request.getName().isBlank()){
            item.setName(request.getName());
        }


    if(request.getDescription() != null
            && !request.getDescription().isBlank()){

        item.setDescription(
                request.getDescription().trim()
        );
    }



    if(request.getCategory() != null){

        item.setCategory(
                request.getCategory()
        );
    }

    

    if(request.getQuantity() != null
            && request.getQuantity() > 0){

        item.setQuantity(
                request.getQuantity()
        );
    }

  

    if(request.getPricePerQuantity() != null
            && request.getPricePerQuantity()
                    .compareTo(BigDecimal.ZERO) > 0){

        item.setPricePerQuantity(
                request.getPricePerQuantity()
        );
    }

   

    if(request.getPriority() != null){

        item.setPriority(
                request.getPriority()
        );
    }

    

    if(request.getProductLinks() != null
            && !request.getProductLinks().isEmpty()){

        item.setProductLinks(
                request.getProductLinks()
        );
    }

    needItemRepo.save(item);

    return new NeedItemResponse(
            "Need item updated successfully", itemId
    );
    }


    @Transactional
    public NeedItemResponse deleteNeedItem(String itemId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("user not present"));
        OrphanageProfile profile = orphanageProfileRepository.findByUser(user).orElseThrow(()-> new OrphanageProfileNotFoundException("profile not found"));


        NeedItem item = needItemRepo.findByNeedItemId(itemId).orElseThrow(() -> new ItemNotFound("No Such Item found"));

        if(!item.getOrphanageCareBridgeId()
        .equals(profile.getCarebridgeId())){

    throw new CommonException(
            "You are not authorized to delete this item"
    );
}

    DeletedItems deletedItems = new DeletedItems();
    deletedItems.setName(item.getName());
    deletedItems.setCategory(item.getCategory());
    deletedItems.setCreatedAt(item.getCreatedAt());
    deletedItems.setDescription(item.getDescription());
    deletedItems.setFulfilledQuantity(item.getFulfilledQuantity());
    deletedItems.setNeedItemId(itemId);
    deletedItems.setOrphanageCareBridgeId(item.getOrphanageCareBridgeId());
    deletedItems.setPricePerQuantity(item.getPricePerQuantity());
    deletedItems.setPriority(item.getPriority());
    deletedItems.setQuantity(item.getQuantity());
    deletedItems.setProductLinks(item.getProductLinks());
    deletedItems.setUpdatedAt(item.getUpdatedAt());

    deletedItemRepo.save(deletedItems);

    needItemRepo.delete(item);

    return new NeedItemResponse("Item deleted", itemId);
    }


    public List<NeedItem> getAllActiveNeeds(){
         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("user not present"));
        OrphanageProfile profile = orphanageProfileRepository.findByUser(user).orElseThrow(()-> new OrphanageProfileNotFoundException("profile not found"));
        List<NeedItem> activeItems = needItemRepo.getByOrphanageCareBridgeId(profile.getCarebridgeId());
        return activeItems;
    }



    public List<NeedItem> getAllNeeds(){

        List<NeedItem> needs = needItemRepo.findAll();

        return needs;
    }
}


