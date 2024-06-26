package hust.server.domain.products.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import hust.server.domain.products.dto.request.QrCodeRequest;
import hust.server.domain.products.dto.response.AdminBranchResponse;
import hust.server.domain.products.entity.Branch;
import hust.server.domain.products.repository.BranchRepository;
import hust.server.infrastructure.enums.MessageCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BranchService {
    @Autowired
    private BranchRepository branchRepository;

    public MessageCode generateQrCode(QrCodeRequest request) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(request.getUrl() + "/" + request.getBranchId(), BarcodeFormat.QR_CODE, request.getWidth(), request.getHeight());

        Path path = FileSystems.getDefault().getPath(request.getPath());
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
        return MessageCode.SUCCESS;
    }

    public List<AdminBranchResponse> getBranches(String userId) {
        List<Branch> branchList = branchRepository.getByCreatedBy(userId);
        return branchList.stream().map(Branch::toAdminBranchResponse).collect(Collectors.toList());
    }
}
