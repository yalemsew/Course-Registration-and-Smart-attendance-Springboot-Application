package edu.miu.courseregistrationcore.integration.listener;

import edu.miu.courseregistrationcommon.dto.AttendanceRecordBarcodeDTO;
import edu.miu.courseregistrationcore.service.AttendanceRecordService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class BarcodeListener {
    private final AttendanceRecordService attendanceRecordService;

    @KafkaListener(topics = {"barcode"})
    public void listen(AttendanceRecordBarcodeDTO dto) {
        log.info("Received attendance record {}", dto);
        System.out.println("reciving barcode lisnerclass" + dto.toString());

        attendanceRecordService.createAttendancerecord(dto);
    }
}
