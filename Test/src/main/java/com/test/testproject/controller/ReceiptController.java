package com.test.testproject.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.test.testproject.req.ReceiptRequest;
import com.test.testproject.res.ReceiptResponse;
import com.test.testproject.service.ReceiptService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name="Receipt API")
public class ReceiptController {

	private final ReceiptService receiptService;

	@PostMapping(value="gen-receipt", produces = {MediaType.APPLICATION_JSON_VALUE})
	@Operation(summary = "Generate Receipt")
	public ResponseEntity<ReceiptResponse> genReceipt(@Valid @RequestBody ReceiptRequest request) {
		log.info("controller");
		var result = receiptService.genReceipt(request);
		return ResponseEntity.ok(result);
	}
}
