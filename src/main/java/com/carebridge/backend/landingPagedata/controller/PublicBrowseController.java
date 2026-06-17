package com.carebridge.backend.landingPagedata.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carebridge.backend.landingPagedata.dto.OrphanageDetailResponse;
import com.carebridge.backend.landingPagedata.dto.OrphanageSummaryResponse;
import com.carebridge.backend.landingPagedata.dto.NeedSummaryResponse;
import com.carebridge.backend.needsManagement.entity.NeedItem;
import com.carebridge.backend.needsManagement.repository.NeedItemRepo;
import com.carebridge.backend.needsManagement.service.NeedItemService;
import com.carebridge.backend.orphanageManagement.entity.OrphanageProfile;
import com.carebridge.backend.orphanageManagement.exception.OrphanageProfileNotFoundException;
import com.carebridge.backend.orphanageManagement.repository.OrphanageProfileRepository;
import com.carebridge.backend.common.enums.VerificationStatus;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PublicBrowseController {

    private final OrphanageProfileRepository orphanageProfileRepository;
    private final NeedItemRepo needItemRepo;
    private final NeedItemService needItemService;

    @GetMapping("/orphanages")
    public ResponseEntity<List<OrphanageSummaryResponse>> getAllOrphanages() {
        List<OrphanageProfile> orphanages = orphanageProfileRepository.findByVerificationStatus(VerificationStatus.VERIFIED);
        List<OrphanageSummaryResponse> response = orphanages.stream()
                .map(this::toOrphanageSummary)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/orphanages/{id}")
    public ResponseEntity<OrphanageDetailResponse> getOrphanageById(@PathVariable String id) {
        OrphanageProfile orphanage = orphanageProfileRepository.findByCarebridgeId(id)
                .orElseThrow(() -> new OrphanageProfileNotFoundException("Orphanage not found"));
        return ResponseEntity.ok(toOrphanageDetail(orphanage));
    }

    @GetMapping("/needs")
    public ResponseEntity<List<NeedSummaryResponse>> getAllNeeds() {
        List<NeedItem> needs = needItemService.getAllNeeds();
        List<NeedSummaryResponse> response = needs.stream()
                .map(this::toNeedSummary)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/needs/{id}")
    public ResponseEntity<NeedSummaryResponse> getNeedById(@PathVariable String id) {
        NeedItem needItem = needItemRepo.findByNeedItemId(id)
                .orElseThrow(() -> new RuntimeException("Need item not found"));
        return ResponseEntity.ok(toNeedSummary(needItem));
    }

    private OrphanageSummaryResponse toOrphanageSummary(OrphanageProfile orphanage) {
        String location = buildLocation(orphanage);
        int activeNeeds = needItemRepo.getByOrphanageCareBridgeId(orphanage.getCarebridgeId()).size();
        return new OrphanageSummaryResponse(
                orphanage.getCarebridgeId(),
                orphanage.getOrphanageName(),
                location,
                orphanage.getNumberOfChildren(),
                activeNeeds,
                orphanage.getOrphanageProfilePic(),
                orphanage.getVerificationStatus().name(),
                "Supporting children in " + location
        );
    }

    private OrphanageDetailResponse toOrphanageDetail(OrphanageProfile orphanage) {
        String location = buildLocation(orphanage);
        List<NeedSummaryResponse> needs = needItemRepo.getByOrphanageCareBridgeId(orphanage.getCarebridgeId()).stream()
                .map(this::toNeedSummary)
                .collect(Collectors.toList());

        return new OrphanageDetailResponse(
                orphanage.getCarebridgeId(),
                orphanage.getOrphanageName(),
                orphanage.getAdminName(),
                orphanage.getVillage(),
                orphanage.getMandal(),
                orphanage.getDistrict(),
                orphanage.getState(),
                orphanage.getCountry(),
                orphanage.getNumberOfChildren(),
                orphanage.getOrphanageEmail(),
                orphanage.isOrphanageEmailVerified(),
                orphanage.getOrphanagePhone(),
                orphanage.getWebsiteLink(),
                orphanage.getSocialMediaLinks(),
                orphanage.getDarpanId(),
                orphanage.getPanNumber(),
                orphanage.getPanPhoto(),
                orphanage.getJjActCertificatePhoto(),
                orphanage.getOrphanageProfilePic(),
                orphanage.getAdminProfilePic(),
                orphanage.getVerificationStatus().name(),
                location,
                "A verified orphanage trusted by CareBridge.",
                needs.size(),
                needs
        );
    }

    private NeedSummaryResponse toNeedSummary(NeedItem needItem) {
        String orphanageName = "Unknown Orphanage";
        String location = "Unknown Location";
        String profilePic = null;

        orphanageProfileRepository.findByCarebridgeId(needItem.getOrphanageCareBridgeId()).ifPresent(orphanage -> {
            // nothing here; build values below through lambda
        });

        if (needItem.getOrphanageCareBridgeId() != null) {
            var orphanage = orphanageProfileRepository.findByCarebridgeId(needItem.getOrphanageCareBridgeId());
            if (orphanage.isPresent()) {
                orphanageName = orphanage.get().getOrphanageName();
                location = buildLocation(orphanage.get());
                profilePic = orphanage.get().getOrphanageProfilePic();
            }
        }

        int remaining = 0;
        int progress = 0;
        if (needItem.getQuantity() != null) {
            remaining = Math.max(0, needItem.getQuantity() - needItem.getFulfilledQuantity());
            progress = Math.min(100,
                    Math.round(needItem.getFulfilledQuantity() * 100f / needItem.getQuantity()));
        }

        return new NeedSummaryResponse(
                needItem.getNeedItemId(),
                needItem.getName(),
                needItem.getDescription(),
                needItem.getCategory() == null ? null : needItem.getCategory().name(),
                needItem.getQuantity(),
                needItem.getFulfilledQuantity(),
                needItem.getReservedQuantity(),
                needItem.getPriority() == null ? null : needItem.getPriority().name(),
                needItem.getPricePerQuantity(),
                needItem.getOrphanageCareBridgeId(),
                orphanageName,
                location,
                profilePic,
                remaining,
                progress
        );
    }

    private String buildLocation(OrphanageProfile orphanage) {
        StringBuilder location = new StringBuilder();
        if (orphanage.getDistrict() != null) {
            location.append(orphanage.getDistrict());
        }
        if (orphanage.getState() != null) {
            if (location.length() > 0) {
                location.append(", ");
            }
            location.append(orphanage.getState());
        }
        return location.toString();
    }
}
