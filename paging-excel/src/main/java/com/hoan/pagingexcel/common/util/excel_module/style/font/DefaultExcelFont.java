package com.hoan.pagingexcel.common.util.excel_module.style.font;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * DefaultExcelFont
 *
 * Can be used with {@link com.mqnic.intlitsadmin.common.util.excel_module.style.CustomExcelCellStyle}
 */
public enum DefaultExcelFont implements ExcelFont{

    DEFAULT("맑은 고딕", (short) 11, false, false, false),
    BOLD("맑은 고딕", (short) 11, true, false, false),
    ITALIC("맑은 고딕", (short) 11, false, true, false),
    UNDERLINE("맑은 고딕", (short) 11, false, false, true),
    BOLD_ITALIC("맑은 고딕", (short) 11, true, true, false),
    BOLD_UNDERLINE("맑은 고딕", (short) 11, true, false, true),
    ITALIC_UNDERLINE("맑은 고딕", (short) 11, false, true, true),
    BOLD_ITALIC_UNDERLINE("맑은 고딕", (short) 11, true, true, true);


    private final String fontName;
    private final short fontSize;
    private final boolean bold;
    private final boolean italic;
    private final boolean underline;

    DefaultExcelFont(String fontName, short fontSize, boolean bold, boolean italic, boolean underline) {
        this.fontName = fontName;
        this.fontSize = fontSize;
        this.bold = bold;
        this.italic = italic;
        this.underline = underline;
    }

    public void apply(Workbook workbook, CellStyle cellStyle) {
        Font font = workbook.createFont();

        font.setFontName(fontName);
        font.setFontHeightInPoints(fontSize);
        font.setBold(bold);
        font.setItalic(italic);
        font.setUnderline(underline ? Font.U_SINGLE : Font.U_NONE);
        cellStyle.setFont(font);
    }

}