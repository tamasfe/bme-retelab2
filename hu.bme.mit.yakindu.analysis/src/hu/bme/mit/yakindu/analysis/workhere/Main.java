package hu.bme.mit.yakindu.analysis.workhere;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.junit.Test;
import org.yakindu.sct.model.sgraph.State;
import org.yakindu.sct.model.sgraph.Statechart;
import org.yakindu.sct.model.sgraph.Transition;

import hu.bme.mit.model2gml.Model2GML;
import hu.bme.mit.yakindu.analysis.modelmanager.ModelManager;

public class Main {
	@Test
	public void test() {
		main(new String[0]);
	}
	
	public static void main(String[] args) {
		ModelManager manager = new ModelManager();
		Model2GML model2gml = new Model2GML();
		
		// Loading model
		EObject root = manager.loadModel("model_input/example.sct");
		
		// Reading model
		Statechart s = (Statechart) root;
		TreeIterator<EObject> iterator = s.eAllContents();
		
		int unnamedStateIndex = 1;
		while (iterator.hasNext()) {
			EObject content = iterator.next();
			if(content instanceof State) {
				State state = (State) content;
				String stateName = state.getName();
				
				System.out.println(stateName);
				
				if (stateName == null || stateName.trim().length() == 0) {
					stateName = "UnnamedState" + String.valueOf(unnamedStateIndex);
					System.out.println("State has no name! (" + stateName + ")");
					unnamedStateIndex += 1;
				}
				
				if (state.getOutgoingTransitions().size() == 0) {
					System.out.println("no outgoing transitions: " + state.getName());
				}
			} else if (content instanceof Transition) {
				Transition t = (Transition) content;
				System.out.println(t.getSource().getName() + " -> " + t.getTarget().getName());
			}
		}
		
		// Transforming the model into a graph representation
		String content = model2gml.transform(root);
		// and saving it
		manager.saveFile("model_output/graph.gml", content);
	}
}
