package ru.clevertec.cleverbank.service.impl;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.cleverbank.model.dto.response.statement.AccountRecordDto;
import ru.clevertec.cleverbank.model.dto.response.statement.CheckDto;
import ru.clevertec.cleverbank.model.dto.response.statement.StatementDto;
import ru.clevertec.cleverbank.service.DocumentService;

/**
 * Document Service to create PDF documents
 *
 * @author Konstantin Voytko
 */
@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    @Override
    @Transactional
    public void saveCheck(CheckDto checkDto) {
        String path = "check/check" + checkDto.getId() + ".pdf";
        try(PdfWriter pdfWriter = new PdfWriter(path)) {
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            pdfDocument.setDefaultPageSize(PageSize.A2);
            Document document = new Document(pdfDocument);

            float[] oneColumnsWidth = {400f};
            float[] twoColumnsWidth = {150f, 250f};
            float borderWidth = 0.5f;

            Table headerTable = new Table(oneColumnsWidth);
            headerTable.addCell(new Cell().add(new Paragraph("Bank check"))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBorderBottom(Border.NO_BORDER));
            document.add(headerTable);

            Table body = new Table(twoColumnsWidth);
            body.addCell(new Cell().add(new Paragraph("Check:"))
                    .setBorder(Border.NO_BORDER)
                    .setBorderLeft(new SolidBorder(borderWidth)));
            body.addCell(new Cell().add(new Paragraph(checkDto.getId()))
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setBorder(Border.NO_BORDER)
                    .setBorderRight(new SolidBorder(borderWidth)));
            body.addCell(new Cell().add(new Paragraph(checkDto.getDate()))
                    .setBorder(Border.NO_BORDER)
                    .setBorderLeft(new SolidBorder(borderWidth)));
            body.addCell(new Cell().add(new Paragraph(checkDto.getTime()))
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setBorder(Border.NO_BORDER)
                    .setBorderRight(new SolidBorder(borderWidth)));
            body.addCell(new Cell().add(new Paragraph("Type of transaction:"))
                    .setBorder(Border.NO_BORDER)
                    .setBorderLeft(new SolidBorder(borderWidth)));
            body.addCell(new Cell().add(new Paragraph(checkDto.getTransactionType()))
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setBorder(Border.NO_BORDER)
                    .setBorderRight(new SolidBorder(borderWidth)));
            if (checkDto.getSupplierBank() != null) {
                body.addCell(new Cell().add(new Paragraph("Supplier's bank:"))
                        .setBorder(Border.NO_BORDER)
                        .setBorderLeft(new SolidBorder(borderWidth)));
                body.addCell(new Cell().add(new Paragraph(checkDto.getSupplierBank()))
                        .setTextAlignment(TextAlignment.RIGHT)
                        .setBorder(Border.NO_BORDER)
                        .setBorderRight(new SolidBorder(borderWidth)));
            }
            if (checkDto.getConsumerBank() != null) {
                body.addCell(new Cell().add(new Paragraph("Consumer's bank:"))
                        .setBorder(Border.NO_BORDER)
                        .setBorderLeft(new SolidBorder(borderWidth)));
                body.addCell(new Cell().add(new Paragraph(checkDto.getConsumerBank()))
                        .setTextAlignment(TextAlignment.RIGHT)
                        .setBorder(Border.NO_BORDER)
                        .setBorderRight(new SolidBorder(borderWidth)));
            }
            if (checkDto.getSupplierAccount() != null) {
                body.addCell(new Cell().add(new Paragraph("Supplier's account:"))
                        .setBorder(Border.NO_BORDER)
                        .setBorderLeft(new SolidBorder(borderWidth)));
                body.addCell(new Cell().add(new Paragraph(checkDto.getSupplierAccount()))
                        .setTextAlignment(TextAlignment.RIGHT)
                        .setBorder(Border.NO_BORDER)
                        .setBorderRight(new SolidBorder(borderWidth)));
            }
            if (checkDto.getConsumerAccount() != null) {
                body.addCell(new Cell().add(new Paragraph("Consumer's account:"))
                        .setBorder(Border.NO_BORDER)
                        .setBorderLeft(new SolidBorder(borderWidth)));
                body.addCell(new Cell().add(new Paragraph(checkDto.getConsumerAccount()))
                        .setTextAlignment(TextAlignment.RIGHT)
                        .setBorder(Border.NO_BORDER)
                        .setBorderRight(new SolidBorder(borderWidth)));
            }
            body.addCell(new Cell().add(new Paragraph("Amount:"))
                    .setBorderTop(Border.NO_BORDER)
                    .setBorderRight(Border.NO_BORDER));
            body.addCell(new Cell().add(new Paragraph(checkDto.getAmount()))
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setBorderTop(Border.NO_BORDER)
                    .setBorderLeft(Border.NO_BORDER));
            document.add(body);

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void saveAccountRecord(AccountRecordDto accountRecordDto) {
    }

    @Override
    @Transactional
    public void saveStatement(StatementDto statementDto) {
    }
}
