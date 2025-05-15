package edu.udel.bioinformatics.pirsr.test.propagation;



import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.proteininformationresource.pirsr.model.PIRRuleDataFactory;
import org.proteininformationresource.pirsr.model.PIRRuleManager;
import org.proteininformationresource.pirsr.propagation.Alignment;
import org.proteininformationresource.pirsr.propagation.PropagationDataFactory;
import org.proteininformationresource.pirsr.propagation.PropagationManager;

import edu.udel.bioinformatics.pirsr.PIRRuleDataFactoryImpl;
import edu.udel.bioinformatics.pirsr.propagation.AlignmentImpl;
import edu.udel.bioinformatics.pirsr.propagation.PropagationDataFactoryImpl;
import edu.udel.bioinformatics.pirsr.propagation.PropagationManagerImpl;
import edu.udel.bioinformatics.pirsr.propagation.PropagationUtil;

public class AlignmentTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		// fail("Not yet implemented");
		PropagationDataFactory propagationDataFactory = PropagationDataFactoryImpl.getInstance();
		PIRRuleDataFactory pirRuleDataFactory = PIRRuleDataFactoryImpl.getInstance();
		PIRRuleManager pirRuleManager = pirRuleDataFactory.buildPIRRuleManager(pirRuleDataFactory);
		
		PropagationManager annotationManager = new PropagationManagerImpl(propagationDataFactory, pirRuleManager);
		//List<Alignment> alignments = annotationManager.loadAlignment(new File("/Users/chenc/Documents/2014/Work/SR/SRHMM.gff"));
		List<Alignment> alignments = annotationManager.loadAlignment(new File("/Users/chenc/Documents/2014/Work/SR/test.gff"));
		
		for(int i = 0; i < alignments.size(); i++) {
			Alignment alignment = alignments.get(i);
			System.out.println(alignment.getEntryId()+"\t" + alignment.getRuleAC() + "\t"+ alignment.getTemplateEntryAC());
			System.out.println(alignment.getAlignedTemplateSeq()+"\n");
			//System.out.println(AlignmentUtil.getTemplateResidues(alignment, "25", "Cter"));
			
			String start = PropagationUtil.convertTemplatePositionToTargetPosition(alignment, "158");
			String end = PropagationUtil.convertTemplatePositionToTargetPosition(alignment, "158");
			System.out.println("Aligned Seq " + start +" "+end +" ?");
			//System.out.println(alignment.getAlignedSeq().substring(Integer.parseInt(start), Integer.parseInt(end)));
			System.out.println(PropagationUtil.getTemplateResidues(alignment, "158", "158"));
			System.out.println(alignment.getAlignedSeq()+"\n");
			System.out.println(PropagationUtil.getAlignedEntryResidues(alignment, "157", "157"));
		//	 start = PropagationUtil.convertTargetPositionToTemplatePosition(alignment, "157");
		//	end = PropagationUtil.convertTargetPositionToTemplatePosition(alignment, "157");
			System.out.println("Template Seq " + start +" "+end +" ?");
//			System.out.println(AlignmentUtil.getAlignedEntryResidues(alignment, "25", "Cter"));
			System.out.println(PropagationUtil.checkPatternMatch(PropagationUtil.getAlignedEntryResidues(alignment, "147", "147"),"Y"));
//			System.out.println(AlignmentUtil.getAlignedEntryResidues(alignment, "Nter", "24"));
			//System.out.println(AlignmentUtil.getAlignedEntryResiduePosition(alignment, "25"));
			//System.out.println(AlignmentUtil.getAlignedEntryResiduePosition(alignment, "Cter"));
			//S-x(100)-A
			
		}
	}

}
