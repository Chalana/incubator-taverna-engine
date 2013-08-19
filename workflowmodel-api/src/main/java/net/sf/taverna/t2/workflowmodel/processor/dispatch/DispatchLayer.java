/*******************************************************************************
 * Copyright (C) 2007 The University of Manchester   
 * 
 *  Modifications to the initial code base are copyright of their
 *  respective authors, or their employers as appropriate.
 * 
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public License
 *  as published by the Free Software Foundation; either version 2.1 of
 *  the License, or (at your option) any later version.
 *    
 *  This program is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *    
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 ******************************************************************************/
package net.sf.taverna.t2.workflowmodel.processor.dispatch;

import net.sf.taverna.t2.workflowmodel.Configurable;
import net.sf.taverna.t2.workflowmodel.WorkflowItem;
import net.sf.taverna.t2.workflowmodel.processor.dispatch.events.DispatchCompletionEvent;
import net.sf.taverna.t2.workflowmodel.processor.dispatch.events.DispatchErrorEvent;
import net.sf.taverna.t2.workflowmodel.processor.dispatch.events.DispatchJobEvent;
import net.sf.taverna.t2.workflowmodel.processor.dispatch.events.DispatchJobQueueEvent;
import net.sf.taverna.t2.workflowmodel.processor.dispatch.events.DispatchResultEvent;

/**
 * Layers within the dispatch stack define a control flow to handle dispatch of
 * jobs from a queue (generated by the iteration system) to appropriate
 * activities.
 * <p>
 * A dispatch layer can receive a reference to the Queue and a set of
 * Activities, or a single Job and a set of Activities from the layer above it
 * (or from the DispatchStackImpl object itself if this is the top layer). It
 * can receive errors, results and partial or total completion events from the
 * layer immediately below it.
 * <p>
 * To assist in graphical representation of the dispatch configuration each
 * layer declares for each class of message whether it intercepts and alters,
 * intercepts and observes or ignores (forwards) the message onto the next layer
 * (either up or down depending on the message) and whether the layer is capable
 * of instigating the creation of each class of message.
 * 
 * @author Tom Oinn
 * 
 */
public interface DispatchLayer<ConfigurationType> extends Configurable<ConfigurationType> {

	/**
	 * Receive a pointer to the job queue along with a set of activities, this
	 * is received from the layer above in the dispatch stack or from the
	 * DispatchStackImpl object itself if this is the top layer
	 */
	public void receiveJobQueue(DispatchJobQueueEvent queueEvent);

	/**
	 * Receive a single job and associated set of activities from the layer
	 * above
	 */
	public void receiveJob(DispatchJobEvent jobEvent);

	/**
	 * Receive a single error reference from the layer below
	 */
	public void receiveError(DispatchErrorEvent errorEvent);

	/**
	 * Receive a result from the layer below
	 */
	public void receiveResult(DispatchResultEvent resultEvent);

	/**
	 * Receive a (possibly partial) completion event from the layer below. This
	 * is only going to be used when the activities invocation is capable of
	 * streaming partial data back up through the dispatch stack before the
	 * activities has completed. Not all dispatch stack layers are compatible
	 * with this mode of operation, for example retry and recursion do not play
	 * well here!
	 * 
	 */
	public void receiveResultCompletion(DispatchCompletionEvent completionEvent);

	/**
	 * Called when there will be no more events with the specified process
	 * identifier, can be used to purge cached state from layers within the
	 * stack
	 */
	public void finishedWith(String owningProcess);

	/**
	 * Set the parent dispatch stack of this layer, this is called when a layer
	 * is added to the dispatch stack and can be safely ignored by end users of
	 * this API
	 */
	public void setDispatchStack(DispatchStack stack);
	
}
