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

package org.jopendocument.dom.style.data;

import org.jopendocument.dom.ODEpoch;
import org.jopendocument.dom.ODPackage;
import org.jopendocument.dom.ODValueType;
import org.jopendocument.dom.Style;
import org.jopendocument.dom.spreadsheet.CellStyle;
import org.jopendocument.dom.spreadsheet.MutableCell;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;
import org.jopendocument.dom.style.data.DataStyle.DataStyleDesc;
import org.jopendocument.util.TimeUtils;
import org.jopendocument.util.TimeUtils.DSTChange;

import java.awt.Color;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.EnumSet;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

import javax.xml.datatype.Duration;

import junit.framework.TestCase;

import org.jdom.Element;
import org.jdom.Namespace;

public class DataStyleTest extends TestCase {

    public void testDays() throws Exception {
        final ODEpoch epoch = ODEpoch.getDefaultEpoch();
        final Calendar cal = Calendar.getInstance();

        // do we get the same number than OO
        cal.clear();
        cal.set(2011, 2, 27, 12, 0);
        assertEquals(new BigDecimal("40629.5"), epoch.getDays(cal));
        assertEquals(cal, epoch.getDate(epoch.getDays(cal)));

        cal.clear();
        cal.set(1909, 8, 4, 0, 0);
        final BigDecimal days = BigDecimal.valueOf(3535);
        assertEquals(cal, epoch.getDate(days));
        assertEquals(days, epoch.getDays(cal));

        // if we pass different Calendar (these aren't even in the same day),
        final Calendar gmtMinus12 = epoch.getDate(days, Calendar.getInstance(TimeZone.getTimeZone("GMT-12:00")));
        final Calendar gmtPlus13 = epoch.getDate(days, Calendar.getInstance(TimeZone.getTimeZone("GMT+13:00")));
        // we get different times,
        assertFalse(gmtMinus12.getTimeInMillis() == gmtPlus13.getTimeInMillis());
        // but they have the same local time
        assertTrue(TimeUtils.normalizeLocalTime(gmtMinus12) == TimeUtils.normalizeLocalTime(gmtPlus13));
        assertTrue(TimeUtils.normalizeLocalTime(cal) == TimeUtils.normalizeLocalTime(gmtPlus13));

        // test before epoch (DST didn't exist)
        for (int i = -10; i < 500; i++) {
            final BigDecimal bd = BigDecimal.valueOf(i);
            assertEquals(bd, epoch.getDays(epoch.getDate(bd)));
        }

        // test round trip (including near DST change)
        {
            final Calendar franceCal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
            final Calendar calUTC = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            calUTC.clear();
            // DST was in use in France from March to October 2013
            calUTC.set(2013, Calendar.NOVEMBER, 1);
            final Date endDate = calUTC.getTime();
            calUTC.set(2013, Calendar.MARCH, 1);

            final DateFormat localTimeDF = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            final Set<DSTChange> changes = EnumSet.noneOf(DSTChange.class);
            while (calUTC.getTimeInMillis() < endDate.getTime()) {
                // in France the hour from 2 to 3 AM is either skipped or rewinded, i.e. the change
                // occur at 1AM UTC
                // http://www.developpement-durable.gouv.fr/Passage-a-l-heure-d-hiver-dimanche.html
                for (int h = 0; h < 3; h++) {
                    calUTC.set(Calendar.HOUR, h);
                    calUTC.set(Calendar.MINUTE, 30);
                    franceCal.setTimeInMillis(calUTC.getTimeInMillis());
                    final DSTChange change = TimeUtils.getDSTChange(franceCal);
                    changes.add(change);
                    // without time zone information
                    final String localTime = localTimeDF.format(franceCal.getTime());

                    final BigDecimal bd = epoch.getDays(franceCal);
                    franceCal.clear();
                    epoch.getDate(bd, franceCal);
                    // wall time is always preserved
                    assertEquals(localTime, localTimeDF.format(franceCal.getTime()));
                    // but absolute time sometimes cannot be preserved
                    if (!TimeUtils.isAmbiguous(change))
                        assertEquals(calUTC.getTimeInMillis(), franceCal.getTimeInMillis());
                }

                calUTC.add(Calendar.DAY_OF_YEAR, 1);
            }
            // all corner cases were tested
            assertEquals(EnumSet.allOf(DSTChange.class), changes);
        }
    }

    public void testFormat() throws Exception {
        final ODPackage pkg = new ODPackage(this.getClass().getResourceAsStream("cellFormat.ods"));
        final Sheet sheet = pkg.getSpreadSheet().getSheet(0);

        // * test that the framework format as OpenOffice
        final int lastRow = sheet.getCurrentRegion(0, 0).getEndPoint().y;
        for (int i = 0; i <= lastRow; i++) {
            final MutableCell<SpreadSheet> cell = sheet.getCellAt(0, i);
            final String byOO = cell.getTextValue();
            final ODValueType origType = cell.getValueType();
            final Object cellValue = cell.getValue();
            // like OO, we should allow any value without removing the data style
            cell.setValue("string");
            cell.setValue(12.3);
            cell.setValue(new Date());
            cell.setValue(true);
            cell.clearValue();
            if (origType != null)
                cell.setValue(cellValue, origType, false, false);
            assertEquals(byOO, cell.getTextValue());
            assertEquals(origType, cell.getValueType());
        }

        // test DEFAULT_DECIMAL_PLACES
        {
            assertNull(Style.getStyleStyleDesc(CellStyle.class, pkg.getVersion()).getDefaultStyle(pkg, true).getTableCellProperties(null).getRawDecimalPlaces());
            final MutableCell<?> a3 = sheet.getCellAt("A3");
            final String officeVal = a3.getTextValue();
            assertEquals("0," + "33333333333333333333333333333".substring(0, DataStyle.DEFAULT_DECIMAL_PLACES), officeVal);
            // we format the same than LibreOffice
            a3.setValue(a3.getValue());
            assertEquals(officeVal, a3.getTextValue());
        }

        // test that the new value is used to find the data style
        final MutableCell<SpreadSheet> a16 = sheet.getCellAt("A17");
        // >=1 with 2 decimal digits
        assertEquals(12.34321d, ((Number) a16.getValue()).doubleValue());
        assertEquals("12,34", a16.getTextValue());
        // <0 without decimal part
        a16.setValue(-12.34321d);
        assertEquals(-12.34321d, ((Number) a16.getValue()).doubleValue());
        assertEquals("-12", a16.getTextValue());
        // in between standard format (with optional decimal digits)
        a16.setValue(0.34321d);
        assertEquals("0,34321", a16.getTextValue());

        // test boolean conversion
        {
            // type not changed, thus "false"
            a16.setValue(Boolean.FALSE, false);
            assertEquals(ODValueType.BOOLEAN, a16.getValueType());
            assertEquals(Boolean.FALSE, a16.getValue());
            assertEquals(BooleanStyle.toString(false, Locale.getDefault(), true), a16.getTextValue());

            // now allow type change
            a16.setValue(Boolean.FALSE, true);
            assertEquals(ODValueType.FLOAT, a16.getValueType());
            assertEquals(0, ((Number) a16.getValue()).intValue());
            // 0 use standard format
            assertEquals("0", a16.getTextValue());

            a16.setValue(Boolean.TRUE, true);
            assertEquals(ODValueType.FLOAT, a16.getValueType());
            assertEquals(1, ((Number) a16.getValue()).intValue());
            assertEquals("1,00", a16.getTextValue());

            final MutableCell<SpreadSheet> a17 = sheet.getCellAt("A18");
            // type not changed, thus "17"
            a17.setValue(17, false);
            assertEquals(ODValueType.FLOAT, a17.getValueType());
            assertEquals(BigDecimal.valueOf(17), a17.getValue());
            assertEquals("17", a17.getTextValue());

            // now allow type change
            a17.setValue(17, true);
            assertEquals(ODValueType.BOOLEAN, a17.getValueType());
            assertTrue((Boolean) a17.getValue());
            assertEquals("VERDADEIRO", a17.getTextValue());
        }

        // test date conversion
        {
            // duration
            final boolean orig = MutableCell.getTimeValueMode();
            testTimeValueMode(a16, !orig);
            testTimeValueMode(a16, orig);
            assertEquals(orig, MutableCell.getTimeValueMode());

            a16.setValue(TimeUtils.getTypeFactory().newDuration(true, 0, 0, 0, 36, 0, 0), true);
            assertEquals(ODValueType.FLOAT, a16.getValueType());
            assertEquals(1.5d, ((Number) a16.getValue()).doubleValue());
            assertEquals("1,50", a16.getTextValue());

            a16.setValue(TimeUtils.getTypeFactory().newDuration(true, 0, 0, 180, 2, 24, 0), true);
            assertEquals(ODValueType.FLOAT, a16.getValueType());
            assertEquals(180.1d, ((Number) a16.getValue()).doubleValue());
            assertEquals("180,10", a16.getTextValue());

            // calendar
            final Calendar cal = Calendar.getInstance();
            cal.clear();
            cal.set(2011, 8, 25, 12, 0);
            a16.setValue(cal, true);
            assertEquals(ODValueType.FLOAT, a16.getValueType());
            assertEquals(new BigDecimal("40811.5"), a16.getValue());
            assertEquals("40811,50", a16.getTextValue());
        }

        // like OO, we should retain the data style, even if we set an incompatible value
        final MutableCell<SpreadSheet> a1 = sheet.getCellAt("A1");
        assertEquals(ODValueType.FLOAT, a1.getValueType());
        assertTrue(a1.getDataStyle() instanceof NumberStyle);
        a1.setValue("toto");
        assertEquals("toto", a1.getValue());
        assertEquals("toto", a1.getTextValue());
        assertEquals(ODValueType.STRING, a1.getValueType());
        assertTrue(a1.getDataStyle() instanceof NumberStyle);
        a1.setValue(1.6);
        // number style was retained
        assertEquals("02", a1.getTextValue());

        // should retain type when possible
        final MutableCell<SpreadSheet> a8 = sheet.getCellAt("A8");
        assertEquals(ODValueType.PERCENTAGE, a8.getValueType());
        a8.setValue(0.35d);
        assertEquals(new BigDecimal("0.35"), a8.getValue());
        assertEquals(ODValueType.PERCENTAGE, a8.getValueType());
        a8.setValue(new Date(), false);
        assertEquals(ODValueType.DATE, a8.getValueType());

        // this used to fail since the conversion for condition evaluation was confused with the
        // conversion for data style
        final MutableCell<SpreadSheet> a12 = sheet.getCellAt("A12");
        assertEquals(ODValueType.DATE, a12.getValueType());
        a12.setValue(40000, true);
        assertEquals(ODValueType.DATE, a12.getValueType());
        assertEquals(sheet.getODDocument().getEpoch().getDate(BigDecimal.valueOf(40000)).getTime(), a12.getValue());
        a12.setValue(new Date());
        assertEquals(ODValueType.DATE, a12.getValueType());

        // test non-lenient
        a1.setValue(Boolean.TRUE, ODValueType.BOOLEAN, false, false);
        assertEquals(Boolean.TRUE, a1.getValue());
        assertEquals(ODValueType.BOOLEAN, a1.getValueType());
        // the language is used even if the data style is not compatible
        assertFalse(a1.getDataStyle() instanceof BooleanStyle);
        assertEquals("WAHR", a1.getTextValue());

        final MutableCell<SpreadSheet> b1 = sheet.getCellAt("B1");
        b1.setValue(0.0912645d, ODValueType.PERCENTAGE, false, false);
        assertEquals(new BigDecimal("0.0912645"), b1.getValue());
        assertEquals(0.0912645d, ((Number) b1.getValue()).doubleValue());
        assertEquals(ODValueType.PERCENTAGE, b1.getValueType());

        // test type inference
        final Date now = new Date();
        b1.setValue(now);
        assertEquals(now, b1.getValue());
        assertEquals(ODValueType.DATE, b1.getValueType());

        // test embedded-text
        {
            final DataStyleDesc<NumberStyle> desc = DataStyle.getDesc(NumberStyle.class, sheet.getODDocument().getVersion());
            final Namespace numberNS = desc.getElementNS();

            // create data style
            final NumberStyle numberStyle = desc.createAutoStyle(pkg, "embedded-text test");
            final Element number = new Element("number", numberNS);
            // LO needs both attributes
            number.setAttribute("min-integer-digits", "3", numberNS);
            number.setAttribute("decimal-places", "4", numberNS);
            number.addContent(new Element("embedded-text", numberNS).setAttribute("position", "2", numberNS).setText("inTxt"));
            // should prepend if greater than digit count
            number.addContent(new Element("embedded-text", numberNS).setAttribute("position", "9", numberNS).setText("other"));
            numberStyle.getElement().addContent(number);

            sheet.ensureRowCount(19);
            final MutableCell<SpreadSheet> a19 = sheet.getCellAt(0, 18);
            a19.getPrivateStyle().getElement().setAttribute("data-style-name", numberStyle.getName(), pkg.getVersion().getSTYLE());
            sheet.setValueAt("embbeded-text test", a19.getX() + 1, a19.getY());

            a19.setValue(53.1);
            assertEquals("other0inTxt53,1000", a19.getTextValue());

            number.removeContent();
            a19.setValue(42.19);
            assertEquals("042,1900", a19.getTextValue());
        }

        // test condition
        final MutableCell<SpreadSheet> neg = sheet.getCellAt("A11");
        final MutableCell<SpreadSheet> pos = sheet.getCellAt("C11");
        // both have the same cell style
        assertEquals(neg.getStyleName(), pos.getStyleName());
        // but their data style differ
        final DataStyle negDataStyle = neg.getDataStyle();
        final DataStyle posDataStyle = pos.getDataStyle();
        assertFalse(negDataStyle.getName().equals(posDataStyle.getName()));
        assertEquals(Color.RED, negDataStyle.getTextProperties().getColor());
        assertEquals(Color.BLACK, posDataStyle.getTextProperties().getColor());
    }

    private void testTimeValueMode(final MutableCell<SpreadSheet> a16, boolean timeValueMode) {
        MutableCell.setTimeValueMode(timeValueMode);
        final Duration newDuration = TimeUtils.getTypeFactory().newDuration(true, 0, 0, 1, 36, 0, 0);
        assertEquals(36, newDuration.getHours());
        a16.setValue(newDuration, false);
        assertEquals(ODValueType.TIME, a16.getValueType());
        // LibreOffice normalize to hours
        assertEquals(timeValueMode ? 60 : 36, ((Duration) a16.getValue()).getHours());
        assertEquals("60:00:00", a16.getTextValue());
    }
}
