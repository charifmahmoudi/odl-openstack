/*
 * Copyright © 2017 MIT and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package pni.antd.nist.gov.enforcer.impl;

import java.util.Collection;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.DataTreeChangeListener;
import org.opendaylight.controller.md.sal.binding.api.DataTreeIdentifier;
import org.opendaylight.controller.md.sal.binding.api.DataTreeModification;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.controller.sal.binding.api.BindingAwareBroker.RpcRegistration;
import org.opendaylight.controller.sal.binding.api.RpcProviderRegistry;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.FlowCapableNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.service.rev130819.SalFlowService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.Nodes;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.nodes.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.enforcer.rev150105.EnforcerService;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnforcerProvider  implements DataTreeChangeListener<FlowCapableNode>{

	private static final Logger LOG = LoggerFactory.getLogger(EnforcerProvider.class);

	private final DataBroker dataBroker;
	private final RpcProviderRegistry rpcProviderRegistry;
	private RpcRegistration<EnforcerService> serviceRegistration;
	private SalFlowService flowService;
	//private InventoryTopologyProvider;

	public EnforcerProvider(final DataBroker dataBroker, RpcProviderRegistry rpcProviderRegistry) {
		this.dataBroker = dataBroker;
		this.rpcProviderRegistry = rpcProviderRegistry;

	}
	/**
	 * Method called when the blueprint container is created.
	 */

	public void init() {
		flowService = rpcProviderRegistry.getRpcService(SalFlowService.class);
		serviceRegistration = rpcProviderRegistry.addRpcImplementation(EnforcerService.class, new EnforcerImpl(flowService));
		//notificationProvider.

		DataTreeIdentifier<FlowCapableNode> nodes = new DataTreeIdentifier<FlowCapableNode>(
				LogicalDatastoreType.OPERATIONAL, getWildCardPath());
		dataBroker.registerDataTreeChangeListener(nodes, this);
		//ChangeListener(LogicalDatastoreType.OPERATIONAL, nodes , this, DataChangeScope.SUBTREE );
		//LogicalDatastoreType.CONFIGURATION , nodes , this, DataChangeScope.SUBTREE );
		LOG.info("EnforcerProvider Session Initiated");
	}
	private InstanceIdentifier<FlowCapableNode> getWildCardPath() {
//
//		FlowCapableNodeBuilder builder = new FlowCapableNodeBuilder();
//		NodeBuilder nodeBuilder = new NodeBuilder();
//		nodeBuilder.addAugmentation(FlowCapableNode.class, builder.build());




		return InstanceIdentifier.builder(Nodes.class)
				.child(Node.class).augmentation(FlowCapableNode.class)
				.build();

	}

	/**
	 * Method called when the blueprint container is destroyed.
	 */
	public void close() {
		serviceRegistration.close();
		LOG.info("EnforcerProvider Closed");
	}

	@Override
	public void onDataTreeChanged(Collection<DataTreeModification<FlowCapableNode>> changes) {
		// TODO Auto-generated method stub
		for (DataTreeModification<FlowCapableNode> node : changes) {
			LOG.info("Node detected" + node.getRootPath().toString());
		}

	}
}