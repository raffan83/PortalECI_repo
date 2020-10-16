/**
 * DynamicReports - Free Java reporting library for creating reports dynamically
 *
 * Copyright (C) 2010 - 2016 Ricardo Mariaca
 * http://www.dynamicreports.org
 *
 * This file is part of DynamicReports.
 *
 * DynamicReports is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * DynamicReports is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with DynamicReports. If not, see <http://www.gnu.org/licenses/>.
 */

package it.portalECI.Util;

import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.hyperLink;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import static net.sf.dynamicreports.report.builder.DynamicReports.tableOfContentsCustomizer;
import static net.sf.dynamicreports.report.builder.DynamicReports.template;

import java.awt.Color;
import java.util.Locale;

import net.sf.dynamicreports.report.base.expression.AbstractValueFormatter;
import net.sf.dynamicreports.report.builder.HyperLinkBuilder;
import net.sf.dynamicreports.report.builder.ReportTemplateBuilder;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.datatype.BigDecimalType;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.builder.tableofcontents.TableOfContentsCustomizerBuilder;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.constant.Markup;
import net.sf.dynamicreports.report.constant.VerticalTextAlignment;
import net.sf.dynamicreports.report.definition.ReportParameters;

/**

 */
public class Templates {
	public static final StyleBuilder rootStyle;
	public static final StyleBuilder boldStyle;
	public static final StyleBuilder italicStyle;
	public static final StyleBuilder boldCenteredStyle;
	public static final StyleBuilder bold12CenteredStyle;
	public static final StyleBuilder bold18CenteredStyle;
	public static final StyleBuilder bold22CenteredStyle;
	public static final StyleBuilder columnStyle;
	public static final StyleBuilder columnStyleLight;
	public static final StyleBuilder columnTitleStyle;
	public static final StyleBuilder columnTitleStyleVerde;
	public static final StyleBuilder columnTitleStyleWhite;
	public static final StyleBuilder groupStyle;
	public static final StyleBuilder subtotalStyle;
	public static final StyleBuilder columnTitleStyleLight;
	
	public static final StyleBuilder footerStyle;
	public static final StyleBuilder footerStyleFormula;
 	public static final ReportTemplateBuilder reportTemplate;
	public static final ReportTemplateBuilder reportTemplateVerde;
	public static final ReportTemplateBuilder reportTemplateWhite;

	public static final CurrencyType currencyType;
	public static final ComponentBuilder<?, ?> dynamicReportsComponent;
	public static final ComponentBuilder<?, ?> footerComponent;

	static {
		rootStyle           = stl.style().setPadding(2).setFontName("Trebuchet MS").setFontSize(8);
		boldStyle           = stl.style(rootStyle).bold().setFontName("Trebuchet MS");
		italicStyle         = stl.style(rootStyle).italic().setFontName("Trebuchet MS");
		boldCenteredStyle   = stl.style(boldStyle).setFontName("Trebuchet MS")
		                         .setTextAlignment(HorizontalTextAlignment.CENTER, VerticalTextAlignment.MIDDLE);
		bold12CenteredStyle = stl.style(boldCenteredStyle).setFontName("Trebuchet MS")
		                         .setFontSize(12);
		bold18CenteredStyle = stl.style(boldCenteredStyle).setFontName("Trebuchet MS")
		                         .setFontSize(18);
		bold22CenteredStyle = stl.style(boldCenteredStyle).setFontName("Trebuchet MS")
                             .setFontSize(22);
		columnStyle         = stl.style(rootStyle).setFontName("Trebuchet MS").setVerticalTextAlignment(VerticalTextAlignment.MIDDLE).setBold(false);
		columnStyleLight         = stl.style(rootStyle).setFontName("Trebuchet MS").setVerticalTextAlignment(VerticalTextAlignment.MIDDLE).setBold(false);
		columnTitleStyle    = stl.style(columnStyle).setFontName("Trebuchet MS")
		                         .setBorder(stl.penThin())
		                         .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
		                         .setBackgroundColor(Color.LIGHT_GRAY)
		                         .bold()
		                         .setFontSize(5).setMarkup(Markup.HTML);
		columnTitleStyleVerde    = stl.style(columnStyle).setFontName("Trebuchet MS")
                .setBorder(stl.penThin())
                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                .setBackgroundColor(new Color(161,219,145))
                .bold()
                .setFontSize(6);
		columnTitleStyleWhite    = stl.style(columnStyleLight).setFontName("Trebuchet MS")
                .setBorder(stl.penThin())
                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                .setBackgroundColor(new Color(255,255,255))
                .bold()
                .setFontSize(5).setMarkup(Markup.HTML);
		columnTitleStyleLight    = stl.style(columnStyle).setFontName("Trebuchet MS")
                .setBorder(stl.penThin())
                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                .setBackgroundColor(new Color(235,233,233))
                .bold()
                .setFontSize(5).setMarkup(Markup.HTML);
		groupStyle          = stl.style(boldStyle).setFontName("Trebuchet MS")
		                         .setHorizontalTextAlignment(HorizontalTextAlignment.LEFT);
		subtotalStyle       = stl.style(boldStyle).setFontName("Trebuchet MS")
		                         .setTopBorder(stl.penThin());
		footerStyle           = stl.style().setPadding(2).setFontName("Trebuchet MS");
		footerStyleFormula          = stl.style().setPadding(2).setFontName("Trebuchet MS").setHorizontalTextAlignment(HorizontalTextAlignment.LEFT);
 
		StyleBuilder crosstabGroupStyle      = stl.style(columnTitleStyle).setFontName("Trebuchet MS");
		StyleBuilder crosstabGroupTotalStyle = stl.style(columnTitleStyle).setFontName("Trebuchet MS")
		                                          .setBackgroundColor(new Color(170, 170, 170));
		StyleBuilder crosstabGrandTotalStyle = stl.style(columnTitleStyle).setFontName("Trebuchet MS")
		                                          .setBackgroundColor(new Color(140, 140, 140));
		StyleBuilder crosstabCellStyle       = stl.style(columnStyle).setFontName("Trebuchet MS")
		                                          .setBorder(stl.penThin());
		
		StyleBuilder crosstabGroupStyleWhite      = stl.style(columnTitleStyle).setFontName("Trebuchet MS");
		StyleBuilder crosstabGroupTotalStyleWhite = stl.style(columnTitleStyle).setFontName("Trebuchet MS")
				 									.setBackgroundColor(Color.LIGHT_GRAY);
		StyleBuilder crosstabGrandTotalStyleWhite = stl.style(columnTitleStyle).setFontName("Trebuchet MS")
													.setBackgroundColor(Color.LIGHT_GRAY);
		StyleBuilder crosstabCellStyleWhite       = stl.style(columnStyle).setFontName("Trebuchet MS")
		                                          .setBorder(stl.penThin());

		TableOfContentsCustomizerBuilder tableOfContentsCustomizer = tableOfContentsCustomizer()
			.setHeadingStyle(0, stl.style(rootStyle).bold());

		reportTemplate = template()
		                   .setLocale(Locale.ENGLISH)
		                   .setColumnStyle(columnStyle)
		                   .setColumnTitleStyle(columnTitleStyle)
		                   .setGroupStyle(groupStyle)
		                   .setGroupTitleStyle(groupStyle)
		                   .setSubtotalStyle(subtotalStyle)
		                   .highlightDetailEvenRows()
		                   .crosstabHighlightEvenRows()
		                   .setCrosstabGroupStyle(crosstabGroupStyle)
		                   .setCrosstabGroupTotalStyle(crosstabGroupTotalStyle)
		                   .setCrosstabGrandTotalStyle(crosstabGrandTotalStyle)
		                   .setCrosstabCellStyle(crosstabCellStyle)
		                   .setTableOfContentsCustomizer(tableOfContentsCustomizer);
		
		reportTemplateWhite = template()
                .setLocale(Locale.ENGLISH)
                .setColumnStyle(columnStyleLight)
                .setColumnTitleStyle(columnTitleStyleLight)
                .setGroupStyle(groupStyle)
                .setGroupTitleStyle(groupStyle)
                .setSubtotalStyle(subtotalStyle)
                .highlightDetailEvenRows()
                .crosstabHighlightEvenRows()
                .setCrosstabGroupStyle(crosstabGroupStyleWhite)
                .setCrosstabGroupTotalStyle(crosstabGroupTotalStyleWhite)
                .setCrosstabGrandTotalStyle(crosstabGrandTotalStyleWhite)
                .setCrosstabCellStyle(crosstabCellStyleWhite)
                .setTableOfContentsCustomizer(tableOfContentsCustomizer);
		
		reportTemplateVerde = template()
                .setLocale(Locale.ITALIAN)
                .setColumnStyle(columnStyle)
                .setColumnTitleStyle(columnTitleStyleVerde)
                .setGroupStyle(groupStyle)
                .setGroupTitleStyle(groupStyle)
                .setSubtotalStyle(subtotalStyle)
                .highlightDetailEvenRows()
                .crosstabHighlightEvenRows()
                .setCrosstabGroupStyle(crosstabGroupStyle)
                .setCrosstabGroupTotalStyle(crosstabGroupTotalStyle)
                .setCrosstabGrandTotalStyle(crosstabGrandTotalStyle)
                .setCrosstabCellStyle(crosstabCellStyle)
                .setTableOfContentsCustomizer(tableOfContentsCustomizer);

		currencyType = new CurrencyType();

		HyperLinkBuilder link = hyperLink("http://www.dynamicreports.org");
		dynamicReportsComponent =
		  cmp.horizontalList(
		  	cmp.image(Templates.class.getResource("images/dynamicreports.png")).setFixedDimension(60, 60),
		  	cmp.verticalList(
		  		cmp.text("DynamicReports").setStyle(bold22CenteredStyle).setHorizontalTextAlignment(HorizontalTextAlignment.LEFT),
		  		cmp.text("http://www.dynamicreports.org").setStyle(italicStyle).setHyperLink(link))).setFixedWidth(300);

		footerComponent = cmp.pageXofY()
		                     .setStyle(
		                     	stl.style(boldCenteredStyle)
		                     	   .setTopBorder(stl.penThin()));
	}

	/**
	 * Creates custom component which is possible to add to any report band component
	 */
	public static ComponentBuilder<?, ?> createTitleComponent(String label) {
		return cmp.horizontalList()
		        .add(
			        	dynamicReportsComponent,
			        	cmp.text(label).setStyle(bold18CenteredStyle).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT))
			        .newRow()
			        .add(cmp.line())
			        .newRow()
			        .add(cmp.verticalGap(10));
	}

	public static CurrencyValueFormatter createCurrencyValueFormatter(String label) {
		return new CurrencyValueFormatter(label);
	}

	public static class CurrencyType extends BigDecimalType {
		private static final long serialVersionUID = 1L;

		@Override
		public String getPattern() {
			return "$ #,###.00";
		}
	}

	private static class CurrencyValueFormatter extends AbstractValueFormatter<String, Number> {
		private static final long serialVersionUID = 1L;

		private String label;

		public CurrencyValueFormatter(String label) {
			this.label = label;
		}

		@Override
		public String format(Number value, ReportParameters reportParameters) {
			return label + currencyType.valueToString(value, reportParameters.getLocale());
		}
	}
}