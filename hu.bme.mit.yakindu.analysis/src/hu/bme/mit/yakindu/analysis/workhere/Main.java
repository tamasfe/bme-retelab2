package hu.bme.mit.yakindu.analysis.workhere;

import java.util.Scanner;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.junit.Test;
import org.yakindu.base.types.Event;
import org.yakindu.base.types.Property;
import org.yakindu.sct.model.sgraph.Scope;
import org.yakindu.sct.model.sgraph.State;
import org.yakindu.sct.model.sgraph.Statechart;
import org.yakindu.sct.model.sgraph.Transition;
import org.yakindu.sct.model.stext.stext.EventDefinition;
import org.yakindu.sct.model.stext.stext.VariableDefinition;

import hu.bme.mit.model2gml.Model2GML;
import hu.bme.mit.yakindu.analysis.RuntimeService;
import hu.bme.mit.yakindu.analysis.TimerService;
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
		
        String varPrintExprs = "";
        String caseArms = "";
		while (iterator.hasNext()) {
			EObject content = iterator.next();
           if (content instanceof EventDefinition) {
				EventDefinition e = (EventDefinition) content;
				String evName = e.getName();
				String evNameTitle = evName.substring(0, 1).toUpperCase() + evName.substring(1);
				caseArms += "case \"" + evName + "\": s.raise" + evNameTitle + "();s.runCycle();break;\n" ;
			} else if (content instanceof VariableDefinition) {
				VariableDefinition v = (VariableDefinition) content;
				String vName = v.getName();
				String vNameTitle = vName.substring(0, 1).toUpperCase() + vName.substring(1);
				varPrintExprs += "System.out.println(\"" + vName + " = \" + s.getSCInterface().get"+vNameTitle+"());\n";
			}
		}
				
		System.out.println("Scanner input = new Scanner(System.in);");
		System.out.println("while (input.hasNext()) {");
		System.out.println("String cmd = input.nextLine().toLowerCase().trim();");
		System.out.println("if (cmd.equals(\"exit\")) {");
		System.out.println("System.exit(0);}String stateName = cmd;");
		System.out.println("switch (stateName) {" + caseArms + "}");
		System.out.println(varPrintExprs);
		System.out.println("}");
		
		// Transforming the model into a graph representation
		String content = model2gml.transform(root);
		// and saving it
		manager.saveFile("model_output/graph.gml", content);
	}
}
