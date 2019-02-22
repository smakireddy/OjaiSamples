/**
 * Copyright (c) 2017 MapR, Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.mapr.ojai.examples;

import org.ojai.Document;
import org.ojai.DocumentStream;
import org.ojai.store.Connection;
import org.ojai.store.DocumentStore;
import org.ojai.store.DriverManager;
import org.ojai.store.Query;
import org.ojai.store.QueryCondition.Op;

public class OJAI_007_FindQueryWithCondition {

    public static void main(final String[] args) {

        System.out.println("==== Start Application ===");

        // Create an OJAI connection to MapR cluster
        final Connection connection = DriverManager.getConnection("ojai:mapr:");

        // Get an instance of OJAI DocumentStore
        final DocumentStore store = connection.getStore("/user/som/demo_table");

        // Build an OJAI query with QueryCondition
        final Query query = connection.newQuery()
//                .select("_id", "address.zipCode")
                .where(
                        connection.newCondition()                   //
                                .is("address.zipCode", Op.EQUAL, 95196)   // Build an OJAI QueryCondition
                                .build())                                 //
                .build();

        // fetch all OJAI Documents from this store
        final DocumentStream stream = store.find(query);

        for (final Document userDocument : stream) {
            // Print the OJAI Document
            System.out.println(userDocument.asJsonString());
        }

        // Close this instance of OJAI DocumentStore
        store.close();

        // close the OJAI connection and release any resources held by the connection
        connection.close();

        System.out.println("==== End Application ===");
    }

}
