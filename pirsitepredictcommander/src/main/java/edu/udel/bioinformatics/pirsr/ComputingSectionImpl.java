package edu.udel.bioinformatics.pirsr;

import java.util.ArrayList;
import java.util.List;

import org.proteininformationresource.pirsr.model.ComputingSection;
import org.proteininformationresource.pirsr.model.ComputingSectionVisitor;
import org.proteininformationresource.pirsr.model.ControlBlock;
import org.proteininformationresource.pirsr.model.DuplicateLine;
import org.proteininformationresource.pirsr.model.FusionBlock;
import org.proteininformationresource.pirsr.model.Line;
import org.proteininformationresource.pirsr.model.PlasmidLine;
import org.proteininformationresource.pirsr.model.RelatedLine;
import org.proteininformationresource.pirsr.model.ScopeBlock;
import org.proteininformationresource.pirsr.model.SizeLine;
import org.proteininformationresource.pirsr.model.TemplateLine;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: July 16, 2014<br>
 * <br>
 * 
 * The Computing section contrasts with the Annotation section in that the line
 * identifiers are no longer restricted to 2 letters. The information starts in
 * the line of the line identifier, the following lines are indented by 1 space.
 * 
 */
public class ComputingSectionImpl implements ComputingSection {

	private List<ControlBlock> controlBlocks;
	private List<Line> lines;
	private List<Line> nonControlBlockLines;
	private SizeLine sizeLine;
	private RelatedLine relatedLine;
	private TemplateLine templateLine;
	private DuplicateLine duplicateLine;
	private PlasmidLine plasmidLine;
	private ScopeBlock scopeBlock;
	private FusionBlock fusionBlock;

	public ComputingSectionImpl() {
		super();
		this.controlBlocks = new ArrayList<ControlBlock>();
		this.lines = new ArrayList<Line>();
		this.nonControlBlockLines = new ArrayList<Line>();
	}

	
	public List<ControlBlock> getControlBlocks() {
		return this.controlBlocks;
	}

	
	public void setControlBlocks(List<ControlBlock> controlBlocks) {
		this.controlBlocks = controlBlocks;
	}

	
	public List<Line> getLines() {
		return this.lines;
	}

	
	public void setLines(List<Line> lines) {
		this.lines = lines;
	}

	
	public SizeLine getSizeLine() {
		return this.sizeLine;
	}

	
	public void setSizeLine(SizeLine sizeLine) {
		this.sizeLine = sizeLine;
	}

	
	public RelatedLine getRelatedLine() {
		return this.relatedLine;
	}

	
	public void setRelatedLine(RelatedLine relatedLine) {
		this.relatedLine = relatedLine;
	}

	
	public TemplateLine getTemplateLine() {
		return this.templateLine;
	}

	
	public void setTemplateLine(TemplateLine templateLine) {
		this.templateLine = templateLine;
	}

	
	public DuplicateLine getDuplicateLine() {
		return this.duplicateLine;
	}

	
	public void setDuplicateLine(DuplicateLine duplicateLine) {
		this.duplicateLine = duplicateLine;
	}

	
	public PlasmidLine getPlasmidLine() {
		return this.plasmidLine;
	}

	
	public void setPlasmidLine(PlasmidLine plasmidLine) {
		this.plasmidLine = plasmidLine;
	}

	
	public ScopeBlock getScopeBlock() {
		return this.scopeBlock;
	}

	
	public void setScopeBlock(ScopeBlock scopeBlock) {
		this.scopeBlock = scopeBlock;
	}

	
	public FusionBlock getFusionBlock() {
		return this.fusionBlock;
	}

	
	public void setFusionBlock(FusionBlock fusionBlock) {
		this.fusionBlock = fusionBlock;
	}

	
	public void accept(ComputingSectionVisitor visitor) {
		visitor.visit(this.lines);
	}

	
	public Line getLastLine() {
		if (this.lines != null && this.lines.size() > 0) {
			return this.lines.get(this.lines.size() - 1);
		}
		return null;
	}

	
	public void setLastLine(Line lastLine) {
		this.lines.set(this.lines.size() - 1, lastLine);
	}

	
	public List<Line> getNonControlBlockLines() {
		return this.nonControlBlockLines;
	}

	
	public void setNonControlBlockLines(List<Line> nonControlBlockLines) {
		this.nonControlBlockLines = nonControlBlockLines;
	}

	
	public Line getLastNonControlBlockLine() {
		if (this.nonControlBlockLines != null && this.nonControlBlockLines.size() > 0) {
			return this.nonControlBlockLines.get(this.nonControlBlockLines.size() - 1);
		}
		return null;
	}

	
	public void setLastNonControlBlockLine(Line lastNonControlBlockLine) {
		if (this.nonControlBlockLines.size() == 0) {
			this.nonControlBlockLines.add(lastNonControlBlockLine);
		} else {
			this.nonControlBlockLines.set(this.nonControlBlockLines.size() - 1, lastNonControlBlockLine);
		}
	}

}
