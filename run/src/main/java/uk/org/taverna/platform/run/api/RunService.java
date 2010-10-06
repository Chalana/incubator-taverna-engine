package uk.org.taverna.platform.run.api;

import java.util.List;
import java.util.Map;

import net.sf.taverna.t2.reference.ReferenceService;
import net.sf.taverna.t2.reference.T2Reference;
import uk.org.taverna.platform.execution.api.InvalidExecutionIdException;
import uk.org.taverna.platform.execution.api.InvalidWorkflowException;
import uk.org.taverna.platform.report.State;
import uk.org.taverna.platform.report.WorkflowReport;
import uk.org.taverna.scufl2.api.core.Workflow;
import uk.org.taverna.scufl2.api.profiles.Profile;

public interface RunService {

	public List<String> getRuns();

	/**
	 * Creates a new run and returns the ID for the run.
	 * 
	 * To start the run use the {@link #start(String)} method.
	 * 
	 * @param workflow
	 *            the workflow to run
	 * @param inputs
	 *            the workflow inputs
	 * @return the run ID
	 * @throws InvalidWorkflowException 
	 */
	public String createRun(Workflow workflow, Profile profile, Map<String, T2Reference> inputs,
			ReferenceService referenceService) throws InvalidWorkflowException;

	/**
	 * Starts a run.
	 * 
	 * @param runID
	 *            the ID of the run
	 * @throws InvalidRunIdException
	 *             if the run ID is not valid
	 * @throws RunStateException
	 *             if the run state is not CREATED
	 * @throws InvalidExecutionIdException 
	 */
	public void start(String runID) throws InvalidRunIdException, RunStateException, InvalidExecutionIdException;

	/**
	 * Pauses a running run.
	 * 
	 * @param runID
	 *            the ID of the run
	 * @throws InvalidRunIdException
	 *             if the run ID is not valid
	 * @throws RunStateException
	 *             if the run state is not RUNNING
	 * @throws InvalidExecutionIdException 
	 */
	public void pause(String runID) throws InvalidRunIdException, RunStateException, InvalidExecutionIdException;

	/**
	 * Resumes a paused run.
	 * 
	 * @param runID
	 *            the ID of the run
	 * @throws InvalidRunIdException
	 *             if the run ID is not valid
	 * @throws RunStateException
	 *             if the run state is not PAUSED
	 * @throws InvalidExecutionIdException 
	 */
	public void resume(String runID) throws InvalidRunIdException, RunStateException, InvalidExecutionIdException;

	/**
	 * Cancels a running or paused run.
	 * 
	 * @param runID
	 *            the ID of the run
	 * @throws InvalidRunIdException
	 *             if the run ID is not valid
	 * @throws RunStateException
	 *             if the run state is not RUNNING or PAUSED
	 * @throws InvalidExecutionIdException 
	 */
	public void cancel(String runID) throws InvalidRunIdException, RunStateException, InvalidExecutionIdException;

	/**
	 * Returns the current state of the run. A run's state can be CREATED,
	 * RUNNING, COMPLETED, PAUSED or CANCELLED.
	 * 
	 * @param runID
	 *            the ID of the run
	 * @return the current state of the run
	 * @throws InvalidRunIdException
	 *             if the run ID is not valid
	 */
	public State getState(String runID) throws InvalidRunIdException;

	public Map<String, T2Reference> getInputs(String runID) throws InvalidRunIdException;

	public Map<String, T2Reference> getOutputs(String runID) throws InvalidRunIdException;

	/**
	 * Returns the status report for the run.
	 * 
	 * @param runID
	 *            the ID of the run
	 * @return the status report for the run
	 * @throws InvalidRunIdException
	 *             if the run ID is not valid
	 */
	public WorkflowReport getWorkflowReport(String runID) throws InvalidRunIdException;

}