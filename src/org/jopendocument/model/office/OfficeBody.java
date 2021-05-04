/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright 2008-2013 jOpenDocument, by ILM Informatique. All rights reserved.
 * 
 * The contents of this file are subject to the terms of the GNU
 * General Public License Version 3 only ("GPL").  
 * You may not use this file except in compliance with the License. 
 * You can obtain a copy of the License at http://www.gnu.org/licenses/gpl-3.0.html
 * See the License for the specific language governing permissions and limitations under the License.
 * 
 * When distributing the software, include this License Header Notice in each file.
 * 
 */

package org.jopendocument.model.office;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.jopendocument.model.OpenDocument;
import org.jopendocument.model.chart.ChartChart;
import org.jopendocument.model.draw.Dr3DScene;
import org.jopendocument.model.draw.DrawA;
import org.jopendocument.model.draw.DrawApplet;
import org.jopendocument.model.draw.DrawCaption;
import org.jopendocument.model.draw.DrawCircle;
import org.jopendocument.model.draw.DrawConnector;
import org.jopendocument.model.draw.DrawControl;
import org.jopendocument.model.draw.DrawCustomShape;
import org.jopendocument.model.draw.DrawEllipse;
import org.jopendocument.model.draw.DrawFloatingFrame;
import org.jopendocument.model.draw.DrawG;
import org.jopendocument.model.draw.DrawImage;
import org.jopendocument.model.draw.DrawLine;
import org.jopendocument.model.draw.DrawMeasure;
import org.jopendocument.model.draw.DrawObject;
import org.jopendocument.model.draw.DrawObjectOle;
import org.jopendocument.model.draw.DrawPage;
import org.jopendocument.model.draw.DrawPageThumbnail;
import org.jopendocument.model.draw.DrawPath;
import org.jopendocument.model.draw.DrawPlugin;
import org.jopendocument.model.draw.DrawPolygon;
import org.jopendocument.model.draw.DrawPolyline;
import org.jopendocument.model.draw.DrawRect;
import org.jopendocument.model.draw.DrawTextBox;
import org.jopendocument.model.presentation.PresentationSettings;
import org.jopendocument.model.table.TableCalculationSettings;
import org.jopendocument.model.table.TableConsolidation;
import org.jopendocument.model.table.TableContentValidations;
import org.jopendocument.model.table.TableDataPilotTables;
import org.jopendocument.model.table.TableDatabaseRanges;
import org.jopendocument.model.table.TableDdeLinks;
import org.jopendocument.model.table.TableLabelRanges;
import org.jopendocument.model.table.TableNamedExpressions;
import org.jopendocument.model.table.TableTable;
import org.jopendocument.model.table.TableTrackedChanges;
import org.jopendocument.model.text.TextAlphabeticalIndex;
import org.jopendocument.model.text.TextAlphabeticalIndexAutoMarkFile;
import org.jopendocument.model.text.TextBibliography;
import org.jopendocument.model.text.TextChange;
import org.jopendocument.model.text.TextChangeEnd;
import org.jopendocument.model.text.TextChangeStart;
import org.jopendocument.model.text.TextDdeConnectionDecls;
import org.jopendocument.model.text.TextH;
import org.jopendocument.model.text.TextIllustrationIndex;
import org.jopendocument.model.text.TextObjectIndex;
import org.jopendocument.model.text.TextOrderedList;
import org.jopendocument.model.text.TextP;
import org.jopendocument.model.text.TextSection;
import org.jopendocument.model.text.TextSequenceDecls;
import org.jopendocument.model.text.TextTableIndex;
import org.jopendocument.model.text.TextTableOfContent;
import org.jopendocument.model.text.TextTrackedChanges;
import org.jopendocument.model.text.TextUnorderedList;
import org.jopendocument.model.text.TextUserFieldDecls;
import org.jopendocument.model.text.TextUserIndex;
import org.jopendocument.model.text.TextVariableDecls;

/**
 * 
 */
public class OfficeBody {

    private OpenDocument doc;

    protected OfficeForms officeForms;

    private final List<OfficeSpreadsheet> officeSpreadsheets = new Vector<OfficeSpreadsheet>();

    protected PresentationSettings presentationSettings;

    protected TableCalculationSettings tableCalculationSettings;

    protected TableConsolidation tableConsolidation;

    protected TableContentValidations tableContentValidations;

    protected TableDatabaseRanges tableDatabaseRanges;

    protected TableDataPilotTables tableDataPilotTables;

    protected TableDdeLinks tableDdeLinks;

    protected TableLabelRanges tableLabelRanges;

    protected TableNamedExpressions tableNamedExpressions;

    protected String tableProtectionKey;

    protected String tableStructureProtected;

    protected TextAlphabeticalIndexAutoMarkFile textAlphabeticalIndexAutoMarkFile;

    protected TextDdeConnectionDecls textDdeConnectionDecls;

    protected List<Object> textHOrTextPOrTextOrderedListOrTextUnorderedListOrTableTableOrDrawPageOrDrawAOrDrawRectOrDrawLineOrDrawPolylineOrDrawPolygonOrDrawPathOrDrawCircleOrDrawEllipseOrDrawGOrDrawPageThumbnailOrDrawTextBoxOrDrawImageOrDrawObjectOrDrawObjectOleOrDrawAppletOrDrawFloatingFrameOrDrawPluginOrDrawMeasureOrDrawCaptionOrDrawConnectorOrChartChartOrDr3DSceneOrDrawControlOrDrawCustomShapeOrTextSectionOrTextTableOfContentOrTextIllustrationIndexOrTextTableIndexOrTextObjectIndexOrTextUserIndexOrTextAlphabeticalIndexOrTextBibliographyOrTextChangeOrTextChangeStartOrTextChangeEnd;

    protected TextSequenceDecls textSequenceDecls;

    protected List<Object> textTrackedChangesOrTableTrackedChanges;

    protected TextUserFieldDecls textUserFieldDecls;

    protected TextVariableDecls textVariableDecls;

    private OfficeText text;

    public void addOfficeSpreadsheet(final OfficeSpreadsheet spread) {
        spread.setBody(this);
        this.officeSpreadsheets.add(spread);
    }

    public OpenDocument getDocument() {
        return this.doc;
    }

    /**
     * Gets the value of the officeForms property.
     * 
     * @return possible object is {@link OfficeForms }
     * 
     */
    public OfficeForms getOfficeForms() {
        return this.officeForms;
    }

    public List<OfficeSpreadsheet> getOfficeSpreadsheets() {
        return this.officeSpreadsheets;
    }

    /**
     * Gets the value of the presentationSettings property.
     * 
     * @return possible object is {@link PresentationSettings }
     * 
     */
    public PresentationSettings getPresentationSettings() {
        return this.presentationSettings;
    }

    /**
     * Gets the value of the tableCalculationSettings property.
     * 
     * @return possible object is {@link TableCalculationSettings }
     * 
     */
    public TableCalculationSettings getTableCalculationSettings() {
        return this.tableCalculationSettings;
    }

    /**
     * Gets the value of the tableConsolidation property.
     * 
     * @return possible object is {@link TableConsolidation }
     * 
     */
    public TableConsolidation getTableConsolidation() {
        return this.tableConsolidation;
    }

    /**
     * Gets the value of the tableContentValidations property.
     * 
     * @return possible object is {@link TableContentValidations }
     * 
     */
    public TableContentValidations getTableContentValidations() {
        return this.tableContentValidations;
    }

    /**
     * Gets the value of the tableDatabaseRanges property.
     * 
     * @return possible object is {@link TableDatabaseRanges }
     * 
     */
    public TableDatabaseRanges getTableDatabaseRanges() {
        return this.tableDatabaseRanges;
    }

    /**
     * Gets the value of the tableDataPilotTables property.
     * 
     * @return possible object is {@link TableDataPilotTables }
     * 
     */
    public TableDataPilotTables getTableDataPilotTables() {
        return this.tableDataPilotTables;
    }

    /**
     * Gets the value of the tableDdeLinks property.
     * 
     * @return possible object is {@link TableDdeLinks }
     * 
     */
    public TableDdeLinks getTableDdeLinks() {
        return this.tableDdeLinks;
    }

    /**
     * Gets the value of the tableLabelRanges property.
     * 
     * @return possible object is {@link TableLabelRanges }
     * 
     */
    public TableLabelRanges getTableLabelRanges() {
        return this.tableLabelRanges;
    }

    /**
     * Gets the value of the tableNamedExpressions property.
     * 
     * @return possible object is {@link TableNamedExpressions }
     * 
     */
    public TableNamedExpressions getTableNamedExpressions() {
        return this.tableNamedExpressions;
    }

    /**
     * Gets the value of the tableProtectionKey property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableProtectionKey() {
        return this.tableProtectionKey;
    }

    /**
     * Gets the value of the tableStructureProtected property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableStructureProtected() {
        if (this.tableStructureProtected == null) {
            return "false";
        } else {
            return this.tableStructureProtected;
        }
    }

    /**
     * Gets the value of the textAlphabeticalIndexAutoMarkFile property.
     * 
     * @return possible object is {@link TextAlphabeticalIndexAutoMarkFile }
     * 
     */
    public TextAlphabeticalIndexAutoMarkFile getTextAlphabeticalIndexAutoMarkFile() {
        return this.textAlphabeticalIndexAutoMarkFile;
    }

    /**
     * Gets the value of the textDdeConnectionDecls property.
     * 
     * @return possible object is {@link TextDdeConnectionDecls }
     * 
     */
    public TextDdeConnectionDecls getTextDdeConnectionDecls() {
        return this.textDdeConnectionDecls;
    }

    /**
     * Gets thevalueofthetextHOrTextPOrTextOrderedListOrTextUnorderedListOrTableTableOrDrawPageOrDrawAOrDrawRectOrDrawLineOrDrawPolylineOrDrawPolygonOrDrawPathOrDrawCircleOrDrawEllipseOrDrawGOrDrawPageThumbnailOrDrawTextBoxOrDrawImageOrDrawObjectOrDrawObjectOleOrDrawAppletOrDrawFloatingFrameOrDrawPluginOrDrawMeasureOrDrawCaptionOrDrawConnectorOrChartChartOrDr3DSceneOrDrawControlOrDrawCustomShapeOrTextSectionOrTextTableOfContentOrTextIllustrationIndexOrTextTableIndexOrTextObjectIndexOrTextUserIndexOrTextAlphabeticalIndexOrTextBibliographyOrTextChangeOrTextChangeStartOrTextChangeEnd
     * property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE>methodforthetextHOrTextPOrTextOrderedListOrTextUnorderedListOrTableTableOrDrawPageOrDrawAOrDrawRectOrDrawLineOrDrawPolylineOrDrawPolygonOrDrawPathOrDrawCircleOrDrawEllipseOrDrawGOrDrawPageThumbnailOrDrawTextBoxOrDrawImageOrDrawObjectOrDrawObjectOleOrDrawAppletOrDrawFloatingFrameOrDrawPluginOrDrawMeasureOrDrawCaptionOrDrawConnectorOrChartChartOrDr3DSceneOrDrawControlOrDrawCustomShapeOrTextSectionOrTextTableOfContentOrTextIllustrationIndexOrTextTableIndexOrTextObjectIndexOrTextUserIndexOrTextAlphabeticalIndexOrTextBibliographyOrTextChangeOrTextChangeStartOrTextChangeEnd
     * property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getTextHOrTextPOrTextOrderedListOrTextUnorderedListOrTableTableOrDrawPageOrDrawAOrDrawRectOrDrawLineOrDrawPolylineOrDrawPolygonOrDrawPathOrDrawCircleOrDrawEllipseOrDrawGOrDrawPageThumbnailOrDrawTextBoxOrDrawImageOrDrawObjectOrDrawObjectOleOrDrawAppletOrDrawFloatingFrameOrDrawPluginOrDrawMeasureOrDrawCaptionOrDrawConnectorOrChartChartOrDr3DSceneOrDrawControlOrDrawCustomShapeOrTextSectionOrTextTableOfContentOrTextIllustrationIndexOrTextTableIndexOrTextObjectIndexOrTextUserIndexOrTextAlphabeticalIndexOrTextBibliographyOrTextChangeOrTextChangeStartOrTextChangeEnd()
     *         .add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link TextH } {@link TextP }
     * {@link TextOrderedList } {@link TextUnorderedList } {@link TableTable } {@link DrawPage }
     * {@link DrawA } {@link DrawRect } {@link DrawLine } {@link DrawPolyline } {@link DrawPolygon }
     * {@link DrawPath } {@link DrawCircle } {@link DrawEllipse } {@link DrawG }
     * {@link DrawPageThumbnail } {@link DrawTextBox } {@link DrawImage } {@link DrawObject }
     * {@link DrawObjectOle } {@link DrawApplet } {@link DrawFloatingFrame } {@link DrawPlugin }
     * {@link DrawMeasure } {@link DrawCaption } {@link DrawConnector } {@link ChartChart }
     * {@link Dr3DScene } {@link DrawControl } {@link DrawCustomShape } {@link TextSection }
     * {@link TextTableOfContent } {@link TextIllustrationIndex } {@link TextTableIndex }
     * {@link TextObjectIndex } {@link TextUserIndex } {@link TextAlphabeticalIndex }
     * {@link TextBibliography } {@link TextChange } {@link TextChangeStart } {@link TextChangeEnd }
     * 
     * 
     */
    public List<Object> getTextHOrTextPOrTextOrderedListOrTextUnorderedListOrTableTableOrDrawPageOrDrawAOrDrawRectOrDrawLineOrDrawPolylineOrDrawPolygonOrDrawPathOrDrawCircleOrDrawEllipseOrDrawGOrDrawPageThumbnailOrDrawTextBoxOrDrawImageOrDrawObjectOrDrawObjectOleOrDrawAppletOrDrawFloatingFrameOrDrawPluginOrDrawMeasureOrDrawCaptionOrDrawConnectorOrChartChartOrDr3DSceneOrDrawControlOrDrawCustomShapeOrTextSectionOrTextTableOfContentOrTextIllustrationIndexOrTextTableIndexOrTextObjectIndexOrTextUserIndexOrTextAlphabeticalIndexOrTextBibliographyOrTextChangeOrTextChangeStartOrTextChangeEnd() {
        if (this.textHOrTextPOrTextOrderedListOrTextUnorderedListOrTableTableOrDrawPageOrDrawAOrDrawRectOrDrawLineOrDrawPolylineOrDrawPolygonOrDrawPathOrDrawCircleOrDrawEllipseOrDrawGOrDrawPageThumbnailOrDrawTextBoxOrDrawImageOrDrawObjectOrDrawObjectOleOrDrawAppletOrDrawFloatingFrameOrDrawPluginOrDrawMeasureOrDrawCaptionOrDrawConnectorOrChartChartOrDr3DSceneOrDrawControlOrDrawCustomShapeOrTextSectionOrTextTableOfContentOrTextIllustrationIndexOrTextTableIndexOrTextObjectIndexOrTextUserIndexOrTextAlphabeticalIndexOrTextBibliographyOrTextChangeOrTextChangeStartOrTextChangeEnd == null) {
            this.textHOrTextPOrTextOrderedListOrTextUnorderedListOrTableTableOrDrawPageOrDrawAOrDrawRectOrDrawLineOrDrawPolylineOrDrawPolygonOrDrawPathOrDrawCircleOrDrawEllipseOrDrawGOrDrawPageThumbnailOrDrawTextBoxOrDrawImageOrDrawObjectOrDrawObjectOleOrDrawAppletOrDrawFloatingFrameOrDrawPluginOrDrawMeasureOrDrawCaptionOrDrawConnectorOrChartChartOrDr3DSceneOrDrawControlOrDrawCustomShapeOrTextSectionOrTextTableOfContentOrTextIllustrationIndexOrTextTableIndexOrTextObjectIndexOrTextUserIndexOrTextAlphabeticalIndexOrTextBibliographyOrTextChangeOrTextChangeStartOrTextChangeEnd = new ArrayList<Object>();
        }
        return this.textHOrTextPOrTextOrderedListOrTextUnorderedListOrTableTableOrDrawPageOrDrawAOrDrawRectOrDrawLineOrDrawPolylineOrDrawPolygonOrDrawPathOrDrawCircleOrDrawEllipseOrDrawGOrDrawPageThumbnailOrDrawTextBoxOrDrawImageOrDrawObjectOrDrawObjectOleOrDrawAppletOrDrawFloatingFrameOrDrawPluginOrDrawMeasureOrDrawCaptionOrDrawConnectorOrChartChartOrDr3DSceneOrDrawControlOrDrawCustomShapeOrTextSectionOrTextTableOfContentOrTextIllustrationIndexOrTextTableIndexOrTextObjectIndexOrTextUserIndexOrTextAlphabeticalIndexOrTextBibliographyOrTextChangeOrTextChangeStartOrTextChangeEnd;
    }

    /**
     * Gets the value of the textSequenceDecls property.
     * 
     * @return possible object is {@link TextSequenceDecls }
     * 
     */
    public TextSequenceDecls getTextSequenceDecls() {
        return this.textSequenceDecls;
    }

    /**
     * Gets the value of the textTrackedChangesOrTableTrackedChanges property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the textTrackedChangesOrTableTrackedChanges
     * property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getTextTrackedChangesOrTableTrackedChanges().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link TextTrackedChanges }
     * {@link TableTrackedChanges }
     * 
     * 
     */
    public List<Object> getTextTrackedChangesOrTableTrackedChanges() {
        if (this.textTrackedChangesOrTableTrackedChanges == null) {
            this.textTrackedChangesOrTableTrackedChanges = new ArrayList<Object>();
        }
        return this.textTrackedChangesOrTableTrackedChanges;
    }

    /**
     * Gets the value of the textUserFieldDecls property.
     * 
     * @return possible object is {@link TextUserFieldDecls }
     * 
     */
    public TextUserFieldDecls getTextUserFieldDecls() {
        return this.textUserFieldDecls;
    }

    /**
     * Gets the value of the textVariableDecls property.
     * 
     * @return possible object is {@link TextVariableDecls }
     * 
     */
    public TextVariableDecls getTextVariableDecls() {
        return this.textVariableDecls;
    }

    public void setDocument(final OpenDocument document) {
        this.doc = document;

    }

    /**
     * Sets the value of the officeForms property.
     * 
     * @param value allowed object is {@link OfficeForms }
     * 
     */
    public void setOfficeForms(final OfficeForms value) {
        this.officeForms = value;
    }

    /**
     * Sets the value of the presentationSettings property.
     * 
     * @param value allowed object is {@link PresentationSettings }
     * 
     */
    public void setPresentationSettings(final PresentationSettings value) {
        this.presentationSettings = value;
    }

    /**
     * Sets the value of the tableCalculationSettings property.
     * 
     * @param value allowed object is {@link TableCalculationSettings }
     * 
     */
    public void setTableCalculationSettings(final TableCalculationSettings value) {
        this.tableCalculationSettings = value;
    }

    /**
     * Sets the value of the tableConsolidation property.
     * 
     * @param value allowed object is {@link TableConsolidation }
     * 
     */
    public void setTableConsolidation(final TableConsolidation value) {
        this.tableConsolidation = value;
    }

    /**
     * Sets the value of the tableContentValidations property.
     * 
     * @param value allowed object is {@link TableContentValidations }
     * 
     */
    public void setTableContentValidations(final TableContentValidations value) {
        this.tableContentValidations = value;
    }

    /**
     * Sets the value of the tableDatabaseRanges property.
     * 
     * @param value allowed object is {@link TableDatabaseRanges }
     * 
     */
    public void setTableDatabaseRanges(final TableDatabaseRanges value) {
        this.tableDatabaseRanges = value;
    }

    /**
     * Sets the value of the tableDataPilotTables property.
     * 
     * @param value allowed object is {@link TableDataPilotTables }
     * 
     */
    public void setTableDataPilotTables(final TableDataPilotTables value) {
        this.tableDataPilotTables = value;
    }

    /**
     * Sets the value of the tableDdeLinks property.
     * 
     * @param value allowed object is {@link TableDdeLinks }
     * 
     */
    public void setTableDdeLinks(final TableDdeLinks value) {
        this.tableDdeLinks = value;
    }

    /**
     * Sets the value of the tableLabelRanges property.
     * 
     * @param value allowed object is {@link TableLabelRanges }
     * 
     */
    public void setTableLabelRanges(final TableLabelRanges value) {
        this.tableLabelRanges = value;
    }

    /**
     * Sets the value of the tableNamedExpressions property.
     * 
     * @param value allowed object is {@link TableNamedExpressions }
     * 
     */
    public void setTableNamedExpressions(final TableNamedExpressions value) {
        this.tableNamedExpressions = value;
    }

    /**
     * Sets the value of the tableProtectionKey property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableProtectionKey(final String value) {
        this.tableProtectionKey = value;
    }

    /**
     * Sets the value of the tableStructureProtected property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableStructureProtected(final String value) {
        this.tableStructureProtected = value;
    }

    /**
     * Sets the value of the textAlphabeticalIndexAutoMarkFile property.
     * 
     * @param value allowed object is {@link TextAlphabeticalIndexAutoMarkFile }
     * 
     */
    public void setTextAlphabeticalIndexAutoMarkFile(final TextAlphabeticalIndexAutoMarkFile value) {
        this.textAlphabeticalIndexAutoMarkFile = value;
    }

    /**
     * Sets the value of the textDdeConnectionDecls property.
     * 
     * @param value allowed object is {@link TextDdeConnectionDecls }
     * 
     */
    public void setTextDdeConnectionDecls(final TextDdeConnectionDecls value) {
        this.textDdeConnectionDecls = value;
    }

    /**
     * Sets the value of the textSequenceDecls property.
     * 
     * @param value allowed object is {@link TextSequenceDecls }
     * 
     */
    public void setTextSequenceDecls(final TextSequenceDecls value) {
        this.textSequenceDecls = value;
    }

    /**
     * Sets the value of the textUserFieldDecls property.
     * 
     * @param value allowed object is {@link TextUserFieldDecls }
     * 
     */
    public void setTextUserFieldDecls(final TextUserFieldDecls value) {
        this.textUserFieldDecls = value;
    }

    /**
     * Sets the value of the textVariableDecls property.
     * 
     * @param value allowed object is {@link TextVariableDecls }
     * 
     */
    public void setTextVariableDecls(final TextVariableDecls value) {
        this.textVariableDecls = value;
    }

    public void setOfficeText(OfficeText text) {
        if (this.text != null) {
            throw new IllegalArgumentException("OfficeText already set");
        }
        this.text = text;

    }

    public OfficeText getText() {
        return text;
    }
}
