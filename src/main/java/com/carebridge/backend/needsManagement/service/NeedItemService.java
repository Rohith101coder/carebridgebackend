package com.carebridge.backend.needsManagement.service;

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
import com.carebridge.backend.needsManagement.entity.NeedItem;
import com.carebridge.backend.needsManagement.exception.ItemAreadyExsist;
import com.carebridge.backend.needsManagement.exception.OrpNotVerified;
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
        item.setPricePerQuantity(request.getPricePerQuantity());
        item.setPriority(request.getPriority());
        item.setProductLinks(request.getProductLinks());
        item.setOrphanageCareBridgeId(carebridgeId);
        item.setNeedItemId(needItemId);
        needItemRepo.save(item);
        return new NeedItemResponse("Item created Succussfully", needItemId);
    }
}
