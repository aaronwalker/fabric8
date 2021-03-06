/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.fabric8.zookeeper.commands;

import io.fabric8.boot.commands.support.AbstractCommandComponent;
import io.fabric8.commands.support.ZNodeCompleter;
import io.fabric8.zookeeper.curator.CuratorFrameworkLocator;
import org.apache.curator.framework.CuratorFramework;
import org.apache.felix.gogo.commands.Action;
import org.apache.felix.gogo.commands.basic.AbstractCommand;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.felix.service.command.Function;

@Component(immediate = true)
@Service({Function.class, AbstractCommand.class})
@org.apache.felix.scr.annotations.Properties({
        @Property(name = "osgi.command.scope", value = Get.SCOPE_VALUE),
        @Property(name = "osgi.command.function", value = Get.FUNCTION_VALUE)
})
public final class Get extends AbstractCommandComponent {

    public static final String SCOPE_VALUE = "zk";
    public static final String FUNCTION_VALUE = "get";
    public static final String DESCRIPTION = "Get a znode's data";

    // Completers
    @Reference(referenceInterface = ZNodeCompleter.class, bind = "bindZnodeCompleter", unbind = "unbindZnodeCompleter")
    private ZNodeCompleter zNodeCompleter; // dummy field

    @Activate
    void activate() {
        activateComponent();
    }

    @Deactivate
    void deactivate() {
        deactivateComponent();
    }

    @Override
    public Action createNewAction() {
        assertValid();
        // this is how we get hold of the curator framework
        CuratorFramework curator = CuratorFrameworkLocator.getCuratorFramework();
        return new GetAction(curator);
    }

    void bindZnodeCompleter(ZNodeCompleter completer) {
        bindCompleter(completer);
    }

    void unbindZnodeCompleter(ZNodeCompleter completer) {
        unbindCompleter(completer);
    }


}
