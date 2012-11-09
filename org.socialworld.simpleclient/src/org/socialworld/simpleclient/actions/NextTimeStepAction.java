package org.socialworld.simpleclient.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.socialworld.SocialWorld;
import org.socialworld.core.Simulation;

public class NextTimeStepAction implements IWorkbenchWindowActionDelegate {

	@Override
	public void dispose() {
		//  Auto-generated method stub

	}

	@Override
	public void init(IWorkbenchWindow window) {
		//  Auto-generated method stub

	}

	@Override
	public void run(IAction action) {
		Simulation simulation = SocialWorld.getCurrent().getSimulation();
		simulation.nextTimeStep();
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		//  Auto-generated method stub

	}

}
