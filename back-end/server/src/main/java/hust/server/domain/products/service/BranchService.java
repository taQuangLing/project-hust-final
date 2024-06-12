package hust.server.domain.products.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import hust.server.domain.products.dto.request.QrCodeRequest;
import hust.server.infrastructure.enums.MessageCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;

@Service
public class BranchService {
    @Autowired
    private BranchService branchService;

    public MessageCode generateQrCode(QrCodeRequest request) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(request.getUrl() + "/" + request.getBranchId(), BarcodeFormat.QR_CODE, request.getWidth(), request.getHeight());

        Path path = FileSystems.getDefault().getPath(request.getPath());
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
        return MessageCode.SUCCESS;
    }
}
