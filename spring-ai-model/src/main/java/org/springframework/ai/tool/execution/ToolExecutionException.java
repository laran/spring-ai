/*
 * Copyright 2023-present the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.ai.tool.execution;

import org.springframework.ai.tool.definition.ToolDefinition;
import org.springframework.lang.Nullable;

/**
 * An exception thrown when a tool execution fails.
 *
 * @author Thomas Vitale
 * @since 1.0.0
 */
public class ToolExecutionException extends RuntimeException {

	private final ToolDefinition toolDefinition;

	@Nullable
	private final String callerAgentId;

	public ToolExecutionException(ToolDefinition toolDefinition, Throwable cause) {
		this(toolDefinition, null, cause);
	}

	/**
	 * Creates a new {@code ToolExecutionException} with caller agent identity context.
	 * The {@code callerAgentId} is used in multi-agent deployments to attribute
	 * null-argument or other tool-call failures to a specific calling agent, enabling
	 * per-agent observability without a separate identity-propagation layer.
	 * @param toolDefinition the definition of the tool that failed
	 * @param callerAgentId the identity of the agent that issued the tool call, or
	 * {@code null} if not available
	 * @param cause the underlying cause
	 */
	public ToolExecutionException(ToolDefinition toolDefinition, @Nullable String callerAgentId, Throwable cause) {
		super(cause.getMessage(), cause);
		this.toolDefinition = toolDefinition;
		this.callerAgentId = callerAgentId;
	}

	public ToolDefinition getToolDefinition() {
		return this.toolDefinition;
	}

	/**
	 * Returns the identity of the agent that issued the tool call, or {@code null} if not
	 * available. Populated from {@link org.springframework.ai.chat.model.ToolContext}
	 * when {@link org.springframework.ai.chat.model.ToolContext#TOOL_CALLER_AGENT_ID} is
	 * present.
	 */
	@Nullable
	public String getCallerAgentId() {
		return this.callerAgentId;
	}

}
