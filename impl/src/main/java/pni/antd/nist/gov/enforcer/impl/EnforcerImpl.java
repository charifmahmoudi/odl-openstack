/*
 * Copyright © 2017 MIT and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package pni.antd.nist.gov.enforcer.impl;

import java.util.concurrent.Future;

import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.service.rev130819.RemoveFlowInputBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.service.rev130819.SalFlowService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.enforcer.rev150105.EnforceInput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.enforcer.rev150105.EnforceOutput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.enforcer.rev150105.EnforceOutputBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.enforcer.rev150105.EnforcerService;
import org.opendaylight.yangtools.yang.common.RpcResult;
import org.opendaylight.yangtools.yang.common.RpcResultBuilder;

/**
 * @author Charif
 *
 */
public class EnforcerImpl implements EnforcerService {

	SalFlowService flowService;
	
	public EnforcerImpl(SalFlowService flowService) {
		this.flowService = flowService;
	}

	/* (non-Javadoc)
	 * @see org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.enforcer.rev150105.EnforcerService#enforce(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.enforcer.rev150105.EnforceInput)
	 */
	@Override
	public Future<RpcResult<EnforceOutput>> enforce(EnforceInput input) {
		
		RemoveFlowInputBuilder flowBuilder = new RemoveFlowInputBuilder();
        flowBuilder.setBarrier(true);

        //flowBuilder.getnNode();
        //RPC to SAL Flow Service in OpenFlowPlugin
        flowService.removeFlow(flowBuilder.build());
		
		
		EnforceOutputBuilder builder = new EnforceOutputBuilder();
		builder.setStatus("Status of " + input.getSource());
		return RpcResultBuilder.success(builder.build()).buildFuture();
		
	}

}
