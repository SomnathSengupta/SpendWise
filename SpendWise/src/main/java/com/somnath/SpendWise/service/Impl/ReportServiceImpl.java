package com.somnath.SpendWise.service.Impl;

import com.somnath.SpendWise.entity.Expense;
import com.somnath.SpendWise.entity.User;
import com.somnath.SpendWise.repository.ExpenseRepository;
import com.somnath.SpendWise.repository.UserRepository;
import com.somnath.SpendWise.service.ReportService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartUtils;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    private final UserRepository userRepository;
    private final ExpenseRepository expenseRepository;

    public ReportServiceImpl(UserRepository userRepository, ExpenseRepository expenseRepository) {
        this.userRepository = userRepository;
        this.expenseRepository = expenseRepository;
    }

    @Override
    public ByteArrayInputStream generateUserReport(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        // Get only current month and year
        LocalDateTime now = LocalDateTime.now();
        int currentMonth = now.getMonthValue();
        int currentYear = now.getYear();

        // Filter expenses for current month
        List<Expense> allExpenses = expenseRepository.findByUserId(userId);
        List<Expense> expenses = allExpenses.stream()
                .filter(e -> e.getDate() != null &&
                        e.getDate().getMonthValue() == currentMonth &&
                        e.getDate().getYear() == currentYear)
                .collect(Collectors.toList());

        // Calculate total expenses and remaining
        BigDecimal totalExpense = expenses.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal remaining = user.getMonthlyInHand();

        try {
            // Create PDF
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();

            // Title
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("Monthly Expense Report (" + now.getMonth() + " " + currentYear + ")", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE);

            // User info
            document.add(new Paragraph("Username: " + user.getUsername()));
            document.add(new Paragraph("Email: " + user.getEmail()));
            document.add(new Paragraph("Month: " + now.getMonth() + " " + currentYear));
            document.add(new Paragraph("Monthly In-Hand: ₹" + user.getMonthlyInHand()));
            document.add(new Paragraph("Total Expenses (This Month): ₹" + totalExpense));
            document.add(new Paragraph("Remaining Balance: ₹" + remaining));
            document.add(Chunk.NEWLINE);

            // Pie chart (only current month data)
            if (!expenses.isEmpty()) {
                DefaultPieDataset dataset = new DefaultPieDataset();
                for (Expense e : expenses) {
                    dataset.setValue(e.getPurpose(), e.getAmount());
                }

                JFreeChart chart = ChartFactory.createPieChart(
                        "Expense Distribution (" + now.getMonth() + ")", dataset, true, true, false);

                File chartFile = File.createTempFile("chart", ".png");
                ChartUtils.saveChartAsPNG(chartFile, chart, 500, 400);
                Image chartImg = Image.getInstance(chartFile.getAbsolutePath());
                chartImg.scaleToFit(500, 400);
                document.add(chartImg);
            } else {
                document.add(new Paragraph("No expenses found for this month."));
            }

            document.close();
            return new ByteArrayInputStream(out.toByteArray());

        } catch (Exception e) {
            throw new RuntimeException("Error generating report: " + e.getMessage());
        }
    }
}
