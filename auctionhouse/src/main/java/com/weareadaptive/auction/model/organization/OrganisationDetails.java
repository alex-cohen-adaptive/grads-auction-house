package com.weareadaptive.auction.model.organization;

import com.weareadaptive.auction.model.user.User;
import java.util.List;

public record OrganisationDetails(String organisationName, List<User> users) {
}
