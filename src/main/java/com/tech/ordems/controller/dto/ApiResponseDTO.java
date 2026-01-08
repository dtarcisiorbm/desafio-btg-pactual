package com.tech.ordems.controller.dto;

import java.util.List;

public record ApiResponseDTO<T>(List<T> data, PaginationResponseDTO paginationResponse) {
}
