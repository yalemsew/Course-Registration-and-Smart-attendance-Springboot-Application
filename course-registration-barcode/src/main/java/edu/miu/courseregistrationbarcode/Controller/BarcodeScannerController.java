package edu.miu.courseregistrationbarcode.Controller;

import edu.miu.courseregistrationcommon.dto.AttendanceRecordBarcodeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/barcode")
public class BarcodeScannerController {

    @Autowired
    private KafkaTemplate<String, AttendanceRecordBarcodeDTO> barcodeKafkaTemplate;

    @PostMapping("/send")
    public void sendBarcode(@RequestBody AttendanceRecordBarcodeDTO attendanceRecordBarcodeDTO) {
        CompletableFuture<SendResult<String, AttendanceRecordBarcodeDTO>> future = barcodeKafkaTemplate.send("barcode", attendanceRecordBarcodeDTO);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println("Message send");
            } else {
                System.out.println("Unable to send message");
            }
        });
    }
}
