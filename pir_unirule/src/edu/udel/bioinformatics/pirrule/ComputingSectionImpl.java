package edu.udel.bioinformatics.pirrule;

import java.util.ArrayList;
import java.util.List;

import org.proteininformationresource.pirrule.model.ComputingSection;
import org.proteininformationresource.pirrule.model.ComputingSectionVisitor;
import org.proteininformationresource.pirrule.model.ControlBlock;
import org.proteininformationresource.pirrule.model.DuplicateLine;
import org.proteininformationresource.pirrule.model.FusionBlock;
import org.proteininformationresource.pirrule.model.Line;
import org.proteininformationresource.pirrule.model.PlasmidLine;
import org.proteininformationresource.pirrule.model.RelatedLine;
import org.proteininformationresource.pirrule.model.ScopeBlock;
import org.proteininformationresource.pirrule.model.SizeLine;
import org.proteininformationresource.pirrule.model.TemplateLine;

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

	@Override
	public List<ControlBlock> getControlBlocks() {
		return this.controlBlocks;
	}

	@Override
	public void setControlBlocks(List<ControlBlock> controlBlocks) {
		this.controlBlocks = controlBlocks;
	}

	@Override
	public List<Line> getLines() {
		return this.lines;
	}

	@Override
	public void setLines(List<Line> lines) {
		this.lines = lines;
	}

	@Override
	public SizeLine getSizeLine() {
		return this.sizeLine;
	}

	@Override
	public void setSizeLine(SizeLine sizeLine) {
		this.sizeLine = sizeLine;
	}

	@Override
	public RelatedLine getRelatedLine() {
		return this.relatedLine;
	}

	@Override
	public void setRelatedLine(RelatedLine relatedLine) {
		this.relatedLine = relatedLine;
	}

	@Override
	public TemplateLine getTemplateLine() {
		return this.templateLine;
	}

	@Override
	public void setTemplateLine(TemplateLine templateLine) {
		this.templateLine = templateLine;
	}

	@Override
	public DuplicateLine getDuplicateLine() {
		return this.duplicateLine;
	}

	@Override
	public void setDuplicateLine(DuplicateLine duplicateLine) {
		this.duplicateLine = duplicateLine;
	}

	@Override
	public PlasmidLine getPlasmidLine() {
		return this.plasmidLine;
	}

	@Override
	public void setPlasmidLine(PlasmidLine plasmidLine) {
		this.plasmidLine = plasmidLine;
	}

	@Override
	public ScopeBlock getScopeBlock() {
		return this.scopeBlock;
	}

	@Override
	public void setScopeBlock(ScopeBlock scopeBlock) {
		this.scopeBlock = scopeBlock;
	}

	@Override
	public FusionBlock getFusionBlock() {
		return this.fusionBlock;
	}

	@Override
	public void setFusionBlock(FusionBlock fusionBlock) {
		this.fusionBlock = fusionBlock;
	}

	@Override
	public void accept(ComputingSectionVisitor visitor) {
		visitor.visit(this.lines);
	}

	@Override
	public Line getLastLine() {
		if (this.lines != null && this.lines.size() > 0) {
			return this.lines.get(this.lines.size() - 1);
		}
		return null;
	}

	@Override
	public void setLastLine(Line lastLine) {
		this.lines.set(this.lines.size() - 1, lastLine);
	}

	@Override
	public List<Line> getNonControlBlockLines() {
		return this.nonControlBlockLines;
	}

	@Override
	public void setNonControlBlockLines(List<Line> nonControlBlockLines) {
		this.nonControlBlockLines = nonControlBlockLines;
	}

	@Override
	public Line getLastNonControlBlockLine() {
		if (this.nonControlBlockLines != null && this.nonControlBlockLines.size() > 0) {
			return this.nonControlBlockLines.get(this.nonControlBlockLines.size() - 1);
		}
		return null;
	}

	@Override
	public void setLastNonControlBlockLine(Line lastNonControlBlockLine) {
		if (this.nonControlBlockLines.size() == 0) {
			this.nonControlBlockLines.add(lastNonControlBlockLine);
		} else {
			this.nonControlBlockLines.set(this.nonControlBlockLines.size() - 1, lastNonControlBlockLine);
		}
	}

}
