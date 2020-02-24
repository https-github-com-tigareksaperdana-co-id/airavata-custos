/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package org.apache.custos.user.profile.persistance.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * User profile entity model
 */
@Entity
@Table(name = "user_profile_entity")
@EntityListeners(AuditingEntityListener.class)
public class UserProfileEntity {

    @Id
    private String id;

    @Column(nullable = false)
    private Long tenantId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String emailAddress;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;


    @OneToMany(mappedBy = "userProfileEntity", cascade = CascadeType.ALL)
    private Set<AttributeUpdateMetadata> attributeUpdateMetadata;

    @OneToMany(mappedBy = "userProfileEntity", cascade = CascadeType.ALL)
    private Set<StatusUpdateMetadata> statusUpdateMetadata;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }


    public Set<AttributeUpdateMetadata> getAttributeUpdateMetadata() {
        return attributeUpdateMetadata;
    }

    public void setAttributeUpdateMetadata(Set<AttributeUpdateMetadata> attributeUpdateMetadata) {
        this.attributeUpdateMetadata = attributeUpdateMetadata;
    }

    public Set<StatusUpdateMetadata> getStatusUpdateMetadata() {
        return statusUpdateMetadata;
    }

    public void setStatusUpdateMetadata(Set<StatusUpdateMetadata> statusUpdateMetadata) {
        this.statusUpdateMetadata = statusUpdateMetadata;
    }
}