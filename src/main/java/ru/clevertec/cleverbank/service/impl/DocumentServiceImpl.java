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

import java.io.File;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Document Service to create PDF documents
 *
 * @author Konstantin Voytko
 */
@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private static final String dateFormat = "dd-MM-yyyy-HH-mm-ss";

    @Override
    @Transactional
    public void saveCheck(CheckDto checkDto) {
        String folderName = "check";
        String date = OffsetDateTime.now().format(DateTimeFormatter.ofPattern(dateFormat));
        String path = folderName + "/check-" + date + ".pdf";

        File receiptFolder = new File(folderName);
        if (!receiptFolder.exists()) {
            receiptFolder.mkdir();
        }

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

            Table bodyTable = new Table(twoColumnsWidth);
            bodyTable.addCell(new Cell().add(new Paragraph("Check:"))
                    .setBorder(Border.NO_BORDER)
                    .setBorderLeft(new SolidBorder(borderWidth)));
            bodyTable.addCell(new Cell().add(new Paragraph(checkDto.getId()))
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setBorder(Border.NO_BORDER)
                    .setBorderRight(new SolidBorder(borderWidth)));
            bodyTable.addCell(new Cell().add(new Paragraph(checkDto.getDate()))
                    .setBorder(Border.NO_BORDER)
                    .setBorderLeft(new SolidBorder(borderWidth)));
            bodyTable.addCell(new Cell().add(new Paragraph(checkDto.getTime()))
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setBorder(Border.NO_BORDER)
                    .setBorderRight(new SolidBorder(borderWidth)));
            bodyTable.addCell(new Cell().add(new Paragraph("Type of transaction:"))
                    .setBorder(Border.NO_BORDER)
                    .setBorderLeft(new SolidBorder(borderWidth)));
            bodyTable.addCell(new Cell().add(new Paragraph(checkDto.getTransactionType()))
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setBorder(Border.NO_BORDER)
                    .setBorderRight(new SolidBorder(borderWidth)));
            if (checkDto.getSupplierBank() != null) {
                bodyTable.addCell(new Cell().add(new Paragraph("Supplier's bank:"))
                        .setBorder(Border.NO_BORDER)
                        .setBorderLeft(new SolidBorder(borderWidth)));
                bodyTable.addCell(new Cell().add(new Paragraph(checkDto.getSupplierBank()))
                        .setTextAlignment(TextAlignment.RIGHT)
                        .setBorder(Border.NO_BORDER)
                        .setBorderRight(new SolidBorder(borderWidth)));
            }
            if (checkDto.getConsumerBank() != null) {
                bodyTable.addCell(new Cell().add(new Paragraph("Consumer's bank:"))
                        .setBorder(Border.NO_BORDER)
                        .setBorderLeft(new SolidBorder(borderWidth)));
                bodyTable.addCell(new Cell().add(new Paragraph(checkDto.getConsumerBank()))
                        .setTextAlignment(TextAlignment.RIGHT)
                        .setBorder(Border.NO_BORDER)
                        .setBorderRight(new SolidBorder(borderWidth)));
            }
            if (checkDto.getSupplierAccount() != null) {
                bodyTable.addCell(new Cell().add(new Paragraph("Supplier's account:"))
                        .setBorder(Border.NO_BORDER)
                        .setBorderLeft(new SolidBorder(borderWidth)));
                bodyTable.addCell(new Cell().add(new Paragraph(checkDto.getSupplierAccount()))
                        .setTextAlignment(TextAlignment.RIGHT)
                        .setBorder(Border.NO_BORDER)
                        .setBorderRight(new SolidBorder(borderWidth)));
            }
            if (checkDto.getConsumerAccount() != null) {
                bodyTable.addCell(new Cell().add(new Paragraph("Consumer's account:"))
                        .setBorder(Border.NO_BORDER)
                        .setBorderLeft(new SolidBorder(borderWidth)));
                bodyTable.addCell(new Cell().add(new Paragraph(checkDto.getConsumerAccount()))
                        .setTextAlignment(TextAlignment.RIGHT)
                        .setBorder(Border.NO_BORDER)
                        .setBorderRight(new SolidBorder(borderWidth)));
            }
            bodyTable.addCell(new Cell().add(new Paragraph("Amount:"))
                    .setBorderTop(Border.NO_BORDER)
                    .setBorderRight(Border.NO_BORDER));
            bodyTable.addCell(new Cell().add(new Paragraph(checkDto.getAmount()))
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setBorderTop(Border.NO_BORDER)
                    .setBorderLeft(Border.NO_BORDER));
            document.add(bodyTable);

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void saveAccountRecord(AccountRecordDto accountRecordDto) {
        String folderName = "account-record";
        String date = OffsetDateTime.now().format(DateTimeFormatter.ofPattern(dateFormat));
        String path = folderName + "/account-record-" + date + ".pdf";

        File receiptFolder = new File(folderName);
        if (!receiptFolder.exists()) {
            receiptFolder.mkdir();
        }

        try(PdfWriter pdfWriter = new PdfWriter(path)) {
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            pdfDocument.setDefaultPageSize(PageSize.A2);
            Document document = new Document(pdfDocument);

            float[] oneColumnsWidth = {400f};
            float[] twoColumnsWidth = {150f, 250f};
            float[] threeColumnsWidth = {60f, 260f, 80f};
            float borderWidth = 0.5f;

            Table headerTable = new Table(oneColumnsWidth);
            headerTable.addCell(new Cell().add(new Paragraph("Statement"))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBorder(Border.NO_BORDER));
            headerTable.addCell(new Cell().add(new Paragraph(accountRecordDto.getBank()))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBorder(Border.NO_BORDER));
            document.add(headerTable);

            Table bodyTable = new Table(twoColumnsWidth);
            bodyTable.addCell(new Cell().add(new Paragraph("Client"))
                    .setBorder(Border.NO_BORDER)
                    .setBorderRight(new SolidBorder(borderWidth)));
            bodyTable.addCell(new Cell().add(new Paragraph(accountRecordDto.getClient()))
                    .setBorder(Border.NO_BORDER));
            bodyTable.addCell(new Cell().add(new Paragraph("Account"))
                    .setBorder(Border.NO_BORDER)
                    .setBorderRight(new SolidBorder(borderWidth)));
            bodyTable.addCell(new Cell().add(new Paragraph(accountRecordDto.getAccount()))
                    .setBorder(Border.NO_BORDER));
            bodyTable.addCell(new Cell().add(new Paragraph("Opening date"))
                    .setBorder(Border.NO_BORDER)
                    .setBorderRight(new SolidBorder(borderWidth)));
            bodyTable.addCell(new Cell().add(new Paragraph(accountRecordDto.getAccountCreateDate()))
                    .setBorder(Border.NO_BORDER));
            bodyTable.addCell(new Cell().add(new Paragraph("Period"))
                    .setBorder(Border.NO_BORDER)
                    .setBorderRight(new SolidBorder(borderWidth)));
            bodyTable.addCell(new Cell().add(new Paragraph(accountRecordDto.getPeriod()))
                    .setBorder(Border.NO_BORDER));
            bodyTable.addCell(new Cell().add(new Paragraph("Date and time of formation"))
                    .setBorder(Border.NO_BORDER)
                    .setBorderRight(new SolidBorder(borderWidth)));
            bodyTable.addCell(new Cell().add(new Paragraph(accountRecordDto.getCreateDateTime()))
                    .setBorder(Border.NO_BORDER));
            bodyTable.addCell(new Cell().add(new Paragraph("Balance"))
                    .setBorder(Border.NO_BORDER)
                    .setBorderRight(new SolidBorder(borderWidth)));
            bodyTable.addCell(new Cell().add(new Paragraph(accountRecordDto.getBalance()))
                    .setBorder(Border.NO_BORDER));
            document.add(bodyTable);

            Table footerTable = new Table(threeColumnsWidth);
            footerTable.addCell(new Cell().add(new Paragraph("Date"))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBorderTop(Border.NO_BORDER)
                    .setBorderLeft(Border.NO_BORDER));
            footerTable.addCell(new Cell().add(new Paragraph("Description"))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBorderTop(Border.NO_BORDER));
            footerTable.addCell(new Cell().add(new Paragraph("Amount"))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBorderTop(Border.NO_BORDER)
                    .setBorderRight(Border.NO_BORDER));
            accountRecordDto.getTransactions().forEach(transaction -> {
                footerTable.addCell(new Cell().add(new Paragraph(transaction.getDate()))
                        .setBorder(Border.NO_BORDER)
                        .setBorderRight(new SolidBorder(borderWidth)));
                footerTable.addCell(new Cell().add(new Paragraph(transaction.getType()))
                        .setBorder(Border.NO_BORDER)
                        .setBorderRight(new SolidBorder(borderWidth)));
                footerTable.addCell(new Cell().add(new Paragraph(transaction.getAmount()))
                        .setBorder(Border.NO_BORDER));
            });
            document.add(footerTable);

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void saveStatement(StatementDto statementDto) {
        String folderName = "statement-money";
        String date = OffsetDateTime.now().format(DateTimeFormatter.ofPattern(dateFormat));
        String path = folderName + "/statement-money-" + date + ".pdf";

        File receiptFolder = new File(folderName);
        if (!receiptFolder.exists()) {
            receiptFolder.mkdir();
        }

        try(PdfWriter pdfWriter = new PdfWriter(path)) {
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            pdfDocument.setDefaultPageSize(PageSize.A2);
            Document document = new Document(pdfDocument);

            float[] oneColumnsWidth = {400f};
            float[] twoColumnsWidth = {150f, 250f};
            float[] twoShortColumnsWidth = {150f, 150f};
            float borderWidth = 0.5f;

            Table headerTable = new Table(oneColumnsWidth);
            headerTable.addCell(new Cell().add(new Paragraph("Money statement"))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBorder(Border.NO_BORDER));
            headerTable.addCell(new Cell().add(new Paragraph(statementDto.getBank()))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBorder(Border.NO_BORDER));
            document.add(headerTable);

            Table bodyTable = new Table(twoColumnsWidth);
            bodyTable.addCell(new Cell().add(new Paragraph("Client"))
                    .setBorder(Border.NO_BORDER)
                    .setBorderRight(new SolidBorder(borderWidth)));
            bodyTable.addCell(new Cell().add(new Paragraph(statementDto.getClient()))
                    .setBorder(Border.NO_BORDER));
            bodyTable.addCell(new Cell().add(new Paragraph("Account"))
                    .setBorder(Border.NO_BORDER)
                    .setBorderRight(new SolidBorder(borderWidth)));
            bodyTable.addCell(new Cell().add(new Paragraph(statementDto.getAccount()))
                    .setBorder(Border.NO_BORDER));
            bodyTable.addCell(new Cell().add(new Paragraph("Opening date"))
                    .setBorder(Border.NO_BORDER)
                    .setBorderRight(new SolidBorder(borderWidth)));
            bodyTable.addCell(new Cell().add(new Paragraph(statementDto.getAccountCreateDate()))
                    .setBorder(Border.NO_BORDER));
            bodyTable.addCell(new Cell().add(new Paragraph("Period"))
                    .setBorder(Border.NO_BORDER)
                    .setBorderRight(new SolidBorder(borderWidth)));
            bodyTable.addCell(new Cell().add(new Paragraph(statementDto.getPeriod()))
                    .setBorder(Border.NO_BORDER));
            bodyTable.addCell(new Cell().add(new Paragraph("Date and time of formation"))
                    .setBorder(Border.NO_BORDER)
                    .setBorderRight(new SolidBorder(borderWidth)));
            bodyTable.addCell(new Cell().add(new Paragraph(statementDto.getCreateDateTime()))
                    .setBorder(Border.NO_BORDER));
            bodyTable.addCell(new Cell().add(new Paragraph("Balance"))
                    .setBorder(Border.NO_BORDER)
                    .setBorderRight(new SolidBorder(borderWidth)));
            bodyTable.addCell(new Cell().add(new Paragraph(statementDto.getBalance()))
                    .setBorder(Border.NO_BORDER));
            document.add(bodyTable);

            Table footerTable = new Table(twoShortColumnsWidth);
            footerTable.addCell(new Cell().add(new Paragraph("Replenishment"))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBorderTop(Border.NO_BORDER)
                    .setBorderLeft(Border.NO_BORDER));
            footerTable.addCell(new Cell().add(new Paragraph("Withdrawal"))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBorderTop(Border.NO_BORDER)
                    .setBorderRight(Border.NO_BORDER));
            footerTable.addCell(new Cell().add(new Paragraph(statementDto.getReplenishment()))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBorderLeft(Border.NO_BORDER)
                    .setBorderBottom(Border.NO_BORDER));
            footerTable.addCell(new Cell().add(new Paragraph(statementDto.getWithdrawal()))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBorderRight(Border.NO_BORDER)
                    .setBorderBottom(Border.NO_BORDER));
            document.add(footerTable);

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
