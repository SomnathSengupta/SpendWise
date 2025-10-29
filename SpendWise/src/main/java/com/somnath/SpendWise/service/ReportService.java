package com.somnath.SpendWise.service;

import java.io.ByteArrayInputStream;

public interface ReportService {
    ByteArrayInputStream generateUserReport(Long userId);
}
