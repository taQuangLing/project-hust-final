package hust.server.app.endpoint.rest;

import com.google.zxing.WriterException;
import hust.server.app.service.ResponseFactory;
import hust.server.domain.products.dto.request.QrCodeRequest;
import hust.server.domain.products.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class QrGenerateController {
    @Autowired
    private BranchService branchService;

    @PostMapping("/admin/v1/qr-code")
    public ResponseEntity<?> generateQrcode(@RequestBody QrCodeRequest request) throws IOException, WriterException {
        return ResponseFactory.response(branchService.generateQrCode(request));
    }
}
