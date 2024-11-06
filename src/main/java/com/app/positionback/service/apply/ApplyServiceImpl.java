package com.app.positionback.service.apply;

import com.app.positionback.domain.apply.ApplyListDTO;
import com.app.positionback.domain.apply.ApplyVO;
import com.app.positionback.domain.file.CertificationFileDTO;
import com.app.positionback.domain.file.FileDTO;
import com.app.positionback.repository.apply.ApplyDAO;
import com.app.positionback.repository.file.CertificationFileDAO;
import com.app.positionback.repository.file.FileDAO;
import com.app.positionback.utill.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Primary
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ApplyServiceImpl implements ApplyService{
    private final ApplyDAO applyDAO;
    private final FileDAO fileDAO;
    private final CertificationFileDAO certificationFileDAO;


    @Override
    public ApplyListDTO getApplyByCorporationId(int page, Pagination pagination, Long corporationId) {
        ApplyListDTO applyListDTO = new ApplyListDTO();
        pagination.setPage(page);
        pagination.setTotal(applyDAO.getTotal(pagination,corporationId));
        pagination.progress();
        applyListDTO.setPagination(pagination);
        applyListDTO.setApplies(applyDAO.findApplyByCorporationId(pagination,corporationId));

        Pagination ongoingPagination = new Pagination();
        ongoingPagination.setStatus("ongoing");
        int ongoingCount = applyDAO.getTotal(ongoingPagination,corporationId);

        Pagination closedPagination = new Pagination();
        closedPagination.setStatus("closed");
        int closedCount = applyDAO.getTotal(closedPagination,corporationId);

        Pagination postitionPagination = new Pagination();
        postitionPagination.setStatus("position");
        int paginationCount = applyDAO.getTotal(postitionPagination,corporationId);

        Pagination reviewPagination = new Pagination();
        reviewPagination.setStatus("review");
        int reviewCount = applyDAO.getTotal(reviewPagination,corporationId);

        pagination.setOngoingCount(ongoingCount);
        pagination.setClosedCount(closedCount);
        pagination.setPositionCount(paginationCount);
        pagination.setReviewCount(reviewCount);


        return applyListDTO;
    }

    @Override
    public int getTotal(Pagination pagination, Long corporationId) {
        return applyDAO.getTotal(pagination,corporationId);
    }

    @Override
    public void setApplyStatus(ApplyVO applyVO) {
        applyDAO.updateApplyStatus(applyVO);
    }

    @Override
    public void uploadCertificationFile(String uuid, String path, MultipartFile file, Long applyId) throws IOException {
        CertificationFileDTO certificationFileDTO = new CertificationFileDTO();
        FileDTO fileDTO = new FileDTO();
        String fileSize = String.format("%.2f", file.getSize() / 1024.0 / 1024.0);

        fileDTO.setFilePath(path);
        fileDTO.setFileName(uuid + "_" + file.getOriginalFilename());
        fileDTO.setFileSize(fileSize);
        fileDAO.save(fileDTO.toVO());

        certificationFileDTO.setFileId(fileDAO.findLastInsertId());
        certificationFileDTO.setApplyId(applyId);

        certificationFileDAO.save(certificationFileDTO.toVO());
    }
}
