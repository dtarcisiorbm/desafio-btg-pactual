package com.tech.ordems.controller.dto;

public record PaginationResponseDTO(Integer page,Integer pageSize ,Integer totalElements,Integer totalPages) {
}
