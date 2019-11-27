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

package org.apache.custos.iam.service;
import io.grpc.stub.StreamObserver;
import org.apache.custos.iam.service.IamAdminServiceGrpc.IamAdminServiceImplBase;
import org.apache.custos.iam.service.AddUserResponse;
import org.apache.custos.iam.service.User;
import org.lognet.springboot.grpc.GRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GRpcService
public class IamAdminService extends IamAdminServiceImplBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(IamAdminService.class);

    @Override
    public void addUser(User request, StreamObserver responseObserver) {
        LOGGER.info("User added");
        responseObserver.onNext(AddUserResponse.newBuilder().setCode("Success").build());
        responseObserver.onCompleted();
    }
}
