package com.weareadaptive.auction.repository;

import com.weareadaptive.auction.model.organization.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Integer> {
}
