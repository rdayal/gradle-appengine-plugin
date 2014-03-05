/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.appengine.task.endpoints

import org.gradle.api.Incubating
import org.gradle.api.tasks.OutputDirectory

/**
 * Endpoints task to download Discovery Document from the Endpoints service
 *
 * @author Appu Goundan
 */
@Incubating
class GetDiscoveryDocsTask extends EndpointsTask {

    @OutputDirectory File discoveryDocDirectory
    List<String> discoveryDocFormat

    @Override
    void executeTask() {
        cleanOutputDirectory()
        logger.info "Generating Discovery Docs in formats : ${getDiscoveryDocFormat()}"
        if (getDiscoveryDocFormat()) {
            getDiscoveryDocFormat().each { format ->
                List<String> extras = []
                extras << "-o" << getDiscoveryDocDirectory().canonicalPath
                extras << "-f" << format
                runEndpointsCommand("get-discovery-doc", extras)
            }
        }
    }

    void cleanOutputDirectory() {
        ant.delete(includeemptydirs: true, failonerror: false) {
            fileset(dir: getDiscoveryDocDirectory(), includes: "**/*")
        }
    }
}